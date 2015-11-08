/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Whizzpered
 */
public class Obstacle extends Entity {

    public Obstacle(float x, float y) {
        super(x, y);
        touchable = true;
        setName("nlo"); //nlo, błąd. UFO
        velocity.x = MyGdxGame.RANDOM.nextBoolean() ? -50 : 50;
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
        if (Math.abs(velocity.y) >= 400) {
            remove();
        }
    }

    @Override
    public void action() {
        velocity.y = getStage().getPlayer().getVelocity().y / 2;
        acceleration.y = 100;
        if (velocity.y < 10) {
            velocity.y = -200;
            acceleration.y = -100;
        }
        getStage().getPlayer().getVelocity().y = -getStage().getPlayer().getVelocity().y / 2;
        getStage().getPlayer().getVelocity().x = getStage().getPlayer().getX() - getX();
        velocity.x = getX() - getStage().getPlayer().getX();
    }
}
