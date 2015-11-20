/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.effects;

import com.mygdx.game.GameStage;

/**
 *
 * @author Whizzpered
 */
public class Effect {
    protected double time;
    protected static final int TICKS_PER_SEC=250;
    protected int listPosition;

    private String name;
    protected GameStage stage;

    public String getName() {
        return name;
    }
    public int getListPosition() {
        return listPosition;
    }
    public Effect(double time,String name,int listPosition) {
        this.name=name;
        this.time=time*TICKS_PER_SEC;
        this.listPosition=listPosition;
    }
    public void init(GameStage stage){
        this.stage=stage;
        apply();
    }
    public void apply(){}
    public void act(float delta){
       time-=delta;
        if(time<=0) {
            dispose();
        }
    }

    public void dispose() {
        stage.getPlayer().removeEffect(listPosition);
    }
}
