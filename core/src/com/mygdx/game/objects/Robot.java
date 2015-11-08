/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Whizzpered
 */
public class Robot extends Entity {

    boolean flipped = false;

    public Robot(float x, float y) {
        super(x, y);
        acceleration = new Vector2(0, 0);
        c = new Color(Color.BLUE);
        setName("robot");
    }

    @Override
    public void act(float delta) {
        sprite.setFlip(getStage().getPlayer().getX() > 160, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setCenterX(160);
        sprite.setCenterY(50);
        sprite.draw(batch);
    }
}
