package com.desafioconexa.miguelm.service.exceptions;

public class FilmAlreadyExistsInDatabase extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public FilmAlreadyExistsInDatabase(String msg){super(msg);}
}
