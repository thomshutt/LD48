package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.thomshutt.ld48.util.ThomUnitConverter;

import java.util.Random;

public class Player implements Drawable {

    private static enum WALL_HIT { NONE, TOP, BOTTOM, LEFT, RIGHT }

    private static final double TWO_PI = Math.PI * 2;

    private Rectangle topBgRect;
    private Rectangle bottomBgRect;

    private double directionRadians;
    private float playerXThoms = 0;
    private float playerYThoms = 0;
    private float speed = 10f;
    private ThomUnitConverter thomUnitConverter;

    public Player() {
        this.directionRadians = new Random(System.currentTimeMillis()).nextFloat() * TWO_PI;
    }

    @Override
    public void tick(float deltaTime) {
        if(hasHitWall() != WALL_HIT.NONE) {

        }

        this.playerXThoms -= Math.cos(this.directionRadians) * deltaTime * this.speed;
        this.playerYThoms -= Math.sin(this.directionRadians) * deltaTime * this.speed;
    }

    private WALL_HIT hasHitWall() {
        if(this.playerXThoms < thomUnitConverter.getScreenLeftmostThoms()) {
            return WALL_HIT.LEFT;
        }
        if(this.playerXThoms > thomUnitConverter.getScreenRightmostThoms()) {
            return WALL_HIT.RIGHT;
        }
        return WALL_HIT.NONE;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(bottomBgRect.x, bottomBgRect.y, bottomBgRect.width, bottomBgRect.height);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(topBgRect.x, topBgRect.y, topBgRect.width, topBgRect.height);
        
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(
                thomUnitConverter.thomsToPixels(this.playerXThoms),
                thomUnitConverter.thomsToPixels(this.playerYThoms),
                10,
                10);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
    }

    @Override
    public void screenSizeChanged(ThomUnitConverter thomUnitConverter) {
        this.thomUnitConverter = thomUnitConverter;
        bottomBgRect = new Rectangle(
                thomUnitConverter.getScreenLeftmostPixel(),
                thomUnitConverter.getScreenBottommostPixel(),
                thomUnitConverter.getScreenWidthPixels(),
                thomUnitConverter.getHalfScreenHeightPixels()
        );
        topBgRect = new Rectangle(
                thomUnitConverter.getScreenLeftmostPixel(),
                0,
                thomUnitConverter.getScreenWidthPixels(),
                thomUnitConverter.getHalfScreenHeightPixels()
        );
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
        return false;
    }

}
