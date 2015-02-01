package com.gaozhongkui.mathematics.screen;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.actor.AlgorithmActor;
import com.gaozhongkui.mathematics.actor.AlgorithmActor.AlgorithState;
import com.gaozhongkui.mathematics.actor.AlgorithmActor.SwitchAlgorithListenter;
import com.gaozhongkui.mathematics.actor.DiceActor;
import com.gaozhongkui.mathematics.actor.FailedPrompterActor;
import com.gaozhongkui.mathematics.actor.FractionActor;
import com.gaozhongkui.mathematics.actor.LittleGirlActor;
import com.gaozhongkui.mathematics.actor.LittleGirlActor.GirlState;
import com.gaozhongkui.mathematics.actor.StartPrompterActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor.StartWelcomeListener;
import com.gaozhongkui.mathematics.actor.WinPrompterActor;
import com.gaozhongkui.mathematics.utils.GameUtils;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;

public class MainScreen extends BaseScreen  implements StartWelcomeListener ,SwitchAlgorithListenter{
	private static List<DiceActor>  mSelectDiceActors=new ArrayList<DiceActor>();
	private static  volatile List<DiceActor> mFristDiceActors=new ArrayList<DiceActor>();
	private static final int InitColumnCount=10;
	private static final int InitLineCount=4;
	private static final int GameOver=1022;
	private static final int ShowNumber=1028;
	private static final int YouWin=1068;
	private static final int RUNDICEACTOR=1030;
	private static final int GirlStateJudge=1006;
	private static final int ShowAdvertisement=1086;
	private static final int PreparationMusic=1168;
	private static int STARTPAUSETIME=600;
	private static int STARTLINETIME=520;
	private Image mDrawingBoard;
    private FractionActor mFractionActor;
    private StartWelcomeActor  mStartWelcomeActor;
    private LittleGirlActor   mGirlActor;
    private StartPrompterActor     mPrompterActor;
    private WinPrompterActor       mWinPrompterActor;
    private FailedPrompterActor    mFailedPrompterActor;
    private AlgorithmActor         mAlgorithmActor;
    private Stage  mDiceStage;
    private HandlerThread mHandlerThread=new HandlerThread("gaozhongkui");
	@Override
	protected void init() {
		mDiceStage=new Stage(mViewport);
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
		mPrompterActor=new StartPrompterActor(mForegroundStage);
		mWinPrompterActor=new WinPrompterActor(mForegroundStage);
		mFailedPrompterActor=new FailedPrompterActor(mForegroundStage);
		mInputMultiplexer.addProcessor(mDiceStage);
		mAlgorithmActor=new AlgorithmActor(mBackgroudStage);
		mAlgorithmActor.setmAlgorithListenter(this);
		GameResource.mBackGroudMusic.setLooping(true);
		initHandler();		
		initGame();
		nextLevel();
		resetScreen();
		if(!GameResource.isFristApp){
			GameResource.mMainHandler.sendEmptyMessageDelayed(ShowAdvertisement, 10);
		}
	}
	private void initGame(){
		GameResource.mLevelCount=0;
		GameResource.mFractionCount=0;
		GameResource.mGameState=GameState.None;
		GameResource.mAlgorithState=AlgorithState.Add;
	}
    @SuppressLint("UseValueOf")
	private void initHandler(){
    	GameResource.mDiceActors=new Boolean[10][10];
    	mHandlerThread.start();
    	GameResource.mHandler=new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
			if(GameResource.mGameState==GameState.None){
				if(GameResource.STARTGAME==arg0.what){
					hideWelcome();
					initScreenLine();
				}else if(RUNDICEACTOR==arg0.what){
					for(int i=0;i<InitColumnCount;i++){
						Message message=GameResource.mMainHandler.obtainMessage();
						message.what=RUNDICEACTOR;
						message.arg1=i;
						message.sendToTarget();
						SystemClock.sleep(STARTLINETIME);
					}
				}else if(GameResource.NEXTLINE==arg0.what){
					   GameResource.mMainHandler.sendEmptyMessage(GirlStateJudge);
						DiceActor actor=(DiceActor) arg0.obj;
	                    if(actor!=null){
	                    	 mFristDiceActors.remove(actor);
						}
	                	if (mFristDiceActors.isEmpty()) {
							int j = 0;
							for (j = 0; j < InitColumnCount; j++) {
								if (GameResource.mGameState == GameState.None) {
									int figure = MathUtils.random(1,getLevelCountToRange());
									DiceActor diceActor = new DiceActor(figure, j);
									Message message = GameResource.mMainHandler.obtainMessage();
									message.what = GameResource.STARTGAME;
									message.obj = diceActor;
									message.sendToTarget();
									SystemClock.sleep(STARTPAUSETIME);
								}
							}
							if (j >= InitColumnCount) {
								for (j = 0; j < InitColumnCount; j++) {
									if (GameResource.mGameState == GameState.None) {
										Message message = GameResource.mMainHandler.obtainMessage();
										message.what = RUNDICEACTOR;
										message.arg1 = j;
										message.sendToTarget();
									}
								}
							}
						}	
					
					}
					
				}
				return false;
			}
		});
    	GameResource.mMainHandler=new Handler(GameUtils.getInstance().getContext().getMainLooper(), new Handler.Callback() {
    			
    			@Override
    			public boolean handleMessage(Message arg0) {
    			if(GameResource.mGameState==GameState.None&&arg0.what!=ShowAdvertisement){
    				if(GameResource.STARTGAME==arg0.what){              //添加
    					 DiceActor diceActor=(DiceActor) arg0.obj;
    					 mDiceStage.addActor(diceActor); 
    					 mFristDiceActors.add(diceActor);
    				}else if(RUNDICEACTOR==arg0.what){   //单个运行
    					DiceActor diceActor=mFristDiceActors.get(arg0.arg1);
    					diceActor.runAction(true);
    				}else if(ShowNumber==arg0.what){
    					GameResource.mCalculationCount=getSetNumber();
    					mGirlActor.showNumber(GameResource.mCalculationCount);
    				}else if(GameResource.AnswerWrong==arg0.what){  /**  错 **/
    					answerWrong();
    					GameResource.mEliminateFailedMusic.play();
    				}else if(GameResource.SelectDice==arg0.what){  /** 选择 **/
    					DiceActor actor=(DiceActor) arg0.obj;
    					mSelectDiceActors.add(actor);
    				}else if(GameOver==arg0.what){
    					GameResource.mMainHandler.sendEmptyMessageDelayed(ShowAdvertisement, 10);
    					showFailed();
    				}else if(YouWin==arg0.what){
    					showWin();
    				}else if(GirlStateJudge==arg0.what){
						boolean pand=false;
    					boolean isStriving=false;
    					for(int i=0;i<GameResource.mDiceActors[0].length;i++){
    						if(GameResource.mDiceActors[i][0]){
								pand=true;
								break;
							}
    					   if(GameResource.mDiceActors[i][3]){
							 isStriving=true;
							break;
						   }
    					}
    					if(pand){
							GameResource.mMainHandler.sendEmptyMessage(GameOver);
						}
    					if(isStriving){
							if(mGirlActor.getmGirlState()!=GirlState.Striving){
								mGirlActor.setmGirlState(GirlState.Striving);
							}
						}else{
							if(mGirlActor.getmGirlState()!=GirlState.Thinking){
								mGirlActor.setmGirlState(GirlState.Thinking);
							}
						}
    				}else if(PreparationMusic==arg0.what){
    					GameResource.mPreparationMusic.play();
    				}
    			}else if(GameResource.WinMake==arg0.what){
					initNextLevel();
				}else if(GameResource.ContinueMake==arg0.what){
					initContinueLevel();
				}else if(GameResource.AgainMake==arg0.what){
					startGuideScreen();
				}else if(ShowAdvertisement==arg0.what){
					SpotManager.getInstance(GameUtils.getInstance().getContext()).loadSpotAds();
					SpotManager.getInstance(GameUtils.getInstance().getContext()).showSpotAds(GameUtils.getInstance().getContext(), new SpotDialogListener() {
						@Override
						public void onShowSuccess() {
						}

						@Override
						public void onShowFailed() {
							GameResource.mMainHandler.sendEmptyMessageDelayed(ShowAdvertisement, 10);
						}

						@Override
						public void onSpotClosed() {
						}

					});
				}
    				return false;
    			}
    		});
    }
    private void initContinueLevel(){
    	GameResource.mFractionCount-=GameResource.mCreenLevelFractionCount;
    	resetScreen();
    }
    private  void initNextLevel(){
    	nextLevel();
    	resetScreen();
    }
    private void startGuideScreen(){
    	initGame();
		nextLevel();
		resetScreen();
    }
    private void answerWrong(){
    	for(DiceActor actor:mSelectDiceActors){
			actor.reset();
		}
		mSelectDiceActors.clear();
		if(GameResource.mAlgorithState==AlgorithState.Add){
			 GameResource.mSelectCalculationCount=0;
		  }else if(GameResource.mAlgorithState==AlgorithState.Multiply){
			 GameResource.mSelectCalculationCount=1;
		 }
    }
    /** 对的 **/
    public static void SelectAnswerRight(){
    	for(DiceActor actor:mSelectDiceActors){
			actor.clickDisappear();
		}
    	GameResource.mSelectAnswerTask++;
    	GameResource.mFractionCount+=mSelectDiceActors.size()*10;
    	GameResource.mCreenLevelFractionCount+=mSelectDiceActors.size()*10;
		mSelectDiceActors.clear();
		if(GameResource.mAlgorithState==AlgorithState.Add){
			 GameResource.mSelectCalculationCount=0;
		 }else if(GameResource.mAlgorithState==AlgorithState.Multiply){
			 GameResource.mSelectCalculationCount=1;
		 }
		if(GameResource.mLevelTask==GameResource.mSelectAnswerTask){
			GameResource.mMainHandler.sendEmptyMessage(YouWin);
		}else{
			GameResource.mMainHandler.sendEmptyMessage(ShowNumber);
		}
		GameResource.mMainHandler.sendEmptyMessage(GirlStateJudge);
		GameResource.mEliminateMusic.play();
    }
    private void initScreenLine(){
    	Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<DiceActor>  diceActors=new ArrayList<DiceActor>();
			for(int i=0;i<InitLineCount;i++){
				for(int j=0;j<InitColumnCount;j++){
					int figure=MathUtils.random(1, getLevelCountToRange());
					DiceActor diceActor=new DiceActor(figure, j);
					mDiceStage.addActor(diceActor); 
					 diceActors.add(diceActor);
					 SystemClock.sleep(200);
				}
				for(int z=0;z<InitColumnCount;z++){
					diceActors.get(z).runAction(true);
					SystemClock.sleep(100);
				}
				diceActors.clear();
			 }
			GameResource.mHandler.sendEmptyMessage(GameResource.NEXTLINE);
			GameResource.mMainHandler.sendEmptyMessage(ShowNumber);
			GameResource.isClick=true;
			}
		});
    	thread.start();
    }
    private int getSetNumber(){
    	int result=0;
        if(GameResource.mLevelCount==1){
        	result=MathUtils.random(1, 6);
		}else if(GameResource.mLevelCount==2){
			result=MathUtils.random(6, 15);
		}else if(GameResource.mLevelCount==3){
			result=MathUtils.random(15, 30);
		}else if(GameResource.mLevelCount==4){
			result=MathUtils.random(30, 46);
		}else if(GameResource.mLevelCount==5){
			result=MathUtils.random(46, 56);
		}else if(GameResource.mLevelCount==6){
			result=MathUtils.random(56, 76);
		}else if(GameResource.mLevelCount==7){
			result=MathUtils.random(76, 86);
		}else if(GameResource.mLevelCount==8){
			result=MathUtils.random(86, 100);
		}
    	return result;
    }
	private int getLevelCountToRange(){
		int result=0;
		if(GameResource.mLevelCount==1){
			result=MathUtils.random(1, 3);
		}else if(GameResource.mLevelCount==1){
			result=MathUtils.random(1, 6);
		}else{
			result=MathUtils.random(1, 9);
		}
			
		return result;
	}
	
	private void nextLevel(){
		GameResource.mLevelCount++;
		if(GameResource.mLevelCount==1){
			GameResource.mLevelTask=30;
		}else if(GameResource.mLevelCount==2){
			GameResource.mLevelTask=40;
		}else if(GameResource.mLevelCount==3){
			GameResource.mLevelTask=50;
		}else if(GameResource.mLevelCount==4){
			GameResource.mLevelTask=60;
		}else if(GameResource.mLevelCount==5){
			GameResource.mLevelTask=70;
		}else if(GameResource.mLevelCount==6){
			GameResource.mLevelTask=80;
		}else if(GameResource.mLevelCount==7){
			GameResource.mLevelTask=90;
		}else if(GameResource.mLevelCount==8){
			GameResource.mLevelTask=100;
		}
	}
	@Override
	protected void draw(float delta) {
		mDiceStage.act();
		mDiceStage.draw();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	
	}

	@Override
	public void hide() {
		clearHandler();
		mHandlerThread.quit();
		GameResource.mBackGroudMusic.stop();
		mDiceStage.dispose();
		clearScreen();
	}

	@Override
	public void StartWelcomeFinish() {
		showWelcome();
	}
	
	private void showWelcome(){
		mPrompterActor.setVisible(true);
		hideWin();
		hideFailed();
	}
	private void showWin(){
		mGirlActor.setmGirlState(GirlState.Win);
		GameResource.isClick=false;
		GameResource.mGameState=GameState.Win;
		GameResource.mWinGroudMusic.play();
		GameResource.mBackGroudMusic.stop();
		mWinPrompterActor.setVisible(true);
		hideWelcome();
		hideFailed();
		clearHandler();
	}
	private void showFailed(){
		mGirlActor.setmGirlState(GirlState.Failed);
		GameResource.isClick=false;
		GameResource.mGameState=GameState.Failed;
		GameResource.mFailedMusic.play();
		GameResource.mBackGroudMusic.stop();
		mFailedPrompterActor.setVisible(true);	
		hideWelcome();
		hideWin();
		clearHandler();
	}
	private void hideWelcome(){
		mPrompterActor.setVisible(false);
	}
	private void hideWin(){
		mWinPrompterActor.setVisible(false);
	}
	private void hideFailed(){
		mFailedPrompterActor.setVisible(false);	
	}
	
	private void clearHandler(){
		GameResource.mMainHandler.removeCallbacksAndMessages(null);
		GameResource.mHandler.removeCallbacksAndMessages(null);
	}
	private void resetScreen(){
		clearHandler();
		GameResource.mMainHandler.sendEmptyMessageDelayed(PreparationMusic, 10);
		GameResource.mBackGroudMusic.play();
		GameResource.mGameState=GameState.None;
		mGirlActor.setmGirlState(GirlState.Thinking);
		mGirlActor.hideNumber();
		GameResource.isClick=false;
		GameResource.mSelectAnswerTask=0;
		GameResource.mCalculationCount=0;
		GameResource.mSelectCalculationCount=0;
		GameResource.mCreenLevelFractionCount=0;
		mFristDiceActors.clear();
		mSelectDiceActors.clear();
		mDiceStage.clear();
		mStartWelcomeActor.startAction();
		hideFailed();
		hideWin();
		hideWelcome();
		resertArray();
	}
	private void resertArray(){
		for(int i=0;i<GameResource.mDiceActors.length;i++){
    		for(int j=0;j<GameResource.mDiceActors[i].length;j++){
    			GameResource.mDiceActors[i][j]=false;
    		}
    	}
	}
	
	public enum GameState{
		None,Win,Failed
	}

	@Override
	public void swtichResult() {
		if(!mSelectDiceActors.isEmpty()){
			answerWrong();
		}
		if(GameResource.mAlgorithState==AlgorithState.Add){
			 GameResource.mSelectCalculationCount=0;
		  }else if(GameResource.mAlgorithState==AlgorithState.Multiply){
			 GameResource.mSelectCalculationCount=1;
		 }	 
	}
	
}
