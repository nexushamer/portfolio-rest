package com.zemoga.porfolio.external.datasources.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class ProfileEntity {
    @Id
    @Column(name = "user_id", length = 80, unique = true)
    private String userId;

    @Column(name = "names", length = 100)
    private String names;

    @Column(name = "lastNames", length = 100)
    private String lastNames;

    @Column(name = "experience_summary", length = 500)
    private String experienceSummary;

    @Lob
    @Column(name = "picture")
    private byte[] picture;
}
