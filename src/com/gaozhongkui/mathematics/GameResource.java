package com.gaozhongkui.mathematics;

import android.os.Handler;

import com.badlogic.gdx.graphics.Texture;

public class GameResource {
	public static final int NEXTLINE=1032;
	public static final int  AnswerWrong=1016;
	public static final int  SelectDice=1018;
	public static int        mLevelCount=0; /**关卡 **/
	public static int        mFractionCount=0; /**积分 **/
	public static int        mLevelTask=0; /**任务 **/
	public static int        mCalculationCount=0;   /** 计算总额 **/
	public static int        mSelectCalculationCount=0;
	public static int        mSelectAnswerTask=0;
	public static boolean      isClick;
	public static Handler       mHandler;
	public static  Handler       mMainHandler;
	public static Boolean[][]  mDiceActors=null;
	public static Texture[]    mBorderDigitals;
	public static Texture[]    mDigitals;
	public static Texture      mBorderDigital;
	public static Texture      mError;
}
