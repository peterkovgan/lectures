package com.monster;

/**
 * Thrown when input parameters for the Game are invalid
 */
class InvalidParameterException extends RuntimeException {
    InvalidParameterException(final String message) {
        super(message);
    }
}
