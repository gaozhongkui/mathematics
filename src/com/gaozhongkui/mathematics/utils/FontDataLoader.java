package com.gaozhongkui.mathematics.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontDataLoader {
	private  final String FONTPATH="data/fonts/huawenxingka.ttf";
	private  final String GAMEALLTITLE="1234567890/";
	private static FontDataLoader mFontDataLoader;
    private FreeTypeFontGenerator  mFreeTypeFontGenerator;
    private FreeTypeBitmapFontData mBitmapFontData;
    private FreeTypeFontParameter mFontParameter;
	private FontDataLoader() {
		mFreeTypeFontGenerator=new FreeTypeFontGenerator(Gdx.files.internal(FONTPATH));
		mFontParameter=new FreeTypeFontParameter();
		mFontParameter.characters=GAMEALLTITLE;
		mFontParameter.flip=false;
		mFontParameter.size=32;
		mBitmapFontData=mFreeTypeFontGenerator.generateData(mFontParameter);
	}

   public static FontDataLoader getInstance(){
	   if(mFontDataLoader==null){
		   mFontDataLoader=new FontDataLoader();
	   }
	   return mFontDataLoader;
   }
   
   public FreeTypeBitmapFontData getBitmapFontData(){
	   return mBitmapFontData;
   }
   
   public FreeTypeBitmapFontData  getBitmapFontData(int textsize){
	   FreeTypeFontParameter fontParameter=new FreeTypeFontParameter();
	   fontParameter.characters=GAMEALLTITLE;
	   fontParameter.flip=false;
	   fontParameter.size=textsize;
	   FreeTypeBitmapFontData mFontData=mFreeTypeFontGenerator.generateData(fontParameter);
	   return mFontData;
   }
}
