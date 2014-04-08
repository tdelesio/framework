package com.delesio.metrics;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface IMetricService {

	public void flushCache();
	public void saveAllMetrics(Collection<Metric> metrics);
	public void saveClientMetric(ClientMetric clientMetric);
	public void saveBulkClientMetrics(List<ClientMetric> clientMetrics);
	public void saveAllOutliers(List<MetricOutlier> outliers);
	public void saveWebRequestMetric(WebRequestMetric webRequestMetric);
}
