/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.effects;

import com.mygdx.game.GameStage;
import com.mygdx.game.objects.Entity;

/**
 *
 * @author Whizzpered
 */
public class Effect {
    protected long time;
    protected int listPosition;
    private String name;
    protected GameStage stage;
    public String getName() {
        return name;
    }

    public Effect(long time,String name) {
        this.name=name;
        this.time=time;
    }
    public void init(GameStage stage,int listPosition){
        this.stage=stage;
        this.listPosition=listPosition;
        apply();
    }
    public void apply(){}
    public void act(float delta){
       time-=1;
        if(time<=0) {
            dispose();
        }
    }
    public  void dispose(){
        stage.getPlayer().removeEffect(listPosition);
    }
}
