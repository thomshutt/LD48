package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.thomshutt.ld48.util.ThomUnitConverter;

public interface Drawable {

    public void tick(float deltaTime);

    public void draw(ShapeRenderer shapeRenderer);
    public void draw(SpriteBatch spriteBatch);

    public void screenSizeChanged(ThomUnitConverter thomUnitConverter);

    public Rectangle getCollisionRectangle();

    public void collideWith(Drawable drawable);

    public boolean isDead();

}
