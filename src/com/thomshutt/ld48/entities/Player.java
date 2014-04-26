package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.BulletFactory;
import com.thomshutt.ld48.DrawableList;
import com.thomshutt.ld48.util.ThomUnitConverter;

import java.util.Random;

public class Player implements Drawable {

    private static enum WALL_HIT { NONE, TOP, BOTTOM, LEFT, RIGHT }
    private static final double TWO_PI = Math.PI * 2;

    private final Random random = new Random(System.currentTimeMillis());
    private final BulletFactory bulletFactory;

    private Rectangle topBgRect;
    private Rectangle bottomBgRect;

    private double directionRadians;
    private float playerXThoms = 0;
    private float playerYThoms = 0;
    private float speed = 50f;
    private int playerWidthThoms = 10;
    private int playerHeightThoms = 10;
    private ThomUnitConverter thomUnitConverter;

    public Player(DrawableList drawableList) {
        this.directionRadians = random.nextFloat() * TWO_PI;
        this.bulletFactory = new BulletFactory(drawableList);
    }

    @Override
    public void tick(float deltaTime) {
        switch(hasHitWall()) {
            case LEFT:
            case RIGHT:
                this.directionRadians = Math.PI - this.directionRadians;
                this.speed += 0.1;
                break;
            case TOP:
            case BOTTOM:
                this.speed += 0.1;
                this.directionRadians = -this.directionRadians;
                break;
            default:
                break;
        }

        this.playerXThoms -= Math.cos(this.directionRadians) * deltaTime * this.speed;
        this.playerYThoms -= Math.sin(this.directionRadians) * deltaTime * this.speed;
    }

    private WALL_HIT hasHitWall() {
        if(this.playerXThoms < thomUnitConverter.getScreenLeftmostThoms())
            return WALL_HIT.LEFT;
        if(this.playerXThoms > thomUnitConverter.getScreenRightmostThoms())
            return WALL_HIT.RIGHT;
        if(this.playerYThoms < thomUnitConverter.getScreenBottommostThoms())
            return WALL_HIT.BOTTOM;
        if(this.playerYThoms > thomUnitConverter.getScreenTopmostThoms())
            return WALL_HIT.TOP;

        return WALL_HIT.NONE;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(bottomBgRect.x, bottomBgRect.y, bottomBgRect.width, bottomBgRect.height);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(topBgRect.x, topBgRect.y, topBgRect.width, topBgRect.height);

        if(this.playerYThoms > 0) {
            shapeRenderer.setColor(Color.BLUE);
        }
        final Vector2 vector2 = thomUnitConverter.thomToPixel(new Vector2(this.playerXThoms, this.playerYThoms));
        shapeRenderer.rect(
                vector2.x,
                vector2.y,
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
                thomUnitConverter.thomToPixel(new Vector2(0, 0)).x,
                thomUnitConverter.getScreenBottommostPixel(),
                thomUnitConverter.getScreenWidthPixels(),
                thomUnitConverter.getHalfScreenHeightPixels()
        );
        topBgRect = new Rectangle(
                thomUnitConverter.thomToPixel(new Vector2(0, 0)).x,
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

    @Override
    public void screenTouched(float touchXPixels, float touchYPixels) {
        final Vector2 vector2 = thomUnitConverter.touchToThoms();
        float radians = (float) (Math.atan2(playerYThoms - vector2.y, playerXThoms - vector2.x));
        double degrees = (Math.toDegrees(radians) + 360) % 360;
        this.bulletFactory.createBullet(this.playerXThoms, this.playerYThoms, Math.toRadians(degrees), this.thomUnitConverter);
    }

}
