package com.badlogic.androidgames.mrnom;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame{
	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new LoadingScreen(this);
	}
}
