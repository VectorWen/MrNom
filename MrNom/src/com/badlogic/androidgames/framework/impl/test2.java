package com.badlogic.androidgames.framework.impl;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;

import android.R;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;

public class test2 extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		
		
		AssetManager assetManager = getAssets();
		
		
		
		Graphics g = new AndroidGraphics(assetManager, null);
		
		g.drawLine(0, 0, 100, 100, Color.RED);
		
		
	}
}
