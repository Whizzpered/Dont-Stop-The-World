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

    protected double time;
    protected static final int TICKS_PER_SEC = 120;
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

    public Effect(double time, String name, int listPosition, Color color) {
        this.name = name;
        this.time = time * TICKS_PER_SEC;
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
        time -= delta;
        if (time <= 0) {
            dispose();
        }
    }

    public void dispose() {
        stage.getPlayer().removeEffect(listPosition);
    }
}
