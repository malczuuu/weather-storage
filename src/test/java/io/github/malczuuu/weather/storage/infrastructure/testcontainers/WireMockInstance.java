package io.github.malczuuu.weather.storage.infrastructure.testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static io.github.malczuuu.weather.storage.infrastructure.testcontainers.ContainerVersions.WIREMOCK_IMAGE;

import org.testcontainers.utility.DockerImageName;
import org.wiremock.integrations.testcontainers.WireMockContainer;

public final class WireMockInstance {

  private static volatile WireMockContainer container;

  public static WireMockContainer getInstance() {
    if (container == null) {
      synchronized (WireMockInstance.class) {
        if (container == null) {
          container = new WireMockContainer(DockerImageName.parse(WIREMOCK_IMAGE));
          container.start();

          configureFor(container.getHost(), container.getPort());
        }
      }
    }
    return container;
  }

  private WireMockInstance() {}
}
