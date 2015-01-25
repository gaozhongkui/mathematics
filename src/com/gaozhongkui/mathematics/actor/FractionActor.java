package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseActor;

public class FractionActor extends BaseActor {
	private Texture mTexture;

	@Override
	protected void initView() {
		mTexture = new Texture("data/images/fraction.png");
		TextureUtils.setTextureSmoothFilter(mTexture);
		setSize(mTexture.getWidth(), mTexture.getHeight());
		setPosition(45, 318);
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
	}

	@Override
	protected void actChild(float arg0) {

	}

	@Override
	protected void clearRes() {

	}

}
