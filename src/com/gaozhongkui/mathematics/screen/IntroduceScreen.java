package com.gaozhongkui.mathematics.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;

public class IntroduceScreen extends BaseScreen {
	private BaseImage mIntroduceGirl;
	private BaseButton  mStartButton;
	private Stage  mOnePage;
	private Stage  mTwoPage;
	private Stage  mThreePage;
	private int mCureentPage;
	private BaseButton mNextPage;
	private BaseButton mPreviousPage;
	@Override
	protected void init() {
		mOnePage=new Stage(mViewport);
		mTwoPage=new Stage(mViewport);
		mThreePage=new Stage(mViewport);
		mBackgroud = new BaseImage("data/images/backgroud.png");
		mBackgroudStage.addActor(mBackgroud);
		mIntroduceGirl=new BaseImage("data/images/introducegirl.png");
		mIntroduceGirl.setPosition(getScreenWidth()/2-mIntroduceGirl.getWidth()/2, 0);
		mBackgroudStage.addActor(mIntroduceGirl);
		mStartButton=new BaseButton("data/images/startbutton.png", "data/images/startbuttondown.png");
		mStartButton.setPosition(getScreenWidth()-mStartButton.getWidth()-92, 10);
		mBackgroudStage.addActor(mStartButton);
		mNextPage=new BaseButton("data/images/nextpage.png", "data/images/nextpagedown.png");
		mPreviousPage=new BaseButton("data/images/previouspage.png", "data/images/previouspagedown.png");
		mPreviousPage.setPosition(438, 98);
		mNextPage.setPosition(578, 98);
		mForegroundStage.addActor(mNextPage);
		mForegroundStage.addActor(mPreviousPage);
		mPreviousPage.setVisible(false);
		initOnePage();
		initTwoPage();
		initThreePage();
		initListener();
	}
	private void initOnePage(){
		Image image=new BaseImage("data/images/introduceone.png");
		image.setPosition(226, 223);
		mOnePage.addActor(image);
	}
	private void initTwoPage(){
		Image image=new BaseImage("data/images/introducetwo.png");
		image.setPosition(214, 138);
		mTwoPage.addActor(image);	
	}
	private void initThreePage(){
		Image image=new BaseImage("data/images/introducethree.png");
		image.setPosition(207, 146);
		mThreePage.addActor(image);		
	}
	private void initListener(){
		mStartButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				startScreen(new MainScreen());
			}
		});
		mNextPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mCureentPage++;
				if(mCureentPage>=2){
					mCureentPage=2;
					mNextPage.setVisible(false);
				}
				mPreviousPage.setVisible(true);
			}
		});
		mPreviousPage.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mCureentPage--;
				if(mCureentPage<=0){
					mCureentPage=0;
					mPreviousPage.setVisible(false);
				}
				mNextPage.setVisible(true);
			}
		});
	}
	@Override
	protected void draw(float delta) {
       if(mCureentPage==0){
    	   mOnePage.act();
    	   mOnePage.draw();
       }else if(mCureentPage==1){
    	   mTwoPage.act();
    	   mTwoPage.draw();
       }else if(mCureentPage==2){
    	   mThreePage.act();
    	   mThreePage.draw();
       }
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
        if(mOnePage!=null){
        	mOnePage.dispose();
        	mOnePage=null;
        }
        if(mTwoPage!=null){
        	mTwoPage.dispose();
        	mTwoPage=null;
        }
        if(mThreePage!=null){
        	mThreePage.dispose();
        	mThreePage=null;
        }
		clearScreen();
	}

}
