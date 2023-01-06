package com.desafioconexa.miguelm.service.exceptions;

public class FeignClientException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public FeignClientException(String msg){super(msg);}
}
