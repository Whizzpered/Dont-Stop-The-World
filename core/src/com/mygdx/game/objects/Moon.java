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
        background = true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        velocity.y = getStage().getPlayer().velocity.y * 2 / 3;
        setX(160 - getStage().getPlayer().getX() / 2);
        if (getY() <= getStage().getPlayer().getY() - 300) {
            setY(getStage().getPlayer().getY() + 400);
        }
    }

}
