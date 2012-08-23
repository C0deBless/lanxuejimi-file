package com.trnnn.game.snake;

import com.example.game.framework.Screen;
import com.example.game.framework.impl.AndroidGame;

public class MrNomGame extends AndroidGame {

	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}