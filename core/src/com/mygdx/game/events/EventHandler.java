/*
 * Copyright (C) 2015 fax
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mygdx.game.events;

import com.mygdx.game.GameStage;
import com.mygdx.game.MyGdxGame;

/**
 *
 * @author fax
 */
public class EventHandler {
    public static final int TICK_PER_SEC=250;
    public static final int MAXIMAL_EVENT_INTERVAL = 5 /*Секунд*/ * TICK_PER_SEC /*Миллисекунд*/; //Максимальный интервал,
    public static final int MINIMAL_EVENT_INTERVAL = 1 /*Секунд*/ * TICK_PER_SEC /*Миллисекунд*/; //Минимальный интервал.

    private static int randomInterval() {
        return MINIMAL_EVENT_INTERVAL + MyGdxGame.RANDOM.nextInt(
                Math.max(1, MAXIMAL_EVENT_INTERVAL - MINIMAL_EVENT_INTERVAL));
    }

    private static double currentTimerValue = randomInterval();
    static Event currentEvent = null;

    public static void act(GameStage gs, float delta) {
        if (currentEvent == null) {
            currentTimerValue -= delta;
            if (currentTimerValue <= 0) {
                currentTimerValue = randomInterval();
                int pr[] = new int[Event.events.size()];
                int range = 0;
                for (int i = 0; i < pr.length; i++) {
                    range += Event.events.get(i).PRIORITY;
                    pr[i] = range;
                }
                if (range > 0) {
                    int rand = MyGdxGame.RANDOM.nextInt(range);
                    for (int i = 0; i < pr.length; i++) {
                        if (i == 0) {
                            if (rand < pr[i]) {
                                currentEvent = Event.events.get(i).getClone();
                            }
                        } else {
                            if (rand >= pr[i - 1] && rand < pr[i]) {
                                currentEvent = Event.events.get(i).getClone();
                            }
                        }
                    }
                } else {
                    currentEvent = Event.events.get(
                            MyGdxGame.RANDOM.nextInt(Event.events.size())).getClone();
                }
                currentEvent.gameStage = gs;
                currentEvent.apply();
            }
        } else {
            currentEvent.act(delta);
        }
    }
}
