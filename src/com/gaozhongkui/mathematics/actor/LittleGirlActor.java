package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.gaozhongkui.mathematics.utils.TextureUtils;
import com.gaozhongkui.mathematics.widget.BaseActor;

public class LittleGirlActor extends BaseActor {
    private Texture[]   mSitTextures;
    private Texture  mCacheTexture;
    private GirlState  mGirlState;
    private int mCreenPostion;
	@Override
	protected void initView() {
		mSitTextures=new Texture[26];
		for(int i=0;i<mSitTextures.length;i++){
			mSitTextures[i]=new Texture("data/images/zuo/sit"+(i+1)+".png");
			TextureUtils.setTextureSmoothFilter(mSitTextures[i]);
		}
		mGirlState=GirlState.Thinking;
		mDuration=5f;
		mCacheTexture=mSitTextures[mCreenPostion];
		setPosition(87, 0);
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
        if(mCacheTexture!=null){
        	batch.draw(mCacheTexture, getX(), getY());
        }
	}

	@Override
	protected void actChild(float arg0) {
		 mPercent++;
         if(mPercent>mDuration){
        	 mPercent=0;
        	 mCreenPostion++;
        	 if(mCreenPostion>=mSitTextures.length){
        		 mCreenPostion=0;
        	 }
        	 mCacheTexture=mSitTextures[mCreenPostion];
         }
	}

	@Override
	protected void clearRes() {

	}
	enum GirlState{
		Thinking,Striving,Win,Failed
	}

}
