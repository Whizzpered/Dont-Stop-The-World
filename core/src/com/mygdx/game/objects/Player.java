/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Whizzpered
 */
public class Player extends Entity {

    double angle = 0;
    boolean up = false;

    public Player(float x, float y) {
        super(x, y);
        acceleration = new Vector2(0, 100);
        c = new Color(Color.BLUE);
    }

    public void up() {
        up = true;
        getVelocity().y = -100;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getVelocity().y > 100) {
            up = false;
        }
        if (!up) {
            angle += getVelocity().y * delta / 25;
            if (angle > 2 * Math.PI) {
                angle -= 2 * Math.PI;
            }

        }
        setX((float) (160 + Math.sin(angle) * 30f));
    }

    @Override
    public void action() {

    }

    public void touchLeft() {
        velocity.x = -250;
    }

    public void touchRight() {
        velocity.x = 250;
    }
}
