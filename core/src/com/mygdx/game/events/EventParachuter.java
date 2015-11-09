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
import com.mygdx.game.objects.Obstacle;
import com.mygdx.game.objects.Player;

/**
 *
 * @author fax
 */
public class EventParachuter extends Event {

    public EventParachuter() {
        super(
                10, //Приоритет выполнения
                1 //Время выполнения в секундах
        );
    }

    @Override
    public void apply(GameStage stage) { //Начало работы ивента.
        System.out.println("Hello");
        add(new Player(stage.getPlayer().getX() - 10, stage.getPlayer().getY()));
    }

    @Override
    public void act(GameStage stage, float delta) { //Процесс работы ивента.
    }

    @Override
    public void dispose(GameStage stage) { //Завершение работы ивента.
        System.out.println("Good bye");
    }
}
