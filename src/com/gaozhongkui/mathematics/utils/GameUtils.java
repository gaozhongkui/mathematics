package com.gaozhongkui.mathematics.utils;

import android.content.Context;

import com.badlogic.gdx.Game;
import com.gaozhongkui.mathematics.widget.BaseScreen;

public class GameUtils {
	private static GameUtils mGameUtils;
	private Game mGame;
    private Context mContext;
	private GameUtils() {
	}

	public static GameUtils getInstance() {
		if (mGameUtils == null) {
			mGameUtils = new GameUtils();
		}
		return mGameUtils;
	}

	public Game getmGame() {
		return mGame;
	}

	public void setmGame(Game mGame) {
		this.mGame = mGame;
	}
	
	public void startScreen(BaseScreen targetScreen){
		if(mGame!=null){
			mGame.setScreen(targetScreen);
		}
	}

	public Context getContext() {
		return mContext;
	}

	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

}
