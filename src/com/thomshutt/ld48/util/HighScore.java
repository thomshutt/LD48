package com.thomshutt.ld48.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class HighScore {

    public static final String PREFERENCES_KEY = "ld48prefs";
    private static final String HIGH_SCORE_KEY = "HIGH_SCORE";

    public static int getHighScore(){
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_KEY);
        return prefs.getInteger(HIGH_SCORE_KEY, 0);
    }

    public static void setHighScore(int latestScore){
        Preferences prefs = Gdx.app.getPreferences(PREFERENCES_KEY);
        if(getHighScore() < latestScore){
            prefs.putInteger(HIGH_SCORE_KEY, latestScore);
        }
        prefs.flush();
    }

}
