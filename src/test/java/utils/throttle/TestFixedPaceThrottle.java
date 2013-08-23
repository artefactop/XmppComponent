package utils.throttle;

import junit.framework.TestCase;
import org.xmpp.utils.throttle.FixedPaceThrottlePolicy;
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
public class TestFixedPaceThrottle extends TestCase {

    public void testFixedPaceBlock() {

        final int maxPerPeriod = 10;
        final int period = 500;
        final String key = "test";

        final FixedPaceThrottlePolicy policy = new FixedPaceThrottlePolicy();
        final ThrottleManager manager = new ThrottleManager(maxPerPeriod, period, 200, 10000);
        manager.setThrottlePolicy(policy);
        long s, d;

        for (int j = 0; j < 5; j++) {

            s = System.currentTimeMillis();

            for (int i = 0; i < maxPerPeriod; i++) {
                System.out.println("I: " + i);
                assertTrue(manager.accept(key));
            }

            for (int l = 0; l < 5; l++)
                assertFalse(manager.accept(key));

            d = period - (System.currentTimeMillis() - s);

            if (d > 0)
                try {
                    final int sp = 10;
                    final int splitP = (int)Math.floor(d / sp);

                    for (int r = 0; r < sp-1; r++){
                        assertFalse(manager.accept(key));
                        Thread.sleep(splitP);
                    }

                    Thread.sleep(splitP);
                    System.out.println("Success Round: " + j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

    }

}
