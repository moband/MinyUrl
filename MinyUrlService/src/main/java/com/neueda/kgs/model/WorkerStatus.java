package com.neueda.kgs.model;

import com.neueda.kgs.model.embedded.AllocatedCounter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "worker_status")
public class WorkerStatus {

    @Id
    private String id;
    private String workerId;

    private List<AllocatedCounter> allocatedRanges = new ArrayList<>();

    public WorkerStatus() {
    }

    public WorkerStatus(String workerId) {
        this.workerId = workerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public List<AllocatedCounter> getAllocatedRanges() {
        return allocatedRanges;
    }

    public void setAllocatedRanges(List<AllocatedCounter> allocatedRanges) {
        this.allocatedRanges = allocatedRanges;
    }
}
