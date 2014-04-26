package com.thomshutt.ld48.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thomshutt.ld48.util.ThomUnitConverter;

public class TitleScreen implements ApplicationListener {

    private final TitleScreenListener titleScreenListener;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ThomUnitConverter thomUnitConverter;

    public TitleScreen(TitleScreenListener titleScreenListener) {
        this.titleScreenListener = titleScreenListener;
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
    }

    @Override
    public void resize(int widthPixels, int heightPixels) {
        this.thomUnitConverter = new ThomUnitConverter(widthPixels, heightPixels);
        this.camera = new OrthographicCamera(widthPixels, heightPixels);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
