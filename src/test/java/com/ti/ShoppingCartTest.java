package com.ti;

import org.junit.Test;
import org.junit.runners.Parameterized;

import static com.google.common.collect.ImmutableMap.of;

public class ShoppingCartTest {
    @Test
    public void shouldCreatePriceListAndCalcuateSimplePricesWithNoDiscount() {
        PriceList priceList = getPriceList();
     //   ShoppingCart shoppingCart = ShoppingCart.from(of(priceList, 3, b, 5, c, 3), priceList);
    }

    @Parameterized.Parameters
    public static PriceList getPriceList() {
        ShoppingItem a = new ShoppingItem("A");
        ShoppingItem b = new ShoppingItem("B");
        ShoppingItem c = new ShoppingItem("C");
        ShoppingItem d = new ShoppingItem("D");
        ShoppingItem[] shoppingItems = new ShoppingItem[]{a, b, c, d};

        PriceList priceList = PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
                shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
                shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
                shoppingItems[3], new PriceItem(7.99)));

        return  priceList;
    }
}
