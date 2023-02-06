package org.ajakz.elker.exception;

public class ElkerNotFoundException extends Exception {

    public ElkerNotFoundException() {
        super("Requested operation for nonexistent Elker", null, true, false);
    }
}
