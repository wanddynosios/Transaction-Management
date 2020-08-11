package model.basic;

public class CannotHotswapException extends RuntimeException {
    public CannotHotswapException(String message){
        super(message);
    }
}
