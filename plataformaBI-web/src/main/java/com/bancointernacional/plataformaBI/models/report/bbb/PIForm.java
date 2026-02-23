package com.bancointernacional.plataformaBI.models.report.bbb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data class to hold all variables needed for the FI PI Questionnaire Thymeleaf template
 */
@Getter
@Setter
@NoArgsConstructor
public class PIForm {
    // Question 1
    private String companyName;
    
    // Question 2
    private String companyEstablishmentDate;
    
    // Question 3
    private String mainCompanyActivity;
    private List<Subsidiary> subsidiaries;
    
    // Question 4
    private boolean isPrivate;
    private boolean isPublic;
    private String stockExchanges;
    
    // Question 5
    private List<Shareholder> shareholders;
    
    // Question 6
    private boolean acquisitionYes;
    private boolean acquisitionNo;
    
    // Question 7
    private int currentYearTotalEmployees;
    private int previousYearTotalEmployees;
    private int currentYearTrainingStaff;
    private int previousYearTrainingStaff;
    private int currentYearComplianceStaff;
    private int previousYearComplianceStaff;
    private int currentYearDirectors;
    private int previousYearDirectors;
    private int currentYearSalesStaff;
    private int previousYearSalesStaff;
    private int currentYearSupportStaff;
    private int previousYearSupportStaff;
    private List<Director> directors;
    private String directorsInwardTurnover;
    private String directorsOutwardTurnover;
    private String otherEmployeesInwardTurnover;
    private String otherEmployeesOutwardTurnover;
    private String totalSalaries;
    
    // Question 8
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
    private String currentVentueCapital;
    private String previousVentueCapital;
    private String otherActivities;
    private String currentYearTrustee;
    private String previousYearTrustee;
    private String currentYearOverseasAdvisory;
    private String previousYearOverseasAdvisory;
    private String currentYearOtherActivities;
    private String previousYearOtherActivities;
    private boolean newServicesYes;
    private boolean newServicesNo;
    private boolean futureServicesYes;
    private boolean futureServicesNo;
    private String regulatoryAuthorities;
    private boolean mergerAdvisorYes;
    private boolean mergerAdvisorNo;
    private List<Merger> mergers;
    private boolean sharePlacingAdvisorYes;
    private boolean sharePlacingAdvisorNo;
    
    // Question 10
    private boolean trustActivitiesYes;
    private boolean trustActivitiesNo;
    
    // Question 11
    private boolean electronicTransferYes;
    private boolean electronicTransferNo;
    
    // Question 12
    private boolean internetAccessYes;
    private boolean internetAccessNo;
    
    // Question 13
    private String externalAuditorName;
    private String externalAuditorAddress;
    private String auditFrequency;
    private boolean internalControlReviewYes;
    private boolean internalControlReviewNo;
    private boolean writtenReportsYes;
    private boolean writtenReportsNo;
    private boolean auditRecommendationsYes;
    private boolean auditRecommendationsNo;
    private String auditRecommendationsDetails;
    private boolean auditorChangedYes;
    private boolean auditorChangedNo;
    private String auditorChangeReasons;
    private boolean internalAuditDeptYes;
    private boolean internalAuditDeptNo;
    private String internalAuditFrequency;
    private boolean ethicsCodeYes;
    private boolean ethicsCodeNo;
    private boolean ethicsCodeAcknowledgementYes;
    private boolean ethicsCodeAcknowledgementNo;
    private boolean conflictDeclarationYes;
    private boolean conflictDeclarationNo;
    
    // Question 14
    private String legalNameAddress;
    private boolean legalOpinionsYes;
    private boolean legalOpinionsNo;
    private boolean internalLegalDeptYes;
    private boolean internalLegalDeptNo;
    private String internalLegalDeptDetails;
    private String internalLegalIndividuals;
    private String deparmentResponsibilities;
    private boolean thirdPartyServicesYes;
    private boolean thirdPartyServicesNo;
    private String thirdPartyServicesDetails;
    private boolean legalReviewYes;
    private boolean legalReviewNo;
    
    // Question 15
    private boolean pendingLitigationYes;
    private boolean pendingLitigationNo;
    private boolean potentialClaimsYes;
    private boolean potentialClaimsNo;
    private boolean regulatoryCriticismYes;
    private boolean regulatoryCriticismNo;
    private String claimsDetails;
    
    // Question 16
    private boolean previousInsuranceYes;
    private boolean previousInsuranceNo;
    private String previousInsurer;
    private String policyEndDate;
    private String policyAssuredSum;
    private String requestedAssuredSum;
    
    // Declaration
    private String signerPosition;
    private String signatureDate;
    
    // Inner classes for complex data structures
    public static class Subsidiary {
        private String name;
        private String location;
        private String dateEstablished;
        private String services;
        
        // Getters and setters
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
        
        // Getters and setters
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
        
        // Getters and setters
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
        
        // Getters and setters
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