package io.github.malczuuu.weather.storage.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ParameterEntityTest {

  @Test
  void givenListOfTwoValues_whenAttemptingToGetOneValue_shouldReturnFirstOne() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(List.of("first", "second"));

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertTrue(hasValue);
    assertEquals("first", value);
  }

  @Test
  void givenEmptyListOfValues_whenAttemptingToGetOneValue_shouldReturnNull() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(List.of());

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertFalse(hasValue);
    assertNull(value);
  }

  @Test
  void givenNullListOfValues_whenAttemptingToGetOneValue_shouldReturnNull() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(null);

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertFalse(hasValue);
    assertNull(value);
  }
}
