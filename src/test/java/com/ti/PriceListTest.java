package com.ti;

import org.junit.Test;
import org.junit.runners.Parameterized;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
public class PriceListTest {

    @Test
    public void shouldCreatePriceListAndCalcuateSimplePricesWithNoDiscount() {

        ShoppingItem[] shoppingItems = getShoppingItems();

        PriceList priceList = PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
                shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
                shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
                shoppingItems[3], new PriceItem(7.99)));

        assertNotNull(priceList);
        assertEquals(34.99d, priceList.calculateTotalPrice(shoppingItems[0], 1), 0.001);
        assertEquals(49.98d, priceList.calculateTotalPrice(shoppingItems[1], 2), 0.001);
        assertEquals(14.99d, priceList.calculateTotalPrice(shoppingItems[2], 1), 0.001);
        assertEquals(23.97d, priceList.calculateTotalPrice(shoppingItems[3], 3), 0.001);

    }


    @Test
    public void shouldCreatePriceListAndCalcuatePricesWithDiscounts() {
        ShoppingItem[] shoppingItems = getShoppingItems();

        PriceList priceList = PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
                shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
                shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
                shoppingItems[3], new PriceItem(7.99)));

        assertNotNull(priceList);
        assertEquals(3 * 34.99, priceList.calculateTotalPrice(shoppingItems[0], 3), 0.001);
        assertEquals(60 + (2 * 24.99), priceList.calculateTotalPrice(shoppingItems[1], 5), 0.001);
        assertEquals(25 + 1 * 14.99, priceList.calculateTotalPrice(shoppingItems[2], 3), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForNullPriceListItems() {
        PriceList.from(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionForMultipleItemsWithSameShoppingItem() {
        ShoppingItem[] shoppingItems = getShoppingItems();

        PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
                shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
                shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
                shoppingItems[3], new PriceItem(7.99),
                shoppingItems[3], new PriceItem(7.99))); // duplicate

    }

    @Parameterized.Parameters
    public static ShoppingItem[] getShoppingItems() {
        ShoppingItem a = new ShoppingItem("A");
        ShoppingItem b = new ShoppingItem("B");
        ShoppingItem c = new ShoppingItem("C");
        ShoppingItem d = new ShoppingItem("D");
        return new ShoppingItem[]{a, b, c, d};
    }
}
