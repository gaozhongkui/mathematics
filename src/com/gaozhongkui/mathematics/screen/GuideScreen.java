package com.gaozhongkui.mathematics.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class GuideScreen extends BaseScreen {
	private BaseButton  mStartButton;
	private BaseButton  mIntroduceButton;
	private final int mBackGauge=15;
	@Override
	protected void init() {
			mBackgroud = new BaseImage("data/images/welcomebackgroud.png");
			mBackgroudStage.addActor(mBackgroud);
			mStartButton=new BaseButton("data/images/startbutton.png", "data/images/startbuttondown.png");
			mIntroduceButton=new BaseButton("data/images/explainbutton.png", "data/images/explainbuttondown.png");
			mStartButton.setPosition(mBackGauge, 102);
			mIntroduceButton.setPosition(mBackGauge, 22);
			mBackgroudStage.addActor(mStartButton);
			mBackgroudStage.addActor(mIntroduceButton);
			initListener();
			initValue();
				
	}
	private void initListener(){
		mStartButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				startScreen(new MainScreen());
			}
		});
		mIntroduceButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				startScreen(new IntroduceScreen());
			}
		});
	}
	 private void initValue(){
		   if (GameResource.mBorderDigitals == null) {
			   GameResource.mBorderDigitals = new BaseTexture[9];
				for (int i = 0; i < GameResource.mBorderDigitals.length; i++) {
					GameResource.mBorderDigitals[i] = new BaseTexture("data/images/dice/borderdigital" + (i + 1) + ".png");
				}
			}
			if (GameResource.mDigitals == null) {
				GameResource.mDigitals = new BaseTexture[9];
				for (int i = 0; i < GameResource.mDigitals.length; i++) {
					GameResource.mDigitals[i] = new BaseTexture("data/images/dice/digital" + (i + 1)+ ".png");
				}
			}
			if (GameResource.mBorderDigital == null) {
				GameResource.mBorderDigital=new BaseTexture("data/images/dice/borderdigital.png");
			}
			if(GameResource.mError==null){
				GameResource.mError=new BaseTexture("data/images/dice/error.png");
			}
			if(GameResource.mBackGroudMusic==null){
				 GameResource.mBackGroudMusic=Gdx.audio.newMusic(Gdx.files.internal("data/sounds/backgroud.mp3"));
			}
		 
			if(GameResource.mPreparationMusic==null){
				 GameResource.mPreparationMusic=Gdx.audio.newSound(Gdx.files.internal("data/sounds/preparation.mp3"));
			}
		 
			if(GameResource.mEliminateFailedMusic==null){
				 GameResource.mEliminateFailedMusic=Gdx.audio.newSound(Gdx.files.internal("data/sounds/eliminatefailed.mp3"));
			}
		   if(GameResource.mEliminateMusic==null){
			   GameResource.mEliminateMusic=Gdx.audio.newSound(Gdx.files.internal("data/sounds/eliminate.mp3"));
		   }
		  if(GameResource.mFailedMusic==null){
			  GameResource.mFailedMusic=Gdx.audio.newSound(Gdx.files.internal("data/sounds/failed.mp3"));
		  }
		  if( GameResource.mWinGroudMusic==null){
			  GameResource.mWinGroudMusic=Gdx.audio.newSound(Gdx.files.internal("data/sounds/win.mp3")); 
		  }
		  
	   }
	@Override
	protected void draw(float delta) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		if(mStartButton!=null){
			mStartButton.remove();
			mStartButton=null;
		}
		if(mIntroduceButton!=null){
			mIntroduceButton.remove();
			mIntroduceButton=null;
		}
		  clearScreen();
	}
	

}
