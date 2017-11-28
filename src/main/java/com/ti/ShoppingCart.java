package com.ti;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter(AccessLevel.PRIVATE)
public class ShoppingCart {

  private final ImmutableMap<ShoppingItem, Integer> items;
  private final PriceList priceList;

  /**
   * Static factory method to create immutable shopping cart
   *
   * @param shoppingCartItems shopping card items
   * @param priceList list of prices
   * @return immutable shopping card
   * @throws IllegalArgumentException when shoppingCartItems is null or when priceList is null
   */
  public static ShoppingCart from(Map<ShoppingItem, Integer> shoppingCartItems, PriceList priceList) {
    checkArgument(shoppingCartItems != null, "Shopping items cannot be null !");
    checkArgument(priceList != null, "Price list cannot be null !");
    return new ShoppingCart(ImmutableMap.copyOf(shoppingCartItems), priceList);
  }

  /**
   * Static factory method to create immutable shopping cart based on existing shopping cart and map of shopping card
   * items to add
   *
   * @param priceList list of prices
   * @param existingShoppingCart existing shopping card
   * @param itemsToAdd shopping items to be added to shopping card
   * @return immutable shopping card
   * @throws IllegalArgumentException when existingShoppingCart is null, when newItems is null, when priceList is null
   * or when trying to add existing shopping items
   */
  public static ShoppingCart addFrom(PriceList priceList, ShoppingCart existingShoppingCart,
      Map<ShoppingItem, Integer> itemsToAdd) {
    checkArgument(existingShoppingCart != null, "Existing shopping items cannot be null !");
    checkArgument(itemsToAdd != null, "Items to add cannot be null !");

    return from(
        ImmutableMap.<ShoppingItem, Integer>builder().putAll(existingShoppingCart.getItems()).putAll(itemsToAdd)
            .build(),
        priceList);
  }

  /**
   * Static factory method to create immutable shopping cart based on existing shopping cart and map of shopping card
   * items to remove
   *
   * @param priceList list of prices
   * @param existingShoppingCart existing shopping card
   * @param itemsToRemove shopping items to be removed from shopping card
   * @return immutable shopping card
   * @throws IllegalArgumentException when existingShoppingCart is null, when newItems is null, when priceList is null
   * or when trying to add existing shopping items
   */
  public static ShoppingCart removeFrom(PriceList priceList, ShoppingCart existingShoppingCart,
      List<ShoppingItem> itemsToRemove) {
    checkArgument(existingShoppingCart != null, "Existing shopping items cannot be null !");
    checkArgument(itemsToRemove != null, "Items to remove cannot be null !");
    return from(
        Maps.difference(existingShoppingCart.getItems(), Maps.toMap(itemsToRemove, si -> 0)).entriesOnlyOnLeft(),
        priceList);
  }

  /**
   * Checkout method which scans items and uses price list to calculate and return actual total price from all items.
   *
   * @return total price  from all items
   */
  public double checkout() {
    List<Double> itemPrices = items.entrySet().stream().
        map(entry -> priceList.calculateTotalPrice(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    return sumItemPrices(itemPrices);
  }


  private double sumItemPrices(List<Double> itemPrices) {
    return itemPrices.stream().mapToDouble(Double::doubleValue).sum();
  }


}
