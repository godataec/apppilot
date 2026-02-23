package com.bancointernacional.plataformaBI.domain.report.bbb;

import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PIForm {
    private String companyName;
    
    private String companyEstablishmentDate;
    
    private String mainCompanyActivity;
    private List<Subsidiary> subsidiaries;
    
    private boolean privateCompany;
    private String privateCompanynormal;
    private String stockExchanges;
    
    private List<Shareholder> shareholders;
    
    private boolean acquisitionYes;
    private String currentYearLoans;
    private String previousYearLoans;
    private String currentYearTotalEmployees;
    private String previousYearTotalEmployees;
    private String currentYearTrainingStaff;
    private String previousYearTrainingStaff;
    private String currentYearComplianceStaff;
    private String previousYearComplianceStaff;
    private String currentYearDirectors;
    private String previousYearDirectors;
    private String currentYearSalesStaff;
    private String previousYearSalesStaff;
    private String currentYearSupportStaff;
    private String previousYearSupportStaff;

    private List<Director> directors;
    private String directorsInwardTurnover;
    private String directorsOutwardTurnover;
    private String otherEmployeesInwardTurnover;
    private String otherEmployeesOutwardTurnover;
    private String totalSalaries;
    
    private String currentYearCommercialLoans;
    private String previousYearCommercialLoans;
    private String currentYearInterbancaLoans;
    private String previousYearInterbancaLoans;
    private String currentYearPersonalLoans;
    private String previousYearPersonalLoans;
    private String currentYearTradeFinancing;
    private String previousYearTradeFinancing;
    private String currentYearForeignExchange;
    private String previousYearForeignExchange;
    private String currentYearCommodityMarket;
    private String previousYearCommodityMarket;
    private String currentYearSecuritiesBroker;
    private String previousYearSecuritiesBroker;
    private String currentYearCustodian;
    private String previousYearCustodian;
    private String currentYearAdvisor;
    private String previousYearAdvisor;
    private String currentYearMergers;
    private String previousYearMergers;
    private String currentYearSharePlacing;
    private String previousYearSharePlacing;
    private String currentVentureCapital;
    private String previousVentureCapital;
    private String otherActivities;
    private String currentYearTrustee;
    private String previousYearTrustee;
    private String currentYearOverseasAdvisory;
    private String previousYearOverseasAdvisory;
    private String currentYearOtherActivities;
    private String previousYearOtherActivities;
    private boolean newServicesYes;
    private boolean futureServicesYes;
    private String regulatoryAuthorities;
    private boolean mergerAdvisorYes;
    private List<Merger> mergers;
    private boolean sharePlacingAdvisorYes;
    private boolean trustActivitiesYes;
    private boolean electronicTransferYes;
    private boolean internetAccessYes;
    private String externalAuditorName;
    private String externalAuditorAddress;
    private String auditFrequency;
    private boolean internalControlReviewYes;
    private boolean writtenReportsYes;
    private boolean auditRecommendationsYes;
    private String auditRecommendationsYesnormal;
    private boolean auditorChangedYes;
    private String auditorChangedYesnormal;
    private boolean internalAuditDeptYes;
    private String internalAuditFrequency;
    private boolean ethicsCodeYes;
    private boolean ethicsCodeAcknowledgementYes;
    private boolean conflictDeclarationYes;
    
    private String legalNameAddress;
    private boolean legalOpinionsYes;
    private boolean internalLegalDeptYes;
    private String internalLegalDeptnormal;
    private String internalLegalIndividuals;
    private String departmentResponsibilities;
    private boolean thirdPartyServicesYes;
    private String ThirdPartyServicesYesnormal;
    private boolean legalReviewYes;
    private boolean pendingLitigationYes;
    private boolean potentialClaimsYes;
    private boolean regulatoryCriticismYes;
    private String claimDetails;
    private boolean previousInsuranceYes;

    private String previousInsurer;
    private String policyEndDate;
    private String policyAssuredSum;
    private String requestedAssuredSum;
    
    private String signerPosition;
    private String signatureDate;


    @JsonProperty("tables")
    private Map<String, TableView> tables;
    
    public static class Subsidiary {
        private String name;
        private String location;
        private String dateEstablished;
        private String services;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }

        public String getDateEstablished() { return dateEstablished; }
        public void setDateEstablished(String dateEstablished) { this.dateEstablished = dateEstablished; }

        public String getServices() { return services; }
        public void setServices(String services) { this.services = services; }
    }
    
    public static class Shareholder {
        private String name;
        private String percentage;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPercentage() { return percentage; }
        public void setPercentage(String percentage) { this.percentage = percentage; }
    }
    
    public static class Director {
        private String name;
        private String serviceLength;
        private String position;
        private String shares;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getServiceLength() { return serviceLength; }
        public void setServiceLength(String serviceLength) { this.serviceLength = serviceLength; }

        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public String getShares() { return shares; }
        public void setShares(String shares) { this.shares = shares; }
    }
    
    public static class Merger {
        private String offerer;
        private String interestedParty;
        private String outcome;
        private String offerValue;

        public String getOfferer() { return offerer; }
        public void setOfferer(String offerer) { this.offerer = offerer; }

        public String getInterestedParty() { return interestedParty; }
        public void setInterestedParty(String interestedParty) { this.interestedParty = interestedParty; }

        public String getOutcome() { return outcome; }
        public void setOutcome(String outcome) { this.outcome = outcome; }

        public String getOfferValue() { return offerValue; }
        public void setOfferValue(String offerValue) { this.offerValue = offerValue; }

    }

}