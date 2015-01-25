package com.gaozhongkui.mathematics.widget;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gaozhongkui.mathematics.MainApplication;
import com.gaozhongkui.mathematics.utils.GameUtils;

public abstract class BaseScreen implements Screen {
	/** 背景层 **/
	protected Stage mBackgroudStage;
	/** 人物层 **/
	protected Stage mForegroundStage;
	protected Viewport mViewport;
	protected InputMultiplexer mInputMultiplexer;
    private long startTime;
    protected  Image mBackgroud;
	@Override
	public void show() {
		mInputMultiplexer = new InputMultiplexer();
		mViewport = new ScalingViewport(Scaling.stretch,MainApplication.SCREENWIDTH, MainApplication.SCREENHEIGHT);
		mBackgroudStage = new Stage(mViewport);
		mForegroundStage = new Stage(mViewport);
		Gdx.input.setInputProcessor(mInputMultiplexer);
		mInputMultiplexer.addProcessor(mForegroundStage);
		mInputMultiplexer.addProcessor(mBackgroudStage);
		init();
	}

	protected abstract void init();

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		mBackgroudStage.act();
		mBackgroudStage.draw();
		draw(delta);
		mForegroundStage.act();
		mForegroundStage.draw();
	}

	protected abstract void draw(float delta);

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public abstract void pause();

	@Override
	public abstract void resume();

	@Override
	public abstract void hide();

	@Override
	public void dispose() {

	}
	protected float getDeltaTime(){
		return Gdx.graphics.getDeltaTime();
	}
	public void startScreen(BaseScreen baseScreen){
		if(System.currentTimeMillis()-startTime>getDeltaTime()){
			startTime=System.currentTimeMillis();
			GameUtils.getInstance().startScreen(baseScreen);
		}
		
	}
	protected void clearScreen(){
		if(mBackgroudStage!=null){
			mBackgroudStage.dispose();
			mBackgroudStage=null;
		}
		if(mForegroundStage!=null){
			mForegroundStage.dispose();
			mForegroundStage=null;
		}
	}

	/** 获取屏幕的宽度 **/
	protected int getScreenWidth() {
		return MainApplication.SCREENWIDTH;
	}

	protected int getScreenHeight() {
		return MainApplication.SCREENHEIGHT;
	}
}
