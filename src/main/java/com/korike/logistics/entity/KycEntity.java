package com.korike.logistics.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="kyc")
public class KycEntity {
    @Id
    @Column(name="kyc_id", length=100)
    private String kycId;

    public String getKycId() {
        return kycId;
    }

    public String getKycName() {
        return kycName;
    }

    public String getKycDescription() {
        return kycDescription;
    }

    @Column(name="kyc_name")
    private String kycName;

    @Column(name="kyc_description")
    private String kycDescription;

}
