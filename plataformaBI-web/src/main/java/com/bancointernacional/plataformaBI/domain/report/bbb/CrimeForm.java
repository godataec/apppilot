package com.bancointernacional.plataformaBI.domain.report.bbb;

import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CrimeForm {
    private String crime1;
    private String crime2;
    private String crime3;
    private String crime4a;
    private String crime4b;
    private String crime4c;
    private String crime4d;
    private String crime4e;
    private String crime4f;

    private String crime5ar1;
    private String crime5ar2;
    private String crime5br1;
    private String crime5br2;
    private String crime5cr1;
    private String crime5cr2;
    private String crime5dr1;
    private String crime5dr2;
    private String crime5er1;
    private String crime5er2;
    private String crime5fr1;
    private String crime5fr2;

    private String crime5fr1a1;
    private String crime5fr2b1;
    private String crime5fr1a2;
    private String crime5fr2b2;
    private String crime5fr1a3;
    private String crime5fr2b3;
    private String crime5fr1a4;
    private String crime5fr2b4;
    private String crime5fr1a5;
    private String crime5fr2b5;

    private String crime5gr1;
    private String crime5gr2;
    private String crime5hr1;
    private String crime5hr2;
    private String crime5ir1;
    private String crime5ir2;

    private String crime6ar1;
    private String crime6ar2;
    private String crime6br1;
    private String crime6br2;
    private String crime6cr1;
    private String crime6cr2;

    private String crimes2p1;

    private String crimes2p2ar1;
    private String crimes2p2ar2;
    private String crimes2p2ar3;
    private String crimes2p2ar4;
    private String crimes2p2ar5;

    private String crimes2p2b1r1;
    private String crimes2p2b1r2;
    private String crimes2p2b1r3;
    private String crimes2p2b1r4;

    private String crimes2p2b2r1;
    private String crimes2p2b2r2;
    private String crimes2p2b2r3;
    private String crimes2p2b2r4;
    private String crimes2p2b2r5;

    private String crimes2p2cr1;
    private String crimes2p2cr3;
    private String crimes2p2cr4;
    private String crimes2p2cr5;


    private String crimes2p2dr1;
    private String crimes2p2dr2;
    private String crimes2p2dr3;
    private String crimes2p2dr4;
    private String crimes2p2dr5;

    private String crimes3p1;

    private Boolean crimes2p2a;
    private Boolean crimes2p2b;
    private Boolean crimes2p2c;
    private Boolean crimes2p2d;
    private String crimes2p2f;
    private String crimes2p2g;

    private Boolean crimes2p3a;
    private String crimes2p3b;

    private String crimes4p1a;
    private String crimes4p1b;
    private String crimes4p1c;
    private String crimes4p1d;

    private String crimes4p2a;
    private String crimes4p2b;
    private String crimes4p2c;
    private String crimes4p2d;
    private String crimes4p2e;
    private String crimes4p2f;
    private String crimes4p2g;

    private String crimes4p3ar1;
    private String crimes4p3ar2;
    private String crimes4p3br1;
    private String crimes4p3br2;
    private String crimes4p3cr1;
    private String crimes4p3cr2;
    private String crimes4p3dr1;
    private String crimes4p3dr2;

    private String crimes4p4;

    @JsonProperty("tables")
    private Map<String, TableView> tables;

    private Boolean crimes5p2a;
    private String crimes5p2anormal;
    private String crimes5p2b;

    private Boolean crimes5p3a;
    private String crimes5p3anormal;
    private String crimes5p3b;

    private Boolean crimes5p4a;
    private String crimes5p4anormal;
    private String crimes5p4b;

    private Boolean crimes6p1a;
    private Boolean crimes6p1b;
    private Boolean crimes6p1c;

    private String crimes6p2;

    private Boolean crimes6p3a;
    private Boolean crimes6p3b;
    private Boolean crimes6p3c;

    private Boolean crimes6p4a;
    private Boolean crimes6p4a1;
    private Boolean crimes6p4a2;
    private Boolean crimes6p4a3;

    private Boolean crimes6p4b1;
    private Boolean crimes6p4b2;
    private Boolean crimes6p4b3;
    private Boolean crimes6p4b4;

    private Boolean crimes6p5;
    private String crimes6p5normal;
    private Boolean crimes6p5a;
    private String crimes6p5b;
    private String crimes6p5c;
    private Boolean crimes6p5d;
    private Boolean crimes6p5e;
    private Boolean crimes6p5f;

    private Boolean crimes6p6a;
    private String crimes6p6anormal;
    private String crimes6p6b;
    private Boolean crimes6p6c1;
    private String crimes6p6c1normal;
    private String crimes6p6c2;
    private Boolean crimes6p6d;
    private Boolean crimes6p6e1;
    private Boolean crimes6p6e2;
    private Boolean crimes6p6e3;
    private String crimes6p6e3normal;
    private Boolean crimes7p1ar1;
    private Boolean crimes7p1ar2;
    private Boolean crimes7p1ar3;

    private String crimes7p1b;

    private Boolean crimes7p1b1r1;
    private Boolean crimes7p1b1r2;
    private Boolean crimes7p1b1r3;

    private Boolean crimes7p1b2r1;
    private Boolean crimes7p1b2r2;
    private Boolean crimes7p1b2r3;

    private Boolean crimes7p1b3r1;
    private Boolean crimes7p1b3r2;
    private Boolean crimes7p1b3r3;

    private Boolean crimes7p1c1r1;
    private Boolean crimes7p1c1r2;
    private Boolean crimes7p1c1r3;

    private Boolean crimes7p1c2r1;
    private Boolean crimes7p1c2r2;
    private Boolean crimes7p1c2r3;

    private Boolean crimes7p1c3r1;
    private Boolean crimes7p1c3r2;
    private Boolean crimes7p1c3r3;

    private String crimes7p1att1;
    private String crimes7p1att2;
    private String crimes7p1att3;

    private Boolean crimes7p2ar1;
    private Boolean crimes7p2ar2;
    private Boolean crimes7p2ar3;

    private Boolean crimes7p2br1;
    private Boolean crimes7p2br2;
    private Boolean crimes7p2br3;

    private Boolean crimes7p2cr1;
    private Boolean crimes7p2cr2;
    private Boolean crimes7p2cr3;

    private Boolean crimes7p2dr1;
    private Boolean crimes7p2dr2;
    private Boolean crimes7p2dr3;

    private Boolean crimes7p2er1;
    private Boolean crimes7p2er2;
    private Boolean crimes7p2er3;

    private String crimes7p2att1;
    private String crimes7p2att2;
    private String crimes7p2att3;

    private Boolean crimes7p3ar1;
    private Boolean crimes7p3ar2;
    private Boolean crimes7p3ar3;

    private Boolean crimes7p3br1;
    private Boolean crimes7p3br2;
    private Boolean crimes7p3br3;

    private Boolean crimes7p4ar1;
    private Boolean crimes7p4ar2;
    private Boolean crimes7p4ar3;

    private String crimes7p4b;

    private Boolean crimes7p4b1r1;
    private Boolean crimes7p4b1r2;
    private Boolean crimes7p4b1r3;

    private String crimes7p4b2r1;
    private String crimes7p4b2r2;
    private String crimes7p4b2r3;

    private Boolean crimes7p5ar1;
    private Boolean crimes7p5ar2;
    private Boolean crimes7p5ar3;

    private Boolean crimes7p5br1;
    private Boolean crimes7p5br2;
    private Boolean crimes7p5br3;

    private Boolean crimes7p5cr1;
    private Boolean crimes7p5cr2;
    private Boolean crimes7p5cr3;

    private Boolean crimes7p5dr1;
    private Boolean crimes7p5dr2;
    private Boolean crimes7p5dr3;

    private Boolean crimes7p5er1;
    private Boolean crimes7p5er2;
    private Boolean crimes7p5er3;

    private Boolean crimes7p5fr1;
    private Boolean crimes7p5fr2;
    private Boolean crimes7p5fr3;

    private Boolean crimes7p5gr1;
    private Boolean crimes7p5gr2;
    private Boolean crimes7p5gr3;

    private Boolean crimes7p6ar1;
    private Boolean crimes7p6ar2;
    private Boolean crimes7p6ar3;

    private String crimes7p6b;

    private Boolean crimes7p6b1r1;
    private Boolean crimes7p6b1r2;
    private Boolean crimes7p6b1r3;

    private Boolean crimes7p6b2r1;
    private Boolean crimes7p6b2r2;
    private Boolean crimes7p6b2r3;

    private String crimes7p6c;

    private Boolean crimes7p6c1r1;
    private Boolean crimes7p6c1r2;
    private Boolean crimes7p6c1r3;

    private Boolean crimes7p6c2r1;
    private Boolean crimes7p6c2r2;
    private Boolean crimes7p6c2r3;

    private Boolean crimes7p6c3r1;
    private Boolean crimes7p6c3r2;
    private Boolean crimes7p6c3r3;

    private Boolean crimes7p6d1r1;
    private Boolean crimes7p6d1r2;
    private Boolean crimes7p6d1r3;

    private Boolean crimes7p6e1r1;
    private Boolean crimes7p6e1r2;
    private Boolean crimes7p6e1r3;

    private String crimes7p7a1r1;
    private String crimes7p7a1r2;
    private String crimes7p7a1r3;

    private String crimes7p7a2r1;
    private String crimes7p7a2r2;
    private String crimes7p7a2r3;

    private String crimes7p7a3r1;
    private String crimes7p7a3r2;
    private String crimes7p7a3r3;

    private Boolean crimes7p7b1r1;
    private String crimes7p7b1r1normal;
    private Boolean crimes7p7b1r2;
    private String crimes7p7b1r2normal;
    private Boolean crimes7p7b1r3;
    private String crimes7p7b1r3normal;

    private Boolean crimes7p7b2r1;
    private String crimes7p7b2r1normal;
    private Boolean crimes7p7b2r2;
    private String crimes7p7b2r2normal;
    private Boolean crimes7p7b2r3;
    private String crimes7p7b2r3normal;

    private Boolean crimes7p7cr1;
    private Boolean crimes7p7cr2;
    private Boolean crimes7p7cr3;

    private Boolean crimes7p8a1r1;
    private Boolean crimes7p8a1r2;
    private Boolean crimes7p8a1r3;

    private Boolean crimes7p8a1sr1;
    private Boolean crimes7p8a1sr2;
    private Boolean crimes7p8a1sr3;

    private String crimes7p8a2r1;

    private Boolean crimes7p8b1sr1;
    private String crimes7p8b1sr1normal;
    private Boolean crimes7p8b1sr2;
    private String crimes7p8b1sr2normal;
    private Boolean crimes7p8b1sr3;
    private String crimes7p8b1sr3normal;

    private Boolean crimes7p8b2r1;
    private Boolean crimes7p8b2r2;
    private Boolean crimes7p8b2r3;

    private Boolean crimes7p8cr1;
    private Boolean crimes7p8cr2;
    private Boolean crimes7p8cr3;


    private Boolean crimes7p8c1r1;
    private Boolean crimes7p8c1r2;
    private Boolean crimes7p8c1r3;

    private Boolean crimes8p1;

    private Boolean crimes8p2a1;
    private Boolean crimes8p2a2;
    private Boolean crimes8p2a3;
    private Boolean crimes8p2a4;
    private Boolean crimes8p2a5;
    private String crimes8p2a5normal;

    private String crimes8p3ar1;
    private String crimes8p3br1;
    private String crimes8p3cr1;

    private String crimes8p3ar2;
    private String crimes8p3br2;
    private String crimes8p3cr2;

    private Boolean crimes8p4;
    private Boolean crimes8p5;
    private Boolean crimes8p6;
    private String crimes8p6normal;

    private Boolean crimes8p6a;
    private Boolean crimes8p6b;
    private Boolean crimes8p6c;

    private Boolean crimes8p7;
    private Boolean crimes8p8;
    private Boolean crimes8p9;
    private Boolean crimes8p10;
    private Boolean crimes8p11;
    private Boolean crimes8p12;
    private Boolean crimes8p13;
    private Boolean crimes8p14;

    private Boolean crimes8p14a;
    private Boolean crimes8p14b;
    private Boolean crimes8p14c;

    private Boolean crimes9p1;
    private Boolean crimes9p2;
    private Boolean crimes9p3;
    private Boolean crimes9p4;
    private Boolean crimes9p5;
    private Boolean crimes9p6;
    private Boolean crimes9p7;

    private String printDate;

}
