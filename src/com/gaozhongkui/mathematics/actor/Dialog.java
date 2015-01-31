package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.utils.FontDataLoader;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BasePrompterActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class Dialog extends BasePrompterActor {
	private Texture mTexture;
	private BaseButton mAgain;
	private BaseButton mContinue;
	private BitmapFont  mBitmapFont;
    public Dialog(Stage  stage) {
    	super(stage);
	}
	protected void initView() {
		mTexture=new BaseTexture("data/images/filedgame.png");
		setSize(mTexture.getWidth(), mTexture.getHeight());
		setPosition(getScreenWidth()/2-getWidth()/2, getScreenHeight()/2-getHeight()/2);
		mStage.addActor(this);
		mAgain=new BaseButton("data/images/again.png", "data/images/again.png");
		mContinue=new BaseButton("data/images/continue.png", "data/images/continue.png");
		mStage.addActor(mAgain);
		mStage.addActor(mContinue);
		mAgain.setPosition(getX()+43, getY()+46);
		mContinue.setPosition(getX()+254, mAgain.getY());
		FreeTypeBitmapFontData mBitmapFontData=FontDataLoader.getInstance().getBitmapFontData(30);
		mBitmapFont=new BitmapFont(mBitmapFontData, mBitmapFontData.getTextureRegions(), false);
		TextureUtils.setTextureRegionSmoothFilter(mBitmapFont.getRegion());
		mBitmapFont.setColor(Color.WHITE);
		initListenter();
	}
	
	private void initListenter(){
		mAgain.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameResource.mMainHandler.sendEmptyMessage(GameResource.AgainMake);
			}
		});
		mContinue.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameResource.mMainHandler.sendEmptyMessage(GameResource.ContinueMake);
			}
		});
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
		TextBounds textBounds=mBitmapFont.getBounds(GameResource.mFractionCount+"");
		mBitmapFont.draw(batch, GameResource.mFractionCount+"", getX()+(getWidth()/2-textBounds.width/2), getY()+180);
	}

	@Override
	protected void actChild(float arg0) {

	}

	@Override
	protected void clearRes() {

	}
    @Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		mAgain.setVisible(visible);
		mContinue.setVisible(visible);
	}
}
