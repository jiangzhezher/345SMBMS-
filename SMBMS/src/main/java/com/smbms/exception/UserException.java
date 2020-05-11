package com.smbms.exception;

/**
 * @author jz
 * @date 2020/4/28 - 11:21
 */
public class UserException extends Exception{
    private String message;

    public UserException(String message){
        super(message);
        this.message = message;
    }

    public UserException(){
        super();
    }

    @Override
    public String getMessage(){
        return message;
    }
}
