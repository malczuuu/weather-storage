package io.github.malczuuu.weather.storage.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class ParameterEntityTest {

  @Test
  void givenListOfTwoValues_whenAttemptingToGetOneValue_shouldReturnFirstOne() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(List.of("first", "second"));

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertThat(hasValue).isTrue();
    assertThat(value).isEqualTo("first");
  }

  @Test
  void givenEmptyListOfValues_whenAttemptingToGetOneValue_shouldReturnNull() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(List.of());

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertThat(hasValue).isFalse();
    assertThat(value).isNull();
  }

  @Test
  void givenNullListOfValues_whenAttemptingToGetOneValue_shouldReturnNull() {
    ParameterEntity entity = new ParameterEntity();
    entity.setValues(null);

    boolean hasValue = entity.hasValue();
    String value = entity.getValue();

    assertThat(hasValue).isFalse();
    assertThat(value).isNull();
  }
}
