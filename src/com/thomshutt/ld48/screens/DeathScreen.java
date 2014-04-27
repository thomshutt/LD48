package com.thomshutt.ld48.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.thomshutt.ld48.LD48;
import com.thomshutt.ld48.util.ThomUnitConverter;

import static com.thomshutt.ld48.LD48.FONT_WHITE;

public class DeathScreen implements ApplicationListener {

    public static final String BUTTON_TEXT_REPLAY = "  PLAY\nAGAIN";
    private final DeathScreenListener deathScreenListener;
    private final int previousHighScore;
    private final int latestScore;

    private static final long COUNT_INTERVAL_MILLIS = 100;
    private long lastCountIncrement = 0;
    private int countUpToLatestScore = 0;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ThomUnitConverter thomUnitConverter;

    public DeathScreen(DeathScreenListener deathScreenListener, int previousHighScore, int latestScore) {
        this.deathScreenListener = deathScreenListener;
        this.previousHighScore = previousHighScore;
        this.latestScore = latestScore;
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
        drawCurrentScoreText();
        drawHighScoreText();
        drawStartButton();
        batch.end();

        incrementScoreCounter();

        if((this.countUpToLatestScore == this.latestScore) && LD48.isInputTouched()){
            this.deathScreenListener.replayButtonPressed();
        }
    }

    private void incrementScoreCounter() {
        if(this.countUpToLatestScore < this.latestScore
                && (this.lastCountIncrement + COUNT_INTERVAL_MILLIS) < System.currentTimeMillis()){
            this.lastCountIncrement = System.currentTimeMillis();
            this.countUpToLatestScore++;
        }
    }

    private void drawCurrentScoreText() {
        FONT_WHITE.setScale(1f);
        final String currentScoreText = "Score: " + this.countUpToLatestScore;

        final BitmapFont.TextBounds bounds = FONT_WHITE.getBounds(currentScoreText);
        float textYPixels = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 75)).y;
        FONT_WHITE.draw(batch, currentScoreText, -(bounds.width / 2), textYPixels - (bounds.height / 2));
    }

    private void drawHighScoreText() {
        FONT_WHITE.setScale(1f);
        final String highScoreText = "High Score: " + this.previousHighScore;
        final BitmapFont.TextBounds bounds = FONT_WHITE.getBounds(highScoreText);
        float textYPixels = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 70)).y;
        FONT_WHITE.draw(batch, highScoreText, -(bounds.width / 2), textYPixels - (bounds.height / 2));
    }

    private void drawStartButton() {
        FONT_WHITE.setScale(2.5f);
        final BitmapFont.TextBounds bounds = FONT_WHITE.getMultiLineBounds(BUTTON_TEXT_REPLAY);
        float textYPixels = this.thomUnitConverter.positionalThomToPixel(new Vector2(0, 40)).y;
        FONT_WHITE.drawMultiLine(batch, BUTTON_TEXT_REPLAY, -(bounds.width / 2), textYPixels + (bounds.height / 2));
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
