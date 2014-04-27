package com.thomshutt.ld48.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.util.ThomUnitConverter;

import static com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import static com.thomshutt.ld48.LD48.FONT_WHITE;

public class TitleScreen implements ApplicationListener {

    private static final String BUTTON_TEXT_START = "START";
    private static final String TEXT_INTRO = "TAP TO DROP BOMBS";
    private static final String TEXT_INTRO_2 = "   BELOW THE SURFACE\nSHOTS ARE INVERTED";

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
            drawIntroText();
            drawStartButton();
        batch.end();

        if(isInputTouched()){
            this.titleScreenListener.startButtonPressed();
        }
    }

    private static boolean isInputTouched(){
        return Gdx.input.justTouched()
                || Gdx.input.isKeyPressed(Input.Keys.SPACE)
                || Gdx.input.isKeyPressed(Input.Keys.ENTER)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    private void drawIntroText() {
        FONT_WHITE.setScale(1f);
        final TextBounds bounds = FONT_WHITE.getMultiLineBounds(TEXT_INTRO);
        float textYPixels = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 75)).y;
        FONT_WHITE.drawMultiLine(batch, TEXT_INTRO, -(bounds.width / 2), textYPixels - (bounds.height / 2));

        final TextBounds bounds2 = FONT_WHITE.getMultiLineBounds(TEXT_INTRO_2);
        float textYPixels2 = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 65)).y;
        FONT_WHITE.drawMultiLine(batch, TEXT_INTRO_2, -(bounds2.width / 2), textYPixels2 - (bounds2.height / 2));
    }

    private void drawStartButton() {
        FONT_WHITE.setScale(1f);
        final TextBounds bounds = FONT_WHITE.getBounds(BUTTON_TEXT_START);
        float textYPixels = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 25)).y;
        FONT_WHITE.draw(batch, BUTTON_TEXT_START, -(bounds.width / 2), textYPixels + (bounds.height / 2));
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
