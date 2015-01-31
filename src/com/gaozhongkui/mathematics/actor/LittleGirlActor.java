package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class LittleGirlActor extends BaseActor {
	private static final int SHOWNUMBERY=248;
    private Texture[]   mSitTextures;
    private Texture[]   mStrivingTextures;
    private Texture[]   mFailedTextures;
    private Texture[]   mWinTextures;
    private Texture[]   mNumbers;
    private Texture[]   mShowNumber;
    private Texture  mCacheTexture;
    private Texture  mDialog;
    private GirlState  mGirlState;
    private boolean isShowNumber;
    private int mCreenPostion;
	@Override
	protected void initView() {
		mSitTextures=new Texture[26];
		for(int i=0;i<mSitTextures.length;i++){
			mSitTextures[i]=new Texture("data/images/zuo/sit"+(i+1)+".png");
			TextureUtils.setTextureSmoothFilter(mSitTextures[i]);
		}
		mNumbers=new Texture[10];
		for(int i=0;i<mNumbers.length;i++){
			mNumbers[i]=new BaseTexture("data/images/digitaldialog/digital"+i+".png");
		}
		mStrivingTextures=new Texture[8];
		for(int i=0;i<mStrivingTextures.length;i++){
			mStrivingTextures[i]=new BaseTexture("data/images/striving/striving"+(i+1)+".png");
		}
		mFailedTextures=new BaseTexture[5];
		for(int i=0;i<mFailedTextures.length;i++){
			mFailedTextures[i]=new BaseTexture("data/images/failed/failed"+(i+1)+".png");
		}
		mWinTextures=new BaseTexture[3];
		for(int i=0;i<mWinTextures.length;i++){
			mWinTextures[i]=new BaseTexture("data/images/win/win"+(i+1)+".png");
		}
		mShowNumber=new Texture[3];
		for(int i=0;i<3;i++){
			mShowNumber[i]=mNumbers[0];
		}
		mDialog=new BaseTexture("data/images/digitaldialog/digitaldialog.png");
		mGirlState=GirlState.Thinking;
		mDuration=5f;
		mCacheTexture=mSitTextures[mCreenPostion];
		setPosition(87, 0);
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
        if(mCacheTexture!=null){
        	batch.draw(mCacheTexture, getX(), getY());
        	if(isShowNumber){
        		batch.draw(mDialog, 227, 215);
        		batch.draw(mShowNumber[0], 232, SHOWNUMBERY);
         		batch.draw(mShowNumber[1], 263, SHOWNUMBERY);
         		batch.draw(mShowNumber[2], 294, SHOWNUMBERY);
        	}
        }
	}

	@Override
	protected void actChild(float arg0) {
		 mPercent++;
         if(mPercent>mDuration){
        	 mPercent=0;
        	 mCreenPostion++;
        	 if(mGirlState==GirlState.Thinking){
        		 if(mCreenPostion>=mSitTextures.length){
            		 mCreenPostion=0;
            	 }
            	 mCacheTexture=mSitTextures[mCreenPostion];
        	 }else if(mGirlState==GirlState.Striving){
        		 if(mCreenPostion>=mStrivingTextures.length){
            		 mCreenPostion=0;
            	 }
            	 mCacheTexture=mStrivingTextures[mCreenPostion]; 
        	 }else if(mGirlState==GirlState.Failed){
        		 if(mCreenPostion>=mFailedTextures.length){
            		 mCreenPostion=0;
            	 }
            	 mCacheTexture=mFailedTextures[mCreenPostion]; 
        	 }else if(mGirlState==GirlState.Win){
        		 if(mCreenPostion>=mWinTextures.length){
            		 mCreenPostion=0;
            	 }
            	 mCacheTexture=mWinTextures[mCreenPostion]; 
        	 }
        	 
         }
	}
    
	public void showNumber(int number){
		if(number>99){
			int a=(number/100);
			int b=(int)(number/10)-(a*10);
			int c=number-(a*100)-(b*10);
			mShowNumber[0]=getNumberByTexture(a);
			mShowNumber[1]=getNumberByTexture(b);
			mShowNumber[2]=getNumberByTexture(c);
		}else if(number>9){
			mShowNumber[0]=mNumbers[0];
			int a=(number/10);
			int b=number-(a*10);
			mShowNumber[1]=getNumberByTexture(a);
			mShowNumber[2]=getNumberByTexture(b);
		}else{
			mShowNumber[0]=mNumbers[0];
			mShowNumber[1]=mNumbers[0];
			mShowNumber[2]=getNumberByTexture(number);
		}
		
		isShowNumber=true;
	}
	public void hideNumber(){
		isShowNumber=false;
	}
	
	public GirlState getmGirlState() {
		return mGirlState;
	}

	public void setmGirlState(GirlState mGirlState) {
		this.mGirlState = mGirlState;
	}

	private Texture  getNumberByTexture(int i){
		return mNumbers[i];
	}
	@Override
	protected void clearRes() {

	}
    public enum GirlState{
  		Thinking,Striving,Win,Failed
	}

}
