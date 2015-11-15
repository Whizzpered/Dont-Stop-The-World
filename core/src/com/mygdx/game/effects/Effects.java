package com.mygdx.game.effects;

import java.util.Random;

/**
 * Created by yasmidrog on 15.11.15.
 */
public class Effects {
    private final static Effect[] avialibleEffects=new Effect[]{
        new Effect(1,"Health"){
            @Override
            public void apply(){
                if(stage.getPlayer().health<Short.MAX_VALUE)
                    stage.getPlayer().health+=1;
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
}
