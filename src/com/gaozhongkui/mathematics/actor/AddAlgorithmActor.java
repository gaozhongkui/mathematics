package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gaozhongkui.mathematics.widget.BaseActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class AddAlgorithmActor extends BaseActor {
	private Texture  mTexture;
	private Texture  mCheckTexture;
	private boolean isCheck;
	@Override
	protected void initView() {
		mTexture=new BaseTexture("data/images/add.png");
		mCheckTexture=new BaseTexture("data/images/checkmark.png");
		setPosition(11, 86);
		setSize(mTexture.getWidth(), mTexture.getHeight());
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
      batch.draw(mTexture, getX(), getY());
      if(isCheck){
    	  batch.draw(mCheckTexture, getX()+28, getY());
      }
	}

	@Override
	protected void actChild(float arg0) {

	}

	@Override
	protected void clearRes() {

	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
