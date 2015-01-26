package com.gaozhongkui.mathematics.actor;

import static com.gaozhongkui.mathematics.screen.MainScreen.mBorderDigital;
import static com.gaozhongkui.mathematics.screen.MainScreen.mBorderDigitals;
import static com.gaozhongkui.mathematics.screen.MainScreen.mDiceActors;
import static com.gaozhongkui.mathematics.screen.MainScreen.mDigitals;
import android.graphics.PointF;
import android.os.Message;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.gaozhongkui.mathematics.screen.MainScreen;
import com.gaozhongkui.mathematics.widget.BaseActor;

public class DiceActor extends BaseActor {
	private static final float mInitLineIntervalDuration=0.05f;
	private static final float mInitDuration=0.1f;
	private static final float mBorderWidth=37;
	private static final float mBorderHeight=37;
	private static final float mOriginalX=346;
	private static final float mOriginalY=398;
	private static final float mLineInterval=13;
	private Texture   mCacheTexture;
	private Texture   mCacheDigitalTexture;
	private PointF    mStartPoint;
	private int     mLineX;
	private float   mIntervalDistance;
	private boolean isDown;
	private int     mFigure;
	private int     mPostion;
	private boolean isRun;
	private boolean isError;
	protected float mErrorDuration = 0;
	protected float mErrorPercent = 0;
	public DiceActor(int figure,int postion) {
		super();
		mFigure=figure;
		mPostion=postion;
		mCacheTexture=mBorderDigitals[figure-1];
		mCacheDigitalTexture=mDigitals[figure-1];
		this.mStartPoint=new PointF(mOriginalX+(postion*mBorderWidth), mOriginalY);
		setPosition(mStartPoint.x, mStartPoint.y);
	}
	@Override
	protected void initView() {
      initListener();
      setSize(mBorderWidth, mBorderHeight);
      mErrorDuration=0.8f;
	}
	private void initListener(){
		addListener(new InputListener(){
	    	   @Override
	    	public boolean touchDown(InputEvent event, float x, float y,
	    			int pointer, int button) {
	    		   if(MainScreen.isClick){
	    			   clickDisappear(); 
	    			   isDown=true;
		    		   mCacheTexture=mBorderDigital;
	    		   }
	    		return true;
	    	}
	    	   @Override
	    	public void touchUp(InputEvent event, float x, float y,
	    			int pointer, int button) {
	    		super.touchUp(event, x, y, pointer, button);
	    	}
	       });	   
	}
	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		if(mCacheTexture!=null){
			batch.draw(mCacheTexture, getX(), getY());
			if(isDown&&!isError){
	          batch.draw(mCacheDigitalTexture, getX()+(getWidth()/2-mCacheDigitalTexture.getWidth()/2), getY()+(getHeight()/2-mCacheDigitalTexture.getHeight()/2));
	        }else{
	        	
	        }
		}
      
	}
    public void runAction(boolean isfrist){
    	int count=0;
    	for(int i=9;i>=mLineX;i--){
    	 boolean pand=mDiceActors[mPostion][i];
    	  if(!pand){
    		  mDiceActors[mPostion][i]=true;
    		  count=(i+1);
    		  mIntervalDistance=((mBorderWidth*count)+mLineInterval)-(mOriginalY-mStartPoint.y);
  	    	  if(isfrist){
  	    		 mDuration=(count*mInitDuration)+mInitLineIntervalDuration;
  	    	  }else{
  	    		 mDiceActors[mPostion][mLineX]=false;
  	    		 mDuration=((count-mLineX)*mInitDuration);
  	    	  }
  	    	  mLineX=i;
  	    	  isRun=true;	
    		  break;
    	  }
    	}
    }
    
    private void clickDisappear(){
    	mDiceActors[mPostion][mLineX]=false;
    	Message message=MainScreen.mMainHandler.obtainMessage();
    	message.what=MainScreen.ALLRUNDICEACTOR;
    	message.obj=this;
    	message.sendToTarget();
    }
	public void reset(){
		isDown=false;
		mCacheTexture=mBorderDigitals[mFigure-1];
	}
	public int getmFigure() {
		return mFigure;
	}
	@Override
	protected void actChild(float arg0) {
		if(isRun){
			mPercent+=arg0;
			if(mPercent>mDuration){
				isRun=false;
				mPercent=0;
				setY(mStartPoint.y-mIntervalDistance);
				mStartPoint.y=getY();
				MainScreen.mHandler.sendEmptyMessage(MainScreen.NEXTLINE);
			}else{
				float p=mPercent/mDuration;
				setY(mStartPoint.y-mIntervalDistance*p);
			}
			
			
			if(isError){
				mErrorPercent+=arg0;
			}
		}
	}
	
	public void removeDice(){
		remove();
	}
	@Override
	protected void clearRes() {

	}

}
