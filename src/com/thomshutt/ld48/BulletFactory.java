package com.thomshutt.ld48;

import com.thomshutt.ld48.entities.Bullet;
import com.thomshutt.ld48.util.ThomUnitConverter;

public class BulletFactory {

    private static final int MIN_INTERVAL_MILLIS = 200;

    private final DrawableList drawableList;
    private long lastBulletTimestamp = 0;

    public BulletFactory(DrawableList drawableList) {
        this.drawableList = drawableList;
    }

    public void createBullet(float startXThoms, float startYThoms, double angleRadians, ThomUnitConverter thomUnitConverter) {
        if(lastBulletTimestamp + MIN_INTERVAL_MILLIS < System.currentTimeMillis()) {
            final Bullet bullet = new Bullet(startXThoms, startYThoms, angleRadians);
            bullet.screenSizeChanged(thomUnitConverter);
            drawableList.addToList(bullet);
            this.lastBulletTimestamp = System.currentTimeMillis();
        }
    }

}
