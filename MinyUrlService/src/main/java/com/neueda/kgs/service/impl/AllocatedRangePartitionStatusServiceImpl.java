package com.neueda.kgs.service.impl;

import com.neueda.kgs.aspect.Retry;
import com.neueda.kgs.model.AllocatedRangePartitionStatus;
import com.neueda.kgs.repository.AllocatedRangePartitionStatusRepository;
import com.neueda.kgs.service.AllocatedRangePartitionStatusService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllocatedRangePartitionStatusServiceImpl implements AllocatedRangePartitionStatusService {

    private AllocatedRangePartitionStatusRepository repository;

    public AllocatedRangePartitionStatusServiceImpl(AllocatedRangePartitionStatusRepository repository) {
        this.repository = repository;
    }


    @Retry(times = 10, on = org.springframework.dao.OptimisticLockingFailureException.class)
    @Override
    public Integer allocateRangePartition() {
        AllocatedRangePartitionStatus allocatedRangePartitionStatus = Optional.ofNullable(repository.findAll()).filter(l -> l.size()>0).map(c -> c.get(0) ).orElseGet(AllocatedRangePartitionStatus::new);
        allocatedRangePartitionStatus.incrementAllocatedPartitionNumber();
        repository.save(allocatedRangePartitionStatus);
        return allocatedRangePartitionStatus.getAllocatedPartitionNumber();
    }
}
