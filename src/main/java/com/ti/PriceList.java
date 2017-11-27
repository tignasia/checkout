package com.ti;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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
     */
    public static PriceList from(Map<ShoppingItem, PriceItem> priceListItems) {
        checkArgument(priceListItems != null, "Price list items cannot be null !");
        return new PriceList(ImmutableMap.copyOf(priceListItems));
    }
/*

    public static PriceList from(PriceList existingPriceList, Map<ShoppingItem, PriceItem> priceListItems) {
        return new PriceList(ImmutableMap.<ShoppingItem, PriceItem>builder().putAll(existingPriceList.getItems()).putAll(priceListItems).build());
    }

    public static PriceList from(PriceList existingPriceList, ShoppingItem newShoppingItem, PriceItem newPriceItem) {
        return new PriceList(ImmutableMap.<ShoppingItem, PriceItem>builder().putAll(existingPriceList.getItems()).putAll(ImmutableMap.of(newShoppingItem,newPriceItem)).build());
    }
*/

    /**
     * Calculates total price from shopping item based on number from units and pricing information.
     * If special pricing does not exist for the item from number from units is lower than
     * eligible for discount pricing simple product from uit price and number from unit is used.
     *
     * @param shoppingItem shopping item
     * @param numOfUnits   number from units
     * @return total price from given shopping item
     * @throws NullPointerException     if shopping item is null
     * @throws IllegalArgumentException if price information for given shoppingItem cannot be found
     */
    public double calculateTotalPrice(ShoppingItem shoppingItem, int numOfUnits) {
        checkNotNull(shoppingItem, "Shopping item cannot be null!");
        PriceItem priceItem = items.get(shoppingItem);
        checkArgument(priceItem != null, "Price for shopping item " + shoppingItem + " cannot be found !");

        if (!specialPricingExist(priceItem) || (specialPricingExist(priceItem) && isNotEligibleForSpecialPricing(numOfUnits, priceItem)))
            return priceItem.getUnitPrice() * numOfUnits;

        return calculateEndPriceUsingSpecialPrice(priceItem.getSpecialPrice().get(), priceItem.getUnitPrice(), numOfUnits);
    }

    private boolean isNotEligibleForSpecialPricing(int numOfUnits, PriceItem priceItem) {
        return numOfUnits < priceItem.getSpecialPrice().get().getNumberOfUnits();
    }

    private boolean specialPricingExist(PriceItem priceItem) {
        return priceItem.getSpecialPrice().isPresent();
    }

    private double calculateEndPriceUsingSpecialPrice(SpecialPrice specialPrice, double standardUnitPrice, int numOfUnits) {
        int divider = numOfUnits / specialPrice.getNumberOfUnits();
        int reminder = numOfUnits % specialPrice.getNumberOfUnits();
        return divider * specialPrice.getPrice() + reminder * standardUnitPrice;
    }

}
