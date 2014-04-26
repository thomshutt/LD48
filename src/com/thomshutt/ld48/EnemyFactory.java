package com.thomshutt.ld48;

import com.thomshutt.ld48.entities.Enemy;
import com.thomshutt.ld48.util.ThomUnitConverter;

import java.util.Random;

public class EnemyFactory {

    private static final int MIN_INTERVAL_MILLIS = 3000;
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private final DrawableList drawableList;
    private final ThomUnitConverter thomUnitConverter;

    private long lastEnemyTimestamp = 0;

    public EnemyFactory(DrawableList drawableList, ThomUnitConverter thomUnitConverter) {
        this.drawableList = drawableList;
        this.thomUnitConverter = thomUnitConverter;
    }

    public void createEnemy() {
        if(lastEnemyTimestamp + MIN_INTERVAL_MILLIS < System.currentTimeMillis()) {
            this.drawableList.addToList(new Enemy(thomUnitConverter, RANDOM.nextInt(75), RANDOM.nextInt(100)));
            lastEnemyTimestamp = System.currentTimeMillis();
        }
    }

}
