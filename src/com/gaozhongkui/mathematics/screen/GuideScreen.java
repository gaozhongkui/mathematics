package com.gaozhongkui.mathematics.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;

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

	@Override
	protected void draw(float delta) {

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
       if(mBackgroud!=null){
    	   mBackgroud.clear();
    	   mBackgroud.remove();
    	   mBackgroud=null;
       }
       if(mStartButton!=null){
    	   mStartButton.clear();
    	   mStartButton.remove();
    	   mStartButton=null;
       }
       if(mIntroduceButton!=null){
    	   mIntroduceButton.clear();
    	   mIntroduceButton.remove();
    	   mIntroduceButton=null;
       }
       clearScreen();
	}
	

}
