package com.zemoga.porfolio.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TweetDetails {
    private String id;
    private String name;
    private String profileImageUrl;
}
