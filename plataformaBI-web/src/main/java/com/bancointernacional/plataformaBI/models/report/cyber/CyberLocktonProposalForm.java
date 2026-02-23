package com.bancointernacional.plataformaBI.models.report.cyber;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


public class CyberLocktonProposalForm {

    @JsonProperty("Clocktonproposals1p1a")
    private String Clocktonproposals1p1a; // Full name

    @JsonProperty("Clocktonproposals1p1b")
    private String Clocktonproposals1p1b; // Address

    @JsonProperty("Clocktonproposals1p1c")
    private String Clocktonproposals1p1c; // City

    @JsonProperty("Clocktonproposals1p1d")
    private String Clocktonproposals1p1d; // State

    @JsonProperty("Clocktonproposals1p1e")
    private String Clocktonproposals1p1e; // ZIP/postcode

    @JsonProperty("Clocktonproposals1p1f")
    private String Clocktonproposals1p1f; // Website

    // Individual completing application
    @JsonProperty("Clocktonproposals1p1h")
    private String Clocktonproposals1p1h; // Full name

    @JsonProperty("Clocktonproposals1p1i")
    private String Clocktonproposals1p1i; // Surname

    @JsonProperty("Clocktonproposals1p1j")
    private String Clocktonproposals1p1j; // Email

    // Principal contact for security/privacy breach
    @JsonProperty("Clocktonproposals1p1k")
    private String Clocktonproposals1p1k; // Name

    @JsonProperty("Clocktonproposals1p1m")
    private String Clocktonproposals1p1m; // Title

    @JsonProperty("Clocktonproposals1p1n")
    private String Clocktonproposals1p1n; // Email

    @JsonProperty("Clocktonproposals1p1o")
    private String Clocktonproposals1p1o; // Phone

    // Business type
    @JsonProperty("Clocktonproposals1p1ar1")
    private String Clocktonproposals1p1ar1; // Sole proprietor, Corporation, Partnership, Other

    @JsonProperty("Clocktonproposals1p1q")
    private String Clocktonproposals1p1q; // Date established

    @JsonProperty("Clocktonproposals1p1r")
    private String Clocktonproposals1p1r; // Business description

    // ========== ANNUAL REVENUES ==========

    // Last complete financial year
    @JsonProperty("Clocktonproposals2p1ar1")
    private String Clocktonproposals2p1ar1;

    @JsonProperty("Clocktonproposals2p1br1")
    private String Clocktonproposals2p1br1;

    @JsonProperty("Clocktonproposals2p1cr1")
    private String Clocktonproposals2p1cr1;

    // Current year (estimate)
    @JsonProperty("Clocktonproposals2p1ar2")
    private String Clocktonproposals2p1ar2;

    @JsonProperty("Clocktonproposals2p1br2")
    private String Clocktonproposals2p1br2;

    @JsonProperty("Clocktonproposals2p1cr2")
    private String Clocktonproposals2p1cr2;

    // Next year (estimate)
    @JsonProperty("Clocktonproposals2p1ar3")
    private String Clocktonproposals2p1ar3;

    @JsonProperty("Clocktonproposals2p1br3")
    private String Clocktonproposals2p1br3;

    @JsonProperty("Clocktonproposals2p1cr3")
    private String Clocktonproposals2p1cr3;

    // Revenue presence questions
    @JsonProperty("Clocktonproposals2p2a")
    private String Clocktonproposals2p2a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p2a1")
    private String Clocktonproposals2p2a1;

    @JsonProperty("Clocktonproposals2p2b")
    private String Clocktonproposals2p2b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p2c")
    private String Clocktonproposals2p2c;

    // Revenue attribution percentages
    @JsonProperty("Clocktonproposals2p2dr1")
    private String Clocktonproposals2p2dr1;

    @JsonProperty("Clocktonproposals2p2dr2")
    private String Clocktonproposals2p2dr2;

    @JsonProperty("Clocktonproposals2p2dr3")
    private String Clocktonproposals2p2dr3;

    // Business changes
    @JsonProperty("Clocktonproposals2p2e")
    private String Clocktonproposals2p2e; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p2f")
    private String Clocktonproposals2p2f;

    @JsonProperty("Clocktonproposals2p2g")
    private String Clocktonproposals2p2g; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p2h")
    private String Clocktonproposals2p2h; // YES/NO/N/A

    // ========== SECTION II: RISK ASSESSMENT ==========

    // IT Budget and Staff
    @JsonProperty("Clocktonproposals2p2i")
    private String Clocktonproposals2p2i;

    @JsonProperty("Clocktonproposals2p2j")
    private String Clocktonproposals2p2j;

    @JsonProperty("Clocktonproposals2p2k")
    private String Clocktonproposals2p2k;

    @JsonProperty("Clocktonproposals2p2l")
    private String Clocktonproposals2p2l;

    // Information security centralization (checkboxes)
    @JsonProperty("Clocktonproposals2p2m")
    private Boolean Clocktonproposals2p2m;

    @JsonProperty("Clocktonproposals2p2n")
    private Boolean Clocktonproposals2p2n;

    @JsonProperty("Clocktonproposals2p2o")
    private Boolean Clocktonproposals2p2o;

    @JsonProperty("Clocktonproposals2p2p")
    private Boolean Clocktonproposals2p2p;

    @JsonProperty("Clocktonproposals2p2q")
    private Boolean Clocktonproposals2p2q;

    @JsonProperty("Clocktonproposals2p2r")
    private Boolean Clocktonproposals2p2r;

    @JsonProperty("Clocktonproposals2p2s")
    private Boolean Clocktonproposals2p2s;

    @JsonProperty("Clocktonproposals2p2s1")
    private String Clocktonproposals2p2s1;

    // Security policies and procedures
    @JsonProperty("Clocktonproposals2p6a")
    private String Clocktonproposals2p6a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6b")
    private String Clocktonproposals2p6b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6b1")
    private String Clocktonproposals2p6b1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6b2")
    private String Clocktonproposals2p6b2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6c")
    private String Clocktonproposals2p6c; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6d")
    private String Clocktonproposals2p6d; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6d2")
    private String Clocktonproposals2p6d2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6d3")
    private String Clocktonproposals2p6d3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p6e")
    private String Clocktonproposals2p6e; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p7a")
    private String Clocktonproposals2p7a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p7a1")
    private String Clocktonproposals2p7a1; // YES/NO/N/A

    // Third party/MSP usage
    @JsonProperty("Clocktonproposals2p8")
    private String Clocktonproposals2p8; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p8a1")
    private String Clocktonproposals2p8a1; // Multiple checkbox values

    @JsonProperty("Clocktonproposals2p8a1_other")
    private String Clocktonproposals2p8a1Other;

    @JsonProperty("Clocktonproposals2p8b")
    private String Clocktonproposals2p8b; // YES/NO/N/A

    // Data and asset inventory
    @JsonProperty("Clocktonproposals2p9")
    private String Clocktonproposals2p9; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p9a")
    private String Clocktonproposals2p9a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p10")
    private String Clocktonproposals2p10; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p10a")
    private String Clocktonproposals2p10a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p11")
    private String Clocktonproposals2p11; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p12")
    private String Clocktonproposals2p12; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p13")
    private String Clocktonproposals2p13; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p13a")
    private String Clocktonproposals2p13a; // Multiple checkbox values

    @JsonProperty("Clocktonproposals2p13b1")
    private String Clocktonproposals2p13b1; // Radio button values

    @JsonProperty("Clocktonproposals2p13b1_other")
    private String Clocktonproposals2p13b1Other;

    @JsonProperty("Clocktonproposals2p14")
    private String Clocktonproposals2p14; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p14a")
    private String Clocktonproposals2p14a; // Radio button values

    @JsonProperty("Clocktonproposals2p14a_other")
    private String Clocktonproposals2p14aOther;

    @JsonProperty("Clocktonproposals2p15")
    private String Clocktonproposals2p15; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p15a1")
    private String Clocktonproposals2p15a1; // Radio button values

    @JsonProperty("Clocktonproposals2p15a1_other")
    private String Clocktonproposals2p15a1Other;

    @JsonProperty("Clocktonproposals2p16")
    private String Clocktonproposals2p16; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p17")
    private String Clocktonproposals2p17; // YES/NO/N/A

    @JsonProperty("Clocktonproposals2p18")
    private String Clocktonproposals2p18;

    // Endpoints
    @JsonProperty("Clocktonproposals2p19r1")
    private String Clocktonproposals2p19r1;

    @JsonProperty("Clocktonproposals2p19r2")
    private String Clocktonproposals2p19r2;

    @JsonProperty("Clocktonproposals2p19r3")
    private String Clocktonproposals2p19r3;

    // Critical systems hosting
    @JsonProperty("Clocktonproposals2p20r1")
    private String Clocktonproposals2p20r1;

    @JsonProperty("Clocktonproposals2p20r2")
    private String Clocktonproposals2p20r2;

    @JsonProperty("Clocktonproposals2p20Ar1")
    private String Clocktonproposals2p20Ar1;

    // ========== DATA ASSESSMENT ==========

    @JsonProperty("Clocktonproposals3p1a")
    private String Clocktonproposals3p1a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1a_records")
    private String Clocktonproposals3p1aRecords;

    @JsonProperty("Clocktonproposals3p1b")
    private String Clocktonproposals3p1b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1b_records")
    private String Clocktonproposals3p1bRecords;

    @JsonProperty("Clocktonproposals3p1c")
    private String Clocktonproposals3p1c; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1c_records")
    private String Clocktonproposals3p1cRecords;

    @JsonProperty("Clocktonproposals3p1d")
    private String Clocktonproposals3p1d; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1d_records")
    private String Clocktonproposals3p1dRecords;

    @JsonProperty("Clocktonproposals3p1e")
    private String Clocktonproposals3p1e; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1e_records")
    private String Clocktonproposals3p1eRecords;

    @JsonProperty("Clocktonproposals3p1f")
    private String Clocktonproposals3p1f; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p1f_records")
    private String Clocktonproposals3p1fRecords;

    @JsonProperty("Clocktonproposals3p1f_explain")
    private String Clocktonproposals3p1fExplain;

    @JsonProperty("Clocktonproposals3p2")
    private String Clocktonproposals3p2;

    @JsonProperty("Clocktonproposals3p3")
    private String Clocktonproposals3p3;

    @JsonProperty("Clocktonproposals3p4")
    private String Clocktonproposals3p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals3p4_volume")
    private String Clocktonproposals3p4Volume;

    @JsonProperty("Clocktonproposals3p5")
    private String Clocktonproposals3p5;

    // ========== EMPLOYEES ==========

    @JsonProperty("Clocktonproposals4p1")
    private String Clocktonproposals4p1; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p2")
    private String Clocktonproposals4p2; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p3")
    private String Clocktonproposals4p3; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p4")
    private String Clocktonproposals4p4; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p5")
    private String Clocktonproposals4p5; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p6")
    private String Clocktonproposals4p6; // Yes/No/N/A

    @JsonProperty("Clocktonproposals4p7")
    private String Clocktonproposals4p7;

    @JsonProperty("Clocktonproposals4p8")
    private String Clocktonproposals4p8;

    @JsonProperty("Clocktonproposals4p9")
    private String Clocktonproposals4p9;

    // ========== MULTIFACTOR AUTHENTICATION ==========

    @JsonProperty("Clocktonproposals5p1a")
    private String Clocktonproposals5p1a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1b")
    private String Clocktonproposals5p1b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1b1")
    private String Clocktonproposals5p1b1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1b2")
    private String Clocktonproposals5p1b2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1b3")
    private String Clocktonproposals5p1b3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1b4")
    private String Clocktonproposals5p1b4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1c")
    private String Clocktonproposals5p1c; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1d")
    private String Clocktonproposals5p1d; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1e")
    private String Clocktonproposals5p1e; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p1f")
    private String Clocktonproposals5p1f; // YES/NO/N/A

    // RDP settings
    @JsonProperty("Clocktonproposals5p2a")
    private String Clocktonproposals5p2a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p2b")
    private String Clocktonproposals5p2b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p2c")
    private String Clocktonproposals5p2c; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p2d")
    private String Clocktonproposals5p2d; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p2e")
    private String Clocktonproposals5p2e; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p3")
    private String Clocktonproposals5p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p4")
    private String Clocktonproposals5p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals5p5")
    private String Clocktonproposals5p5;

    // ========== NETWORK SECURITY ==========

    @JsonProperty("Clocktonproposals6p1")
    private String Clocktonproposals6p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p1a")
    private String Clocktonproposals6p1a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p1b")
    private String Clocktonproposals6p1b; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p2")
    private String Clocktonproposals6p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p3")
    private String Clocktonproposals6p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p4")
    private String Clocktonproposals6p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p5")
    private String Clocktonproposals6p5; // YES/NO/N/A

    @JsonProperty("Clocktonproposals6p6")
    private String Clocktonproposals6p6;

    // ========== ENDPOINT SECURITY ==========

    @JsonProperty("Clocktonproposals7p1")
    private String Clocktonproposals7p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p2")
    private String Clocktonproposals7p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p2a")
    private String Clocktonproposals7p2a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p3")
    private String Clocktonproposals7p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p4")
    private String Clocktonproposals7p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p5")
    private String Clocktonproposals7p5; // YES/NO/N/A

    @JsonProperty("Clocktonproposals7p6")
    private String Clocktonproposals7p6;

    // ========== EMAIL SECURITY ==========

    @JsonProperty("Clocktonproposals8p1")
    private String Clocktonproposals8p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals8p2")
    private String Clocktonproposals8p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals8p3")
    private String Clocktonproposals8p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals8p4")
    private String Clocktonproposals8p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals8p5")
    private String Clocktonproposals8p5;

    // ========== BACKUP AND RECOVERY ==========

    @JsonProperty("Clocktonproposals9p1")
    private String Clocktonproposals9p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p1a")
    private String Clocktonproposals9p1a;

    @JsonProperty("Clocktonproposals9p2")
    private String Clocktonproposals9p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p2a")
    private String Clocktonproposals9p2a;

    @JsonProperty("Clocktonproposals9p3")
    private String Clocktonproposals9p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p4")
    private String Clocktonproposals9p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p5")
    private String Clocktonproposals9p5; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p5a")
    private String Clocktonproposals9p5a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals9p6")
    private String Clocktonproposals9p6;

    // ========== INCIDENT RESPONSE ==========

    @JsonProperty("Clocktonproposals10p1")
    private String Clocktonproposals10p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals10p1a")
    private String Clocktonproposals10p1a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals10p2")
    private String Clocktonproposals10p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals10p3")
    private String Clocktonproposals10p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals10p3a")
    private String Clocktonproposals10p3a;

    @JsonProperty("Clocktonproposals10p4")
    private String Clocktonproposals10p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals10p5")
    private String Clocktonproposals10p5;

    // ========== VULNERABILITY MANAGEMENT ==========

    @JsonProperty("Clocktonproposals11p1")
    private String Clocktonproposals11p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals11p1a")
    private String Clocktonproposals11p1a;

    @JsonProperty("Clocktonproposals11p2")
    private String Clocktonproposals11p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals11p2a")
    private String Clocktonproposals11p2a;

    @JsonProperty("Clocktonproposals11p3")
    private String Clocktonproposals11p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals11p3a")
    private String Clocktonproposals11p3a;

    @JsonProperty("Clocktonproposals11p4")
    private String Clocktonproposals11p4;

    // ========== THIRD-PARTY RISK MANAGEMENT ==========

    @JsonProperty("Clocktonproposals12p1")
    private String Clocktonproposals12p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals12p2")
    private String Clocktonproposals12p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals12p3")
    private String Clocktonproposals12p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals12p4")
    private String Clocktonproposals12p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals12p5")
    private String Clocktonproposals12p5;

    // ========== COMPLIANCE AND GOVERNANCE ==========

    @JsonProperty("Clocktonproposals13p1")
    private String Clocktonproposals13p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals13p1a")
    private String Clocktonproposals13p1a; // YES/NO/N/A

    @JsonProperty("Clocktonproposals13p2")
    private String Clocktonproposals13p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals13p2a")
    private String Clocktonproposals13p2a;

    @JsonProperty("Clocktonproposals13p3")
    private String Clocktonproposals13p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals13p3a")
    private String Clocktonproposals13p3a;

    @JsonProperty("Clocktonproposals13p4")
    private String Clocktonproposals13p4;

    // ========== CLOUD SECURITY ==========

    @JsonProperty("Clocktonproposals14p1")
    private String Clocktonproposals14p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals14p1a")
    private String Clocktonproposals14p1a;

    @JsonProperty("Clocktonproposals14p2")
    private String Clocktonproposals14p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals14p3")
    private String Clocktonproposals14p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals14p4")
    private String Clocktonproposals14p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals14p5")
    private String Clocktonproposals14p5;

    // ========== PRIVACY AND DATA PROTECTION ==========

    @JsonProperty("Clocktonproposals15p1")
    private String Clocktonproposals15p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals15p2")
    private String Clocktonproposals15p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals15p3")
    private String Clocktonproposals15p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals15p4")
    private String Clocktonproposals15p4; // YES/NO/N/A

    @JsonProperty("Clocktonproposals15p5")
    private String Clocktonproposals15p5;

    // ========== SECTION III: CLAIMS HISTORY ==========

    @JsonProperty("Clocktonproposals16p1")
    private String Clocktonproposals16p1; // YES/NO/N/A

    @JsonProperty("Clocktonproposals16p1a")
    private String Clocktonproposals16p1a;

    @JsonProperty("Clocktonproposals16p2")
    private String Clocktonproposals16p2; // YES/NO/N/A

    @JsonProperty("Clocktonproposals16p2a")
    private String Clocktonproposals16p2a;

    @JsonProperty("Clocktonproposals16p3")
    private String Clocktonproposals16p3; // YES/NO/N/A

    @JsonProperty("Clocktonproposals16p3a")
    private String Clocktonproposals16p3a;

    // ========== SECTION IV: COVERAGE REQUIREMENTS ==========

    @JsonProperty("Clocktonproposals17p1")
    private String Clocktonproposals17p1;

    @JsonProperty("Clocktonproposals17p2")
    private String Clocktonproposals17p2;

    @JsonProperty("Clocktonproposals17p3")
    private String Clocktonproposals17p3;

    @JsonProperty("Clocktonproposals17p4")
    private String Clocktonproposals17p4;

    // ========== SECTION V: DECLARATIONS AND SIGNATURES ==========

    @JsonProperty("Clocktonproposals18p1")
    private String Clocktonproposals18p1;

    @JsonProperty("Clocktonproposals18p2")
    private String Clocktonproposals18p2;

    @JsonProperty("Clocktonproposals18p3")
    private String Clocktonproposals18p3;

    @JsonProperty("Clocktonproposals18p4")
    private String Clocktonproposals18p4;

    // ========== AUDIT FIELDS ==========

    @JsonProperty("CreatedAt")
    private LocalDateTime CreatedAt;

    @JsonProperty("UpdatedAt")
    private LocalDateTime UpdatedAt;

    @JsonProperty("CreatedBy")
    private String CreatedBy;

    @JsonProperty("UpdatedBy")
    private String UpdatedBy;


    public String getClocktonproposals1p1a() {
        return Clocktonproposals1p1a;
    }

    public void setClocktonproposals1p1a(String clocktonproposals1p1a) {
        Clocktonproposals1p1a = clocktonproposals1p1a;
    }

    public String getClocktonproposals1p1b() {
        return Clocktonproposals1p1b;
    }

    public void setClocktonproposals1p1b(String clocktonproposals1p1b) {
        Clocktonproposals1p1b = clocktonproposals1p1b;
    }

    public String getClocktonproposals1p1c() {
        return Clocktonproposals1p1c;
    }

    public void setClocktonproposals1p1c(String clocktonproposals1p1c) {
        Clocktonproposals1p1c = clocktonproposals1p1c;
    }

    public String getClocktonproposals1p1d() {
        return Clocktonproposals1p1d;
    }

    public void setClocktonproposals1p1d(String clocktonproposals1p1d) {
        Clocktonproposals1p1d = clocktonproposals1p1d;
    }

    public String getClocktonproposals1p1e() {
        return Clocktonproposals1p1e;
    }

    public void setClocktonproposals1p1e(String clocktonproposals1p1e) {
        Clocktonproposals1p1e = clocktonproposals1p1e;
    }

    public String getClocktonproposals1p1f() {
        return Clocktonproposals1p1f;
    }

    public void setClocktonproposals1p1f(String clocktonproposals1p1f) {
        Clocktonproposals1p1f = clocktonproposals1p1f;
    }

    public String getClocktonproposals1p1h() {
        return Clocktonproposals1p1h;
    }

    public void setClocktonproposals1p1h(String clocktonproposals1p1h) {
        Clocktonproposals1p1h = clocktonproposals1p1h;
    }

    public String getClocktonproposals1p1i() {
        return Clocktonproposals1p1i;
    }

    public void setClocktonproposals1p1i(String clocktonproposals1p1i) {
        Clocktonproposals1p1i = clocktonproposals1p1i;
    }

    public String getClocktonproposals1p1j() {
        return Clocktonproposals1p1j;
    }

    public void setClocktonproposals1p1j(String clocktonproposals1p1j) {
        Clocktonproposals1p1j = clocktonproposals1p1j;
    }

    public String getClocktonproposals1p1k() {
        return Clocktonproposals1p1k;
    }

    public void setClocktonproposals1p1k(String clocktonproposals1p1k) {
        Clocktonproposals1p1k = clocktonproposals1p1k;
    }

    public String getClocktonproposals1p1m() {
        return Clocktonproposals1p1m;
    }

    public void setClocktonproposals1p1m(String clocktonproposals1p1m) {
        Clocktonproposals1p1m = clocktonproposals1p1m;
    }

    public String getClocktonproposals1p1n() {
        return Clocktonproposals1p1n;
    }

    public void setClocktonproposals1p1n(String clocktonproposals1p1n) {
        Clocktonproposals1p1n = clocktonproposals1p1n;
    }

    public String getClocktonproposals1p1o() {
        return Clocktonproposals1p1o;
    }

    public void setClocktonproposals1p1o(String clocktonproposals1p1o) {
        Clocktonproposals1p1o = clocktonproposals1p1o;
    }

    public String getClocktonproposals1p1ar1() {
        return Clocktonproposals1p1ar1;
    }

    public void setClocktonproposals1p1ar1(String clocktonproposals1p1ar1) {
        Clocktonproposals1p1ar1 = clocktonproposals1p1ar1;
    }

    public String getClocktonproposals1p1q() {
        return Clocktonproposals1p1q;
    }

    public void setClocktonproposals1p1q(String clocktonproposals1p1q) {
        Clocktonproposals1p1q = clocktonproposals1p1q;
    }

    public String getClocktonproposals1p1r() {
        return Clocktonproposals1p1r;
    }

    public void setClocktonproposals1p1r(String clocktonproposals1p1r) {
        Clocktonproposals1p1r = clocktonproposals1p1r;
    }

    public String getClocktonproposals2p1ar1() {
        return Clocktonproposals2p1ar1;
    }

    public void setClocktonproposals2p1ar1(String clocktonproposals2p1ar1) {
        Clocktonproposals2p1ar1 = clocktonproposals2p1ar1;
    }

    public String getClocktonproposals2p1br1() {
        return Clocktonproposals2p1br1;
    }

    public void setClocktonproposals2p1br1(String clocktonproposals2p1br1) {
        Clocktonproposals2p1br1 = clocktonproposals2p1br1;
    }

    public String getClocktonproposals2p1cr1() {
        return Clocktonproposals2p1cr1;
    }

    public void setClocktonproposals2p1cr1(String clocktonproposals2p1cr1) {
        Clocktonproposals2p1cr1 = clocktonproposals2p1cr1;
    }

    public String getClocktonproposals2p1ar2() {
        return Clocktonproposals2p1ar2;
    }

    public void setClocktonproposals2p1ar2(String clocktonproposals2p1ar2) {
        Clocktonproposals2p1ar2 = clocktonproposals2p1ar2;
    }

    public String getClocktonproposals2p1br2() {
        return Clocktonproposals2p1br2;
    }

    public void setClocktonproposals2p1br2(String clocktonproposals2p1br2) {
        Clocktonproposals2p1br2 = clocktonproposals2p1br2;
    }

    public String getClocktonproposals2p1cr2() {
        return Clocktonproposals2p1cr2;
    }

    public void setClocktonproposals2p1cr2(String clocktonproposals2p1cr2) {
        Clocktonproposals2p1cr2 = clocktonproposals2p1cr2;
    }

    public String getClocktonproposals2p1ar3() {
        return Clocktonproposals2p1ar3;
    }

    public void setClocktonproposals2p1ar3(String clocktonproposals2p1ar3) {
        Clocktonproposals2p1ar3 = clocktonproposals2p1ar3;
    }

    public String getClocktonproposals2p1br3() {
        return Clocktonproposals2p1br3;
    }

    public void setClocktonproposals2p1br3(String clocktonproposals2p1br3) {
        Clocktonproposals2p1br3 = clocktonproposals2p1br3;
    }

    public String getClocktonproposals2p1cr3() {
        return Clocktonproposals2p1cr3;
    }

    public void setClocktonproposals2p1cr3(String clocktonproposals2p1cr3) {
        Clocktonproposals2p1cr3 = clocktonproposals2p1cr3;
    }

    public String getClocktonproposals2p2a() {
        return Clocktonproposals2p2a;
    }

    public void setClocktonproposals2p2a(String clocktonproposals2p2a) {
        Clocktonproposals2p2a = clocktonproposals2p2a;
    }

    public String getClocktonproposals2p2a1() {
        return Clocktonproposals2p2a1;
    }

    public void setClocktonproposals2p2a1(String clocktonproposals2p2a1) {
        Clocktonproposals2p2a1 = clocktonproposals2p2a1;
    }

    public String getClocktonproposals2p2b() {
        return Clocktonproposals2p2b;
    }

    public void setClocktonproposals2p2b(String clocktonproposals2p2b) {
        Clocktonproposals2p2b = clocktonproposals2p2b;
    }

    public String getClocktonproposals2p2c() {
        return Clocktonproposals2p2c;
    }

    public void setClocktonproposals2p2c(String clocktonproposals2p2c) {
        Clocktonproposals2p2c = clocktonproposals2p2c;
    }

    public String getClocktonproposals2p2dr1() {
        return Clocktonproposals2p2dr1;
    }

    public void setClocktonproposals2p2dr1(String clocktonproposals2p2dr1) {
        Clocktonproposals2p2dr1 = clocktonproposals2p2dr1;
    }

    public String getClocktonproposals2p2dr2() {
        return Clocktonproposals2p2dr2;
    }

    public void setClocktonproposals2p2dr2(String clocktonproposals2p2dr2) {
        Clocktonproposals2p2dr2 = clocktonproposals2p2dr2;
    }

    public String getClocktonproposals2p2dr3() {
        return Clocktonproposals2p2dr3;
    }

    public void setClocktonproposals2p2dr3(String clocktonproposals2p2dr3) {
        Clocktonproposals2p2dr3 = clocktonproposals2p2dr3;
    }

    public String getClocktonproposals2p2e() {
        return Clocktonproposals2p2e;
    }

    public void setClocktonproposals2p2e(String clocktonproposals2p2e) {
        Clocktonproposals2p2e = clocktonproposals2p2e;
    }

    public String getClocktonproposals2p2f() {
        return Clocktonproposals2p2f;
    }

    public void setClocktonproposals2p2f(String clocktonproposals2p2f) {
        Clocktonproposals2p2f = clocktonproposals2p2f;
    }

    public String getClocktonproposals2p2g() {
        return Clocktonproposals2p2g;
    }

    public void setClocktonproposals2p2g(String clocktonproposals2p2g) {
        Clocktonproposals2p2g = clocktonproposals2p2g;
    }

    public String getClocktonproposals2p2h() {
        return Clocktonproposals2p2h;
    }

    public void setClocktonproposals2p2h(String clocktonproposals2p2h) {
        Clocktonproposals2p2h = clocktonproposals2p2h;
    }

    public String getClocktonproposals2p2i() {
        return Clocktonproposals2p2i;
    }

    public void setClocktonproposals2p2i(String clocktonproposals2p2i) {
        Clocktonproposals2p2i = clocktonproposals2p2i;
    }

    public String getClocktonproposals2p2j() {
        return Clocktonproposals2p2j;
    }

    public void setClocktonproposals2p2j(String clocktonproposals2p2j) {
        Clocktonproposals2p2j = clocktonproposals2p2j;
    }

    public String getClocktonproposals2p2k() {
        return Clocktonproposals2p2k;
    }

    public void setClocktonproposals2p2k(String clocktonproposals2p2k) {
        Clocktonproposals2p2k = clocktonproposals2p2k;
    }

    public String getClocktonproposals2p2l() {
        return Clocktonproposals2p2l;
    }

    public void setClocktonproposals2p2l(String clocktonproposals2p2l) {
        Clocktonproposals2p2l = clocktonproposals2p2l;
    }

    public Boolean getClocktonproposals2p2m() {
        return Clocktonproposals2p2m;
    }

    public void setClocktonproposals2p2m(Boolean clocktonproposals2p2m) {
        Clocktonproposals2p2m = clocktonproposals2p2m;
    }

    public Boolean getClocktonproposals2p2n() {
        return Clocktonproposals2p2n;
    }

    public void setClocktonproposals2p2n(Boolean clocktonproposals2p2n) {
        Clocktonproposals2p2n = clocktonproposals2p2n;
    }

    public Boolean getClocktonproposals2p2o() {
        return Clocktonproposals2p2o;
    }

    public void setClocktonproposals2p2o(Boolean clocktonproposals2p2o) {
        Clocktonproposals2p2o = clocktonproposals2p2o;
    }

    public Boolean getClocktonproposals2p2p() {
        return Clocktonproposals2p2p;
    }

    public void setClocktonproposals2p2p(Boolean clocktonproposals2p2p) {
        Clocktonproposals2p2p = clocktonproposals2p2p;
    }

    public Boolean getClocktonproposals2p2q() {
        return Clocktonproposals2p2q;
    }

    public void setClocktonproposals2p2q(Boolean clocktonproposals2p2q) {
        Clocktonproposals2p2q = clocktonproposals2p2q;
    }

    public Boolean getClocktonproposals2p2r() {
        return Clocktonproposals2p2r;
    }

    public void setClocktonproposals2p2r(Boolean clocktonproposals2p2r) {
        Clocktonproposals2p2r = clocktonproposals2p2r;
    }

    public Boolean getClocktonproposals2p2s() {
        return Clocktonproposals2p2s;
    }

    public void setClocktonproposals2p2s(Boolean clocktonproposals2p2s) {
        Clocktonproposals2p2s = clocktonproposals2p2s;
    }

    public String getClocktonproposals2p2s1() {
        return Clocktonproposals2p2s1;
    }

    public void setClocktonproposals2p2s1(String clocktonproposals2p2s1) {
        Clocktonproposals2p2s1 = clocktonproposals2p2s1;
    }

    public String getClocktonproposals2p6a() {
        return Clocktonproposals2p6a;
    }

    public void setClocktonproposals2p6a(String clocktonproposals2p6a) {
        Clocktonproposals2p6a = clocktonproposals2p6a;
    }

    public String getClocktonproposals2p6b() {
        return Clocktonproposals2p6b;
    }

    public void setClocktonproposals2p6b(String clocktonproposals2p6b) {
        Clocktonproposals2p6b = clocktonproposals2p6b;
    }

    public String getClocktonproposals2p6b1() {
        return Clocktonproposals2p6b1;
    }

    public void setClocktonproposals2p6b1(String clocktonproposals2p6b1) {
        Clocktonproposals2p6b1 = clocktonproposals2p6b1;
    }

    public String getClocktonproposals2p6b2() {
        return Clocktonproposals2p6b2;
    }

    public void setClocktonproposals2p6b2(String clocktonproposals2p6b2) {
        Clocktonproposals2p6b2 = clocktonproposals2p6b2;
    }

    public String getClocktonproposals2p6c() {
        return Clocktonproposals2p6c;
    }

    public void setClocktonproposals2p6c(String clocktonproposals2p6c) {
        Clocktonproposals2p6c = clocktonproposals2p6c;
    }

    public String getClocktonproposals2p6d() {
        return Clocktonproposals2p6d;
    }

    public void setClocktonproposals2p6d(String clocktonproposals2p6d) {
        Clocktonproposals2p6d = clocktonproposals2p6d;
    }

    public String getClocktonproposals2p6d2() {
        return Clocktonproposals2p6d2;
    }

    public void setClocktonproposals2p6d2(String clocktonproposals2p6d2) {
        Clocktonproposals2p6d2 = clocktonproposals2p6d2;
    }

    public String getClocktonproposals2p6d3() {
        return Clocktonproposals2p6d3;
    }

    public void setClocktonproposals2p6d3(String clocktonproposals2p6d3) {
        Clocktonproposals2p6d3 = clocktonproposals2p6d3;
    }

    public String getClocktonproposals2p6e() {
        return Clocktonproposals2p6e;
    }

    public void setClocktonproposals2p6e(String clocktonproposals2p6e) {
        Clocktonproposals2p6e = clocktonproposals2p6e;
    }

    public String getClocktonproposals2p7a() {
        return Clocktonproposals2p7a;
    }

    public void setClocktonproposals2p7a(String clocktonproposals2p7a) {
        Clocktonproposals2p7a = clocktonproposals2p7a;
    }

    public String getClocktonproposals2p7a1() {
        return Clocktonproposals2p7a1;
    }

    public void setClocktonproposals2p7a1(String clocktonproposals2p7a1) {
        Clocktonproposals2p7a1 = clocktonproposals2p7a1;
    }

    public String getClocktonproposals2p8() {
        return Clocktonproposals2p8;
    }

    public void setClocktonproposals2p8(String clocktonproposals2p8) {
        Clocktonproposals2p8 = clocktonproposals2p8;
    }

    public String getClocktonproposals2p8a1() {
        return Clocktonproposals2p8a1;
    }

    public void setClocktonproposals2p8a1(String clocktonproposals2p8a1) {
        Clocktonproposals2p8a1 = clocktonproposals2p8a1;
    }

    public String getClocktonproposals2p8a1Other() {
        return Clocktonproposals2p8a1Other;
    }

    public void setClocktonproposals2p8a1Other(String clocktonproposals2p8a1Other) {
        Clocktonproposals2p8a1Other = clocktonproposals2p8a1Other;
    }

    public String getClocktonproposals2p8b() {
        return Clocktonproposals2p8b;
    }

    public void setClocktonproposals2p8b(String clocktonproposals2p8b) {
        Clocktonproposals2p8b = clocktonproposals2p8b;
    }

    public String getClocktonproposals2p9() {
        return Clocktonproposals2p9;
    }

    public void setClocktonproposals2p9(String clocktonproposals2p9) {
        Clocktonproposals2p9 = clocktonproposals2p9;
    }

    public String getClocktonproposals2p9a() {
        return Clocktonproposals2p9a;
    }

    public void setClocktonproposals2p9a(String clocktonproposals2p9a) {
        Clocktonproposals2p9a = clocktonproposals2p9a;
    }

    public String getClocktonproposals2p10() {
        return Clocktonproposals2p10;
    }

    public void setClocktonproposals2p10(String clocktonproposals2p10) {
        Clocktonproposals2p10 = clocktonproposals2p10;
    }

    public String getClocktonproposals2p10a() {
        return Clocktonproposals2p10a;
    }

    public void setClocktonproposals2p10a(String clocktonproposals2p10a) {
        Clocktonproposals2p10a = clocktonproposals2p10a;
    }

    public String getClocktonproposals2p11() {
        return Clocktonproposals2p11;
    }

    public void setClocktonproposals2p11(String clocktonproposals2p11) {
        Clocktonproposals2p11 = clocktonproposals2p11;
    }

    public String getClocktonproposals2p12() {
        return Clocktonproposals2p12;
    }

    public void setClocktonproposals2p12(String clocktonproposals2p12) {
        Clocktonproposals2p12 = clocktonproposals2p12;
    }

    public String getClocktonproposals2p13() {
        return Clocktonproposals2p13;
    }

    public void setClocktonproposals2p13(String clocktonproposals2p13) {
        Clocktonproposals2p13 = clocktonproposals2p13;
    }

    public String getClocktonproposals2p13a() {
        return Clocktonproposals2p13a;
    }

    public void setClocktonproposals2p13a(String clocktonproposals2p13a) {
        Clocktonproposals2p13a = clocktonproposals2p13a;
    }

    public String getClocktonproposals2p13b1() {
        return Clocktonproposals2p13b1;
    }

    public void setClocktonproposals2p13b1(String clocktonproposals2p13b1) {
        Clocktonproposals2p13b1 = clocktonproposals2p13b1;
    }

    public String getClocktonproposals2p13b1Other() {
        return Clocktonproposals2p13b1Other;
    }

    public void setClocktonproposals2p13b1Other(String clocktonproposals2p13b1Other) {
        Clocktonproposals2p13b1Other = clocktonproposals2p13b1Other;
    }

    public String getClocktonproposals2p14() {
        return Clocktonproposals2p14;
    }

    public void setClocktonproposals2p14(String clocktonproposals2p14) {
        Clocktonproposals2p14 = clocktonproposals2p14;
    }

    public String getClocktonproposals2p14a() {
        return Clocktonproposals2p14a;
    }

    public void setClocktonproposals2p14a(String clocktonproposals2p14a) {
        Clocktonproposals2p14a = clocktonproposals2p14a;
    }

    public String getClocktonproposals2p14aOther() {
        return Clocktonproposals2p14aOther;
    }

    public void setClocktonproposals2p14aOther(String clocktonproposals2p14aOther) {
        Clocktonproposals2p14aOther = clocktonproposals2p14aOther;
    }

    public String getClocktonproposals2p15() {
        return Clocktonproposals2p15;
    }

    public void setClocktonproposals2p15(String clocktonproposals2p15) {
        Clocktonproposals2p15 = clocktonproposals2p15;
    }

    public String getClocktonproposals2p15a1() {
        return Clocktonproposals2p15a1;
    }

    public void setClocktonproposals2p15a1(String clocktonproposals2p15a1) {
        Clocktonproposals2p15a1 = clocktonproposals2p15a1;
    }

    public String getClocktonproposals2p15a1Other() {
        return Clocktonproposals2p15a1Other;
    }

    public void setClocktonproposals2p15a1Other(String clocktonproposals2p15a1Other) {
        Clocktonproposals2p15a1Other = clocktonproposals2p15a1Other;
    }

    public String getClocktonproposals2p16() {
        return Clocktonproposals2p16;
    }

    public void setClocktonproposals2p16(String clocktonproposals2p16) {
        Clocktonproposals2p16 = clocktonproposals2p16;
    }

    public String getClocktonproposals2p17() {
        return Clocktonproposals2p17;
    }

    public void setClocktonproposals2p17(String clocktonproposals2p17) {
        Clocktonproposals2p17 = clocktonproposals2p17;
    }

    public String getClocktonproposals2p18() {
        return Clocktonproposals2p18;
    }

    public void setClocktonproposals2p18(String clocktonproposals2p18) {
        Clocktonproposals2p18 = clocktonproposals2p18;
    }

    public String getClocktonproposals2p19r1() {
        return Clocktonproposals2p19r1;
    }

    public void setClocktonproposals2p19r1(String clocktonproposals2p19r1) {
        Clocktonproposals2p19r1 = clocktonproposals2p19r1;
    }

    public String getClocktonproposals2p19r2() {
        return Clocktonproposals2p19r2;
    }

    public void setClocktonproposals2p19r2(String clocktonproposals2p19r2) {
        Clocktonproposals2p19r2 = clocktonproposals2p19r2;
    }

    public String getClocktonproposals2p19r3() {
        return Clocktonproposals2p19r3;
    }

    public void setClocktonproposals2p19r3(String clocktonproposals2p19r3) {
        Clocktonproposals2p19r3 = clocktonproposals2p19r3;
    }

    public String getClocktonproposals2p20r1() {
        return Clocktonproposals2p20r1;
    }

    public void setClocktonproposals2p20r1(String clocktonproposals2p20r1) {
        Clocktonproposals2p20r1 = clocktonproposals2p20r1;
    }

    public String getClocktonproposals2p20r2() {
        return Clocktonproposals2p20r2;
    }

    public void setClocktonproposals2p20r2(String clocktonproposals2p20r2) {
        Clocktonproposals2p20r2 = clocktonproposals2p20r2;
    }

    public String getClocktonproposals2p20Ar1() {
        return Clocktonproposals2p20Ar1;
    }

    public void setClocktonproposals2p20Ar1(String clocktonproposals2p20Ar1) {
        Clocktonproposals2p20Ar1 = clocktonproposals2p20Ar1;
    }

    public String getClocktonproposals3p1a() {
        return Clocktonproposals3p1a;
    }

    public void setClocktonproposals3p1a(String clocktonproposals3p1a) {
        Clocktonproposals3p1a = clocktonproposals3p1a;
    }

    public String getClocktonproposals3p1aRecords() {
        return Clocktonproposals3p1aRecords;
    }

    public void setClocktonproposals3p1aRecords(String clocktonproposals3p1aRecords) {
        Clocktonproposals3p1aRecords = clocktonproposals3p1aRecords;
    }

    public String getClocktonproposals3p1b() {
        return Clocktonproposals3p1b;
    }

    public void setClocktonproposals3p1b(String clocktonproposals3p1b) {
        Clocktonproposals3p1b = clocktonproposals3p1b;
    }

    public String getClocktonproposals3p1bRecords() {
        return Clocktonproposals3p1bRecords;
    }

    public void setClocktonproposals3p1bRecords(String clocktonproposals3p1bRecords) {
        Clocktonproposals3p1bRecords = clocktonproposals3p1bRecords;
    }

    public String getClocktonproposals3p1c() {
        return Clocktonproposals3p1c;
    }

    public void setClocktonproposals3p1c(String clocktonproposals3p1c) {
        Clocktonproposals3p1c = clocktonproposals3p1c;
    }

    public String getClocktonproposals3p1cRecords() {
        return Clocktonproposals3p1cRecords;
    }

    public void setClocktonproposals3p1cRecords(String clocktonproposals3p1cRecords) {
        Clocktonproposals3p1cRecords = clocktonproposals3p1cRecords;
    }

    public String getClocktonproposals3p1d() {
        return Clocktonproposals3p1d;
    }

    public void setClocktonproposals3p1d(String clocktonproposals3p1d) {
        Clocktonproposals3p1d = clocktonproposals3p1d;
    }

    public String getClocktonproposals3p1dRecords() {
        return Clocktonproposals3p1dRecords;
    }

    public void setClocktonproposals3p1dRecords(String clocktonproposals3p1dRecords) {
        Clocktonproposals3p1dRecords = clocktonproposals3p1dRecords;
    }

    public String getClocktonproposals3p1e() {
        return Clocktonproposals3p1e;
    }

    public void setClocktonproposals3p1e(String clocktonproposals3p1e) {
        Clocktonproposals3p1e = clocktonproposals3p1e;
    }

    public String getClocktonproposals3p1eRecords() {
        return Clocktonproposals3p1eRecords;
    }

    public void setClocktonproposals3p1eRecords(String clocktonproposals3p1eRecords) {
        Clocktonproposals3p1eRecords = clocktonproposals3p1eRecords;
    }

    public String getClocktonproposals3p1f() {
        return Clocktonproposals3p1f;
    }

    public void setClocktonproposals3p1f(String clocktonproposals3p1f) {
        Clocktonproposals3p1f = clocktonproposals3p1f;
    }

    public String getClocktonproposals3p1fRecords() {
        return Clocktonproposals3p1fRecords;
    }

    public void setClocktonproposals3p1fRecords(String clocktonproposals3p1fRecords) {
        Clocktonproposals3p1fRecords = clocktonproposals3p1fRecords;
    }

    public String getClocktonproposals3p1fExplain() {
        return Clocktonproposals3p1fExplain;
    }

    public void setClocktonproposals3p1fExplain(String clocktonproposals3p1fExplain) {
        Clocktonproposals3p1fExplain = clocktonproposals3p1fExplain;
    }

    public String getClocktonproposals3p2() {
        return Clocktonproposals3p2;
    }

    public void setClocktonproposals3p2(String clocktonproposals3p2) {
        Clocktonproposals3p2 = clocktonproposals3p2;
    }

    public String getClocktonproposals3p3() {
        return Clocktonproposals3p3;
    }

    public void setClocktonproposals3p3(String clocktonproposals3p3) {
        Clocktonproposals3p3 = clocktonproposals3p3;
    }

    public String getClocktonproposals3p4() {
        return Clocktonproposals3p4;
    }

    public void setClocktonproposals3p4(String clocktonproposals3p4) {
        Clocktonproposals3p4 = clocktonproposals3p4;
    }

    public String getClocktonproposals3p4Volume() {
        return Clocktonproposals3p4Volume;
    }

    public void setClocktonproposals3p4Volume(String clocktonproposals3p4Volume) {
        Clocktonproposals3p4Volume = clocktonproposals3p4Volume;
    }

    public String getClocktonproposals3p5() {
        return Clocktonproposals3p5;
    }

    public void setClocktonproposals3p5(String clocktonproposals3p5) {
        Clocktonproposals3p5 = clocktonproposals3p5;
    }

    public String getClocktonproposals4p1() {
        return Clocktonproposals4p1;
    }

    public void setClocktonproposals4p1(String clocktonproposals4p1) {
        Clocktonproposals4p1 = clocktonproposals4p1;
    }

    public String getClocktonproposals4p2() {
        return Clocktonproposals4p2;
    }

    public void setClocktonproposals4p2(String clocktonproposals4p2) {
        Clocktonproposals4p2 = clocktonproposals4p2;
    }

    public String getClocktonproposals4p3() {
        return Clocktonproposals4p3;
    }

    public void setClocktonproposals4p3(String clocktonproposals4p3) {
        Clocktonproposals4p3 = clocktonproposals4p3;
    }

    public String getClocktonproposals4p4() {
        return Clocktonproposals4p4;
    }

    public void setClocktonproposals4p4(String clocktonproposals4p4) {
        Clocktonproposals4p4 = clocktonproposals4p4;
    }

    public String getClocktonproposals4p5() {
        return Clocktonproposals4p5;
    }

    public void setClocktonproposals4p5(String clocktonproposals4p5) {
        Clocktonproposals4p5 = clocktonproposals4p5;
    }

    public String getClocktonproposals4p6() {
        return Clocktonproposals4p6;
    }

    public void setClocktonproposals4p6(String clocktonproposals4p6) {
        Clocktonproposals4p6 = clocktonproposals4p6;
    }

    public String getClocktonproposals4p7() {
        return Clocktonproposals4p7;
    }

    public void setClocktonproposals4p7(String clocktonproposals4p7) {
        Clocktonproposals4p7 = clocktonproposals4p7;
    }

    public String getClocktonproposals4p8() {
        return Clocktonproposals4p8;
    }

    public void setClocktonproposals4p8(String clocktonproposals4p8) {
        Clocktonproposals4p8 = clocktonproposals4p8;
    }

    public String getClocktonproposals4p9() {
        return Clocktonproposals4p9;
    }

    public void setClocktonproposals4p9(String clocktonproposals4p9) {
        Clocktonproposals4p9 = clocktonproposals4p9;
    }

    public String getClocktonproposals5p1a() {
        return Clocktonproposals5p1a;
    }

    public void setClocktonproposals5p1a(String clocktonproposals5p1a) {
        Clocktonproposals5p1a = clocktonproposals5p1a;
    }

    public String getClocktonproposals5p1b() {
        return Clocktonproposals5p1b;
    }

    public void setClocktonproposals5p1b(String clocktonproposals5p1b) {
        Clocktonproposals5p1b = clocktonproposals5p1b;
    }

    public String getClocktonproposals5p1b1() {
        return Clocktonproposals5p1b1;
    }

    public void setClocktonproposals5p1b1(String clocktonproposals5p1b1) {
        Clocktonproposals5p1b1 = clocktonproposals5p1b1;
    }

    public String getClocktonproposals5p1b2() {
        return Clocktonproposals5p1b2;
    }

    public void setClocktonproposals5p1b2(String clocktonproposals5p1b2) {
        Clocktonproposals5p1b2 = clocktonproposals5p1b2;
    }

    public String getClocktonproposals5p1b3() {
        return Clocktonproposals5p1b3;
    }

    public void setClocktonproposals5p1b3(String clocktonproposals5p1b3) {
        Clocktonproposals5p1b3 = clocktonproposals5p1b3;
    }

    public String getClocktonproposals5p1b4() {
        return Clocktonproposals5p1b4;
    }

    public void setClocktonproposals5p1b4(String clocktonproposals5p1b4) {
        Clocktonproposals5p1b4 = clocktonproposals5p1b4;
    }

    public String getClocktonproposals5p1c() {
        return Clocktonproposals5p1c;
    }

    public void setClocktonproposals5p1c(String clocktonproposals5p1c) {
        Clocktonproposals5p1c = clocktonproposals5p1c;
    }

    public String getClocktonproposals5p1d() {
        return Clocktonproposals5p1d;
    }

    public void setClocktonproposals5p1d(String clocktonproposals5p1d) {
        Clocktonproposals5p1d = clocktonproposals5p1d;
    }

    public String getClocktonproposals5p1e() {
        return Clocktonproposals5p1e;
    }

    public void setClocktonproposals5p1e(String clocktonproposals5p1e) {
        Clocktonproposals5p1e = clocktonproposals5p1e;
    }

    public String getClocktonproposals5p1f() {
        return Clocktonproposals5p1f;
    }

    public void setClocktonproposals5p1f(String clocktonproposals5p1f) {
        Clocktonproposals5p1f = clocktonproposals5p1f;
    }

    public String getClocktonproposals5p2a() {
        return Clocktonproposals5p2a;
    }

    public void setClocktonproposals5p2a(String clocktonproposals5p2a) {
        Clocktonproposals5p2a = clocktonproposals5p2a;
    }

    public String getClocktonproposals5p2b() {
        return Clocktonproposals5p2b;
    }

    public void setClocktonproposals5p2b(String clocktonproposals5p2b) {
        Clocktonproposals5p2b = clocktonproposals5p2b;
    }

    public String getClocktonproposals5p2c() {
        return Clocktonproposals5p2c;
    }

    public void setClocktonproposals5p2c(String clocktonproposals5p2c) {
        Clocktonproposals5p2c = clocktonproposals5p2c;
    }

    public String getClocktonproposals5p2d() {
        return Clocktonproposals5p2d;
    }

    public void setClocktonproposals5p2d(String clocktonproposals5p2d) {
        Clocktonproposals5p2d = clocktonproposals5p2d;
    }

    public String getClocktonproposals5p2e() {
        return Clocktonproposals5p2e;
    }

    public void setClocktonproposals5p2e(String clocktonproposals5p2e) {
        Clocktonproposals5p2e = clocktonproposals5p2e;
    }

    public String getClocktonproposals5p3() {
        return Clocktonproposals5p3;
    }

    public void setClocktonproposals5p3(String clocktonproposals5p3) {
        Clocktonproposals5p3 = clocktonproposals5p3;
    }

    public String getClocktonproposals5p4() {
        return Clocktonproposals5p4;
    }

    public void setClocktonproposals5p4(String clocktonproposals5p4) {
        Clocktonproposals5p4 = clocktonproposals5p4;
    }

    public String getClocktonproposals5p5() {
        return Clocktonproposals5p5;
    }

    public void setClocktonproposals5p5(String clocktonproposals5p5) {
        Clocktonproposals5p5 = clocktonproposals5p5;
    }

    public String getClocktonproposals6p1() {
        return Clocktonproposals6p1;
    }

    public void setClocktonproposals6p1(String clocktonproposals6p1) {
        Clocktonproposals6p1 = clocktonproposals6p1;
    }

    public String getClocktonproposals6p1a() {
        return Clocktonproposals6p1a;
    }

    public void setClocktonproposals6p1a(String clocktonproposals6p1a) {
        Clocktonproposals6p1a = clocktonproposals6p1a;
    }

    public String getClocktonproposals6p1b() {
        return Clocktonproposals6p1b;
    }

    public void setClocktonproposals6p1b(String clocktonproposals6p1b) {
        Clocktonproposals6p1b = clocktonproposals6p1b;
    }

    public String getClocktonproposals6p2() {
        return Clocktonproposals6p2;
    }

    public void setClocktonproposals6p2(String clocktonproposals6p2) {
        Clocktonproposals6p2 = clocktonproposals6p2;
    }

    public String getClocktonproposals6p3() {
        return Clocktonproposals6p3;
    }

    public void setClocktonproposals6p3(String clocktonproposals6p3) {
        Clocktonproposals6p3 = clocktonproposals6p3;
    }

    public String getClocktonproposals6p4() {
        return Clocktonproposals6p4;
    }

    public void setClocktonproposals6p4(String clocktonproposals6p4) {
        Clocktonproposals6p4 = clocktonproposals6p4;
    }

    public String getClocktonproposals6p5() {
        return Clocktonproposals6p5;
    }

    public void setClocktonproposals6p5(String clocktonproposals6p5) {
        Clocktonproposals6p5 = clocktonproposals6p5;
    }

    public String getClocktonproposals6p6() {
        return Clocktonproposals6p6;
    }

    public void setClocktonproposals6p6(String clocktonproposals6p6) {
        Clocktonproposals6p6 = clocktonproposals6p6;
    }

    public String getClocktonproposals7p1() {
        return Clocktonproposals7p1;
    }

    public void setClocktonproposals7p1(String clocktonproposals7p1) {
        Clocktonproposals7p1 = clocktonproposals7p1;
    }

    public String getClocktonproposals7p2() {
        return Clocktonproposals7p2;
    }

    public void setClocktonproposals7p2(String clocktonproposals7p2) {
        Clocktonproposals7p2 = clocktonproposals7p2;
    }

    public String getClocktonproposals7p2a() {
        return Clocktonproposals7p2a;
    }

    public void setClocktonproposals7p2a(String clocktonproposals7p2a) {
        Clocktonproposals7p2a = clocktonproposals7p2a;
    }

    public String getClocktonproposals7p3() {
        return Clocktonproposals7p3;
    }

    public void setClocktonproposals7p3(String clocktonproposals7p3) {
        Clocktonproposals7p3 = clocktonproposals7p3;
    }

    public String getClocktonproposals7p4() {
        return Clocktonproposals7p4;
    }

    public void setClocktonproposals7p4(String clocktonproposals7p4) {
        Clocktonproposals7p4 = clocktonproposals7p4;
    }

    public String getClocktonproposals7p5() {
        return Clocktonproposals7p5;
    }

    public void setClocktonproposals7p5(String clocktonproposals7p5) {
        Clocktonproposals7p5 = clocktonproposals7p5;
    }

    public String getClocktonproposals7p6() {
        return Clocktonproposals7p6;
    }

    public void setClocktonproposals7p6(String clocktonproposals7p6) {
        Clocktonproposals7p6 = clocktonproposals7p6;
    }

    public String getClocktonproposals8p1() {
        return Clocktonproposals8p1;
    }

    public void setClocktonproposals8p1(String clocktonproposals8p1) {
        Clocktonproposals8p1 = clocktonproposals8p1;
    }

    public String getClocktonproposals8p2() {
        return Clocktonproposals8p2;
    }

    public void setClocktonproposals8p2(String clocktonproposals8p2) {
        Clocktonproposals8p2 = clocktonproposals8p2;
    }

    public String getClocktonproposals8p3() {
        return Clocktonproposals8p3;
    }

    public void setClocktonproposals8p3(String clocktonproposals8p3) {
        Clocktonproposals8p3 = clocktonproposals8p3;
    }

    public String getClocktonproposals8p4() {
        return Clocktonproposals8p4;
    }

    public void setClocktonproposals8p4(String clocktonproposals8p4) {
        Clocktonproposals8p4 = clocktonproposals8p4;
    }

    public String getClocktonproposals8p5() {
        return Clocktonproposals8p5;
    }

    public void setClocktonproposals8p5(String clocktonproposals8p5) {
        Clocktonproposals8p5 = clocktonproposals8p5;
    }

    public String getClocktonproposals9p1() {
        return Clocktonproposals9p1;
    }

    public void setClocktonproposals9p1(String clocktonproposals9p1) {
        Clocktonproposals9p1 = clocktonproposals9p1;
    }

    public String getClocktonproposals9p1a() {
        return Clocktonproposals9p1a;
    }

    public void setClocktonproposals9p1a(String clocktonproposals9p1a) {
        Clocktonproposals9p1a = clocktonproposals9p1a;
    }

    public String getClocktonproposals9p2() {
        return Clocktonproposals9p2;
    }

    public void setClocktonproposals9p2(String clocktonproposals9p2) {
        Clocktonproposals9p2 = clocktonproposals9p2;
    }

    public String getClocktonproposals9p2a() {
        return Clocktonproposals9p2a;
    }

    public void setClocktonproposals9p2a(String clocktonproposals9p2a) {
        Clocktonproposals9p2a = clocktonproposals9p2a;
    }

    public String getClocktonproposals9p3() {
        return Clocktonproposals9p3;
    }

    public void setClocktonproposals9p3(String clocktonproposals9p3) {
        Clocktonproposals9p3 = clocktonproposals9p3;
    }

    public String getClocktonproposals9p4() {
        return Clocktonproposals9p4;
    }

    public void setClocktonproposals9p4(String clocktonproposals9p4) {
        Clocktonproposals9p4 = clocktonproposals9p4;
    }

    public String getClocktonproposals9p5() {
        return Clocktonproposals9p5;
    }

    public void setClocktonproposals9p5(String clocktonproposals9p5) {
        Clocktonproposals9p5 = clocktonproposals9p5;
    }

    public String getClocktonproposals9p5a() {
        return Clocktonproposals9p5a;
    }

    public void setClocktonproposals9p5a(String clocktonproposals9p5a) {
        Clocktonproposals9p5a = clocktonproposals9p5a;
    }

    public String getClocktonproposals9p6() {
        return Clocktonproposals9p6;
    }

    public void setClocktonproposals9p6(String clocktonproposals9p6) {
        Clocktonproposals9p6 = clocktonproposals9p6;
    }

    public String getClocktonproposals10p1() {
        return Clocktonproposals10p1;
    }

    public void setClocktonproposals10p1(String clocktonproposals10p1) {
        Clocktonproposals10p1 = clocktonproposals10p1;
    }

    public String getClocktonproposals10p1a() {
        return Clocktonproposals10p1a;
    }

    public void setClocktonproposals10p1a(String clocktonproposals10p1a) {
        Clocktonproposals10p1a = clocktonproposals10p1a;
    }

    public String getClocktonproposals10p2() {
        return Clocktonproposals10p2;
    }

    public void setClocktonproposals10p2(String clocktonproposals10p2) {
        Clocktonproposals10p2 = clocktonproposals10p2;
    }

    public String getClocktonproposals10p3() {
        return Clocktonproposals10p3;
    }

    public void setClocktonproposals10p3(String clocktonproposals10p3) {
        Clocktonproposals10p3 = clocktonproposals10p3;
    }

    public String getClocktonproposals10p3a() {
        return Clocktonproposals10p3a;
    }

    public void setClocktonproposals10p3a(String clocktonproposals10p3a) {
        Clocktonproposals10p3a = clocktonproposals10p3a;
    }

    public String getClocktonproposals10p4() {
        return Clocktonproposals10p4;
    }

    public void setClocktonproposals10p4(String clocktonproposals10p4) {
        Clocktonproposals10p4 = clocktonproposals10p4;
    }

    public String getClocktonproposals10p5() {
        return Clocktonproposals10p5;
    }

    public void setClocktonproposals10p5(String clocktonproposals10p5) {
        Clocktonproposals10p5 = clocktonproposals10p5;
    }

    public String getClocktonproposals11p1() {
        return Clocktonproposals11p1;
    }

    public void setClocktonproposals11p1(String clocktonproposals11p1) {
        Clocktonproposals11p1 = clocktonproposals11p1;
    }

    public String getClocktonproposals11p1a() {
        return Clocktonproposals11p1a;
    }

    public void setClocktonproposals11p1a(String clocktonproposals11p1a) {
        Clocktonproposals11p1a = clocktonproposals11p1a;
    }

    public String getClocktonproposals11p2() {
        return Clocktonproposals11p2;
    }

    public void setClocktonproposals11p2(String clocktonproposals11p2) {
        Clocktonproposals11p2 = clocktonproposals11p2;
    }

    public String getClocktonproposals11p2a() {
        return Clocktonproposals11p2a;
    }

    public void setClocktonproposals11p2a(String clocktonproposals11p2a) {
        Clocktonproposals11p2a = clocktonproposals11p2a;
    }

    public String getClocktonproposals11p3() {
        return Clocktonproposals11p3;
    }

    public void setClocktonproposals11p3(String clocktonproposals11p3) {
        Clocktonproposals11p3 = clocktonproposals11p3;
    }

    public String getClocktonproposals11p3a() {
        return Clocktonproposals11p3a;
    }

    public void setClocktonproposals11p3a(String clocktonproposals11p3a) {
        Clocktonproposals11p3a = clocktonproposals11p3a;
    }

    public String getClocktonproposals11p4() {
        return Clocktonproposals11p4;
    }

    public void setClocktonproposals11p4(String clocktonproposals11p4) {
        Clocktonproposals11p4 = clocktonproposals11p4;
    }

    public String getClocktonproposals12p1() {
        return Clocktonproposals12p1;
    }

    public void setClocktonproposals12p1(String clocktonproposals12p1) {
        Clocktonproposals12p1 = clocktonproposals12p1;
    }

    public String getClocktonproposals12p2() {
        return Clocktonproposals12p2;
    }

    public void setClocktonproposals12p2(String clocktonproposals12p2) {
        Clocktonproposals12p2 = clocktonproposals12p2;
    }

    public String getClocktonproposals12p3() {
        return Clocktonproposals12p3;
    }

    public void setClocktonproposals12p3(String clocktonproposals12p3) {
        Clocktonproposals12p3 = clocktonproposals12p3;
    }

    public String getClocktonproposals12p4() {
        return Clocktonproposals12p4;
    }

    public void setClocktonproposals12p4(String clocktonproposals12p4) {
        Clocktonproposals12p4 = clocktonproposals12p4;
    }

    public String getClocktonproposals12p5() {
        return Clocktonproposals12p5;
    }

    public void setClocktonproposals12p5(String clocktonproposals12p5) {
        Clocktonproposals12p5 = clocktonproposals12p5;
    }

    public String getClocktonproposals13p1() {
        return Clocktonproposals13p1;
    }

    public void setClocktonproposals13p1(String clocktonproposals13p1) {
        Clocktonproposals13p1 = clocktonproposals13p1;
    }

    public String getClocktonproposals13p1a() {
        return Clocktonproposals13p1a;
    }

    public void setClocktonproposals13p1a(String clocktonproposals13p1a) {
        Clocktonproposals13p1a = clocktonproposals13p1a;
    }

    public String getClocktonproposals13p2() {
        return Clocktonproposals13p2;
    }

    public void setClocktonproposals13p2(String clocktonproposals13p2) {
        Clocktonproposals13p2 = clocktonproposals13p2;
    }

    public String getClocktonproposals13p2a() {
        return Clocktonproposals13p2a;
    }

    public void setClocktonproposals13p2a(String clocktonproposals13p2a) {
        Clocktonproposals13p2a = clocktonproposals13p2a;
    }

    public String getClocktonproposals13p3() {
        return Clocktonproposals13p3;
    }

    public void setClocktonproposals13p3(String clocktonproposals13p3) {
        Clocktonproposals13p3 = clocktonproposals13p3;
    }

    public String getClocktonproposals13p3a() {
        return Clocktonproposals13p3a;
    }

    public void setClocktonproposals13p3a(String clocktonproposals13p3a) {
        Clocktonproposals13p3a = clocktonproposals13p3a;
    }

    public String getClocktonproposals13p4() {
        return Clocktonproposals13p4;
    }

    public void setClocktonproposals13p4(String clocktonproposals13p4) {
        Clocktonproposals13p4 = clocktonproposals13p4;
    }

    public String getClocktonproposals14p1() {
        return Clocktonproposals14p1;
    }

    public void setClocktonproposals14p1(String clocktonproposals14p1) {
        Clocktonproposals14p1 = clocktonproposals14p1;
    }

    public String getClocktonproposals14p1a() {
        return Clocktonproposals14p1a;
    }

    public void setClocktonproposals14p1a(String clocktonproposals14p1a) {
        Clocktonproposals14p1a = clocktonproposals14p1a;
    }

    public String getClocktonproposals14p2() {
        return Clocktonproposals14p2;
    }

    public void setClocktonproposals14p2(String clocktonproposals14p2) {
        Clocktonproposals14p2 = clocktonproposals14p2;
    }

    public String getClocktonproposals14p3() {
        return Clocktonproposals14p3;
    }

    public void setClocktonproposals14p3(String clocktonproposals14p3) {
        Clocktonproposals14p3 = clocktonproposals14p3;
    }

    public String getClocktonproposals14p4() {
        return Clocktonproposals14p4;
    }

    public void setClocktonproposals14p4(String clocktonproposals14p4) {
        Clocktonproposals14p4 = clocktonproposals14p4;
    }

    public String getClocktonproposals14p5() {
        return Clocktonproposals14p5;
    }

    public void setClocktonproposals14p5(String clocktonproposals14p5) {
        Clocktonproposals14p5 = clocktonproposals14p5;
    }

    public String getClocktonproposals15p1() {
        return Clocktonproposals15p1;
    }

    public void setClocktonproposals15p1(String clocktonproposals15p1) {
        Clocktonproposals15p1 = clocktonproposals15p1;
    }

    public String getClocktonproposals15p2() {
        return Clocktonproposals15p2;
    }

    public void setClocktonproposals15p2(String clocktonproposals15p2) {
        Clocktonproposals15p2 = clocktonproposals15p2;
    }

    public String getClocktonproposals15p3() {
        return Clocktonproposals15p3;
    }

    public void setClocktonproposals15p3(String clocktonproposals15p3) {
        Clocktonproposals15p3 = clocktonproposals15p3;
    }

    public String getClocktonproposals15p4() {
        return Clocktonproposals15p4;
    }

    public void setClocktonproposals15p4(String clocktonproposals15p4) {
        Clocktonproposals15p4 = clocktonproposals15p4;
    }

    public String getClocktonproposals15p5() {
        return Clocktonproposals15p5;
    }

    public void setClocktonproposals15p5(String clocktonproposals15p5) {
        Clocktonproposals15p5 = clocktonproposals15p5;
    }

    public String getClocktonproposals16p1() {
        return Clocktonproposals16p1;
    }

    public void setClocktonproposals16p1(String clocktonproposals16p1) {
        Clocktonproposals16p1 = clocktonproposals16p1;
    }

    public String getClocktonproposals16p1a() {
        return Clocktonproposals16p1a;
    }

    public void setClocktonproposals16p1a(String clocktonproposals16p1a) {
        Clocktonproposals16p1a = clocktonproposals16p1a;
    }

    public String getClocktonproposals16p2() {
        return Clocktonproposals16p2;
    }

    public void setClocktonproposals16p2(String clocktonproposals16p2) {
        Clocktonproposals16p2 = clocktonproposals16p2;
    }

    public String getClocktonproposals16p2a() {
        return Clocktonproposals16p2a;
    }

    public void setClocktonproposals16p2a(String clocktonproposals16p2a) {
        Clocktonproposals16p2a = clocktonproposals16p2a;
    }

    public String getClocktonproposals16p3() {
        return Clocktonproposals16p3;
    }

    public void setClocktonproposals16p3(String clocktonproposals16p3) {
        Clocktonproposals16p3 = clocktonproposals16p3;
    }

    public String getClocktonproposals16p3a() {
        return Clocktonproposals16p3a;
    }

    public void setClocktonproposals16p3a(String clocktonproposals16p3a) {
        Clocktonproposals16p3a = clocktonproposals16p3a;
    }

    public String getClocktonproposals17p1() {
        return Clocktonproposals17p1;
    }

    public void setClocktonproposals17p1(String clocktonproposals17p1) {
        Clocktonproposals17p1 = clocktonproposals17p1;
    }

    public String getClocktonproposals17p2() {
        return Clocktonproposals17p2;
    }

    public void setClocktonproposals17p2(String clocktonproposals17p2) {
        Clocktonproposals17p2 = clocktonproposals17p2;
    }

    public String getClocktonproposals17p3() {
        return Clocktonproposals17p3;
    }

    public void setClocktonproposals17p3(String clocktonproposals17p3) {
        Clocktonproposals17p3 = clocktonproposals17p3;
    }

    public String getClocktonproposals17p4() {
        return Clocktonproposals17p4;
    }

    public void setClocktonproposals17p4(String clocktonproposals17p4) {
        Clocktonproposals17p4 = clocktonproposals17p4;
    }

    public String getClocktonproposals18p1() {
        return Clocktonproposals18p1;
    }

    public void setClocktonproposals18p1(String clocktonproposals18p1) {
        Clocktonproposals18p1 = clocktonproposals18p1;
    }

    public String getClocktonproposals18p2() {
        return Clocktonproposals18p2;
    }

    public void setClocktonproposals18p2(String clocktonproposals18p2) {
        Clocktonproposals18p2 = clocktonproposals18p2;
    }

    public String getClocktonproposals18p3() {
        return Clocktonproposals18p3;
    }

    public void setClocktonproposals18p3(String clocktonproposals18p3) {
        Clocktonproposals18p3 = clocktonproposals18p3;
    }

    public String getClocktonproposals18p4() {
        return Clocktonproposals18p4;
    }

    public void setClocktonproposals18p4(String clocktonproposals18p4) {
        Clocktonproposals18p4 = clocktonproposals18p4;
    }

    public LocalDateTime getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        CreatedAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }
}