package com.ti;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Price from the good. It contains simple price pe unt and optional
 * special price (discount pricing) in form: "if you buy given numberOfUnits you will pay given price"
 */
@EqualsAndHashCode
@ToString
@Getter
public class PriceItem {
    private final double unitPrice;
    private final Optional<SpecialPrice> specialPrice;

    public PriceItem(double unitPrice) {
        this.unitPrice = unitPrice;
        this.specialPrice = Optional.empty();
    }

    public PriceItem(double unitPrice, SpecialPrice specialPrice) {
        checkNotNull(specialPrice, "Special price must not be null !");
        this.unitPrice = unitPrice;
        this.specialPrice = Optional.of(specialPrice);
    }
}
