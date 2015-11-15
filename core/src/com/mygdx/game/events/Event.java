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
import com.mygdx.game.objects.Entity;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fax
 */
public abstract class Event implements Cloneable {

    static ArrayList<Event> events = new ArrayList<Event>();
    protected float colorIndication=0f;
    public final int PRIORITY;
    private ArrayList<Entity> entities;
    private double timer;
    GameStage gameStage;

    public Event(int priority, int timer) {
        this.PRIORITY = priority;
        this.timer = timer * 60;
        events.add(this);
    }

    public void add(Entity entity) {
        gameStage.addEntity(entity);
        entities.add(entity);
    }

    final void act(float delta) {
        timer -= delta;
        if (timer <= 0) {
            dispose(gameStage);
            for (Entity e : entities) {
                e.remove();
            }
            EventHandler.currentEvent = null;
        } else {
            act(gameStage, delta);
        }
    }

    final void apply() {
        apply(gameStage);
    }
    public abstract void apply(GameStage stage);

    public abstract void act(GameStage stage, float delta);

    public abstract void dispose(GameStage stage);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Event getClone() {
        try {
            Event e = ((Event) clone());
            e.entities = new ArrayList<Entity>();
            return e;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
