package com.janvi.exception;

public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String code){
        super("Short URL not Found: "+code);
    }
}
