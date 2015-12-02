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

    private Effect effect;
    private Player pl;
    public Obstacle(float x, float y) {
        super(x, y);
        touchable = true;
        setName("nlo");
        velocity.x = MyGdxGame.RANDOM.nextBoolean() ? -50 : 50;
        
    }
    
    @Override
    public void init() {
        if (new Random().nextInt(100) < 20) {
            effect = EffectsList.getRandomEffect();
            effect.init(getStage()); 
        }
        pl=getStage().getPlayer();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() - pl.getY() < -200) {
            getStage().getActors().removeValue(this, true);
        }
        if (!used && collides(pl)) {
            if (pl.getVelocity().y > pl.MAX_COLLIDE_VELOCITY&&!pl.invincible) {
                pl.health -= 1;
            }
            action();
        }
        if (Math.abs(velocity.y) >= 400) {
            remove();
        }
    }

    @Override
    public void action() {
        velocity.y = pl.getVelocity().y / 2;
        acceleration.y = 100;
        pl.getVelocity().y = -pl.getVelocity().y / 2;
        pl.getVelocity().x = pl.getX() - getX();
        velocity.x = getX() - pl.getX();
        touchable = false;
        used = true;
        if (effect != null) {
            pl.addEffect(effect);
        }
        sprite = getStage().getAtlas().createSprite("nlo_damaged");
        sprite.setFlip(false, true);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (effect != null && !used) {
            effect.getSprite().setCenter(getX(), getY() - pl.getY() + 60);
            effect.getSprite().draw(batch);
        }
    }
}
