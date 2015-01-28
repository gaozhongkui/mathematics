package com.gaozhongkui.mathematics.actor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.utils.FontDataLoader;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseActor;

public class FractionActor extends BaseActor {
	private final float TextDistance=116;
	private Texture mTexture;
    private BitmapFont mLevelBitmapFont;  /** 关卡 **/
    private BitmapFont mFractionBitmapFont;  /** 分数 **/
    private BitmapFont mTaskBitmapFont;     /** 任务 **/
	@Override
	protected void initView() {
		mTexture = new Texture("data/images/fraction.png");
		TextureUtils.setTextureSmoothFilter(mTexture);
		setSize(mTexture.getWidth(), mTexture.getHeight());
		setPosition(45, 318);
		FreeTypeBitmapFontData fontData=FontDataLoader.getInstance().getBitmapFontData(23);
		mLevelBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		mFractionBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		mTaskBitmapFont=new BitmapFont(fontData, fontData.getTextureRegions(), false);
		TextureUtils.setTextureRegionSmoothFilter(mLevelBitmapFont.getRegion());
		TextureUtils.setTextureRegionSmoothFilter(mFractionBitmapFont.getRegion());
		TextureUtils.setTextureRegionSmoothFilter(mTaskBitmapFont.getRegion());
		Color color=Color.valueOf("025991");
		mTaskBitmapFont.setColor(color);
		mLevelBitmapFont.setColor(color);
		mFractionBitmapFont.setColor(color);
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
		mLevelBitmapFont.draw(batch, ""+GameResource.mLevelCount, getX()+TextDistance, getY()+108);
		mFractionBitmapFont.draw(batch, ""+GameResource.mFractionCount, getX()+TextDistance, getY()+78);
		mTaskBitmapFont.draw(batch, ""+GameResource.mLevelTask, getX()+TextDistance, getY()+48);
	}

	@Override
	protected void actChild(float arg0) {

	}

	@Override
	protected void clearRes() {

	}

	
}
