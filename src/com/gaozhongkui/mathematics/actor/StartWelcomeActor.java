package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gaozhongkui.mathematics.widget.BaseActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class StartWelcomeActor extends BaseActor {
	private final static  int UPDOWNFLOATING=12;
	private StartWelcomeListener  mStartWelcomeListener;
	private Sprite mQuasi;
	private Sprite mAddition;
	private Sprite mStartChar;
	private Sprite mStarss;
	private int mPreparation = 140;
	private float mPreparationDuration;
	private float mPreparationPercent;
	private float mUPDownPreparationDuration ;
	private float mUPDownPreparationPercent ;
	private ActionState mActionState;
	private boolean isSwtich;
	private int mSwtichUpDownCount=6;
	@Override
	protected void initView() {
		mQuasi = new Sprite(new BaseTexture("data/images/quasi.png"));
		mAddition = new Sprite(new BaseTexture("data/images/addition.png"));
		mStartChar=new Sprite(new BaseTexture("data/images/startgame.png"));
		mStarss=new Sprite(new BaseTexture("data/images/starss.png"));
		mQuasi.setPosition(-mQuasi.getWidth(), mPreparation);
		mAddition.setPosition(getScreenWidth() + mAddition.getWidth(),mPreparation);
		mStartChar.setPosition(392, 126);
		mStarss.setPosition(354, 262);
		mPreparationDuration =0.3f;
		mUPDownPreparationDuration = 0.08f;
		mActionState = ActionState.None;
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		if (mActionState != ActionState.None) {
			if(mActionState!=ActionState.StartChar){
				mQuasi.draw(batch);
				mAddition.draw(batch);
			}else{
				if(isSwtich){
					mStarss.draw(batch);
				}
				mStartChar.draw(batch);
			}
			
		}

	}

	@Override
	protected void actChild(float arg0) {
		if (mActionState == ActionState.MoveLeftRight) {
			mPreparationPercent+=arg0;
			if(mPreparationPercent>mPreparationDuration){
				mPreparationPercent=0;
				mActionState=ActionState.MoveUPDown;
			}
			float p=mPreparationPercent/mPreparationDuration;
			mQuasi.setPosition((-mQuasi.getWidth()+(mQuasi.getWidth()+357)*p), mPreparation);
			mAddition.setPosition(((getScreenWidth() + mAddition.getWidth())-(247+mAddition.getWidth())*p), mPreparation);
		}else if(mActionState == ActionState.MoveUPDown){
			mUPDownPreparationPercent+=arg0;
			if(mUPDownPreparationPercent>mUPDownPreparationDuration){
				mUPDownPreparationPercent=0;
				if(mSwtichUpDownCount<0){
					mActionState=ActionState.StartChar;
					mSwtichUpDownCount=6;
				}else{
					mSwtichUpDownCount--;	
				}
				if(isSwtich){
					isSwtich=false;
				}else{
					isSwtich=true;
				}
			}
			float p=mUPDownPreparationPercent/mUPDownPreparationDuration;
			if(isSwtich){
				mQuasi.setPosition(357,mPreparation+UPDOWNFLOATING*p );
				mAddition.setPosition(553,mPreparation-UPDOWNFLOATING*p);	
			} else {
				mQuasi.setPosition(357 ,(mPreparation+UPDOWNFLOATING)-UPDOWNFLOATING*p);
				mAddition.setPosition(553,(mPreparation-UPDOWNFLOATING)+UPDOWNFLOATING*p);	
			}
			
		}else if(mActionState == ActionState.StartChar){

			mUPDownPreparationPercent+=arg0;
			if(mUPDownPreparationPercent>(mUPDownPreparationDuration)){
				mUPDownPreparationPercent=0;
				if(mSwtichUpDownCount<0){
					mActionState=ActionState.None;
					if(mStartWelcomeListener!=null){
						mStartWelcomeListener.StartWelcomeFinish();
					}
				}else{
					mSwtichUpDownCount--;
				}
				if(isSwtich){
					isSwtich=false;
				}else{
					isSwtich=true;
				}
			}
		}
	}

	public void startAction() {
		mSwtichUpDownCount=6;
		mUPDownPreparationPercent=0;
		mPreparationPercent=0;
		mActionState = ActionState.MoveLeftRight;
	}

	@Override
	protected void clearRes() {

	}

	enum ActionState {
		None, MoveLeftRight, MoveUPDown, StartChar;
	}

	
	public void setmStartWelcomeListener(StartWelcomeListener mStartWelcomeListener) {
		this.mStartWelcomeListener = mStartWelcomeListener;
	}


	public interface StartWelcomeListener{
		public void StartWelcomeFinish();
		
	}
}
