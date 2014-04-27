package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.util.ThomUnitConverter;

public class Bullet implements Drawable {

    private static final int SPEED = 80;

    private final double directionRadians;
    private float bulletXThoms;
    private float bulletYThoms;
    private ThomUnitConverter thomUnitConverter;
    private boolean isDead = false;

    public Bullet(float startXThoms, float startYThoms, double directionRadians) {
        this.bulletXThoms = startXThoms;
        this.bulletYThoms = startYThoms;
        this.directionRadians = directionRadians;
    }

    @Override
    public void tick(float deltaTime) {
        this.bulletXThoms -= Math.cos(this.directionRadians) * deltaTime * SPEED;
        this.bulletYThoms -= Math.sin(this.directionRadians) * deltaTime * SPEED;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        if (this.bulletYThoms > thomUnitConverter.getHalfScreenHeightThoms()) {
            shapeRenderer.setColor(Color.WHITE);
        } else {
            shapeRenderer.setColor(Color.BLACK);
        }
        final Vector2 vector2 = thomUnitConverter.thomToPixel(new Vector2(this.bulletXThoms, this.bulletYThoms));
        shapeRenderer.circle(
                vector2.x,
                vector2.y,
                3);
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
        final Vector2 vector2 = thomUnitConverter.thomToPixel(new Vector2(this.bulletXThoms, this.bulletYThoms));
        return new Rectangle(
                vector2.x,
                vector2.y,
                2,
                2
        );
    }

    @Override
    public void collideWith(Drawable drawable) {
        this.isDead = true;
    }

    @Override
    public boolean isDead() {
        return this.isDead || this.thomUnitConverter.isOffScreen(new Vector2(this.bulletXThoms, this.bulletYThoms));
    }

    @Override
    public void screenTouched(float touchXThoms, float touchYThoms) {
        // Do Nothing
    }

}
