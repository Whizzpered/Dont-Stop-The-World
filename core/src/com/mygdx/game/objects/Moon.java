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
public class Moon extends Entity {

    public Moon(float x, float y) {
        super(x, y);
        setName("moon");
    }

    @Override
    public void act(float delta) {
        velocity.y = getStage().getPlayer().velocity.y * 2 / 3;
        setX(getX() + velocity.cpy().scl(delta).x);
        setY(getY() + velocity.cpy().scl(delta).y);
        cl.set(getX(), getY(), getWidth() / 3);
        if (getX() < 0) {
            setX(0);
        }

        if (getX() > this.getStage().getWidth() - getWidth()) {
            setX(this.getStage().getWidth() - getWidth());
        }

    }

}
