package com.mymemefolder.mmfgateway.utils;

public class ActionResult {
    private String errorMessage;

    private ActionResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return errorMessage == null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static ActionResult success() {
        return new ActionResult(null);
    }

    public static ActionResult failure(String errorMessage) {
        return new ActionResult(errorMessage);
    }
}
