package com.ti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.Test;

public class PriceItemTest {

  @Test
  public void shouldCreateProperSimplePriceItem() {
    // Given/ When
    PriceItem priceItem = new PriceItem(89.99d);
    // Then
    assertEquals(89.99d,priceItem.getUnitPrice(),0.001d);
    assertTrue(!priceItem.getSpecialPrice().isPresent());
    assertEquals(Optional.empty(),priceItem.getSpecialPrice());
  }


  @Test
  public void shouldCreateProperPriceItemWithSpecialPrice() {
    // Given/ When
    PriceItem priceItem = new PriceItem(89.99d,new SpecialPrice(3, 60d));
    // Then
    assertEquals(89.99d,priceItem.getUnitPrice(),0.001d);
    assertTrue(priceItem.getSpecialPrice().isPresent());
    assertEquals(new SpecialPrice(3, 60d),priceItem.getSpecialPrice().get());
  }


  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionForPriceItemBelowZero() {
    // Given/When
    new PriceItem(-7.99);
    // Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionForPriceItemWithSpecialPriceWithZeroUnits() {
    // Given/When
    new PriceItem(7.99, new SpecialPrice(0, 60d));
    // Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionForPriceItemWithNullSpecialPrice() {
    // Given/When
       new PriceItem(-7.99, null);
    // Then (Exception)
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowIllegalArgumentExceptionWithSpecialPriceBelowZeroAmount() {
    // Given
   new PriceItem(7.99, new SpecialPrice(3, -10d));
    // Then (Exception)
  }


}
