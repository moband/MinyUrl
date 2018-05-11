package com.neueda.kgs.service;


import com.neueda.kgs.model.embedded.AllocatedCounter;
import com.neueda.kgs.model.WorkerStatus;
import com.neueda.kgs.repository.WorkerStatusRepository;
import com.neueda.kgs.service.impl.WorkerStatusServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkerStatusServiceTest {

    @InjectMocks
    private WorkerStatusServiceImpl service;

    @Mock
    private WorkerStatusRepository workerStatusRepository;
    @Mock
    private AllocatedRangePartitionStatusService allocatedRangePartitionStatusService;

    @Before
    public void contextLoads() {
        MockitoAnnotations.initMocks(this);
    }

    private WorkerStatus initEastingScenarioModel() {
        AllocatedCounter allocatedCounter = new AllocatedCounter();
        allocatedCounter.setExhausted(false);
        allocatedCounter.setCounter(19999998L);
        allocatedCounter.setRangeNumber(1);

        WorkerStatus workerStatus = new WorkerStatus("33cc6eebd387");
        workerStatus.getAllocatedRanges().add(allocatedCounter);

        return workerStatus;
    }

    private WorkerStatus initNewRangeAllocationScenarioModel() {
        AllocatedCounter allocatedCounter = new AllocatedCounter();
        allocatedCounter.setExhausted(true);
        allocatedCounter.setCounter(19999999L);
        allocatedCounter.setRangeNumber(1);

        WorkerStatus workerStatus = new WorkerStatus("33cc6eebd387");
        workerStatus.getAllocatedRanges().add(allocatedCounter);

        return workerStatus;
    }

    @Test
    public void should_generateDecimalID_when_urlIsValidAndDoesNotExist() {

        //Given
        String workerId = "33cc6eebd387";
        Integer allocatedRangePartition = 1;
        when(allocatedRangePartitionStatusService.allocateRangePartition()).thenReturn(allocatedRangePartition);

        //When
        Long allocatedDecimalID = service.getNewKey(workerId);

        //Then
        assertThat(allocatedDecimalID).isEqualTo(1L);
    }

    @Test
    public void should_ExhaustAFilledRange_when_theLastAllocationRequest() {

        //Given
        WorkerStatus workerStatus = initEastingScenarioModel();
        when(workerStatusRepository.findByWorkerId(workerStatus.getWorkerId())).thenReturn(workerStatus);

        //When
        Long allocatedDecimalID = service.getNewKey(workerStatus.getWorkerId());

        //Then
        assertThat(allocatedDecimalID).isNotNull();
        assertThat(workerStatus.getAllocatedRanges().get(0).getExhausted()).isEqualTo(true);
    }

    @Test
    public void should_AllocateNewRange_when_AllOtherRangesAlreadyExhauted() {

        //Given
        WorkerStatus workerStatus = initNewRangeAllocationScenarioModel();
        Integer alreadyAllocatedRangeSize = workerStatus.getAllocatedRanges().size();
        Integer allocatedRangePartition = 2;
        when(workerStatusRepository.findByWorkerId(workerStatus.getWorkerId())).thenReturn(workerStatus);
        when(allocatedRangePartitionStatusService.allocateRangePartition()).thenReturn(allocatedRangePartition);

        //When
        Long allocatedDecimalID = service.getNewKey(workerStatus.getWorkerId());

        //Then
        assertThat(allocatedDecimalID).isNotNull();
        assertThat(workerStatus.getAllocatedRanges().size()).isEqualTo(alreadyAllocatedRangeSize + 1);
    }


}
