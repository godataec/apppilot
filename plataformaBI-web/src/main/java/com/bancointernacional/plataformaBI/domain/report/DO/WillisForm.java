package com.bancointernacional.plataformaBI.domain.report.DO;

import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class WillisForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String willisDOpONEa;
    private String willisDOpONEb;
    private String willisDOpONEc;
    private String willisDOpONEd;
    private String willisDOpONEe;
    private Double willisDOpONEeHome;
    private Double willisDOpONEeEurope;
    private Double willisDOpONEeUSA;
    private Double willisDOpONEeWorld;

    private String willisDOpTWOaPublic;
    private String willisDOpTWOaPrivate;
    private Integer willisDOpTWOb;
    private Double willisDOpTWOc;

    private String willisDOpTHREEDname;
    private Double willisDOpTHREEDpercent;

    private Boolean willisDOpTHREEEYes;
    private Boolean willisDOpTHREEENo;
    private String willisDOpTHREEFYes;
    private String willisDOpTHREEFYesnormal;
    private Boolean willisDOpTHREEFNo;
    private String willisDOpTHREEFstockExchanges;
    private String willisDOpTHREEG;
    private Boolean willisDOpTHREEHYes;
    private Boolean willisDOpTHREEHNo;
    private String willisDOpTHREEdetails;

    private Boolean willisDOpTHREEaYes;
    private Boolean willisDOpTHREEaNo;
    private Boolean willisDOpTHREEbYes;
    private Boolean willisDOpTHREEbNo;
    private Boolean willisDOpTHREEcYes;
    private Boolean willisDOpTHREEcNo;

    private Boolean willisDOpFOURYes;
    private Boolean willisDOpFOURNo;
    private String willisDOpFOURa;
    private String willisDOpFOURb;
    private String willisDOpFOURcname;
    private Double willisDOpFOURcpercent;

    private String willisDOpFIVEa;
    private String willisDOpFIVEb;
    private String willisDOpFIVEc;
    private String willisDOpFIVEcIF;
    private String willisDOpFIVEd;
    private String willisDOpFIVEe;
    private String willisDOpFIVEeif;

    private String willisDOpSIX;
    private String willisDOpSIXa;
    private String willisDOpSIXb;
    private String willisDOpSIXc;

    private String willisDOpSeven;

    private Date printDate;

    @JsonProperty("tables")
    private Map<String, TableView> tables;

    public String getWillisDOpONEa() {
        return willisDOpONEa;
    }

    public void setWillisDOpONEa(String willisDOpONEa) {
        this.willisDOpONEa = willisDOpONEa;
    }

    public String getWillisDOpONEb() {
        return willisDOpONEb;
    }

    public void setWillisDOpONEb(String willisDOpONEb) {
        this.willisDOpONEb = willisDOpONEb;
    }

    public String getWillisDOpONEc() {
        return willisDOpONEc;
    }

    public void setWillisDOpONEc(String willisDOpONEc) {
        this.willisDOpONEc = willisDOpONEc;
    }

    public String getWillisDOpONEd() {
        return willisDOpONEd;
    }

    public void setWillisDOpONEd(String willisDOpONEd) {
        this.willisDOpONEd = willisDOpONEd;
    }

    public String getWillisDOpONEe() {
        return willisDOpONEe;
    }

    public void setWillisDOpONEe(String willisDOpONEe) {
        this.willisDOpONEe = willisDOpONEe;
    }

    public Double getWillisDOpONEeHome() {
        return willisDOpONEeHome;
    }

    public void setWillisDOpONEeHome(Double willisDOpONEeHome) {
        this.willisDOpONEeHome = willisDOpONEeHome;
    }

    public Double getWillisDOpONEeEurope() {
        return willisDOpONEeEurope;
    }

    public void setWillisDOpONEeEurope(Double willisDOpONEeEurope) {
        this.willisDOpONEeEurope = willisDOpONEeEurope;
    }

    public Double getWillisDOpONEeUSA() {
        return willisDOpONEeUSA;
    }

    public void setWillisDOpONEeUSA(Double willisDOpONEeUSA) {
        this.willisDOpONEeUSA = willisDOpONEeUSA;
    }

    public Double getWillisDOpONEeWorld() {
        return willisDOpONEeWorld;
    }

    public void setWillisDOpONEeWorld(Double willisDOpONEeWorld) {
        this.willisDOpONEeWorld = willisDOpONEeWorld;
    }

    public String getWillisDOpTWOaPublic() {
        return willisDOpTWOaPublic;
    }

    public void setWillisDOpTWOaPublic(String willisDOpTWOaPublic) {
        this.willisDOpTWOaPublic = willisDOpTWOaPublic;
    }

    public String getWillisDOpTWOaPrivate() {
        return willisDOpTWOaPrivate;
    }

    public void setWillisDOpTWOaPrivate(String willisDOpTWOaPrivate) {
        this.willisDOpTWOaPrivate = willisDOpTWOaPrivate;
    }

    public Integer getWillisDOpTWOb() {
        return willisDOpTWOb;
    }

    public void setWillisDOpTWOb(Integer willisDOpTWOb) {
        this.willisDOpTWOb = willisDOpTWOb;
    }

    public Double getWillisDOpTWOc() {
        return willisDOpTWOc;
    }

    public void setWillisDOpTWOc(Double willisDOpTWOc) {
        this.willisDOpTWOc = willisDOpTWOc;
    }

    public String getWillisDOpTHREEDname() {
        return willisDOpTHREEDname;
    }

    public void setWillisDOpTHREEDname(String willisDOpTHREEDname) {
        this.willisDOpTHREEDname = willisDOpTHREEDname;
    }

    public Double getWillisDOpTHREEDpercent() {
        return willisDOpTHREEDpercent;
    }

    public void setWillisDOpTHREEDpercent(Double willisDOpTHREEDpercent) {
        this.willisDOpTHREEDpercent = willisDOpTHREEDpercent;
    }

    public Boolean getWillisDOpTHREEEYes() {
        return willisDOpTHREEEYes;
    }

    public void setWillisDOpTHREEEYes(Boolean willisDOpTHREEEYes) {
        this.willisDOpTHREEEYes = willisDOpTHREEEYes;
    }

    public Boolean getWillisDOpTHREEENo() {
        return willisDOpTHREEENo;
    }

    public void setWillisDOpTHREEENo(Boolean willisDOpTHREEENo) {
        this.willisDOpTHREEENo = willisDOpTHREEENo;
    }

    public String getWillisDOpTHREEFYes() {
        return willisDOpTHREEFYes;
    }

    public void setWillisDOpTHREEFYes(String willisDOpTHREEFYes) {
        this.willisDOpTHREEFYes = willisDOpTHREEFYes;
    }

        public String getWillisDOpTHREEFYesnormal() {
        return willisDOpTHREEFYesnormal;
    }

    public void setWillisDOpTHREEFYesnormal(String willisDOpTHREEFYesnormal) {
        this.willisDOpTHREEFYesnormal = willisDOpTHREEFYesnormal;
    }

    public Boolean getWillisDOpTHREEFNo() {
        return willisDOpTHREEFNo;
    }

    public void setWillisDOpTHREEFNo(Boolean willisDOpTHREEFNo) {
        this.willisDOpTHREEFNo = willisDOpTHREEFNo;
    }

    public String getWillisDOpTHREEFstockExchanges() {
        return willisDOpTHREEFstockExchanges;
    }

    public void setWillisDOpTHREEFstockExchanges(String willisDOpTHREEFstockExchanges) {
        this.willisDOpTHREEFstockExchanges = willisDOpTHREEFstockExchanges;
    }

    public String getWillisDOpTHREEG() {
        return willisDOpTHREEG;
    }

    public void setWillisDOpTHREEG(String willisDOpTHREEG) {
        this.willisDOpTHREEG = willisDOpTHREEG;
    }

    public Boolean getWillisDOpTHREEHYes() {
        return willisDOpTHREEHYes;
    }

    public void setWillisDOpTHREEHYes(Boolean willisDOpTHREEHYes) {
        this.willisDOpTHREEHYes = willisDOpTHREEHYes;
    }

    public Boolean getWillisDOpTHREEHNo() {
        return willisDOpTHREEHNo;
    }

    public void setWillisDOpTHREEHNo(Boolean willisDOpTHREEHNo) {
        this.willisDOpTHREEHNo = willisDOpTHREEHNo;
    }

    public String getWillisDOpTHREEdetails() {
        return willisDOpTHREEdetails;
    }

    public void setWillisDOpTHREEdetails(String willisDOpTHREEdetails) {
        this.willisDOpTHREEdetails = willisDOpTHREEdetails;
    }

    public Boolean getWillisDOpTHREEaYes() {
        return willisDOpTHREEaYes;
    }

    public void setWillisDOpTHREEaYes(Boolean willisDOpTHREEaYes) {
        this.willisDOpTHREEaYes = willisDOpTHREEaYes;
    }

    public Boolean getWillisDOpTHREEaNo() {
        return willisDOpTHREEaNo;
    }

    public void setWillisDOpTHREEaNo(Boolean willisDOpTHREEaNo) {
        this.willisDOpTHREEaNo = willisDOpTHREEaNo;
    }

    public Boolean getWillisDOpTHREEbYes() {
        return willisDOpTHREEbYes;
    }

    public void setWillisDOpTHREEbYes(Boolean willisDOpTHREEbYes) {
        this.willisDOpTHREEbYes = willisDOpTHREEbYes;
    }

    public Boolean getWillisDOpTHREEbNo() {
        return willisDOpTHREEbNo;
    }

    public void setWillisDOpTHREEbNo(Boolean willisDOpTHREEbNo) {
        this.willisDOpTHREEbNo = willisDOpTHREEbNo;
    }

    public Boolean getWillisDOpTHREEcYes() {
        return willisDOpTHREEcYes;
    }

    public void setWillisDOpTHREEcYes(Boolean willisDOpTHREEcYes) {
        this.willisDOpTHREEcYes = willisDOpTHREEcYes;
    }

    public Boolean getWillisDOpTHREEcNo() {
        return willisDOpTHREEcNo;
    }

    public void setWillisDOpTHREEcNo(Boolean willisDOpTHREEcNo) {
        this.willisDOpTHREEcNo = willisDOpTHREEcNo;
    }

    public Boolean getWillisDOpFOURYes() {
        return willisDOpFOURYes;
    }

    public void setWillisDOpFOURYes(Boolean willisDOpFOURYes) {
        this.willisDOpFOURYes = willisDOpFOURYes;
    }

    public Boolean getWillisDOpFOURNo() {
        return willisDOpFOURNo;
    }

    public void setWillisDOpFOURNo(Boolean willisDOpFOURNo) {
        this.willisDOpFOURNo = willisDOpFOURNo;
    }

    public String getWillisDOpFOURa() {
        return willisDOpFOURa;
    }

    public void setWillisDOpFOURa(String willisDOpFOURa) {
        this.willisDOpFOURa = willisDOpFOURa;
    }

    public String getWillisDOpFOURb() {
        return willisDOpFOURb;
    }

    public void setWillisDOpFOURb(String willisDOpFOURb) {
        this.willisDOpFOURb = willisDOpFOURb;
    }

    public String getWillisDOpFOURcname() {
        return willisDOpFOURcname;
    }

    public void setWillisDOpFOURcname(String willisDOpFOURcname) {
        this.willisDOpFOURcname = willisDOpFOURcname;
    }

    public Double getWillisDOpFOURcpercent() {
        return willisDOpFOURcpercent;
    }

    public void setWillisDOpFOURcpercent(Double willisDOpFOURcpercent) {
        this.willisDOpFOURcpercent = willisDOpFOURcpercent;
    }

    public String getWillisDOpFIVEa() {
        return willisDOpFIVEa;
    }

    public void setWillisDOpFIVEa(String willisDOpFIVEa) {
        this.willisDOpFIVEa = willisDOpFIVEa;
    }

    public String getWillisDOpFIVEb() {
        return willisDOpFIVEb;
    }

    public void setWillisDOpFIVEb(String willisDOpFIVEb) {
        this.willisDOpFIVEb = willisDOpFIVEb;
    }

    public String getWillisDOpFIVEc() {
        return willisDOpFIVEc;
    }

    public void setWillisDOpFIVEc(String willisDOpFIVEc) {
        this.willisDOpFIVEc = willisDOpFIVEc;
    }

    public String getWillisDOpFIVEcIF() {
        return willisDOpFIVEcIF;
    }

    public void setWillisDOpFIVEcIF(String willisDOpFIVEcIF) {
        this.willisDOpFIVEcIF = willisDOpFIVEcIF;
    }

    public String getWillisDOpFIVEd() {
        return willisDOpFIVEd;
    }

    public void setWillisDOpFIVEd(String willisDOpFIVEd) {
        this.willisDOpFIVEd = willisDOpFIVEd;
    }

    public String getWillisDOpFIVEe() {
        return willisDOpFIVEe;
    }

    public void setWillisDOpFIVEe(String willisDOpFIVEe) {
        this.willisDOpFIVEe = willisDOpFIVEe;
    }

    public String getWillisDOpFIVEeif() {
        return willisDOpFIVEeif;
    }

    public void setWillisDOpFIVEeif(String willisDOpFIVEeif) {
        this.willisDOpFIVEeif = willisDOpFIVEeif;
    }

    public String getWillisDOpSIX() {
        return willisDOpSIX;
    }

    public void setWillisDOpSIX(String willisDOpSIX) {
        this.willisDOpSIX = willisDOpSIX;
    }

    public String getWillisDOpSIXa() {
        return willisDOpSIXa;
    }

    public void setWillisDOpSIXa(String willisDOpSIXa) {
        this.willisDOpSIXa = willisDOpSIXa;
    }

    public String getWillisDOpSIXb() {
        return willisDOpSIXb;
    }

    public void setWillisDOpSIXb(String willisDOpSIXb) {
        this.willisDOpSIXb = willisDOpSIXb;
    }

    public String getWillisDOpSIXc() {
        return willisDOpSIXc;
    }

    public void setWillisDOpSIXc(String willisDOpSIXc) {
        this.willisDOpSIXc = willisDOpSIXc;
    }

    public String getWillisDOpSeven() {
        return willisDOpSeven;
    }

    public void setWillisDOpSeven(String willisDOpSeven) {
        this.willisDOpSeven = willisDOpSeven;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    public Map<String, TableView> getTables() {
        return tables;
    }

    public void setTables(Map<String, TableView> tables) {
        this.tables = tables;
    }
}