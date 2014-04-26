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

    public static void main(String[] args) {
        ThomUnitConverter thomUnitConverter = new ThomUnitConverter(150, 200);
        System.out.println(thomUnitConverter.thomsToPixels(0));
        System.out.println(thomUnitConverter.thomsToPixels(50));
        System.out.println(thomUnitConverter.thomsToPixels(100));
        System.out.println(thomUnitConverter.thomsToPixels(-50));
        System.out.println(thomUnitConverter.thomsToPixels(-100));
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

    public float getScreenWidthPixels(){ return this.screenWidthPixels; }
    public float getHalfScreenWidthPixels(){ return this.screenWidthPixels / 2; }
    public float getScreenHeightPixels(){ return this.screenHeightPixels; }
    public float getHalfScreenHeightPixels(){ return this.screenHeightPixels / 2; }

    public int getScreenRightmostPixel(){ return thomsToPixels((this.screenWidthThoms / 2.0f)); }
    public float getScreenRightmostThoms(){ return this.screenWidthThoms / 2.0f; }

    public int getScreenLeftmostPixel(){ return thomsToPixels(-(this.screenWidthThoms / 2.0f)); }
    public float getScreenLeftmostThoms(){ return -this.screenWidthThoms / 2.0f; }

    public int getScreenTopmostPixel(){ return Math.round(this.screenHeightPixels / 2.0f); }
    public float getScreenTopmostThoms(){ return this.screenHeightThoms / 2.0f; }

    public float getScreenBottommostPixel(){ return getScreenTopmostPixel() - this.screenHeightPixels; }
    public float getScreenBottommostThoms(){ return -(this.screenHeightThoms / 2.0f); }

    public boolean isOffScreen(float xThoms, float yThoms) {
        return yThoms < getScreenBottommostPixel() || xThoms > getScreenRightmostPixel();
    }

}
