package io.github.malczuuu.weather.storage._testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static io.github.malczuuu.weather.storage._testcontainers.ContainerVersions.WIREMOCK_VERSION;

import org.wiremock.integrations.testcontainers.WireMockContainer;

public class WireMockInstance {

  private static volatile WireMockContainer container;

  public static WireMockContainer getInstance() {
    if (container == null) {
      synchronized (WireMockInstance.class) {
        if (container == null) {
          container = new WireMockContainer("wiremock/wiremock:" + WIREMOCK_VERSION);
          container.start();

          configureFor(container.getHost(), container.getPort());
        }
      }
    }
    return container;
  }
}
