/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameStage;

/**
 *
 * @author Whizzpered
 */
public class Entity extends Actor {

    Vector2 velocity, acceleration;
    protected Sprite sprite;
    Color c;
    Circle cl;
    public boolean touchable = false;

    public Sprite getSprite() {
        return sprite;
    }

    public Circle getCircle() {
        return cl;
    }

    public boolean collides(Entity ent) {
        if (getY() < ent.getY() + ent.getHeight()) {
            return (Intersector.overlaps(ent.getCircle(), cl));
        }
        return false;
    }

    @Override
    public GameStage getStage() {
        return (GameStage) (super.getStage());
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Entity(float x, float y) {
        setX(x);
        setY(y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        cl = new Circle();
        setName("astronaut");
    }

    public void setSprite() {
        sprite = getStage().getAtlas().createSprite(getName());
        sprite.setFlip(false, true);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }

    @Override
    public void act(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        if (velocity.y > 200) {
            velocity.y = 200;
        }
        if (velocity.x > 0) {
            velocity.x -= 10;
        }
        if (velocity.x < 0) {
            velocity.x += 10;
        }
        setX(getX() + velocity.cpy().scl(delta).x);
        setY(getY() + velocity.cpy().scl(delta).y);
        cl.set(getX(), getY(), getWidth() / 3);
        if (getX() < 0) {
            setX(0);
        }

        if (getX() > this.getStage().getWidth() - getWidth()) {
            setX(this.getStage().getWidth() - getWidth());
        }

    }

    public void action() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setCenterX(getX());
        sprite.setCenterY(getY() - getStage().getPlayer().getY() + 160);
        sprite.draw(batch);
    }
}
