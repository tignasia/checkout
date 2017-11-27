package com.ti;

import com.google.common.collect.ImmutableMap;
import com.ti.*;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;

public class Test {
    public static void main(String[] args) {

        ShoppingItem a = new ShoppingItem("A");
        ShoppingItem b = new ShoppingItem("B");
        ShoppingItem c = new ShoppingItem("C");
        ShoppingItem d = new ShoppingItem("D");

        PriceList priceList = PriceList.from(of(a, new PriceItem(34.99d),
                b, new PriceItem(24.99d, new SpecialPrice(3, 60d)),
                c, new PriceItem(14.99d, new SpecialPrice(2, 25d)),
                d,new PriceItem(7.99)));



         // 3 x 34.99 = 104.97
        // 60  + 2 x 24.99 =  109.98
        // 25 + 1 x 14.99  = 39.99
        ShoppingCart shoppingCart = ShoppingCart.from(of(a, 3, b, 5, c, 3), priceList);

     //   254.94 + 7.99
        //ShoppingCart newShoppingCart = ShoppingCart.builder().withShoppingCart(shoppingCart).withPriceList(priceList).item(d).numberOfUnits(2).build();

       ShoppingCart newShoppingCart = ShoppingCart.from(priceList,shoppingCart,d,1);
       ShoppingCart newestShoppingCart = ShoppingCart.from(priceList, shoppingCart, ImmutableMap.of(d,3));


        //   254.94
        System.out.println(shoppingCart.checkout());
        //   254.94 + 7.99 = 262.93
        System.out.println(newShoppingCart.checkout());

        //   254.94 + 3 x 7.99 = 278.91
        System.out.println(newestShoppingCart.checkout());

    }
}
