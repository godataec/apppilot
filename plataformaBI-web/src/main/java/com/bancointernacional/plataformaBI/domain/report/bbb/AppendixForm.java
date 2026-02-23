package com.bancointernacional.plataformaBI.domain.report.bbb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * Model class for Anexo No. 3 - Question 12 of PI Proposal Form
 * Contains all variables needed to populate the Thymeleaf template
 */
@Setter
@Getter
public class AppendixForm implements Serializable {

    @JsonProperty("Anexo3pis1p12")
    private String Anexo3pis1p12;

    @JsonProperty("Anexo3pis1p12normal")
    private String Anexo3pis1p12normal;

    @JsonProperty("Anexo3pis2p12r")
    private String Anexo3pis2p12r;

    @JsonProperty("Anexo3pis1p12a1")
    private String Anexo3pis1p12a1;

    @JsonProperty("Anexo3pis1p12a2")
    private String Anexo3pis1p12a2;

    @JsonProperty("Anexo3pis1p12a3")
    private String Anexo3pis1p12a3;

    @JsonProperty("Anexo3pis1p12a4")
    private String Anexo3pis1p12a4;

    @JsonProperty("Anexo3pis1p12a6")
    private String Anexo3pis1p12a6;

    @JsonProperty("Anexo3pis1p12a7")
    private String Anexo3pis1p12a7;

    @JsonProperty("Anexo3pis1p12a8")
    private String Anexo3pis1p12a8;

    @JsonProperty("Anexo3pis1p12a9")
    private String Anexo3pis1p12a9;

    @JsonProperty("Anexo3pis1p12a10a")
    private String Anexo3pis1p12a10a;

    @JsonProperty("Anexo3pis1p12a10g")
    private String Anexo3pis1p12a10g;

    @JsonProperty("Anexo3pis1p12b")
    private String Anexo3pis1p12b;

    @JsonProperty("Anexo3pis1p12bnormal")
    private String Anexo3pis1p12bnormal;

    @JsonProperty("Anexo3pis1p12c")
    private String Anexo3pis1p12c;

    @JsonProperty("Anexo3pis1p12cnormal")
    private String Anexo3pis1p12cnormal;

    @JsonProperty("Anexo3pis1p12d")
    private String Anexo3pis1p12d;

    @JsonProperty("Anexo3pis1p12e")
    private String Anexo3pis1p12e;

    @JsonProperty("Anexo3pis1p12f")
    private String Anexo3pis1p12f;

    @JsonProperty("Anexo3pis1p12g")
    private String Anexo3pis1p12g;

    @JsonProperty("Anexo3pis1p12g2")
    private String Anexo3pis1p12g2;

    @JsonProperty("tables")
    private Map<String, TableView> tables;
}