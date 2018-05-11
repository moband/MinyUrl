package com.neueda.kgs.service.impl;

import com.neueda.kgs.aspect.Retry;
import com.neueda.kgs.model.embedded.AllocatedCounter;
import com.neueda.kgs.model.WorkerStatus;
import com.neueda.kgs.repository.WorkerStatusRepository;
import com.neueda.kgs.service.AllocatedRangePartitionStatusService;
import com.neueda.kgs.service.WorkerStatusService;
import com.neueda.kgs.util.GlobalConstants;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkerStatusServiceImpl implements WorkerStatusService {
    private WorkerStatusRepository workerStatusRepository;
    private AllocatedRangePartitionStatusService allocatedRangePartitionStatusService;


    public WorkerStatusServiceImpl(WorkerStatusRepository workerStatusRepository, AllocatedRangePartitionStatusService allocatedRangePartitionStatusService) {
        this.workerStatusRepository = workerStatusRepository;
        this.allocatedRangePartitionStatusService = allocatedRangePartitionStatusService;
    }

    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    @Override
    public Long getNewKey(String workerId) {
        WorkerStatus workerStatus = Optional.ofNullable(workerStatusRepository.findByWorkerId(workerId))
                .map(c -> c)
                .orElseGet(() -> new WorkerStatus(workerId));

        Long key = workerStatus.getAllocatedRanges().stream().filter(a -> a.getExhausted() == false)
                .findFirst()
                .map(s -> {
                    Long key2 = workerStatus.getAllocatedRanges().stream().filter(a -> a.getExhausted() == false).mapToLong(a -> {
                        a.incrementCounter();
                        a.setExhausted(a.getCounter() == ((a.getRangeNumber() + 1) * GlobalConstants.KeyNumbersInPartitions - 1));
                        return a.getCounter();
                    }).findFirst().getAsLong();
                    return key2;
                })
                .orElseGet(() -> {

                    Integer newRangePartitionNumber = allocatedRangePartitionStatusService.allocateRangePartition();

                    AllocatedCounter allocatedCounter = new AllocatedCounter();
                    allocatedCounter.setRangeNumber(newRangePartitionNumber);
                    allocatedCounter.setCounter((long) ((newRangePartitionNumber - 1) * GlobalConstants.KeyNumbersInPartitions + 1));

                    workerStatus.getAllocatedRanges().add(allocatedCounter);
                    Long key2 = allocatedCounter.getCounter();
                    return key2;
                });

        workerStatusRepository.save(workerStatus);
        return key;

    }
}
