package com.thomshutt.ld48;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LD48";
		cfg.width = 450;
		cfg.height = 600;
        cfg.resizable = false;
		
		new LwjglApplication(new LD48(), cfg);
	}
}
