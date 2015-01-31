package com.gaozhongkui.mathematics;

import android.os.Handler;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.gaozhongkui.mathematics.screen.MainScreen;
import com.gaozhongkui.mathematics.screen.MainScreen.GameState;

public class GameResource {
	public static final int NEXTLINE=1032;
	public static final int  AnswerWrong=1016;
	public static final int  SelectDice=1018;
	public static final int AgainMake=1266;
	public static final int WinMake=1206;
	public static final int ContinueMake=1208;
	public static final int STARTGAME=1026;
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
    public static GameState  mGameState;
    public static  Music      mBackGroudMusic;
    public static  Sound      mEliminateMusic;//消除
    public static  Sound      mEliminateFailedMusic;//消除失败
    public static  Sound      mFailedMusic;     //失败
    public static  Sound      mPreparationMusic; //准备
    public static  Sound      mWinGroudMusic;   //赢
    public static  boolean    initMainScreen;
    public static MainScreen  mMainScreen;

}
