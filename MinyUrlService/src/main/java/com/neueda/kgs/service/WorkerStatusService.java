package com.neueda.kgs.service;

import com.neueda.kgs.exception.WorkerNotFoundException;
import com.neueda.kgs.model.WorkerStatus;

public interface WorkerStatusService {

    Long getNewKey(String workerId);
}
