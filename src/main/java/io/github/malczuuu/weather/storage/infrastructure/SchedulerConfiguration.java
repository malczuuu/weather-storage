package io.github.malczuuu.weather.storage.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty("weather.scheduler.enabled")
@Configuration
@EnableScheduling
public class SchedulerConfiguration {}
