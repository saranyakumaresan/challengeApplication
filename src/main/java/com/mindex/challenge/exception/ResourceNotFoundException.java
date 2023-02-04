package com.mindex.challenge.exception;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String notFoundMsg){
        super(notFoundMsg);
    }

}
