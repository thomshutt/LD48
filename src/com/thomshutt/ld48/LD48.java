package com.thomshutt.ld48;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    public static Texture TEXTURE_ABOVE;
    public static Texture TEXTURE_BELOW;
    public static Sprite SPRITE_ABOVE;
    public static Sprite SPRITE_BELOW;

    @Override
    public void create() {
        FONT_WHITE = new BitmapFont(Gdx.files.internal("data/font_white.fnt"), Gdx.files.internal("data/font_white.png"), false);
        TEXTURE_ABOVE = new Texture(Gdx.files.internal("data/above.png"));
        TEXTURE_BELOW = new Texture(Gdx.files.internal("data/below.png"));
        SPRITE_ABOVE = new Sprite(LD48.TEXTURE_ABOVE);
        SPRITE_BELOW = new Sprite(LD48.TEXTURE_BELOW);
        SPRITE_ABOVE.setAlpha(0.8f);
        SPRITE_BELOW.setAlpha(0.8f);
        this.currentScreen.create();
    }

    @Override
    public void resize(int screenWidthPixels, int screenHeightPixels) {
        this.screenWidthPixels = screenWidthPixels;
        this.screenHeightPixels = screenHeightPixels;
        this.currentScreen.resize(screenWidthPixels, screenHeightPixels);

        SPRITE_ABOVE.setSize(screenWidthPixels, (screenWidthPixels / 3) * 2);
        SPRITE_ABOVE.setX(-(screenWidthPixels / 2));
        SPRITE_ABOVE.setY(0);

        SPRITE_BELOW.setSize(screenWidthPixels, screenHeightPixels / 2);
        SPRITE_BELOW.setX(-(screenWidthPixels / 2));
        SPRITE_BELOW.setY(-(screenHeightPixels / 2));
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
