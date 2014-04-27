package com.thomshutt.ld48.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.DrawableList;
import com.thomshutt.ld48.EnemyFactory;
import com.thomshutt.ld48.entities.Drawable;
import com.thomshutt.ld48.entities.Enemy;
import com.thomshutt.ld48.entities.Player;
import com.thomshutt.ld48.util.ThomUnitConverter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScreen implements ApplicationListener, DrawableList {

    private final GameScreenListener gameScreenListener;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private List<Drawable> drawables = new CopyOnWriteArrayList<Drawable>();

    private ThomUnitConverter thomUnitConverter;
    private ShapeRenderer shapeRenderer;
    private EnemyFactory enemyFactory;
    private Player player;
    private int score = 0;
    private Rectangle bottomBgRect;
    private Rectangle topBgRect;

    public GameScreen(GameScreenListener gameScreenListener) {
        this.gameScreenListener = gameScreenListener;
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        Gdx.gl.glClearColor(0, 0, 0, 1);

        this.player = new Player(this);
        this.drawables.add(player);
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
        simulateCollisions();
        removeDeadDrawables();
        checkForInput();
        tick();
        this.enemyFactory.createEnemy();
        if(this.player.isDead()) {
            this.gameScreenListener.gameFinished(this.score);
        }
    }

    private void checkForInput() {
        if(Gdx.input.justTouched()) {
            for (Drawable drawable : drawables) {
                drawable.screenTouched(Gdx.input.getX(), Gdx.input.getY());
            }
        }
    }

    private void removeDeadDrawables() {
        for (Drawable drawable : drawables) {
            if(drawable.isDead()){
                drawables.remove(drawable);
                if(drawable instanceof Enemy) {
                    this.score++;
                }
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
                if(drawable1.getCollisionRectangle() == null || drawable1 == drawable) {
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
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(bottomBgRect.x, bottomBgRect.y, bottomBgRect.width, bottomBgRect.height);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(topBgRect.x, topBgRect.y, topBgRect.width, topBgRect.height);
        shapeRenderer.end();

        batch.begin();
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Drawable drawable : drawables) {
            drawable.draw(shapeRenderer);
        }
        shapeRenderer.end();

    }

    @Override
    public void resize(int width, int height) {
        this.thomUnitConverter = new ThomUnitConverter(width, height);
        this.enemyFactory = new EnemyFactory(this, thomUnitConverter);
        this.camera = new OrthographicCamera(width, height);
        this.shapeRenderer.setProjectionMatrix(camera.combined);
        this.batch.setProjectionMatrix(camera.combined);
        for (Drawable drawable : drawables) {
            drawable.screenSizeChanged(this.thomUnitConverter);
        }
        bottomBgRect = new Rectangle(
                thomUnitConverter.positionalThomToPixel(new Vector2(0, 0)).x,
                thomUnitConverter.getScreenBottommostPixel(),
                thomUnitConverter.getScreenWidthPixels(),
                thomUnitConverter.getHalfScreenHeightPixels()
        );
        topBgRect = new Rectangle(
                thomUnitConverter.positionalThomToPixel(new Vector2(0, 0)).x,
                0,
                thomUnitConverter.getScreenWidthPixels(),
                thomUnitConverter.getHalfScreenHeightPixels()
        );
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void addToList(Drawable drawable) {
        this.drawables.add(drawable);
    }

}
