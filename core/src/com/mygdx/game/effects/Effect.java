/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.effects;

import com.mygdx.game.GameStage;
import com.mygdx.game.objects.Entity;

/**
 *
 * @author Whizzpered
 */
public class Effect {

    private int listPosition;
    private GameStage stage;

    public Effect(GameStage stage) {
        this.stage = stage;
    }

    public void apply() {
    }

    public void act(float delta) {
    }

    public void dispose() {
        stage.getPlayer().removeEffect(listPosition);
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
