package com.zemoga.porfolio.adapters.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecordNotFoundException extends RuntimeException {
    private final String description;
}
