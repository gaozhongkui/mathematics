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
	private boolean isFristRun;
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
	    			   if(!isDown){
	    				   isDown=true;
	    				   mCacheTexture=mBorderDigital;
			    		   MainScreen.mCalculationCount-=  mFigure;
			    		   MainScreen.mMainHandler.obtainMessage(MainScreen.SelectDice, DiceActor.this).sendToTarget();
	    			   }
	    		   }
	    		return true;
	    	}
	       @Override
	    public void touchUp(InputEvent event, float x, float y,
	    		int pointer, int button) {
	    	super.touchUp(event, x, y, pointer, button);
		    	if(MainScreen.mCalculationCount==0){
		    		  MainScreen.mMainHandler.obtainMessage(MainScreen.AnswerRight).sendToTarget();
		    	}else if(MainScreen.mCalculationCount<0){
		    		isError=true;
		    		 
		    	}
	        }
	       });	
		
	}
	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		if(mCacheTexture!=null){
			batch.draw(mCacheTexture, getX(), getY());
			if(isDown){
	          batch.draw(mCacheDigitalTexture, getX()+(getWidth()/2-mCacheDigitalTexture.getWidth()/2), getY()+(getHeight()/2-mCacheDigitalTexture.getHeight()/2));
	        }
			if(isError){
				batch.draw(MainScreen.mError, getX()+(getWidth()/2-MainScreen.mError.getWidth()/2), getY()+(getHeight()/2-MainScreen.mError.getHeight()/2));	
			}
		}
      
	}
    public void runAction(boolean isfrist){
    	if(isRun){
    	 return;	
    	}
    	isFristRun=isfrist;
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
    
    public void clickDisappear(){
    	mDiceActors[mPostion][mLineX]=false;
    	removeDice();
    	Message message=MainScreen.mMainHandler.obtainMessage();
    	message.what=MainScreen.ALLRUNDICEACTOR;
    	message.obj=this;
    	message.sendToTarget();
    }
	public void reset(){
		isError=false;
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
				if(isFristRun){
					Message message=MainScreen.mHandler.obtainMessage();
					message.what=MainScreen.NEXTLINE;
					message.obj=this;
					message.sendToTarget();
				}
			}else{
				float p=mPercent/mDuration;
				setY(mStartPoint.y-mIntervalDistance*p);
			}
			
			
			if(isError){
				mErrorPercent+=arg0;
				if(mErrorPercent>mErrorDuration){
					 mErrorPercent=0;
					 isError=false;
					 MainScreen.mMainHandler.obtainMessage(MainScreen.AnswerWrong).sendToTarget();
				}
			}
		}
	}
	
	public void removeDice(){
		clear();
	}
	@Override
	protected void clearRes() {

	}

}
