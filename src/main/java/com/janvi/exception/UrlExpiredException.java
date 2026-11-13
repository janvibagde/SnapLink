package com.janvi.exception;

public class UrlExpiredException extends  RuntimeException{
    public UrlExpiredException(String code){
        super ("Short URL expired or disabled: "+code);
    }
}
