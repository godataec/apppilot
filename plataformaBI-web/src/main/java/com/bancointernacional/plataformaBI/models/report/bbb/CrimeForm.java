package com.bancointernacional.plataformaBI.models.report.bbb;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CrimeForm {
    // SECTION A - PARTICULARS OF PROPOSER
    private String crime1;  // Title of the Proposer
    private String crime2;  // Principal Address
    private String crime3;  // When Established
    private String crime4a; // Authorised Capital
    private String crime4b; // Paid up Capital
    private String crime4c; // Total Assets
    private String crime4d; // Total Deposit
    private String crime4e; // Total Loans and Discounts
    private String crime4f; // Annual Statement/Report info

    // Revenue percentages by operation type (2023 and 2022)
    private String crime5ar1; // Commercial Banking 2023
    private String crime5ar2; // Commercial Banking 2022
    private String crime5br1; // Trust Operations 2023
    private String crime5br2; // Trust Operations 2022
    private String crime5cr1; // Retail Banking 2023
    private String crime5cr2; // Retail Banking 2022
    private String crime5dr1; // Stock Brokerage Transactions 2023
    private String crime5dr2; // Stock Brokerage Transactions 2022
    private String crime5er1; // Foreign Exchange Dealings 2023
    private String crime5er2; // Foreign Exchange Dealings 2022
    private String crime5fr1; // Investment Banking 2023
    private String crime5fr2; // Investment Banking 2022

    // Investment Banking subcategories
    private String crime5fr1a1; // Fund Management 2023
    private String crime5fr2b1; // Fund Management 2022
    private String crime5fr1a2; // Venture Capital 2023
    private String crime5fr2b2; // Venture Capital 2022
    private String crime5fr1a3; // Overseas Advisory 2023
    private String crime5fr2b3; // Overseas Advisory 2022
    private String crime5fr1a4; // Merger and Acquisition advice 2023
    private String crime5fr2b4; // Merger and Acquisition advice 2022
    private String crime5fr1a5; // Share Placing and New Issues 2023
    private String crime5fr2b5; // Share Placing and New Issues 2022

    private String crime5gr1; // Leasing 2023
    private String crime5gr2; // Leasing 2022
    private String crime5hr1; // Factoring 2023
    private String crime5hr2; // Factoring 2022
    private String crime5ir1; // Others 2023
    private String crime5ir2; // Others 2022

    // Account information
    private String crime6ar1; // Current cheque accounts - Number
    private String crime6ar2; // Current cheque accounts - Value
    private String crime6br1; // Inactive/dormant accounts - Number
    private String crime6br2; // Inactive/dormant accounts - Value
    private String crime6cr1; // Savings and deposit accounts - Number
    private String crime6cr2; // Savings and deposit accounts - Value

    // SECTION B - STAFF AND LOCATIONS
    private String crimes2p1; // Number of Directors

    // Number of locations
    private String crimes2p2ar1; // Head Office
    private String crimes2p2ar2; // Computer Centre / Administration Centre
    private String crimes2p2ar3; // Main Branches
    private String crimes2p2ar4; // Other Locations
    private String crimes2p2ar5; // Subsidiaries

    // Number of Employees - Banking Duties
    private String crimes2p2b1r3; // Main Branches
    private String crimes2p2b1r4; // Other Locations
    private String crimes2p2b1r5; // Subsidiaries

    // Number of Employees - Non Banking Duties
    private String crimes2p2b2r1; // Head Office
    private String crimes2p2b2r2; // Computer Centre / Administration Centre
    private String crimes2p2b2r3; // Main Branches
    private String crimes2p2b2r4; // Other Locations
    private String crimes2p2b2r5; // Subsidiaries

    // ATM information
    private String crimes2p2cr1; // Head Office
    private String crimes2p2cr3; // Main Branches
    private String crimes2p2cr4; // Other Locations
    private String crimes2p2cr5; // Subsidiaries

    // Maximum number of ATMs at any one location
    private String crimes2p2dr1; // Head Office
    private String crimes2p2dr2; // Computer Centre / Administration Centre
    private String crimes2p2dr3; // Main Branches
    private String crimes2p2dr4; // Other Locations
    private String crimes2p2dr5; // Subsidiaries

    // SECTION C - PARTICULARS OF COVERAGE
    private String crimes3p1; // Limit of indemnity required

    // Existing insurance policies
    private Boolean crimes2p2a; // Bankers Blanket Bond
    private Boolean crimes2p2b; // Electronic & Computer Crime
    private Boolean crimes2p2c; // Professional Indemnity
    private Boolean crimes2p2d; // Directors and Officers Liability
    private String crimes2p2f;  // Sum Insured
    private String crimes2p2g;  // Insurer

    // Insurance history
    private Boolean crimes2p3a; // Has any proposal been declined
    private String crimes2p3b;  // Reasons if yes

    // SECTION D - VALUES AT RISK
    // Securities on premises
    private String crimes4p1a; // Head Office
    private String crimes4p1b; // Main Branches
    private String crimes4p1c; // Other Locations
    private String crimes4p1d; // Custodians

    // Cash on premises
    private String crimes4p2a; // Head Office
    private String crimes4p2b; // Main Branches
    private String crimes4p2c; // Other Locations
    private String crimes4p2d; // Individual Tellers
    private String crimes4p2e; // Entire Counter
    private String crimes4p2f; // ATMs
    private String crimes4p2g; // Custodians

    // Transit values - Cash and Securities
    private String crimes4p3ar1; // Own Armoured Vehicle - Cash
    private String crimes4p3ar2; // Own Armoured Vehicle - Securities
    private String crimes4p3br1; // Own Non-Armoured Vehicle - Cash
    private String crimes4p3br2; // Own Non-Armoured Vehicle - Securities
    private String crimes4p3cr1; // Third Party Armoured Vehicle - Cash
    private String crimes4p3cr2; // Third Party Armoured Vehicle - Securities
    private String crimes4p3dr1; // Messengers - Cash
    private String crimes4p3dr2; // Messengers - Securities

    private String crimes4p4; // Third Party Armoured Vehicle company name

    // SECTION E - CLAIM EXPERIENCE
    // Loss details
    private String crimes5p1tar1; // Date of Discovery
    private String crimes5p1tar2; // Nature of Loss
    private String crimes5p1tar3; // Location
    private String crimes5p1tar4; // Amount of Loss

    private Boolean crimes5p2a; // Knowledge of potential loss
    private String crimes5p2b;  // Details if yes

    private Boolean crimes5p3a; // Remedial action taken
    private String crimes5p3b;  // Details if yes

    private Boolean crimes5p4a; // Subject to insurance survey
    private String crimes5p4b;  // Implementation of recommendations

    // SECTION F - INTERNAL CONTROLS AND PROCEDURES
    private Boolean crimes6p1a; // Rule book or written instructions
    private Boolean crimes6p1b; // Employee awareness of instructions
    private Boolean crimes6p1c; // Segregation of duties

    private Boolean crimes6p2; // Employee security training

    private Boolean crimes6p3a; // Unannounced position changes
    private Boolean crimes6p3b; // Mandatory vacation policy
    private Boolean crimes6p3c; // Staff recruitment procedures

    // Joint custody
    private Boolean crimes6p4a;  // Joint custody established
    private Boolean crimes6p4a1; // Access to property in safes/vaults
    private Boolean crimes6p4a2; // Keys to safes and vaults
    private Boolean crimes6p4a3; // Access codes and test keys

    // Dual control
    private Boolean crimes6p4b1; // Securities and instruments
    private Boolean crimes6p4b2; // Official checks and traveler's checks
    private Boolean crimes6p4b3; // Dormant accounts
    private Boolean crimes6p4b4; // Access codes and test keys

    // Internal audit
    private Boolean crimes6p5;  // Internal audit department
    private Boolean crimes6p5a; // Audit manual and program
    private String crimes6p5b;  // Number of audit staff
    private String crimes6p5c;  // Frequency of audits
    private Boolean crimes6p5d; // Surprise audits
    private Boolean crimes6p5e; // Comprehensive audit scope
    private Boolean crimes6p5f; // Auditors forbidden to originate entries

    // External audit
    private Boolean crimes6p6a; // External audit conducted
    private String crimes6p6b;  // External auditor name
    private Boolean crimes6p6c1; // Audit covers all offices
    private String crimes6p6c2;  // Audit scope if not all offices
    private Boolean crimes6p6d;  // Auditors visit all locations
    private Boolean crimes6p6e1; // Review of internal controls
    private Boolean crimes6p6e2; // Reports to Board of Directors
    private Boolean crimes6p6e3; // Qualified opinions in last audit
    private String crimes6p6f;   // Details of qualifications

    // SECTION G - PHYSICAL SECURITY
    // Vaults and strongrooms
    private Boolean crimes7p1ar1; // Head Office
    private Boolean crimes7p1ar2; // Main Branches
    private Boolean crimes7p1ar3; // Other Locations

    private String crimes7p1b; // Equipment details

    // Vault equipment - Dial combination lock
    private Boolean crimes7p1b1r1; // Head Office
    private Boolean crimes7p1b1r2; // Main Branches
    private Boolean crimes7p1b1r3; // Other Locations

    // Vault equipment - Time lock
    private Boolean crimes7p1b2r1; // Head Office
    private Boolean crimes7p1b2r2; // Main Branches
    private Boolean crimes7p1b2r3; // Other Locations

    // Vault equipment - Lockable day gate
    private Boolean crimes7p1b3r1; // Head Office
    private Boolean crimes7p1b3r2; // Main Branches
    private Boolean crimes7p1b3r3; // Other Locations

    // Vault construction - Reinforced concrete and steel
    private Boolean crimes7p1c1r1; // Head Office
    private Boolean crimes7p1c1r2; // Main Branches
    private Boolean crimes7p1c1r3; // Other Locations

    // Vault construction - Resistant doors
    private Boolean crimes7p1c2r1; // Head Office
    private Boolean crimes7p1c2r2; // Main Branches
    private Boolean crimes7p1c2r3; // Other Locations

    // Vault construction - Anti-explosive device
    private Boolean crimes7p1c3r1; // Head Office
    private Boolean crimes7p1c3r2; // Main Branches
    private Boolean crimes7p1c3r3; // Other Locations

    // Alternative protection details
    private String crimes7p1att1; // Head Office
    private String crimes7p1att2; // Main Branches
    private String crimes7p1att3; // Other Locations

    // Safes
    private Boolean crimes7p2ar1; // Head Office
    private Boolean crimes7p2ar2; // Main Branches
    private Boolean crimes7p2ar3; // Other Locations

    // Safe equipment - Combination locks with relocking device
    private Boolean crimes7p2br1; // Head Office
    private Boolean crimes7p2br2; // Main Branches
    private Boolean crimes7p2br3; // Other Locations

    // Safe construction - Resistant doors
    private Boolean crimes7p2cr1; // Head Office
    private Boolean crimes7p2cr2; // Main Branches
    private Boolean crimes7p2cr3; // Other Locations

    // Safe construction - Anti-explosive device
    private Boolean crimes7p2dr1; // Head Office
    private Boolean crimes7p2dr2; // Main Branches
    private Boolean crimes7p2dr3; // Other Locations

    // Safe anchoring
    private Boolean crimes7p2er1; // Head Office
    private Boolean crimes7p2er2; // Main Branches
    private Boolean crimes7p2er3; // Other Locations

    // Alternative protection details for safes
    private String crimes7p2att1; // Head Office
    private String crimes7p2att2; // Main Branches
    private String crimes7p2att3; // Other Locations

    // Doors and windows - Substantial locks on doors
    private Boolean crimes7p3ar1; // Head Office
    private Boolean crimes7p3ar2; // Main Branches
    private Boolean crimes7p3ar3; // Other Locations

    // Doors and windows - Substantial locks or bars on windows
    private Boolean crimes7p3br1; // Head Office
    private Boolean crimes7p3br2; // Main Branches
    private Boolean crimes7p3br3; // Other Locations

    // Alarms - Burglary alarm systems
    private Boolean crimes7p4ar1; // Head Office
    private Boolean crimes7p4ar2; // Main Branches
    private Boolean crimes7p4ar3; // Other Locations

    private String crimes7p4b; // Alarm connection details

    // Alarm connection - Police station
    private Boolean crimes7p4b1r1; // Head Office
    private Boolean crimes7p4b1r2; // Main Branches
    private Boolean crimes7p4b1r3; // Other Locations

    // Alarm connection - Other locations
    private String crimes7p4b2r1; // Head Office
    private String crimes7p4b2r2; // Main Branches
    private String crimes7p4b2r3; // Other Locations

    // Teller positions - Robbery alarm systems
    private Boolean crimes7p5ar1; // Head Office
    private Boolean crimes7p5ar2; // Main Branches
    private Boolean crimes7p5ar3; // Other Locations

    // Teller positions - Robbery alarm button/pedal
    private Boolean crimes7p5br1; // Head Office
    private Boolean crimes7p5br2; // Main Branches
    private Boolean crimes7p5br3; // Other Locations

    // Teller positions - Anti-bandit glass
    private Boolean crimes7p5cr1; // Head Office
    private Boolean crimes7p5cr2; // Main Branches
    private Boolean crimes7p5cr3; // Other Locations

    // Teller positions - Separation from banking hall
    private Boolean crimes7p5dr1; // Head Office
    private Boolean crimes7p5dr2; // Main Branches
    private Boolean crimes7p5dr3; // Other Locations

    // Teller positions - Excess cash removal
    private Boolean crimes7p5er1; // Head Office
    private Boolean crimes7p5er2; // Main Branches
    private Boolean crimes7p5er3; // Other Locations

    // Teller positions - Cash to safe/vault at closing
    private Boolean crimes7p5fr1; // Head Office
    private Boolean crimes7p5fr2; // Main Branches
    private Boolean crimes7p5fr3; // Other Locations

    // Teller positions - Bait/decoy money
    private Boolean crimes7p5gr1; // Head Office
    private Boolean crimes7p5gr2; // Main Branches
    private Boolean crimes7p5gr3; // Other Locations

    // Guards - Police patrol
    private Boolean crimes7p6ar1; // Head Office
    private Boolean crimes7p6ar2; // Main Branches
    private Boolean crimes7p6ar3; // Other Locations

    private String crimes7p6b; // Armed guards details

    // Armed guards - Day
    private Boolean crimes7p6b1r1; // Head Office
    private Boolean crimes7p6b1r2; // Main Branches
    private Boolean crimes7p6b1r3; // Other Locations

    // Armed guards - Night
    private Boolean crimes7p6b2r1; // Head Office
    private Boolean crimes7p6b2r2; // Main Branches
    private Boolean crimes7p6b2r3; // Other Locations

    private String crimes7p6c; // Guard provider details

    // Guard provider - Police
    private Boolean crimes7p6c1r1; // Head Office
    private Boolean crimes7p6c1r2; // Main Branches
    private Boolean crimes7p6c1r3; // Other Locations

    // Guard provider - Agency
    private Boolean crimes7p6c2r1; // Head Office
    private Boolean crimes7p6c2r2; // Main Branches
    private Boolean crimes7p6c2r3; // Other Locations

    // Guard provider - Bank itself
    private Boolean crimes7p6c3r1; // Head Office
    private Boolean crimes7p6c3r2; // Main Branches
    private Boolean crimes7p6c3r3; // Other Locations

    // Guards - Bullet proof cages
    private Boolean crimes7p6d1r1; // Head Office
    private Boolean crimes7p6d1r2; // Main Branches
    private Boolean crimes7p6d1r3; // Other Locations

    // Guards - Application at all locations
    private Boolean crimes7p6e1r1; // Head Office
    private Boolean crimes7p6e1r2; // Main Branches
    private Boolean crimes7p6e1r3; // Other Locations

    // Safe deposit boxes - Number of boxes
    private String crimes7p7a1r1; // Head Office
    private String crimes7p7a1r2; // Main Branches
    private String crimes7p7a1r3; // Other Locations

    // Safe deposit boxes - Number rented
    private String crimes7p7a2r1; // Head Office
    private String crimes7p7a2r2; // Main Branches
    private String crimes7p7a2r3; // Other Locations

    // Safe deposit boxes - Number of locations with facilities
    private String crimes7p7a3r1; // Head Office
    private String crimes7p7a3r2; // Main Branches
    private String crimes7p7a3r3; // Other Locations

    // Safe deposit boxes - Separate vault
    private Boolean crimes7p7b1r1; // Head Office
    private Boolean crimes7p7b1r2; // Main Branches
    private Boolean crimes7p7b1r3; // Other Locations

    // Safe deposit boxes - Location if not in separate vault
    private String crimes7p7b2r1; // Head Office
    private String crimes7p7b2r2; // Main Branches
    private String crimes7p7b2r3; // Other Locations

    // Safe deposit boxes - Customer supervision
    private Boolean crimes7p7b3r1; // Head Office
    private Boolean crimes7p7b3r2; // Main Branches
    private Boolean crimes7p7b3r3; // Other Locations

    // Safe deposit boxes - Control details if customers left unattended
    private String crimes7p7att1; // Head Office
    private String crimes7p7att2; // Main Branches
    private String crimes7p7att3; // Other Locations

    // Safe deposit boxes - Dual control
    private Boolean crimes7p7cr1; // Head Office
    private Boolean crimes7p7cr2; // Main Branches
    private Boolean crimes7p7cr3; // Other Locations

    // Transit - Third party security/armored vehicle
    private Boolean crimes7p8a1r1; // Head Office
    private Boolean crimes7p8a1r2; // Main Branches
    private Boolean crimes7p8a1r3; // Other Locations

    // Transit - Third party full insurance and responsibility
    private Boolean crimes7p8a1sr1; // Head Office
    private Boolean crimes7p8a1sr2; // Main Branches
    private Boolean crimes7p8a1sr3; // Other Locations

    // Transit - Alternative arrangements
    private String crimes7p8a2r1;

    // Transit - Messengers
    private Boolean crimes7p8b1sr1; // Head Office
    private Boolean crimes7p8b1sr2; // Main Branches
    private Boolean crimes7p8b1sr3; // Other Locations

    // Transit - Number of messengers
    private String crimes7p8b2r1; // Head Office
    private String crimes7p8b2r2; // Main Branches
    private String crimes7p8b2r3; // Other Locations

    // Transit - Messengers accompanied by police/armed guards
    private Boolean crimes7p8b3r1; // Head Office
    private Boolean crimes7p8b3r2; // Main Branches
    private Boolean crimes7p8b3r3; // Other Locations

    // Transit - Irregular intervals and routes
    private Boolean crimes7p8c1r1; // Head Office
    private Boolean crimes7p8c1r2; // Main Branches
    private Boolean crimes7p8c1r3; // Other Locations

    // SECTION H - COMPUTER SYSTEMS
    private Boolean crimes8p1; // ATM network or electronic point of sale system

    // Funds transfer methods
    private Boolean crimes8p2a1; // BACS
    private Boolean crimes8p2a2; // SWIFT
    private Boolean crimes8p2a3; // CHAPS
    private Boolean crimes8p2a4; // Bankwire
    private String crimes8p2a5;  // Other methods

    // Wire transfer statistics - Bank accounts
    private String crimes8p3ar1; // Average number per month
    private String crimes8p3br1; // Average value per transfer
    private String crimes8p3cr1; // Maximum value per transfer

    // Wire transfer statistics - Customer accounts
    private String crimes8p3ar2; // Average number per month
    private String crimes8p3br2; // Average value per transfer
    private String crimes8p3cr2; // Maximum value per transfer

    private Boolean crimes8p4; // Verification and authentication process
    private Boolean crimes8p5; // Secure fund transfer instructions
    private Boolean crimes8p6; // Electronic instruction transfers
    private String crimes8p61;  // Details for corporate/business accounts

    // Electronic transfers - Corporate accounts
    private Boolean crimes8p6a; // Written agreement with customers
    private Boolean crimes8p6b; // Transfer limits in agreement
    private Boolean crimes8p6c; // Verification by second person

    private Boolean crimes8p7;  // Password access levels
    private Boolean crimes8p8;  // Secure source documents
    private Boolean crimes8p9;  // Secure remote terminals
    private Boolean crimes8p10; // Software access control
    private Boolean crimes8p11; // Physical separation of data centers
    private Boolean crimes8p12; // Access control for data centers
    private Boolean crimes8p13; // Off-site data storage
    private Boolean crimes8p14; // Independent contractors for programming

    // Independent contractors controls
    private Boolean crimes8p14a; // Access restrictions
    private Boolean crimes8p14b; // Oversight of activities
    private Boolean crimes8p14c; // Safeguards for other systems

    // FUND/INVESTMENT/ASSET MANAGEMENT
    private Boolean crimes9p1; // Authorized transactions
    private Boolean crimes9p2; // Formal trading policies
    private Boolean crimes9p3; // Compliance review
    private Boolean crimes9p4; // Exceeding limits handling
    private Boolean crimes9p5; // Counterparty confirmations
    private Boolean crimes9p6; // Independent evaluation
    private Boolean crimes9p7; // Segregation of responsibilities

    // Date information
    private String printDate; // Date of form completion

}
