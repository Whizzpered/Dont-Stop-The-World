/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.GameStage;

/**
 *
 * @author Whizzpered
 */
public class Effect {

    protected double currrentTime, defaultTime;
    protected static final int TICKS_PER_SEC = 100;
    protected int listPosition;
    private Color color;
    private String name;
    protected GameStage stage;
    Sprite sprite;

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getListPosition() {
        return listPosition;
    }

    public Effect(double currrentTime, String name, int listPosition, Color color) {
        this.name = name;
        this.currrentTime =defaultTime= currrentTime * TICKS_PER_SEC;
        this.listPosition = listPosition;
        this.color = color;
    }

    public void init(GameStage stage) {
        this.stage = stage;
        sprite = stage.getAtlas().createSprite("buff");
        sprite.setFlip(false, true);
        sprite.setColor(getColor());
    }

    public void apply() {
    }

    public void act(float delta) {
        currrentTime -= delta;
        if (currrentTime <= 0) {
            dispose();
        }
    }
    public void draw(){

    }
    public void dispose() {
        stage.getPlayer().removeEffect(listPosition);
        currrentTime = defaultTime;
    }
}
