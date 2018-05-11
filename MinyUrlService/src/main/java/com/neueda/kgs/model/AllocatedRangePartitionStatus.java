package com.neueda.kgs.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "range_partition_status")
public class AllocatedRangePartitionStatus {
    @Id
    private String id;
    private Integer allocatedPartitionNumber=0;
    @Version
    private Integer version;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAllocatedPartitionNumber() {
        return allocatedPartitionNumber;
    }

    public void setAllocatedPartitionNumber(Integer allocatedPartitionNumber) {
        this.allocatedPartitionNumber = allocatedPartitionNumber;
    }
    public void incrementAllocatedPartitionNumber()
    {
        ++this.allocatedPartitionNumber;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
