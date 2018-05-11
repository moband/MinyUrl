package com.neueda.kgs.exception;

public class InvalidAddressException extends Exception{

    public InvalidAddressException() { super();
    }

    public InvalidAddressException(String message) {
        super(message);
    }
}
