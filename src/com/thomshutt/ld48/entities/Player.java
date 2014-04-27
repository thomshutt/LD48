package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.BulletFactory;
import com.thomshutt.ld48.DrawableList;
import com.thomshutt.ld48.LD48;
import com.thomshutt.ld48.util.ThomUnitConverter;

public class Player implements Drawable {

    public static enum WALL_HIT {NONE, TOP, BOTTOM, LEFT, RIGHT;}
    private static final Vector2 PLAYER_SIZE_THOMS = new Vector2(5, 5);

    private final BulletFactory bulletFactory;

    private double directionRadians;
    private float playerXThoms = 37;
    private float playerYThoms = 50;
    private float speed = 50f;
    private Vector2 playerSizePixels;
    private boolean isDead = false;


    private ThomUnitConverter thomUnitConverter;

    public Player(DrawableList drawableList) {
        this.directionRadians = LD48.RANDOM.nextFloat() * LD48.TWO_PI;
        this.bulletFactory = new BulletFactory(drawableList);
    }

    @Override
    public void tick(float deltaTime) {
        switch (hasHitWall()) {
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
        if (this.playerXThoms < thomUnitConverter.getScreenLeftmostThoms())
            return WALL_HIT.LEFT;
        if (this.playerXThoms > thomUnitConverter.getScreenRightmostThoms())
            return WALL_HIT.RIGHT;
        if (this.playerYThoms < thomUnitConverter.getScreenBottommostThoms())
            return WALL_HIT.BOTTOM;
        if (this.playerYThoms > thomUnitConverter.getScreenTopmostThoms())
            return WALL_HIT.TOP;

        return WALL_HIT.NONE;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer) {
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        LD48.SPRITE_ABOVE.draw(spriteBatch);
        LD48.SPRITE_BELOW.draw(spriteBatch);

        final Vector2 positionPixels = thomUnitConverter.positionalThomToPixel(new Vector2(this.playerXThoms, this.playerYThoms));
        if (this.playerYThoms > thomUnitConverter.getHalfScreenHeightThoms()) {
            spriteBatch.draw(LD48.TEXTURE_PLAYER_ABOVE, positionPixels.x, positionPixels.y, playerSizePixels.x, playerSizePixels.y);
        } else {
            spriteBatch.draw(LD48.TEXTURE_PLAYER_BELOW, positionPixels.x, positionPixels.y, playerSizePixels.x, playerSizePixels.y);
        }
    }

    @Override
    public void screenSizeChanged(ThomUnitConverter thomUnitConverter) {
        this.thomUnitConverter = thomUnitConverter;
        playerSizePixels = thomUnitConverter.sizeThomToPixel(PLAYER_SIZE_THOMS);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        final Vector2 vector2 = thomUnitConverter.positionalThomToPixel(new Vector2(this.playerXThoms, this.playerYThoms));
        return new Rectangle(
                vector2.x,
                vector2.y,
                this.playerSizePixels.x,
                this.playerSizePixels.y
        );
    }

    @Override
    public void collideWith(Drawable drawable) {
        if(drawable instanceof Enemy){
            this.isDead = true;
        }
    }

    @Override
    public boolean isDead() {
        return this.isDead;
    }

    @Override
    public void screenTouched(float touchXPixels, float touchYPixels) {
        final Vector2 vector2 = thomUnitConverter.touchToThoms();
        float radians = (float) (Math.atan2(playerYThoms - vector2.y, playerXThoms - vector2.x));
        double degrees = (Math.toDegrees(radians) + 360) % 360;
        if (this.playerYThoms < thomUnitConverter.getHalfScreenHeightThoms()) {
            degrees += 180;
        }
        this.bulletFactory.createBullet(this.playerXThoms, this.playerYThoms, Math.toRadians(degrees), this.thomUnitConverter);
    }

}
