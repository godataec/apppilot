package com.bancointernacional.plataformaBI.enums;

public enum AnswerTypes {

    NORMAL("NORMAL"),
    DES("DES"),
    SUB("SUB"),
    OPTIONAL("OPTIONAL"),
    SELECTOR("SELECTOR"),
    OPTIONALNORMAL("OPTIONAL-NORMAL"),
    MULTIPLE("MULTIPLE"),
    SUBOPTIONAL("SUB-OPTIONAL"),
    SELECTOREXCEL("SELECTOR-EXCEL"),
    SELECTORNORMAL("SELECTOR-NORMAL"),
    TABLE("TABLE");


    private final String typeword;


    AnswerTypes(String typeword) {
        this.typeword = typeword;
    }

    public String getWord(){
        return typeword;
    }
}
