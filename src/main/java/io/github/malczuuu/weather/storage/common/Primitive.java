package io.github.malczuuu.weather.storage.common;

public final class Primitive {

  public static boolean primitive(Boolean object) {
    return primitive(object, false);
  }

  public static boolean primitive(Boolean object, boolean defaultValue) {
    return object != null ? object : defaultValue;
  }
}
