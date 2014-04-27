package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.LD48;
import com.thomshutt.ld48.util.ThomUnitConverter;
import com.thomshutt.ld48.entities.Player.WALL_HIT;

import static com.thomshutt.ld48.entities.Player.WALL_HIT.*;

public class Enemy implements Drawable {

    private ThomUnitConverter thomUnitConverter;
    private double directionRadians;
    private double speed = 10;
    private float enemyXThoms;
    private float enemyYThoms;
    private boolean isDead = false;

    public Enemy(ThomUnitConverter thomUnitConverter, float startXThoms, float startYThoms) {
        this.screenSizeChanged(thomUnitConverter);
        this.directionRadians = LD48.RANDOM.nextFloat() * LD48.TWO_PI;
        this.enemyXThoms = startXThoms;
        this.enemyYThoms = startYThoms;
    }

    @Override
    public void tick(float deltaTime) {
        switch (hasHitWall()) {
            case LEFT:
            case RIGHT:
                this.directionRadians = Math.PI - this.directionRadians;
                this.speed += 0.2;
                break;
            case TOP:
            case BOTTOM:
                this.speed += 0.2;
                this.directionRadians = -this.directionRadians;
                break;
            default:
                break;
        }

        this.enemyXThoms -= Math.cos(this.directionRadians) * deltaTime * this.speed;
        this.enemyYThoms -= Math.sin(this.directionRadians) * deltaTime * this.speed;
    }

    private WALL_HIT hasHitWall() {
        if (this.enemyXThoms < thomUnitConverter.getScreenLeftmostThoms())
            return LEFT;
        if (this.enemyXThoms > thomUnitConverter.getScreenRightmostThoms())
            return RIGHT;
        if (this.enemyYThoms < thomUnitConverter.getScreenBottommostThoms())
            return BOTTOM;
        if (this.enemyYThoms > thomUnitConverter.getScreenTopmostThoms())
            return TOP;

        return NONE;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        final Vector2 vector2 = thomUnitConverter.thomToPixel(new Vector2(this.enemyXThoms, this.enemyYThoms));
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(
                vector2.x,
                vector2.y,
                20,
                20);
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
        final Vector2 vector2 = thomUnitConverter.thomToPixel(new Vector2(this.enemyXThoms, this.enemyYThoms));
        return new Rectangle(
                vector2.x,
                vector2.y,
                20,
                20
        );
    }

    @Override
    public void collideWith(Drawable drawable) {
        this.isDead = true;
    }

    @Override
    public boolean isDead() {
        return this.isDead || thomUnitConverter.isOffScreen(new Vector2(this.enemyXThoms, this.enemyYThoms));
    }

    @Override
    public void screenTouched(float touchXThoms, float touchYThoms) {
        // Do Nothing
    }

}
