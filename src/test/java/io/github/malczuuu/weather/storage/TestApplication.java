package io.github.malczuuu.weather.storage;

import io.github.malczuuu.weather.storage._testcontainers.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
  }
}
