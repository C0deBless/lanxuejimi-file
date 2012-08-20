package com.badlogic.androidgames.mrnom;

import com.example.game.framework.Screen;
import com.example.game.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {

	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}