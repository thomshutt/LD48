package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.thomshutt.ld48.util.ThomUnitConverter;

public class Bullet implements Drawable {

    private static final int speed = 10;

    private final double directionRadians;
    private float bulletXThoms;
    private float bulletYThoms;
    private ThomUnitConverter thomUnitConverter;

    public Bullet(float startXThoms, float startYThoms, double directionRadians) {
        this.bulletXThoms = startXThoms;
        this.bulletYThoms = startYThoms;
        this.directionRadians = directionRadians;
    }

    @Override
    public void tick(float deltaTime) {
        this.bulletXThoms -= Math.cos(this.directionRadians) * deltaTime * this.speed;
        this.bulletYThoms -= Math.sin(this.directionRadians) * deltaTime * this.speed;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        if(this.bulletYThoms > 0) {
            shapeRenderer.setColor(Color.WHITE);
        } else {
            shapeRenderer.setColor(Color.BLACK);
        }
        shapeRenderer.rect(
                thomUnitConverter.thomsToPixels(this.bulletXThoms),
                thomUnitConverter.thomsToPixels(this.bulletYThoms),
                10,
                10);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {

    }

    @Override
    public void screenSizeChanged(ThomUnitConverter thomUnitConverter) {
        this.thomUnitConverter = thomUnitConverter;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return null;
    }

    @Override
    public void collideWith(Drawable drawable) {

    }

    @Override
    public boolean isDead() {
        return this.thomUnitConverter.isOffScreen(this.bulletXThoms, this.bulletYThoms);
    }

    @Override
    public void screenTouched(float touchXThoms, float touchYThoms) {
        // Do Nothing
    }

}
