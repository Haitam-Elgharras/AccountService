package dev.jam.accountservice.exceptions;

public class UserAccessDeniedException extends RuntimeException {
    public UserAccessDeniedException(String accessDenied) {
        super(accessDenied);
    }
}
