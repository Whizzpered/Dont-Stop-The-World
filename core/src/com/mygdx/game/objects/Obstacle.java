package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.effects.Effect;
import com.mygdx.game.effects.EffectsList;

import java.util.Random;

/**
 *
 * @author Whizzpered
 */
public class Obstacle extends Entity {

    private int DEATH_VELOCITY = 560;
    private Effect effect;

    public Obstacle(float x, float y) {
        super(x, y);
        touchable = true;
        setName("nlo");
        velocity.x = MyGdxGame.RANDOM.nextBoolean() ? -50 : 50;
        if (new Random().nextInt(100) < 20) {
            effect = EffectsList.getRandomEffect();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() - getStage().getPlayer().getY() < -200) {
            getStage().getActors().removeValue(this, true);
        }
        if (!used && collides(getStage().getPlayer())) {
            if (getStage().getPlayer().getVelocity().y > DEATH_VELOCITY) {
                getStage().getPlayer().health -= 1;
            }
            action();
        }
        if (Math.abs(velocity.y) >= 400) {
            remove();
        }
    }

    @Override
    public void action() {
        velocity.y = getStage().getPlayer().getVelocity().y / 2;
        acceleration.y = 100;
        getStage().getPlayer().getVelocity().y = -getStage().getPlayer().getVelocity().y / 2;
        getStage().getPlayer().getVelocity().x = getStage().getPlayer().getX() - getX();
        velocity.x = getX() - getStage().getPlayer().getX();
        touchable = false;
        used = true;
        if (effect != null) {
            getStage().getPlayer().addEffect(effect);
        }
        sprite = getStage().getAtlas().createSprite("nlo_damaged");
        sprite.setFlip(false, true);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (effect != null) {
            Sprite buffSprite = getStage().getAtlas().createSprite("buff");
            buffSprite.setFlip(false, true);
            buffSprite.setCenter(getX(), getY() - getStage().getPlayer().getY() + 60);
            buffSprite.draw(batch);
        }
    }
}
