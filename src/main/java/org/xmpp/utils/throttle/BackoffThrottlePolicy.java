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

public class BackoffThrottlePolicy implements ThrottlePolicy {
    @Override
    public boolean accept(final ThrottleMonitor throttleMonitor) {
        if (throttleMonitor.getPacketsSent().incrementAndGet() > throttleMonitor.getMaxPerPeriod()) {
            long delta = System.currentTimeMillis() - throttleMonitor.getLastTimestamp().get();
            if (delta <= throttleMonitor.getPeriodInterval()) {
                // Ignore The Packet
                throttleMonitor.update();
                return false;
            } else {
                if (throttleMonitor.getPacketsSent().get() > throttleMonitor.getMaxPerPeriod()) {
                    final int save = -((int) ((Math.floor(delta
                            / throttleMonitor.getPeriodInterval())) * throttleMonitor.getMaxPerPeriod()));
                    if (throttleMonitor.getPacketsSent().addAndGet(save) < 0) {
                        throttleMonitor.reset(1);
                    }
                }
                throttleMonitor.update();
                return throttleMonitor.getPacketsSent().get() <= throttleMonitor.getMaxPerPeriod();
            }
        }
        throttleMonitor.update();
        return true;
    }
}
