package com.neueda.kgs.exception;

public class WorkerNotFoundException  extends Exception{

    public WorkerNotFoundException() { super();
    }

    public WorkerNotFoundException(String message) {
        super(message);
    }
}
