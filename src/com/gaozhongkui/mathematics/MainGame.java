package com.gaozhongkui.mathematics;

import com.badlogic.gdx.Game;
import com.gaozhongkui.mathematics.screen.GuideScreen;
import com.gaozhongkui.mathematics.utils.GameUtils;

public class MainGame extends Game {

	@Override
	public void create() {
		GameUtils.getInstance().setmGame(this);
		setScreen(new GuideScreen());
	}

}
