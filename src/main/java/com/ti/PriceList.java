package com.ti;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Immutable list of prices for shopping items
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PRIVATE)
public class PriceList {

  private final Map<ShoppingItem, PriceItem> items;


  /**
   * Static factory method which creates immutable price list
   *
   * @param priceListItems map of prices as ShoppingItem to PriceItem key-value pairs
   * @return price list
   * @throws NullPointerException if shopping item is null
   * @throws IllegalArgumentException if shopping items or price items inside priceListItems are null
   */
  public static PriceList from(Map<ShoppingItem, PriceItem> priceListItems) {
    checkArgument(priceListItems != null, "Price list items cannot be null !");
    return new PriceList(ImmutableMap.copyOf(priceListItems));
  }

  /**
   * Calculates total price from shopping item based on number from units and pricing information. If special pricing
   * does not exist for the item from number from units is lower than eligible for discount pricing simple product from
   * uit price and number from unit is used.
   *
   * @param shoppingItem shopping item
   * @param numOfUnits number from units
   * @return total price from given shopping item
   * @throws IllegalArgumentException if shopping item is null or if price information for given shoppingItem cannot be
   * found
   */
  public double calculateTotalPrice(ShoppingItem shoppingItem, int numOfUnits) {
    checkArgument(shoppingItem != null, "Shopping item cannot be null!");
    PriceItem priceItem = items.get(shoppingItem);
    checkArgument(priceItem != null, "Price for shopping item " + shoppingItem + " cannot be found !");

    if (!specialPricingExist(priceItem) || (specialPricingExist(priceItem) && isNotEligibleForSpecialPricing(
        numOfUnits,
        priceItem))) {
      return priceItem.getUnitPrice() * numOfUnits;
    }

    return calculateEndPriceUsingSpecialPrice(priceItem.getSpecialPrice().get(), priceItem.getUnitPrice(), numOfUnits);
  }

  /**
   * Returns price information for given shopping item as {@code Optional}
   *
   * @param shoppingItem shopping item
   * @return price information for given shopping item
   */
  public Optional<PriceItem> getPriceInformation(ShoppingItem shoppingItem) {
    return Optional.ofNullable(items.get(shoppingItem));
  }

  private boolean isNotEligibleForSpecialPricing(int numOfUnits, PriceItem priceItem) {
    return numOfUnits < priceItem.getSpecialPrice().get().getNumberOfUnits();
  }

  private boolean specialPricingExist(PriceItem priceItem) {
    return priceItem.getSpecialPrice().isPresent();
  }

  private double calculateEndPriceUsingSpecialPrice(SpecialPrice specialPrice, double standardUnitPrice,
      int numOfUnits) {
    int divider = numOfUnits / specialPrice.getNumberOfUnits();
    int reminder = numOfUnits % specialPrice.getNumberOfUnits();
    return divider * specialPrice.getAmount() + reminder * standardUnitPrice;
  }

}
