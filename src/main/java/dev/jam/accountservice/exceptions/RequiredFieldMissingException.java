package dev.jam.accountservice.exceptions;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String requiredFieldsAreMissing) {
        super(requiredFieldsAreMissing);
    }
}
