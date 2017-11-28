package com.ti;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Price from the good. It contains simple price pe unt and optional special price (discount pricing) in form: "if you
 * buy given numberOfUnits you will pay given price"
 */
@EqualsAndHashCode
@ToString
@Getter
public class PriceItem {

  private final double unitPrice;
  private final Optional<SpecialPrice> specialPrice;

  public PriceItem(double unitPrice) {
    checkArgument(unitPrice >= 0, "Unit price must be greater or equal to zero !");
    this.unitPrice = unitPrice;
    this.specialPrice = Optional.empty();
  }

  public PriceItem(double unitPrice, SpecialPrice specialPrice) {
    checkArgument(unitPrice >= 0, "Unit price must be greater or equal to zero !");
    checkArgument(specialPrice != null, "Special price must not be null !");
    this.unitPrice = unitPrice;
    this.specialPrice = Optional.of(specialPrice);
  }
}
