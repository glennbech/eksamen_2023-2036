package com.example.s3rekognition;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;
import java.util.Map;

@Configuration
public class MetricsConfig {

    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient(){
        return CloudWatchAsyncClient
                .builder()
                .region(Region.EU_WEST_1)
                .build();
    }
/*
    @Bean
    public MeterRegistry getMeterRegistry() {
        CloudWatchConfig cloudWatchConfig = setUpCloudWatchConfig();
        return new CloudWatchMeterRegistry(
                cloudWatchConfig,
                Clock.SYSTEM,
                cloudWatchAsyncClient());

    }*/
    @Bean
    public MeterRegistry meterRegistry(CloudWatchAsyncClient cloudWatchAsyncClient) {
        return new CloudWatchMeterRegistry(cloudWatchConfig(), Clock.SYSTEM, cloudWatchAsyncClient);
    }


    private CloudWatchConfig cloudWatchConfig() {
        return new CloudWatchConfig() {
            private final Map<String,String> configuration = Map.of(
                    "cloudwatch.namespace","ScanTime-2036",
                    "cloudwatch.step", Duration.ofSeconds(20).toString());

            @Override
            public String get(String key) {
                return configuration.get(key);
            }
        };
    }
}
