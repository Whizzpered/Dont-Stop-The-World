package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.objects.Obstacle;
import com.mygdx.gui.Element;
import com.mygdx.gui.GUILayer;
import com.mygdx.gui.GUIUtils;

/**
 *
 * @author Whizzpered
 */
public class MenuStage extends Stage {

    private GameStage gameStage;
    private MenuStage thisClass = this;
    private MyGdxGame game;
    private GUILayer layer = new GUILayer();
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Texture logoTexture;
    public MyGdxGame getGame() {
        return game;
    }

    public BitmapFont getFont() {
        return font;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public MenuStage(Viewport vp, MyGdxGame game) {
        super(vp);
        this.game = game;
        initAssets();
        initGUI();
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
            }
        });
    }

    private void initAssets() {
        logoTexture= new Texture(Gdx.files.internal("logo.png"));
        asset = new AssetManager();
        asset.load("pack.pack", TextureAtlas.class);
        asset.load("gui.pack", TextureAtlas.class);
        asset.finishLoading();
        GUIUtils.GUI_ATLAS = asset.get("gui.pack");
        font = new BitmapFont(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        layer.act(delta);
    }

    public void initGUI() {
        layer.add(new Element("Start", 160, 200, 104, 58) {
            @Override
            public void tap() {
                if (gameStage == null) {
                    gameStage = new GameStage(game.vp, thisClass);
                }
                game.stage = gameStage;
                Gdx.input.setInputProcessor(gameStage);
            }
        });
        layer.add(new Element("Exit", 160, 340, 104, 58) {
            @Override
            public void tap() {
                System.exit(0);
            }
        });
        layer.add(new Element("Exit (1)", 160,270, 104, 58) {
            @Override
            public void tap() {
                gameStage = new GameStage(game.vp, thisClass);
                Gdx.input.setInputProcessor(gameStage);
            }
        });
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();
        getBatch().begin();
        getBatch().draw(logoTexture, 33, 10);
        if (gameStage != null) {
            getFont().draw(getBatch(), "Score:" + gameStage.getPoints(), 120, 200);
        }
        layer.draw(getBatch(), 1);
        getBatch().end();
    }
}
