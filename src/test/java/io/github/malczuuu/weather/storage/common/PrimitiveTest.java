package io.github.malczuuu.weather.storage.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PrimitiveTest {

  @ParameterizedTest
  @CsvSource(
      value = {"true,true", "false,false", "null,false"},
      nullValues = "null")
  void givenBooleanObject_whenConvertingWithoutDefaultValue_shouldReturnFalseOnlyIfNull(
      Boolean object, boolean expectedPrimitive) {
    boolean primitive = Primitive.primitive(object);

    assertThat(primitive).isEqualTo(expectedPrimitive);
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        "true,false,true",
        "false,false,false",
        "null,false,false",
        "true,true,true",
        "false,true,false",
        "null,true,true"
      },
      nullValues = "null")
  void givenBooleanObject_whenConvertingWithDefaultValue_shouldReturnDefaultIfNull(
      Boolean object, boolean defaultValue, boolean expectedPrimitive) {
    boolean primitive = Primitive.primitive(object, defaultValue);

    assertThat(primitive).isEqualTo(expectedPrimitive);
  }
}
