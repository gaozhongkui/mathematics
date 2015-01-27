package com.gaozhongkui.mathematics.actor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.gaozhongkui.mathematics.utils.FontDataLoader;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseActor;

public class FractionActor extends BaseActor {
	private final float TextDistance=116;
	private Texture mTexture;
    private BitmapFont mLevelBitmapFont;  /** 关卡 **/
    private BitmapFont mFractionBitmapFont;  /** 分数 **/
    private BitmapFont mTaskBitmapFont;     /** 任务 **/
    private int mLevel=0;
    private int mFraction=0;
    private int mTask=0;
	@Override
	protected void initView() {
		mTexture = new Texture("data/images/fraction.png");
		TextureUtils.setTextureSmoothFilter(mTexture);
		setSize(mTexture.getWidth(), mTexture.getHeight());
		setPosition(45, 318);
		FreeTypeBitmapFontData fontData=FontDataLoader.getInstance().getBitmapFontData(20);
		mLevelBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		mFractionBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		mTaskBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		mTaskBitmapFont.setColor(Color.BLACK);
		mLevelBitmapFont.setColor(Color.BLACK);
		mFractionBitmapFont.setColor(Color.BLACK);
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
		mLevelBitmapFont.draw(batch, ""+mLevel, getX()+TextDistance, getY()+106);
		mFractionBitmapFont.draw(batch, ""+mFraction, getX()+TextDistance, getY()+76);
		mTaskBitmapFont.draw(batch, ""+mTask, getX()+TextDistance, getY()+46);
	}

	@Override
	protected void actChild(float arg0) {

	}

	@Override
	protected void clearRes() {

	}

	public int getmLevel() {
		return mLevel;
	}

	public void setmLevel(int mLevel) {
		this.mLevel = mLevel;
	}

	public int getmFraction() {
		return mFraction;
	}

	public void setmFraction(int mFraction) {
		this.mFraction = mFraction;
	}

	public int getmTask() {
		return mTask;
	}

	public void setmTask(int mTask) {
		this.mTask = mTask;
	}

}
