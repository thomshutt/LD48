package com.thomshutt.ld48;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thomshutt.ld48.screens.*;
import com.thomshutt.ld48.util.HighScore;

import java.util.Random;

public class LD48 implements ApplicationListener, TitleScreenListener, GameScreenListener, DeathScreenListener {

    private ApplicationListener currentScreen = new TitleScreen(this);
    private int screenWidthPixels;
    private int screenHeightPixels;

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final double TWO_PI = Math.PI * 2;
    public static BitmapFont FONT_WHITE;

    @Override
    public void create() {
        FONT_WHITE = new BitmapFont(Gdx.files.internal("data/font_white.fnt"), Gdx.files.internal("data/font_white.png"), false);
        this.currentScreen.create();
    }

    @Override
    public void resize(int screenWidthPixels, int screenHeightPixels) {
        this.screenWidthPixels = screenWidthPixels;
        this.screenHeightPixels = screenHeightPixels;
        this.currentScreen.resize(screenWidthPixels, screenHeightPixels);
    }

    @Override
    public void render() {
        this.currentScreen.render();
    }

    @Override
    public void pause() {
        this.currentScreen.pause();
    }

    @Override
    public void resume() {
        this.currentScreen.resume();
    }

    @Override
    public void dispose() {
        this.currentScreen.dispose();
    }

    @Override
    public void startButtonPressed() {
        startNewGame();
    }

    @Override
    public void replayButtonPressed() {
        startNewGame();
    }

    @Override
    public void gameFinished(int latestScore) {
        final int previousHighScore = HighScore.getHighScore();
        HighScore.setHighScore(latestScore);
        this.currentScreen = new DeathScreen(this, previousHighScore, latestScore);
        this.currentScreen.create();
        this.currentScreen.resize(this.screenWidthPixels, this.screenHeightPixels);
    }

    private void startNewGame(){
        this.currentScreen = new GameScreen(this);
        this.currentScreen.create();
        this.currentScreen.resize(this.screenWidthPixels, this.screenHeightPixels);
    }

    public static boolean isInputTouched(){
        return Gdx.input.justTouched()
                || Gdx.input.isKeyPressed(Input.Keys.SPACE)
                || Gdx.input.isKeyPressed(Input.Keys.ENTER)
                || Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

}
