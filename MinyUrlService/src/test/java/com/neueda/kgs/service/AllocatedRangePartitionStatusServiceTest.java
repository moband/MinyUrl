package com.neueda.kgs.service;

import com.neueda.kgs.model.AllocatedRangePartitionStatus;
import com.neueda.kgs.repository.AllocatedRangePartitionStatusRepository;
import com.neueda.kgs.service.impl.AllocatedRangePartitionStatusServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllocatedRangePartitionStatusServiceTest {

    @InjectMocks
    private AllocatedRangePartitionStatusServiceImpl service;

    @Mock
    private AllocatedRangePartitionStatusRepository repository;


    @Before
    public void contextLoads() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void should_rangeCounterShouldIncreased_when_allocationRangeRequest() {

        //Given
        AllocatedRangePartitionStatus allocatedRangePartitionStatus = new AllocatedRangePartitionStatus();
        allocatedRangePartitionStatus.setAllocatedPartitionNumber(1);
        Integer lastAllocatedPartition = allocatedRangePartitionStatus.getAllocatedPartitionNumber();
        when(repository.findAll()).thenReturn(Arrays.asList(allocatedRangePartitionStatus));

        //When
        Integer newRangePartition = service.allocateRangePartition();

        //Then
        assertThat(newRangePartition).isNotNull();
        assertThat(newRangePartition).isEqualTo(lastAllocatedPartition + 1);
    }


    @Test
    public void should_rangeShouldBeCreated_when_noRangePartitionAlreadyInDB() {

        //Given

        //When
        Integer newRangePartition = service.allocateRangePartition();

        //Then
        assertThat(newRangePartition).isNotNull();
        assertThat(newRangePartition).isEqualTo(1);
    }

}
