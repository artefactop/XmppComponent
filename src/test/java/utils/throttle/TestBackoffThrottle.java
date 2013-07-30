package utils.throttle;

import junit.framework.TestCase;
import org.xmpp.utils.throttle.BackoffThrottlePolicy;
import org.xmpp.utils.throttle.ThrottleManager;

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
public class TestBackoffThrottle extends TestCase {

    public void testBackoffBlock() {

        final int maxPerPeriod = 10;
        final int period = 2000;
        final String key = "test";

        final BackoffThrottlePolicy policy = new BackoffThrottlePolicy();
        final ThrottleManager manager = new ThrottleManager(maxPerPeriod, period, 200, 10000);
        manager.setThrottlePolicy(policy);
        long s, d;

        for (int j = 0; j < 5; j++) {

            s = System.currentTimeMillis();

            for (int i = 0; i < maxPerPeriod; i++) {
                assertTrue(manager.accept(key));
            }

            assertFalse(manager.accept(key));

            d = period - (System.currentTimeMillis() - s);

            if (d > 0)
                try {
                    Thread.sleep(d + period + 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            System.out.println("Success: " + j);

        }

    }

}
