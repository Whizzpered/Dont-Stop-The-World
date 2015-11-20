package com.mygdx.game.effects;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Created by yasmidrog on 15.11.15.
 */
public class EffectsList {
    private final static Effect[] avialibleEffects=new Effect[]{
        new Effect(1,"Health",1, Color.RED){
            @Override
            public void apply(){
                if(stage.getPlayer().health<Short.MAX_VALUE)
                    stage.getPlayer().health+=1;
                time=-1;
            }
            @Override
            public void dispose(){
              super.dispose();
            }
            @Override
            public void act(float delta){
              super.act(delta);
            }
        }
    };
    public static Effect getRandomEffect(){
        return avialibleEffects[new Random().nextInt(avialibleEffects.length)];
    }
    public static int getAmount(){
        return 10;
    }
}
