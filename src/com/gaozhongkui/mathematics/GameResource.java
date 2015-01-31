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
	public static int        mLevelCount=0; /**�ؿ� **/
	public static int        mFractionCount=0; /**���� **/
	public static int        mLevelTask=0; /**���� **/
	public static int        mCalculationCount=0;   /** �����ܶ� **/
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
    public static  Sound      mEliminateMusic;//����
    public static  Sound      mEliminateFailedMusic;//����ʧ��
    public static  Sound      mFailedMusic;     //ʧ��
    public static  Sound      mPreparationMusic; //׼��
    public static  Sound      mWinGroudMusic;   //Ӯ
    public static  boolean    initMainScreen;
    public static MainScreen  mMainScreen;

}
