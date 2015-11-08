/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

/**
 *
 * @author Whizzpered
 */
public class Obstacle extends Entity {

    public Obstacle(float x, float y) {
        super(x, y);
        touchable = true;
        setName("nlo");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() - getStage().getPlayer().getY() < -200) {
            getStage().getActors().removeValue(this, true);
        }
        if (collides(getStage().getPlayer())) {
            action();
        }

    }

    @Override
    public void action() {
        getStage().getPlayer().up();
        getStage().getActors().removeValue(this, true);
    }
}
