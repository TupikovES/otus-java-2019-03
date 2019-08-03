package ru.otus.hw.atmemul.atm.exception;

import ru.otus.hw.atmemul.atm.Par;

public class IllegalParException extends Exception {

    private static final String MSG = "Банокомат не принимает купюры номиналом: ";

    public IllegalParException() {
    }

    public IllegalParException(Par par) {
        super(MSG + par.getValue());
    }

    public IllegalParException(String message) {
        super(message);
    }

    public IllegalParException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParException(Throwable cause) {
        super(cause);
    }

    public IllegalParException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
