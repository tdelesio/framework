package com.delesio.metrics;

import java.util.List;

public interface IWebRequestMetricCache {
	public void addToCache(WebRequestMetric metric);
	public List<Long>getKeys();
	public WebRequestMetric getWebRequestMetric(Long id);
}
