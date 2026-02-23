package com.bancointernacional.plataformaBI.enums;

public enum ActionTypes {
    REOPEN("REOPEN"),
    CLOSED("CLOSED");

    private final String processAction;


    ActionTypes(String processAction) {
        this.processAction = processAction;
    }

    public String getWord(){
        return processAction;
    }
}
