package com.ti;

import static com.google.common.base.Preconditions.checkArgument;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Special amount (discount pricing) in form: "if you buy given numberOfUnits you will pay given amount"
 */
@EqualsAndHashCode
@ToString
@Getter
public class SpecialPrice {

  private final int numberOfUnits;
  private final double amount;

  public SpecialPrice(int numberOfUnits, double amount) {
    checkArgument(numberOfUnits >= 1, "Number of units must be greater or equal to one !");
    checkArgument(Double.compare(amount, Double.valueOf(0.0)) >= 0,
        "Special price amount must be greater or equal to zero !");

    this.numberOfUnits = numberOfUnits;
    this.amount = amount;
  }
}
