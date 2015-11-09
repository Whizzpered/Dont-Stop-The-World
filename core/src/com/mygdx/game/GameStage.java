/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.objects.Entity;
import com.mygdx.game.objects.Moon;
import com.mygdx.game.objects.Obstacle;
import com.mygdx.game.objects.Player;
import com.mygdx.game.objects.Robot;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 *
 * @author Whizzpered
 */
public class GameStage extends Stage {

    private float points;

    private Player pl;
    private OrthographicCamera cam;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;

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
    }

    private void initAssets() {
        asset = new AssetManager();
        asset.load("TexturePack.pack", TextureAtlas.class);
        asset.finishLoading();
        atlas = asset.get("TexturePack.pack");
        font = new BitmapFont(true);
    }

    public void initCam() {
        cam = new OrthographicCamera();
        cam.setToOrtho(true);
        getViewport().setCamera(cam);
    }

    @Override
    public void act() {
        super.act();
        points += (500 - pl.getVelocity().y) / 100f;
        if (getObstacles().size < 3) {
            addEntity(new Obstacle(MyGdxGame.RANDOM.nextInt(260) + 30,
                    pl.getY() + 420 + MyGdxGame.RANDOM.nextInt(100)));
            addEntity(new Obstacle(MyGdxGame.RANDOM.nextInt(260) + 30,
                    pl.getY() + 580 + MyGdxGame.RANDOM.nextInt(100)));
        }
    }

    public void addEntity(Entity ent) {
        addActor(ent);
        ent.setSprite();
    }

    public void initEntities() {
        addEntity(new Moon(180, 190));
        pl = new Player(160, 0);
        addEntity(pl);
        addEntity(new Robot(160, 0));
        setKeyboardFocus(pl);
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                double dist;
                for (Obstacle ob : getObstacles()) {
                    dist = Math.sqrt(Math.pow(x - ob.getX(), 2) + Math.pow(y - ob.getSprite().getY(), 2));
                    Gdx.app.log("Dragged", dist + "");
                    if (dist < 3 * ob.getSprite().getWidth() / 4 && ob.touchable) {
                        ob.setX(x);
                        break;
                    }
                }
            }
        });
        addEntity(new Obstacle(120, 200));
    }

    @Override
    public void draw() {
        super.draw();
        getBatch().begin();
        getFont().setColor(Color.WHITE);
        getFont().draw(getBatch(), String.valueOf(getPoints()), 10, 10);
        getBatch().end();
    }
}
