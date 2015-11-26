/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.objects.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.events.*;
import com.mygdx.gui.Element;
import com.mygdx.gui.GUILayer;

/**
 *
 * @author Whizzpered
 */
public class GameStage extends Stage {

    public MenuStage menu;
    private GUILayer layer = new GUILayer();
    private float points;
    private float slowCoef = 1f;
    private InputListener touchListener;
    private Player pl;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Entity focus;
    public boolean changeEventColor = false;
    private boolean gameOver = false, paused = false;

    public boolean isGameOverOrPaused() {
        return gameOver || paused;
    }

    public void setGameOver(boolean gameover) {
        gameOver = gameover;
        removeListener(touchListener);
    }
    public void setPaused (boolean paused){
        this.paused = paused;
    }

    //list of Events that are currently working
    public BitmapFont getFont() {
        return font;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public Player getPlayer() {
        return pl;
    }

    public int getPoints() {
        return (int) points;
    }

    public void setSlowCoef(float slowCoef) {
        this.slowCoef = slowCoef;
    }

    public Array<Obstacle> getObstacles() {
        Array<Obstacle> ob = new Array<Obstacle>();
        for (Actor a : this.getActors()) {
            if (a instanceof Obstacle) {
                ob.add((Obstacle) a);
            }
        }
        return ob;
    }

    public GameStage(Viewport vp, MenuStage menu) {
        super(vp);
        this.menu = menu;
        initialize();
    }

    public void initialize() {
        initAssets();
        initEntities();
        initGUI();
        initEvents();
    }

    private void initAssets() {
        asset = menu.asset;
        atlas = asset.get("pack.pack");
        font = new BitmapFont(true);
    }

    public void initEvents() {
        Event slow = new EventSlowness();
    }

    @Override
    public void act(float delta) {
        if (!isGameOverOrPaused()) {
            EventHandler.act(this, 1);
            super.act(delta / slowCoef);
            points += (500 - pl.getVelocity().y) / 5000f / slowCoef;
            if (getObstacles().size < 3) {
                addEntity(new Obstacle(MyGdxGame.RANDOM.nextInt(260) + 30,
                        pl.getY() + 420 + MyGdxGame.RANDOM.nextInt(100)));
                addEntity(new Obstacle(MyGdxGame.RANDOM.nextInt(260) + 30,
                        pl.getY() + 580 + MyGdxGame.RANDOM.nextInt(100)));
            }
        }
        layer.act(delta);
    }

    public void addEntity(Entity ent) {
        addActor(ent);
        ent.init();
        ent.setSprite();
    }

    public void initGUI() {
        layer.add(new Element("Pause", 298, 13, 52, 29) {
            @Override
            public void tap() {
                menu.getGame().stage = menu;
                Gdx.input.setInputProcessor(menu);
            }
        });
    }

    public void initEntities() {
        addEntity(new Moon(180, 190));
        pl = new Player(160, 0);
        addEntity(pl);
        setKeyboardFocus(pl);
        touchListener=createListener();
        addListener(touchListener);
        addEntity(new Obstacle(120, 200));
    }
    private InputListener createListener(){
        return new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {
                if (focus == null) {
                    double dist;
                    for (Obstacle ob : getObstacles()) {
                        dist = Math.sqrt(Math.pow(x - ob.getX(), 2) + Math.pow(y - ob.getSprite().getY(), 2));
                        if (dist < ob.getSprite().getWidth() && ob.touchable) {
                            focus = ob;
                            break;
                        }
                    }
                }
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (focus != null && focus.touchable) {
                    focus.setX(x);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                focus = null;
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
            }
        };
    }

    @Override
    public void draw() {
        float c = Math.max(0, getPlayer().getVelocity().y - 200) / 500;
        if (!changeEventColor) {
            Gdx.gl.glClearColor(c, (1f - c) * 0.3f, (1f - c) * 0.3f, 1f);
        } else {
            Gdx.gl.glClearColor(0.4f, 0.8f, 0f, 0.7f);//204.204.0
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();
        getBatch().begin();
        getFont().setColor(Color.WHITE);
        getFont().draw(getBatch(), String.valueOf(getPoints()), 10, 10);
        getFont().draw(getBatch(), (int) (getPlayer().getVelocity().y / 10) + " kM/h", 10, 30);
        getFont().setColor(Color.YELLOW);
        getFont().draw(getBatch(), getPlayer().health + " HP", 10, 50);
        layer.draw(getBatch(), 1);
        getBatch().end();
    }
}
