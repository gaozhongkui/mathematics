package com.gaozhongkui.mathematics.screen;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.actor.DiceActor;
import com.gaozhongkui.mathematics.actor.FractionActor;
import com.gaozhongkui.mathematics.actor.LittleGirlActor;
import com.gaozhongkui.mathematics.actor.StartPrompterActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor.StartWelcomeListener;
import com.gaozhongkui.mathematics.utils.GameUtils;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class MainScreen extends BaseScreen  implements StartWelcomeListener {
	public static Texture[] mBorderDigitals;
	public static Texture[] mDigitals;
	public static Texture mBorderDigital;
	public static Texture mError;
	public static boolean isClick;
	public static Boolean[][]  mDiceActors=null;
	private static List<DiceActor>  mSelectDiceActors=new ArrayList<DiceActor>();
	public static List<DiceActor>  mShowDiceActors=new ArrayList<DiceActor>();
	private static  volatile List<DiceActor> mFristDiceActors=new ArrayList<DiceActor>();
	public static int   mCalculationCount=0;   /** 计算总额 **/
	public static int   mSelectCalculationCount=0;
	private static final int InitColumnCount=10;
	private static final int InitLineCount=4;
	private static final int STARTGAME=1026;
	private static final int GameOver=1022;
	private static final int ShowNumber=1022;
	public static final int  AnswerRight=1012;
	public static final int  AnswerWrong=1016;
	public static final int  SelectDice=1018;
	private static int mLevelCount=1; /**关卡 **/
	private static int mFractionCount=1; /**积分 **/
	private static int mLevelTask=1; /**任务 **/
	public  static final int ALLRUNDICEACTOR=1028;
	public  static final int NEXTLINE=1032;
	private static final int RUNDICEACTOR=1030;
	private static int STARTPAUSETIME=200;
	private static int STARTLINETIME=100;
	private Image mDrawingBoard;
    private FractionActor mFractionActor;
    private StartWelcomeActor  mStartWelcomeActor;
    private LittleGirlActor   mGirlActor;
    private StartPrompterActor     mPrompterActor;
    private BaseButton mStartBut;
    private HandlerThread mHandlerThread=new HandlerThread("gaozhongkui");
    public static Handler       mHandler;
    public static  Handler       mMainHandler;
	@Override
	protected void init() {
		mBackgroud = new BaseImage("data/images/backgroud.png");
		mBackgroudStage.addActor(mBackgroud);
		mDrawingBoard=new BaseImage("data/images/drawingboard.png");
		mDrawingBoard.setPosition(326, 0);
		mBackgroudStage.addActor(mDrawingBoard);
		mFractionActor=new FractionActor();
		mBackgroudStage.addActor(mFractionActor);
		mStartWelcomeActor=new StartWelcomeActor();
		mForegroundStage.addActor(mStartWelcomeActor);
		mGirlActor=new LittleGirlActor();
		mBackgroudStage.addActor(mGirlActor);
		mStartWelcomeActor.setmStartWelcomeListener(this);
		mPrompterActor=new StartPrompterActor();
		mForegroundStage.addActor(mPrompterActor);
		mStartBut=new BaseButton( "data/images/startbutdown.png","data/images/startbutdown.png");
		mStartBut.setPosition(436, 148);
		mForegroundStage.addActor(mStartBut);
		initLittening();
		initHandler();
		initValue();
		resetScreen();
	}
    @SuppressLint("UseValueOf")
	private void initHandler(){
    	mDiceActors=new Boolean[10][10];
    	for(int i=0;i<mDiceActors.length;i++){
    		for(int j=0;j<mDiceActors[i].length;j++){
    			mDiceActors[i][j]=new Boolean(false);
    		}
    	}
    	
    	mHandlerThread.start();
    	mHandler=new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				if(STARTGAME==arg0.what){
					HidePromterActor();
					initScreenLine();
				}else if(RUNDICEACTOR==arg0.what){
					for(int i=0;i<InitColumnCount;i++){
						Message message=mMainHandler.obtainMessage();
						message.what=RUNDICEACTOR;
						message.arg1=i;
						message.sendToTarget();
						SystemClock.sleep(STARTLINETIME);
					}
				}else if(NEXTLINE==arg0.what){
					DiceActor actor=(DiceActor) arg0.obj;
				    mFristDiceActors.remove(actor);
					mShowDiceActors.add(actor);
					boolean pand=false;
					for(int i=0;i<mDiceActors[0].length;i++){
						if(mDiceActors[i][0]){
							pand=true;
							break;
						}
					}
					if(mFristDiceActors.isEmpty()){
						if(!pand){
							int j=0;
							for(j=0;j<InitColumnCount;j++){
								int figure=MathUtils.random(1, getLevelCountToRange());
								DiceActor diceActor=new DiceActor(figure, j);
								Message message=mMainHandler.obtainMessage();
								message.what=STARTGAME;
								message.obj=diceActor;
								message.sendToTarget();
								SystemClock.sleep(STARTPAUSETIME);
							}
							if(j>=InitColumnCount){
								for(j=0;j<InitColumnCount;j++){
									Message message=mMainHandler.obtainMessage();
									message.what=RUNDICEACTOR;
									message.arg1=j;
									message.sendToTarget();
								}
							}
						}else{
							mHandler.sendEmptyMessage(GameOver);
						}
						
					}
				}else if(GameOver==arg0.what){
					System.out.println("游戏结束");
				}
				return false;
			}
		});
    	 mMainHandler=new Handler(GameUtils.getInstance().getContext().getMainLooper(), new Handler.Callback() {
    			
    			@Override
    			public boolean handleMessage(Message arg0) {
    				if(STARTGAME==arg0.what){              //添加
    					 DiceActor diceActor=(DiceActor) arg0.obj;
    					 mBackgroudStage.addActor(diceActor); 
    					 mFristDiceActors.add(diceActor);
    				}else if(RUNDICEACTOR==arg0.what){   //单个运行
    					DiceActor diceActor=mFristDiceActors.get(arg0.arg1);
    					diceActor.runAction(true);
    				}else if(ShowNumber==arg0.what){
    					mCalculationCount=getSetNumber();
    					mGirlActor.showNumber(mCalculationCount);
    				}else if(ALLRUNDICEACTOR==arg0.what){
    					DiceActor actor=(DiceActor) arg0.obj;
    					mShowDiceActors.remove(actor);
    					for(int i=0;i<mShowDiceActors.size();i++){
    						mShowDiceActors.get(i).runAction(false);
    					}
    				}else if(AnswerRight==arg0.what){   /** 对**/
    					for(DiceActor actor:mSelectDiceActors){
    						actor.clickDisappear();
    						mShowDiceActors.remove(actor);
    					}
    					mMainHandler.sendEmptyMessage(ShowNumber);
    					mSelectDiceActors.clear();
    					mSelectCalculationCount=0;
    				}else if(AnswerWrong==arg0.what){  /**  错 **/
    					for(DiceActor actor:mSelectDiceActors){
    						actor.reset();
    					}
    					mSelectDiceActors.clear();
    					mSelectCalculationCount=0;
    				}else if(SelectDice==arg0.what){  /** 选择 **/
    					DiceActor actor=(DiceActor) arg0.obj;
    					mSelectDiceActors.add(actor);
    				}
    				
    				return false;
    			}
    		});
    }
    private void initScreenLine(){
    	Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
			for(int i=0;i<InitLineCount;i++){
				for(int j=0;j<InitColumnCount;j++){
					int figure=MathUtils.random(1, getLevelCountToRange());
					DiceActor diceActor=new DiceActor(figure, j);
					Message message=mMainHandler.obtainMessage();
					message.what=STARTGAME;
					message.obj=diceActor;
					message.sendToTarget();
					SystemClock.sleep(STARTPAUSETIME);
				}
				mHandler.sendEmptyMessage(RUNDICEACTOR);
				SystemClock.sleep(STARTLINETIME*InitLineCount);
			 }
			mMainHandler.sendEmptyMessage(ShowNumber);
			isClick=true;
			}
		});
    	thread.start();
    }
    private int getSetNumber(){
        if(mLevelCount==0){
			
		}
    	return MathUtils.random(10);
    }
	private int getLevelCountToRange(){
		if(mLevelCount==0){
			
		}
			
		return 3;
	}
	 private void initValue(){
		   if (mBorderDigitals == null) {
				mBorderDigitals = new BaseTexture[9];
				for (int i = 0; i < mBorderDigitals.length; i++) {
					mBorderDigitals[i] = new BaseTexture("data/images/dice/borderdigital" + (i + 1) + ".png");
				}
			}
			if (mDigitals == null) {
				mDigitals = new BaseTexture[9];
				for (int i = 0; i < mDigitals.length; i++) {
					mDigitals[i] = new BaseTexture("data/images/dice/digital" + (i + 1)+ ".png");
				}
			}
			if (mBorderDigital == null) {
				mBorderDigital=new BaseTexture("data/images/dice/borderdigital.png");
			}
			if(mError==null){
				mError=new BaseTexture("data/images/dice/error.png");
			}
	   }
	private void initLittening(){
		mStartBut.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				mHandler.obtainMessage(STARTGAME).sendToTarget();
			}
		});
	}
	@Override
	protected void draw(float delta) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	
	}

	@Override
	public void hide() {
		mHandlerThread.quit();
	}

	@Override
	public void StartWelcomeFinish() {
		showPromterActor();
	}
	private void HidePromterActor(){
		mStartBut.setVisible(false);
		mPrompterActor.setVisible(false);
		
	}
	private void showPromterActor(){
		mStartBut.setVisible(true);
		mPrompterActor.setVisible(true);
		
	}
	
	private void resetScreen(){
		isClick=false;
		mCalculationCount=0;
		mFristDiceActors.clear();
		mSelectDiceActors.clear();
		mStartWelcomeActor.startAction();
		clearAllDice();
		HidePromterActor();
	}
	
	private void clearAllDice(){
		for(DiceActor diceActor:mShowDiceActors){
			diceActor.clear();
		}
		mShowDiceActors.clear();
	}
}
