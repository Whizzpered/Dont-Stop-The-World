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
import com.mygdx.gui.GUIUtils;

/**
 *
 * @author Whizzpered
 */
public class GameStage extends Stage {

    private GUILayer layer = new GUILayer();
    private float points;
    private float slowCoef = 1f;
    private Player pl;
    private OrthographicCamera cam;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Entity focus;
    public boolean changeEventColor = false;
    private boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameover) {
        gameOver = gameover;
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

    public GameStage(Viewport vp) {
        super(vp);
        initialize();
    }

    public void initialize() {
        initAssets();
        initCam();
        initEntities();
        initGUI();
        initEvents();
    }

    private void initAssets() {
        asset = new AssetManager();
        asset.load("pack.pack", TextureAtlas.class);
        asset.finishLoading();
        atlas = GUIUtils.GUI_ATLAS = asset.get("pack.pack");
        font = new BitmapFont(true);
    }

    public void initCam() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true);
        getViewport().setCamera(cam);
    }

    public void initEvents() {
        Event slow = new EventSlowness();
    }

    @Override
    public void act(float delta) {
        if (!gameOver) {
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
    
    public void initGUI(){
        layer.add(new Element("nlo", 10, 10, 64, 64) {
            
            @Override
            public void tap() {
                System.out.println("Hello");
            }
        });
    }

    public void initEntities() {
        addEntity(new Moon(180, 190));
        pl = new Player(160, 0);
        addEntity(pl);
        setKeyboardFocus(pl);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {
                if (focus == null && !gameOver) {
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
                if (focus != null && focus.touchable && !gameOver) {
                    focus.setX(x);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                focus = null;
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
            }
        });
        addEntity(new Obstacle(120, 200));
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
        if (gameOver) {
            getFont().setColor(Color.BLACK);
            getFont().draw(getBatch(), "GameOver", 10, 70);
        }
        layer.draw(getBatch(), 1);
        getBatch().end();
    }
}
