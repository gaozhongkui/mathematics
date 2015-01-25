package com.gaozhongkui.mathematics.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gaozhongkui.mathematics.MainApplication;

public abstract class BaseActor extends Actor {
	protected float mDuration = 0;
	protected float mPercent = 0;

	public BaseActor() {
		initView();
	}

	protected abstract void initView();

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		drawChild(batch, parentAlpha);

	}

	protected abstract void drawChild(Batch batch, float parentAlpha);

	@Override
	public void act(float arg0) {
		super.act(arg0);
		actChild(arg0);
	}

	protected abstract void actChild(float arg0);

	protected float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}
	/** 获取屏幕的宽度 **/
	protected int getScreenWidth() {
		return MainApplication.SCREENWIDTH;
	}

	protected int getScreenHeight() {
		return MainApplication.SCREENHEIGHT;
	}
	@Override
	public void clear() {
		super.clear();
		remove();
		clearRes();
	}
	protected abstract void clearRes();
}
