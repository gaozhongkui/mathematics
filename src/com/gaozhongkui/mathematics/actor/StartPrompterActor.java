package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class StartPrompterActor extends BasePrompterActor {
	private Texture  mTexture;
	private Texture  mHintTexture;
	private BitmapFont  mBitmapFont;
	private BaseButton mStartBut;
	public StartPrompterActor(Stage  stage) {
		super(stage);
	}
	protected void initView() {
		mTexture=new BaseTexture("data/images/prompter/startprompter.png");
		mHintTexture=new BaseTexture("data/images/prompter/startprompterhint.png");
		setPosition(396, 0);
		FreeTypeBitmapFontData mBitmapFontData=FontDataLoader.getInstance().getBitmapFontData(26);
		mBitmapFont=new BitmapFont(mBitmapFontData, mBitmapFontData.getTextureRegions(), false);
		mBitmapFont.setColor(Color.RED);
        TextureUtils.setTextureRegionSmoothFilter(mBitmapFont.getRegion());
        mStartBut=new BaseButton( "data/images/startbutdown.png","data/images/startbutdown.png");
		mStartBut.setPosition(436, 148);
		mStage.addActor(this);
		mStage.addActor(mStartBut);
		mStartBut.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameResource.mHandler.obtainMessage(GameResource.STARTGAME).sendToTarget();
			}
		});
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
		batch.draw(mHintTexture, getX()+17, getY()+240);
		mBitmapFont.draw(batch, GameResource.mLevelTask+"", getX()+45, getY()+292);
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
		mStartBut.setVisible(visible);
	}

}
