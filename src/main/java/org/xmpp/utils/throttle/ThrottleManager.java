package org.xmpp.utils.throttle;

import org.zoolu.tools.ConcurrentTimelineHashMap;

/*
 * Copyright (C) 2011 - Jingle Nodes - Yuilop - Neppo
 *
 * This file is part of Switji (http://jinglenodes.org)
 *
 * Switji is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Switji is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MjSip; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Author(s):
 * Benhur Langoni (bhlangonijr@gmail.com)
 * Thiago Camargo (barata7@gmail.com)
 */
public class ThrottleManager {

    private final int expires;
    private final int maxMonitors;
    private int maxPerPeriod = 60;
    private final ConcurrentTimelineHashMap<String, ThrottleMonitor> monitors = new ConcurrentTimelineHashMap<String, ThrottleMonitor>();
    private int periodInterval = 60000;
    private ThrottlePolicy throttlePolicy = new BackoffThrottlePolicy();

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

        return throttlePolicy.accept(monitor);
    }

    public void setThrottlePolicy(final ThrottlePolicy throttlePolicy) {
        if (throttlePolicy != null)
            this.throttlePolicy = throttlePolicy;
    }
}