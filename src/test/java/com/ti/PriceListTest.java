package com.ti;

import static com.google.common.collect.ImmutableMap.of;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

public class PriceListTest extends CheckoutTest {


  @Test
  public void shouldCreateCorrectPriceList() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    // When
    PriceList priceList = getTestPriceList(shoppingItems);

    // Then
    assertNotNull(priceList);
    Optional[] priceItems = asList(shoppingItems).stream().map(priceList::getPriceInformation).toArray(Optional[]::new);

    asList(priceItems).stream().forEach(priceItem -> assertTrue(priceItem.isPresent()));

    assertEquals(new PriceItem(34.99d), priceItems[0].get());
    assertEquals(new PriceItem(24.99d, new SpecialPrice(3, 60d)), priceItems[1].get());
    assertEquals(new PriceItem(14.99d, new SpecialPrice(2, 25d)), priceItems[2].get());
    assertEquals(new PriceItem(7.99), priceItems[3].get());

  }


  @Test
  public void shouldCalculateSimplePricesWithNoDiscount() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When/Then
    assertNotNull(priceList);
    assertEquals(34.99d, priceList.calculateTotalPrice(shoppingItems[0], 1), 0.001d);
    assertEquals(49.98d, priceList.calculateTotalPrice(shoppingItems[1], 2), 0.001d);
    assertEquals(14.99d, priceList.calculateTotalPrice(shoppingItems[2], 1), 0.001d);
    assertEquals(23.97d, priceList.calculateTotalPrice(shoppingItems[3], 3), 0.001d);

  }

  @Test
  public void shouldCreatePriceListAndCalculatePricesWithDiscounts() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When/Then
    assertNotNull(priceList);
    assertEquals(3 * 34.99, priceList.calculateTotalPrice(shoppingItems[0], 3), 0.001);
    assertEquals(60 + (2 * 24.99), priceList.calculateTotalPrice(shoppingItems[1], 5), 0.001);
    assertEquals(25 + 1 * 14.99, priceList.calculateTotalPrice(shoppingItems[2], 3), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionForNullPriceListItems() {
    PriceList.from(null);
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowNullPointerExceptionForNullPriceItemsInTheMap() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();
    priceListItems.put(shoppingItems[0], null);
    priceListItems.put(shoppingItems[1], null);
    priceListItems.put(shoppingItems[2], null);
    priceListItems.put(shoppingItems[3], null);
    // When
    PriceList.from(priceListItems);
    // Then (Exception)
  }

  @Test(expected = NullPointerException.class)
  public void shouldThrowNullPointerExceptionForNullShoppingItemsInTheMap() {
    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();
    priceListItems.put(null, new PriceItem(7.99));
    // When
    PriceList.from(priceListItems);
    // Then (Exception)
  }

  @Test
  public void shouldCalculateZeroPriceWithSpecialPriceWithZeroAmount() {
    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();
    // When
    priceListItems.put(new ShoppingItem("A"), new PriceItem(7.99, new SpecialPrice(3, 0d)));
    PriceList priceList = PriceList.from(priceListItems);
    // Then
    assertEquals(0, priceList.calculateTotalPrice(new ShoppingItem("A"), 3), 0.001);
  }

  @Test
  public void shouldCalculateProperlyLargePriceItem() {
    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();
    // When
    priceListItems.put(new ShoppingItem("A"), new PriceItem(Integer.MAX_VALUE));
    PriceList priceList = PriceList.from(priceListItems);
    // Then
    assertEquals(Integer.MAX_VALUE * 3d, priceList.calculateTotalPrice(new ShoppingItem("A"), 3), 0.001);
  }

  @Test
  public void shouldCalculateProperlyLargePriceItemWithSpecialPrice() {
    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();
    // When
    priceListItems.put(new ShoppingItem("A"), new PriceItem(Integer.MAX_VALUE,new SpecialPrice(1000,500d * Integer.MAX_VALUE)));
    PriceList priceList = PriceList.from(priceListItems);
    // Then
    assertEquals(1500d * Integer.MAX_VALUE, priceList.calculateTotalPrice(new ShoppingItem("A"), 2500), 0.001);
  }



  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionForMultipleItemsWithSameShoppingItem() {
    ShoppingItem[] shoppingItems = getTestShoppingItems();

    PriceList.from(of(shoppingItems[0], new PriceItem(34.99d),
        shoppingItems[1], new PriceItem(24.99d, new SpecialPrice(3, 60d)),
        shoppingItems[2], new PriceItem(14.99d, new SpecialPrice(2, 25d)),
        shoppingItems[3], new PriceItem(7.99),
        shoppingItems[3], new PriceItem(7.99))); // duplicate

  }
}
