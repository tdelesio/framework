package com.delesio.metrics;

import java.util.List;
import java.util.UUID;


public interface IMetricCache {
	public MetricDataPoint getMetricDataPoint(UUID uuid);
	public List<UUID> getKeys();
	
}
