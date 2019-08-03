package ru.otus.hw.atmemul.atm.exception;

public class ImpossibleWithdrawAmountRequestedException extends Exception {

    private static final String MSG = "Невозможно выдать запрошенную сумму";

    public ImpossibleWithdrawAmountRequestedException() {
        super(MSG);
    }

    public ImpossibleWithdrawAmountRequestedException(String message) {
        super(message);
    }

    public ImpossibleWithdrawAmountRequestedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImpossibleWithdrawAmountRequestedException(Throwable cause) {
        super(cause);
    }

    public ImpossibleWithdrawAmountRequestedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
