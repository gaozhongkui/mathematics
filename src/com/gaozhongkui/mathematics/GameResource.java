package com.gaozhongkui.mathematics;

import android.os.Handler;

import com.badlogic.gdx.graphics.Texture;

public class GameResource {
	public static final int ALLRUNDICEACTOR=1028;
	public static final int NEXTLINE=1032;
	public static final int  AnswerRight=1012;
	public static final int  AnswerWrong=1016;
	public static final int  SelectDice=1018;
	public static int        mLevelCount=1; /**�ؿ� **/
	public static int        mFractionCount=1; /**���� **/
	public static int        mLevelTask=1; /**���� **/
	public static int        mCalculationCount=0;   /** �����ܶ� **/
	public static int        mSelectCalculationCount=0;
	public static Handler       mHandler;
	public static  Handler       mMainHandler;
	public static Boolean[][]  mDiceActors=null;
	public static Texture[]    mBorderDigitals;
	public static Texture[]    mDigitals;
	public static Texture      mBorderDigital;
	public static Texture      mError;
}