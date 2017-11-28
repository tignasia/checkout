package com.ti;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class ShoppingCartTest extends CheckoutTest {

  @Test
  public void shouldCreateShoppingCartAndCalculatePricesProperly() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), priceList);
    // When/Then
    // 254.94
    assertEquals((3 * 34.99) + (60 + 2 * 24.99) + (25 + 14.99), shoppingCart.checkout(), 0.001d);
  }

  @Test
  public void shouldCreateShoppingFromExistingShoppingCartUsingNewItemsToAddAndCalculatePricesProperly() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), priceList);
    ShoppingCart newShoppingCart = ShoppingCart.addFrom(priceList, shoppingCart, ImmutableMap.of(shoppingItems[3], 3));
    // When/Then
    assertEquals(254.94 + 3 * 7.99, newShoppingCart.checkout(), 0.001d);
  }


  @Test
  public void shouldCreateShoppingFromExistingShoppingCartUsingRemovedItemsAndCalculatePricesProperly() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), priceList);
    ShoppingCart newShoppingCart = ShoppingCart.removeFrom(priceList, shoppingCart, ImmutableList.of(shoppingItems[2]));
    // When/Then
    assertEquals(254.94 - (25 + 14.99), newShoppingCart.checkout(), 0.001d);
  }



  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionIfPriceListIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    // When
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), null);
    //Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionIfShoppingCartItemsIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When
    ShoppingCart shoppingCart = ShoppingCart.from(null, priceList);
    //Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWhenAddingIfExistingShoppingCartIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When
    ShoppingCart newShoppingCart = ShoppingCart.addFrom(priceList, null, ImmutableMap.of(shoppingItems[3], 3));
    //Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWhenAddingIfItemsToAddIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), priceList);
    ShoppingCart newShoppingCart = ShoppingCart.addFrom(priceList, shoppingCart, null);
    //Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWhenRemovingIfExistingShoppingCartIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When
    ShoppingCart newShoppingCart = ShoppingCart.removeFrom(priceList, null, ImmutableList.of(shoppingItems[3]));
    //Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWhenRemovingIfItemsToAddIsNull() {
    // Given
    ShoppingItem[] shoppingItems = getTestShoppingItems();
    PriceList priceList = getTestPriceList(shoppingItems);
    // When
    ShoppingCart shoppingCart = ShoppingCart.from(getTestShoppingCartItems(shoppingItems), priceList);
    ShoppingCart newShoppingCart = ShoppingCart.removeFrom(priceList, shoppingCart, null);
    //Then (Exception)
  }


  @Test
  public void shouldCalculateProperlyShoppingCartWithLargePriceItems() {
    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();

    priceListItems.put(new ShoppingItem("A"), new PriceItem(Integer.MAX_VALUE));
    PriceList priceList = PriceList.from(priceListItems);
    ShoppingCart shoppingCart = ShoppingCart.from(ImmutableMap.of(new ShoppingItem("A"), 500), priceList);
    // When/Then
    assertEquals(500d * Integer.MAX_VALUE, shoppingCart.checkout(),0.001d);
  }

  @Test
  public void shouldCalculateProperlyShoppingCartWithLargePriceItemsWithSpecialPrice() {

    // Given
    Map<ShoppingItem, PriceItem> priceListItems = new HashMap<>();

    priceListItems.put(new ShoppingItem("A"), new PriceItem(Integer.MAX_VALUE,new SpecialPrice(1000,500d * Integer.MAX_VALUE)));
    PriceList priceList = PriceList.from(priceListItems);
    ShoppingCart shoppingCart = ShoppingCart.from(ImmutableMap.of(new ShoppingItem("A"), 2500), priceList);
    // When/Then
    assertEquals(1500d * Integer.MAX_VALUE, shoppingCart.checkout(),0.001d);
  }


  private ImmutableMap<ShoppingItem, Integer> getTestShoppingCartItems(ShoppingItem[] shoppingItems) {
    return of(shoppingItems[0], 3, shoppingItems[1], 5, shoppingItems[2], 3);
  }


}
