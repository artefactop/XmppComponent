package org.xmpp.utils.throttle;

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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple Threadless Throttle Monitor. Just assign an instance of it for each
 * throttle criterial entity for instance user, ip etc... Everytime you receive
 * a request call the method accept(), which will return if the request should
 * be accepted or not. Make sure the accept method is always called.
 */

public class ThrottleMonitor {

    private final AtomicLong lastTimestamp = new AtomicLong(System.currentTimeMillis());
    private int maxPerPeriod = 60;
    private final AtomicInteger packetsSent = new AtomicInteger(0);
    private int periodInterval = 60000;

    public ThrottleMonitor(int maxPerPeriod, int periodInterval) {
        this.maxPerPeriod = maxPerPeriod;
        this.periodInterval = periodInterval;
    }

    public ThrottleMonitor() {
    }

    protected void update() {
        lastTimestamp.set(System.currentTimeMillis());
    }

    protected void reset() {
        packetsSent.set(0);
        lastTimestamp.set(System.currentTimeMillis());
    }

    protected void reset(final int num) {
        packetsSent.set(num);
        lastTimestamp.set(System.currentTimeMillis());
    }

    public AtomicLong getLastTimestamp() {
        return lastTimestamp;
    }

    public int getMaxPerPeriod() {
        return maxPerPeriod;
    }

    public AtomicInteger getPacketsSent() {
        return packetsSent;
    }

    public int getPeriodInterval() {
        return periodInterval;
    }
}
