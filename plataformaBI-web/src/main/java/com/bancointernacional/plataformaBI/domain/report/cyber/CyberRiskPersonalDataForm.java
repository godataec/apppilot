package com.bancointernacional.plataformaBI.domain.report.cyber;

import java.io.Serializable;

/**
 * Model class for CyberRisk Datos Personales Procesados Internacional form.
 * Contains all fields needed to populate the Thymeleaf template.
 */
public class CyberRiskPersonalDataForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Información general
    private String cyberRiskA; // RAZÓN SOCIAL DEL ASEGURADO
    private String cyberRiskB; // NÚMERO TOTAL DE REGISTROS MANEJADO POR EL ASEGURADO
    
    // Registros por región
    private String cyberRiskC; // Europa
    private String cyberRiskD; // EU / Canadá
    private String cyberRiskE; // Resto del Mundo
    
    // Información comercial y marketing
    private String cyberRiskF; // SI
    private String cyberRiskFnormal; //Respuesta que se muestra en PDF
    private String cyberRiskG; // NO
    private String cyberRiskH; // NUMERO DE REGISTROS
    
    // Información financiera o de Tarjetas de Crédito
    private String cyberRiskI; // SI
    private String cyberRiskInormal; //Respuesta que se muestra en PDF
    private String cyberRiskJ; // NO
    private String cyberRiskK; // NUMERO DE REGISTROS
    
    // Información de salud
    private String cyberRiskL; // SI
    private String cyberRiskLnormal; //Respuesta que se muestra en PDF
    private String cyberRiskM; // NO
    private String cyberRiskN; // NUMERO DE REGISTROS
    
    // Otros
    private String cyberRiskO; // Otros, por favor especificar
    
    // Personas Naturales
    private String cyberRiskP; // Créditos
    private String cyberRiskQ; // Tarjeta de Crédito
    private String cyberRiskR; // Información de Salud
    
    // Persona Jurídica
    private String cyberRiskS; // Créditos
    
    // Total
    private String cyberRiskT; // TOTAL
    
    // Default constructor
    public CyberRiskPersonalDataForm() {
    }
    
    // Getters and Setters
    public String getCyberRiskA() {
        return cyberRiskA;
    }
    
    public void setCyberRiskA(String cyberRiskA) {
        this.cyberRiskA = cyberRiskA;
    }
    
    public String getCyberRiskB() {
        return cyberRiskB;
    }
    
    public void setCyberRiskB(String cyberRiskB) {
        this.cyberRiskB = cyberRiskB;
    }
    
    public String getCyberRiskC() {
        return cyberRiskC;
    }
    
    public void setCyberRiskC(String cyberRiskC) {
        this.cyberRiskC = cyberRiskC;
    }
    
    public String getCyberRiskD() {
        return cyberRiskD;
    }
    
    public void setCyberRiskD(String cyberRiskD) {
        this.cyberRiskD = cyberRiskD;
    }
    
    public String getCyberRiskE() {
        return cyberRiskE;
    }
    
    public void setCyberRiskE(String cyberRiskE) {
        this.cyberRiskE = cyberRiskE;
    }
    
    public String getCyberRiskF() {
        return cyberRiskF;
    }
    
    public void setCyberRiskF(String cyberRiskF) {
        this.cyberRiskF = cyberRiskF;
    }

    public String getCyberRiskFnormal() {
        return cyberRiskFnormal;
    }
    
    public void setCyberRiskFnormal(String cyberRiskFnormal) {
        this.cyberRiskFnormal = cyberRiskFnormal;
    }
    
    public String getCyberRiskG() {
        return cyberRiskG;
    }
    
    public void setCyberRiskG(String cyberRiskG) {
        this.cyberRiskG = cyberRiskG;
    }
    
    public String getCyberRiskH() {
        return cyberRiskH;
    }
    
    public void setCyberRiskH(String cyberRiskH) {
        this.cyberRiskH = cyberRiskH;
    }
    
    public String getCyberRiskI() {
        return cyberRiskI;
    }
    
    public void setCyberRiskI(String cyberRiskI) {
        this.cyberRiskI = cyberRiskI;
    }

    public String getCyberRiskInormal() {
        return cyberRiskInormal;
    }

    public void setCyberRiskInormal(String cyberRiskInormal) {
        this.cyberRiskInormal = cyberRiskInormal;
    }
    
    public String getCyberRiskJ() {
        return cyberRiskJ;
    }
    
    public void setCyberRiskJ(String cyberRiskJ) {
        this.cyberRiskJ = cyberRiskJ;
    }
    
    public String getCyberRiskK() {
        return cyberRiskK;
    }
    
    public void setCyberRiskK(String cyberRiskK) {
        this.cyberRiskK = cyberRiskK;
    }
    
    public String getCyberRiskL() {
        return cyberRiskL;
    }
    
    public void setCyberRiskL(String cyberRiskL) {
        this.cyberRiskL = cyberRiskL;
    }

    public String getCyberRiskLnormal() {
        return cyberRiskLnormal;
    }

    public void setCyberRiskLnormal(String cyberRiskLnormal) {
        this.cyberRiskLnormal = cyberRiskLnormal;
    }
    
    public String getCyberRiskM() {
        return cyberRiskM;
    }
    
    public void setCyberRiskM(String cyberRiskM) {
        this.cyberRiskM = cyberRiskM;
    }
    
    public String getCyberRiskN() {
        return cyberRiskN;
    }
    
    public void setCyberRiskN(String cyberRiskN) {
        this.cyberRiskN = cyberRiskN;
    }
    
    public String getCyberRiskO() {
        return cyberRiskO;
    }
    
    public void setCyberRiskO(String cyberRiskO) {
        this.cyberRiskO = cyberRiskO;
    }
    
    public String getCyberRiskP() {
        return cyberRiskP;
    }
    
    public void setCyberRiskP(String cyberRiskP) {
        this.cyberRiskP = cyberRiskP;
    }
    
    public String getCyberRiskQ() {
        return cyberRiskQ;
    }
    
    public void setCyberRiskQ(String cyberRiskQ) {
        this.cyberRiskQ = cyberRiskQ;
    }
    
    public String getCyberRiskR() {
        return cyberRiskR;
    }
    
    public void setCyberRiskR(String cyberRiskR) {
        this.cyberRiskR = cyberRiskR;
    }
    
    public String getCyberRiskS() {
        return cyberRiskS;
    }
    
    public void setCyberRiskS(String cyberRiskS) {
        this.cyberRiskS = cyberRiskS;
    }
    
    public String getCyberRiskT() {
        return cyberRiskT;
    }
    
    public void setCyberRiskT(String cyberRiskT) {
        this.cyberRiskT = cyberRiskT;
    }
    
    @Override
    public String toString() {
        return "CyberRiskTableAnswers{" +
                "cyberRiskA='" + cyberRiskA + '\'' +
                ", cyberRiskB='" + cyberRiskB + '\'' +
                ", cyberRiskC='" + cyberRiskC + '\'' +
                ", cyberRiskD='" + cyberRiskD + '\'' +
                ", cyberRiskE='" + cyberRiskE + '\'' +
                ", cyberRiskF='" + cyberRiskF + '\'' +
                ", cyberRiskG='" + cyberRiskG + '\'' +
                ", cyberRiskH='" + cyberRiskH + '\'' +
                ", cyberRiskI='" + cyberRiskI + '\'' +
                ", cyberRiskJ='" + cyberRiskJ + '\'' +
                ", cyberRiskK='" + cyberRiskK + '\'' +
                ", cyberRiskL='" + cyberRiskL + '\'' +
                ", cyberRiskM='" + cyberRiskM + '\'' +
                ", cyberRiskN='" + cyberRiskN + '\'' +
                ", cyberRiskO='" + cyberRiskO + '\'' +
                ", cyberRiskP='" + cyberRiskP + '\'' +
                ", cyberRiskQ='" + cyberRiskQ + '\'' +
                ", cyberRiskR='" + cyberRiskR + '\'' +
                ", cyberRiskS='" + cyberRiskS + '\'' +
                ", cyberRiskT='" + cyberRiskT + '\'' +
                '}';
    }
}