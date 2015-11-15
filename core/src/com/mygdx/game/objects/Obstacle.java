package com.mygdx.game.objects;

import com.mygdx.game.MyGdxGame;

/**
 *
 * @author Whizzpered
 */
public class Obstacle extends Entity {

    public Obstacle(float x, float y) {
        super(x, y);
        touchable = true;
        setName("nlo");
        velocity.x = MyGdxGame.RANDOM.nextBoolean() ? -50 : 50;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (getY() - getStage().getPlayer().getY() < -200) {
            getStage().getActors().removeValue(this, true);
        }
        if (!used && collides(getStage().getPlayer())) {
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
        sprite = getStage().getAtlas().createSprite("nlo_damaged");
        sprite.setFlip(false, true);
    }
}
