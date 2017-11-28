package com.ti;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Simple shopping item where name is the unique identifier
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class ShoppingItem {

  private final String name;
}
