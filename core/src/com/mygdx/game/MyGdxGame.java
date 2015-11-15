package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    public static final Random RANDOM = new Random();
    private GameStage stage;

    @Override
    public void create() {
        stage = new GameStage(new StretchViewport(320, 480));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float c = Math.max(0, stage.getPlayer().getVelocity().y - 200) / 500;
        if(!stage.changeEventColor)
           Gdx.gl.glClearColor(c, (1f - c) *0.3f,(1f - c)*0.3f, 1f);
        else   Gdx.gl.glClearColor(c, (1f - c) * 0.3f, (1f - c)*0.1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
