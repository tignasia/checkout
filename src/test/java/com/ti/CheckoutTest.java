package com.ti;

import static com.google.common.collect.ImmutableMap.of;

/**
 * Base test class
 */
public class CheckoutTest {

  protected PriceList getTestPriceList(ShoppingItem[] shoppingItems) {
    return PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
        shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
        shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
        shoppingItems[3], new PriceItem(7.99)));
  }

  protected ShoppingItem[] getTestShoppingItems() {
    ShoppingItem a = new ShoppingItem("A");
    ShoppingItem b = new ShoppingItem("B");
    ShoppingItem c = new ShoppingItem("C");
    ShoppingItem d = new ShoppingItem("D");
    return new ShoppingItem[]{a, b, c, d};
  }
}
