package com.thomshutt.ld48.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * <---  75  --->
 * ^   * * * * * * *
 * |   *
 * |   *
 * |   *
 * <p/>
 * 100 *
 * <p/>
 * |   *
 * |   *
 * |   *
 * v   *
 */
public class ThomUnitConverter {

    private final float ratioX;
    private final float ratioY;
    private final float screenWidthPixels;
    private final float screenHeightPixels;
    private int screenWidthThoms = 75;
    private int screenHeightThoms = 100;

    public ThomUnitConverter(int screenWidthPixels, int screenHeightPixels) {
        this.screenWidthPixels = screenWidthPixels;
        this.screenHeightPixels = screenHeightPixels;
        this.ratioX = this.screenWidthPixels / this.getScreenWidthThoms();
        this.ratioY = this.screenHeightPixels / this.getScreenHeightThoms();
    }

    public static void main(String[] args) {
        ThomUnitConverter thomUnitConverter = new ThomUnitConverter(150, 200);
        System.out.println(thomUnitConverter.pixelToThom(new Vector2(75, 100)));
        System.out.println(thomUnitConverter.pixelToThom(new Vector2(0, 0)));
        System.out.println(thomUnitConverter.pixelToThom(new Vector2(-75, -100)));
    }

    public Vector2 thomToPixel(Vector2 thoms) {
        return new Vector2(
                (thoms.x * ratioX) - getHalfScreenWidthPixels(),
                (thoms.y * ratioY) - getHalfScreenHeightPixels()
        );
    }

    public Vector2 touchToThoms() {
        return new Vector2(
                Gdx.input.getX() / ratioX,
                screenHeightThoms - (Gdx.input.getY() / ratioY)
        );
    }

    public Vector2 pixelToThom(Vector2 pixels) {
        return new Vector2(
                (getHalfScreenWidthPixels() - pixels.x) / ratioX,
                (getHalfScreenHeightPixels() - pixels.y) / ratioY
        );
    }

    public int getScreenWidthThoms() {
        return screenWidthThoms;
    }

    public int getScreenHeightThoms() {
        return screenHeightThoms;
    }
    public float getHalfScreenHeightThoms() {
        return screenHeightThoms / 2f;
    }

    public float getScreenWidthPixels() {
        return this.screenWidthPixels;
    }

    public float getHalfScreenWidthPixels() {
        return this.screenWidthPixels / 2;
    }

    public float getScreenHeightPixels() {
        return this.screenHeightPixels;
    }

    public float getHalfScreenHeightPixels() {
        return this.screenHeightPixels / 2;
    }

    public float getScreenRightmostThoms() {
        return this.screenWidthThoms;
    }

    public float getScreenLeftmostThoms() {
        return 0;
    }

    public int getScreenTopmostPixel() {
        return Math.round(this.screenHeightPixels / 2.0f);
    }

    public float getScreenTopmostThoms() {
        return this.screenHeightThoms;
    }

    public float getScreenBottommostPixel() {
        return getScreenTopmostPixel() - this.screenHeightPixels;
    }

    public float getScreenBottommostThoms() {
        return 0;
    }

    public boolean isOffScreen(float xThoms, float yThoms) {
        // TODO
        return false;
    }

}
