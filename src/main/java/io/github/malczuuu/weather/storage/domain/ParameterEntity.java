package io.github.malczuuu.weather.storage.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@ToString
@Document(collection = "parameters")
public class ParameterEntity {

  @MongoId(targetType = FieldType.OBJECT_ID)
  private String id;

  @Field(name = "code")
  private String code;

  @Field(name = "values")
  private List<String> values;

  @Setter
  @Field(name = "version")
  private Long version;

  public ParameterEntity() {}

  public ParameterEntity(String code, String value) {
    this(code, List.of(value));
  }

  public ParameterEntity(String code, List<String> value) {
    this(null, code, value, null);
  }

  public ParameterEntity(String id, String code, List<String> values, Long version) {
    this.id = id;
    this.code = code;
    this.values = values;
    this.version = version;
  }

  public String getValue() {
    return hasValue() ? getValues().getFirst() : null;
  }

  public boolean hasValue() {
    return !getValues().isEmpty();
  }

  public List<String> getValues() {
    return values == null ? List.of() : values;
  }

  public void setValues(List<String> values) {
    this.values = values != null ? List.copyOf(values) : List.of();
  }
}
