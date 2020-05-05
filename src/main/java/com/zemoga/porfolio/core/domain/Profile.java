package com.zemoga.porfolio.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {
    private String userId;
    private String names;
    private String lastNames;
    private String experienceSummary;
    private Object tweets;
    private String pictureFileName;
    private String twitterUser;
}
