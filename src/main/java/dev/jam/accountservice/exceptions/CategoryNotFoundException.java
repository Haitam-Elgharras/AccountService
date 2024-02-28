package dev.jam.accountservice.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
