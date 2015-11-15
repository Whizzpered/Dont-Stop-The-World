package com.mygdx.game.events;

import com.mygdx.game.GameStage;


/**
 * Created by yasmidrog on 15.11.15.
 */
public class EventSlowness extends Event{

        public EventSlowness() {
            super(
                    10, //Приоритет выполнения
                    10 //Время выполнения в секундах
            );
            colorIndication=true;
        }
        @Override
        public void apply(GameStage stage) { //Начало работы ивента.
            gameStage.changeEventColor=true;
            stage.setSlowCoef(2.8f);
        }

        @Override
        public void act(GameStage stage, float delta) { //Процесс работы ивента.
        }

        @Override
        public void dispose(GameStage stage) {//Завершение работы ивента.
            gameStage.changeEventColor=false;
            stage.setSlowCoef(1f);
        }
}

