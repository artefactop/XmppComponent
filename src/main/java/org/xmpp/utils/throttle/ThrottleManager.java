package org.xmpp.utils.throttle;

import org.zoolu.tools.ConcurrentTimelineHashMap;

/**
 * Created by IntelliJ IDEA. User: thiago Date: 4/3/12 Time: 6:00 PM
 */
public class ThrottleManager {

    private final int expires;
    private final int maxMonitors;
    private int maxPerPeriod = 60;
    private final ConcurrentTimelineHashMap<String, ThrottleMonitor> monitors = new ConcurrentTimelineHashMap<String, ThrottleMonitor>();
    private int periodInterval = 60000;

    public ThrottleManager(int maxPerPeriod, int periodInterval, int maxMonitors, int expires) {
        this.maxPerPeriod = maxPerPeriod;
        this.periodInterval = periodInterval;
        this.maxMonitors = maxMonitors;
        this.expires = expires;
    }

    public boolean accept(final String key) {
        ThrottleMonitor monitor = monitors.get(key);
        if (monitor == null) {
            if (monitors.size() > maxMonitors) {
                monitors.cleanUpExpired(expires);
            }
            monitor = new ThrottleMonitor(maxPerPeriod, periodInterval);
            monitors.put(key, monitor);
        }

        return monitor.accept();
    }

}