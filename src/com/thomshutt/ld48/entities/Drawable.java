package com.thomshutt.ld48.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public interface Drawable {

    public void tick(float deltaTime);

    public void draw(ShapeRenderer shapeRenderer);

    public void screenSizeChanged();

    public Rectangle getCollisionRectangle();

    public void collideWith(Drawable drawable);

    public boolean isDead();

}
