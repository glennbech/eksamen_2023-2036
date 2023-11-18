package com.example.s3rekognition;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.StandardUnit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CloudWatchMetricsSender {
    private final AmazonCloudWatch cloudWatch;
    private final List<MetricDatum> metricData = new ArrayList<>();

    public CloudWatchMetricsSender() {
        this.cloudWatch = AmazonCloudWatchClientBuilder.standard().build();
    }
    public void collectMetric(String metricName, double value, String dimensionName, String dimensionValue) {
        MetricDatum datum = new MetricDatum()
                .withMetricName(metricName)
                .withUnit(StandardUnit.Count)
                .withValue(value)
                .withTimestamp(new Date())
                .withDimensions(new Dimension().withName(dimensionName).withValue(dimensionValue));

        synchronized (metricData) {
            metricData.add(datum);
        }
    }

    public void sendCollectedMetrics() {
        synchronized (metricData) {
            if (!metricData.isEmpty()) {
                PutMetricDataRequest request = new PutMetricDataRequest()
                        .withNamespace("PPECompliance")
                        .withMetricData(metricData);
                cloudWatch.putMetricData(request);
                metricData.clear();
            }
        }
    }
}
