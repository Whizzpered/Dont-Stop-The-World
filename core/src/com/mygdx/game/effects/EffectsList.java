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
                if(stage.getPlayer().health<stage.getPlayer().maxHealth)
                    stage.getPlayer().health+=1;
                currrentTime =-1;
            }
            @Override
            public void dispose(){
              super.dispose();
            }
            @Override
            public void act(float delta){
              super.act(delta);
            }
        }, new Effect(10,"X2",2, Color.YELLOW){
                float coef;
                @Override
                public void apply(){
                    coef=stage.pointCoef;
                    stage.pointCoef=coef/2;
                }
                @Override
                public void dispose(){
                    super.dispose();
                    stage.pointCoef=coef;
                }
                @Override
                public void act(float delta){
                    super.act(delta);
                }
                 @Override
                  public void draw(){
                     stage.getBatch().begin();
                     stage.getFont().setColor(this.getColor());
                     stage.getFont().draw(stage.getBatch(), "X2", 70, 10);
                     stage.getBatch().end();
                  }

        },
            new Effect(10,"Decrease_velocity",3, Color.ORANGE){
                float a;
                @Override
                public void apply(){
                    a=stage.getPlayer().accelAbsolute;
                    stage.getPlayer().accelAbsolute =a/2;
                }
                @Override
                public void dispose(){
                    super.dispose();
                    stage.getPlayer().accelAbsolute =a;
                }
                @Override
                public void act(float delta){
                    super.act(delta);
                }
                @Override
                public void draw(){
                    stage.getBatch().begin();
                    stage.getFont().setColor(this.getColor());
                    stage.getFont().draw(stage.getBatch(),"/2" , 70, 30);
                    stage.getBatch().end();
                }
            },
            new Effect(10,"Invincible",4, Color.DARK_GRAY){
                @Override
                public void apply(){
                    stage.getPlayer().invincible=true;
                }
                @Override
                public void dispose(){
                    super.dispose();
                    stage.getPlayer().invincible=false;
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
