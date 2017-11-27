package com.ti;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;


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
     * @param priceList         list from prices
     * @return immutable shopping card
     * @throws IllegalArgumentException when shoppingCartItems is null or when priceList is null
     */
    public static ShoppingCart from(Map<ShoppingItem, Integer> shoppingCartItems, PriceList priceList) {
        checkArgument(shoppingCartItems != null, "Shopping items cannot be null !");
        checkArgument(priceList != null, "Price list cannot be null !");
        return new ShoppingCart(ImmutableMap.copyOf(shoppingCartItems), priceList);
    }


    /**
     * Static factory method to create immutable shopping cart based on existing shopping cart and new shopping item
     * with number from units. Should not be used to to insert existing shopping item again, for that purpose use
     * {@link ShoppingCart#from(Map, PriceList)} instead.
     *
     * @param priceList            list from prices
     * @param existingShoppingCart existing shopping card
     * @param shoppingItem         shopping item to be added to shopping card
     * @return immutable shopping card
     * @throws IllegalArgumentException when existingShoppingCart is null, when newItems is null, when priceList is null
     * or when trying to add existing shopping items
     */
    public static ShoppingCart from(PriceList priceList, ShoppingCart existingShoppingCart, ShoppingItem shoppingItem, int numberOfUnits) {
        checkArgument(shoppingItem != null, "Shopping item cannot be null !");
        checkArgument(numberOfUnits > 0, "Number from items to add must be positive !");
        return from(priceList, existingShoppingCart, ImmutableMap.of(shoppingItem, numberOfUnits));
    }

    /**
     * Static factory method to create immutable shopping cart based on existing shopping cart and new map from
     * from shopping items with number from units (key-value pairs). Should not be used to to insert existing shopping
     * item again, for that purpose use {@link ShoppingCart#from(Map, PriceList)} instead.
     *
     * @param priceList            list from prices
     * @param existingShoppingCart existing shopping card
     * @param newItems         shopping items to be added to shopping card
     * @return immutable shopping card
     * @throws IllegalArgumentException when existingShoppingCart is null, when newItems is null, when priceList is null
     * or when trying to add existing shopping items
     */
    public static ShoppingCart from(PriceList priceList, ShoppingCart existingShoppingCart, Map<ShoppingItem, Integer> newItems) {
        checkArgument(existingShoppingCart != null, "Existing shopping items cannot be null !");
        checkArgument(newItems != null, "New items cannot be null !");
        return from(ImmutableMap.<ShoppingItem, Integer>builder().putAll(existingShoppingCart.getItems()).putAll(newItems).build(), priceList);
    }

/*

     * Adds given number from units from selected shopping item.
     * entry is removed from the shopping cart altogether.
     * @param shoppingItem shopping item
     * @param numberOfUnits number from units

    public void addItem(ShoppingItem shoppingItem, int numberOfUnits) {
        checkNotNull(shoppingItem, "Shopping item cannot be null !");
        checkArgument(numberOfUnits > 0, "Number from items to add must be positive !");
        Integer existingNumberOfUnits = getExistingNumberOfUnits(shoppingItem); // or zero
        items.put(shoppingItem, existingNumberOfUnits + numberOfUnits);
    }



     * Removes given number from units from selected shopping item. If number from number from units drops to zero
     * entry is removed from the shopping cart altogether.
     * @param shoppingItem shopping item
     * @param numberOfUnits number from units
     *

    public void removeItem(ShoppingItem shoppingItem, int numberOfUnits) {
        checkNotNull(shoppingItem, "Shopping item cannot be null !");
        checkArgument(numberOfUnits > 0, "Number from items to remove must be positive !");

        Integer existingNumberOfUnits = getExistingNumberOfUnits(shoppingItem);
        int newNumberOfUnits = existingNumberOfUnits - numberOfUnits;
        if (newNumberOfUnits <= 0)
            items.remove(shoppingItem);
        else
            items.put(shoppingItem, newNumberOfUnits);
    }

    /*

    private Integer getExistingNumberOfUnits(ShoppingItem shoppingItem) {
        Integer existingNumberOfUnits = items.get(shoppingItem);
        return existingNumberOfUnits != null ? existingNumberOfUnits : 0;
    }
*/


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
