/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.EffectsList;

/**
 *
 * @author Whizzpered
 */
public class Player extends Entity {
    boolean up = false;
    public short health=4;
    private final int DEATH_VELOCITY=680;
    public Effect[] effects= new Effect[EffectsList.getAmount()];
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
        if (velocity.y > DEATH_VELOCITY) {
           health=0;
        }
        if(health<=0){
            getStage().setGameOver(true);
        }
        for (Effect e: effects){
            if(e!=null)
              e.act(1);
        }
    }

    @Override
    public void action() {
    }
    public void addEffect(Effect e){
        e.apply();
        effects[e.getListPosition()] = e;
    }
    public void removeEffect(int i){
        effects[i]=null;
    }
    public void touchLeft() {
        velocity.x = -250;
    }

    public void touchRight() {
        velocity.x = 250;
    }
}
