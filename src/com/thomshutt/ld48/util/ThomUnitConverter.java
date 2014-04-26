package com.thomshutt.ld48.util;

/**

     <---  75  --->
 ^   * * * * * * *
 |   *
 |   *
 |   *
     *
 100 *
     *
 |   *
 |   *
 |   *
 v   *

 */
public class ThomUnitConverter {

    private final float thomToPixelRatio;
    private final float screenWidthPixels;
    private final float screenHeightPixels;
    private int screenWidthThoms = 75;
    private int screenHeightThoms = 100;

    public ThomUnitConverter(int screenWidthPixels, int screenHeightPixels) {
        this.screenWidthPixels = screenWidthPixels;
        this.screenHeightPixels = screenHeightPixels;
        this.thomToPixelRatio = this.screenWidthPixels / this.getScreenWidthThoms();
    }

    public int thomsToPixels(double thoms){
        return (int) Math.round(thoms * this.thomToPixelRatio);
    }

    public int pixelsToThoms(double pixels){
        return (int) Math.round(pixels / this.thomToPixelRatio);
    }

    public int getScreenWidthThoms() {
        return screenWidthThoms;
    }
    public int getScreenHeightThoms() {
        return screenHeightThoms;
    }

    public int getScreenLeftmostPixel(){ return thomsToPixels(-(this.screenWidthThoms / 2.0f)); }
    public int getScreenRightmostPixel(){ return thomsToPixels((this.screenWidthThoms / 2.0f)); }
    public float getScreenRightmostThoms(){ return this.screenWidthThoms / 2.0f; }
    public int getScreenTopmostPixel(){ return Math.round(this.screenHeightPixels / 2); }
    public int getScreenBottommostPixel(){ return getScreenTopmostPixel() - thomsToPixels(this.screenHeightThoms); }

    public boolean isOffScreen(float xThoms, float yThoms) {
        return yThoms < getScreenBottommostPixel() || xThoms > getScreenRightmostPixel();
    }
}
