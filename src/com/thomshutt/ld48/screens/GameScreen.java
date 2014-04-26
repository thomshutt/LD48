package com.thomshutt.ld48.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.thomshutt.ld48.entities.Drawable;
import com.thomshutt.ld48.entities.Player;
import com.thomshutt.ld48.util.ThomUnitConverter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScreen implements ApplicationListener {

    private final GameScreenListener gameScreenListener;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private List<Drawable> drawables = new CopyOnWriteArrayList<Drawable>();

    private ThomUnitConverter thomUnitConverter;
    private ShapeRenderer shapeRenderer;

    public GameScreen(GameScreenListener gameScreenListener) {
        this.gameScreenListener = gameScreenListener;
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        Gdx.gl.glClearColor(0, 0, 0, 1);

        this.drawables.add(new Player());
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        doUpdate();
        doRender();
    }

    private void doUpdate(){
        removeDeadDrawables();
        simulateCollisions();
        tick();
    }

    private void removeDeadDrawables() {
        for (Drawable drawable : drawables) {
            if(drawable.isDead()){
                drawables.remove(drawable);
            }
        }
    }

    private void tick() {
        final float deltaTime = Gdx.graphics.getDeltaTime();
        for (Drawable drawable : drawables) {
            drawable.tick(deltaTime);
        }
    }

    private void simulateCollisions() {
        for (Drawable drawable : drawables) {
            if(drawable.getCollisionRectangle() == null) {
                continue;
            }
            for (Drawable drawable1 : drawables) {
                if(drawable1.getCollisionRectangle() == null) {
                    continue;
                }
                if(drawable.getCollisionRectangle().overlaps(drawable1.getCollisionRectangle())){
                    drawable.collideWith(drawable1);
                }
            }
        }
    }

    private void doRender(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Drawable drawable : drawables) {
            drawable.draw(shapeRenderer);
        }
        shapeRenderer.end();

        batch.begin();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.thomUnitConverter = new ThomUnitConverter(width, height);
        this.camera = new OrthographicCamera(width, height);
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.batch.setProjectionMatrix(camera.combined);
        for (Drawable drawable : drawables) {
            drawable.screenSizeChanged(this.thomUnitConverter);
        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

}
