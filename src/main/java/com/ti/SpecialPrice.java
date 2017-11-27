package com.ti;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Special price (discount pricing) in form: "if you buy given numberOfUnits you will pay given price"
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class SpecialPrice {
    private final int numberOfUnits;
    private final double price;
}
