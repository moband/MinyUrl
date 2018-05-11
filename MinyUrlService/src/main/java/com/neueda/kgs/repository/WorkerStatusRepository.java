package com.neueda.kgs.repository;

import com.neueda.kgs.model.WorkerStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerStatusRepository extends MongoRepository<WorkerStatus,String> {
    WorkerStatus findByWorkerId(String id);

  @Query(value = "{ 'workerId' : ?0 }", fields = "{ 'allocatedRanges.isExhausted' : false }")
  WorkerStatus findByWorkerIdAndAllocatedRangesIsExhausted(String workerId, boolean isExhausted);
}
