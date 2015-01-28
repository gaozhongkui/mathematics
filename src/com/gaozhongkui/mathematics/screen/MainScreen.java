package com.gaozhongkui.mathematics.screen;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.actor.DiceActor;
import com.gaozhongkui.mathematics.actor.FractionActor;
import com.gaozhongkui.mathematics.actor.LittleGirlActor;
import com.gaozhongkui.mathematics.actor.LittleGirlActor.GirlState;
import com.gaozhongkui.mathematics.actor.StartPrompterActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor;
import com.gaozhongkui.mathematics.actor.StartWelcomeActor.StartWelcomeListener;
import com.gaozhongkui.mathematics.utils.GameUtils;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BaseImage;
import com.gaozhongkui.mathematics.widget.BaseScreen;

public class MainScreen extends BaseScreen  implements StartWelcomeListener {
	public static boolean isClick;
	private static List<DiceActor>  mSelectDiceActors=new ArrayList<DiceActor>();
	private static  volatile List<DiceActor> mFristDiceActors=new ArrayList<DiceActor>();
	private static final int InitColumnCount=10;
	private static final int InitLineCount=4;
	private static final int STARTGAME=1026;
	private static final int GameOver=1022;
	private static final int ShowNumber=1022;
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
		resetScreen();
	}
    @SuppressLint("UseValueOf")
	private void initHandler(){
    	GameResource.mDiceActors=new Boolean[10][10];
    	for(int i=0;i<GameResource.mDiceActors.length;i++){
    		for(int j=0;j<GameResource.mDiceActors[i].length;j++){
    			GameResource.mDiceActors[i][j]=new Boolean(false);
    		}
    	}
    	
    	mHandlerThread.start();
    	GameResource.mHandler=new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
			@Override
			public boolean handleMessage(Message arg0) {
				if(STARTGAME==arg0.what){
					HidePromterActor();
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
					DiceActor actor=(DiceActor) arg0.obj;
				    mFristDiceActors.remove(actor);
					boolean pand=false;
					boolean isStriving=false;
					for(int i=0;i<GameResource.mDiceActors[0].length;i++){
						if(GameResource.mDiceActors[i][0]){
							pand=true;
							break;
						}
						if(GameResource.mDiceActors[i][3]){
							isStriving=true;
						}
					}
					if(isStriving){
						if(mGirlActor.getmGirlState()!=GirlState.Striving){
							mGirlActor.setmGirlState(GirlState.Striving);
						}
					}
					if(mFristDiceActors.isEmpty()){
						if(!pand){
							int j=0;
							for(j=0;j<InitColumnCount;j++){
								int figure=MathUtils.random(1, getLevelCountToRange());
								DiceActor diceActor=new DiceActor(figure, j);
								Message message=GameResource.mMainHandler.obtainMessage();
								message.what=STARTGAME;
								message.obj=diceActor;
								message.sendToTarget();
								SystemClock.sleep(STARTPAUSETIME);
							}
							if(j>=InitColumnCount){
								for(j=0;j<InitColumnCount;j++){
									Message message=GameResource.mMainHandler.obtainMessage();
									message.what=RUNDICEACTOR;
									message.arg1=j;
									message.sendToTarget();
								}
							}
						}else{
							GameResource.mHandler.sendEmptyMessage(GameOver);
						}
						
					}
				}else if(GameOver==arg0.what){
					mGirlActor.setmGirlState(GirlState.Failed);
					System.out.println("游戏结束");
				}
				return false;
			}
		});
    	GameResource.mMainHandler=new Handler(GameUtils.getInstance().getContext().getMainLooper(), new Handler.Callback() {
    			
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
    					GameResource.mCalculationCount=getSetNumber();
    					mGirlActor.showNumber(GameResource.mCalculationCount);
    				}else if(GameResource.AnswerWrong==arg0.what){  /**  错 **/
    					for(DiceActor actor:mSelectDiceActors){
    						actor.reset();
    					}
    					mSelectDiceActors.clear();
    					GameResource.mSelectCalculationCount=0;
    				}else if(GameResource.SelectDice==arg0.what){  /** 选择 **/
    					DiceActor actor=(DiceActor) arg0.obj;
    					mSelectDiceActors.add(actor);
    				}
    				
    				return false;
    			}
    		});
    }
    
    /** 对的 **/
    public static void SelectAnswerRight(){
    	for(DiceActor actor:mSelectDiceActors){
			actor.clickDisappear();
		}
		GameResource.mMainHandler.sendEmptyMessage(ShowNumber);
		mSelectDiceActors.clear();
		GameResource.mSelectCalculationCount=0;
    }
    private void initScreenLine(){
    	Thread thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
			for(int i=0;i<InitLineCount;i++){
				for(int j=0;j<InitColumnCount;j++){
					int figure=MathUtils.random(1, getLevelCountToRange());
					DiceActor diceActor=new DiceActor(figure, j);
					Message message=GameResource.mMainHandler.obtainMessage();
					message.what=STARTGAME;
					message.obj=diceActor;
					message.sendToTarget();
					SystemClock.sleep(STARTPAUSETIME);
				}
				GameResource.mHandler.sendEmptyMessage(RUNDICEACTOR);
				SystemClock.sleep(STARTLINETIME*InitLineCount);
			 }
			GameResource.mMainHandler.sendEmptyMessage(ShowNumber);
			isClick=true;
			}
		});
    	thread.start();
    }
    private int getSetNumber(){
        if(GameResource.mLevelCount==0){
			
		}
    	return MathUtils.random(1, 6);
    }
	private int getLevelCountToRange(){
		if(GameResource.mLevelCount==0){
			
		}
			
		return 3;
	}
	private void initLittening(){
		mStartBut.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameResource.mHandler.obtainMessage(STARTGAME).sendToTarget();
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
		GameResource.mCalculationCount=0;
		mFristDiceActors.clear();
		mSelectDiceActors.clear();
		mStartWelcomeActor.startAction();
		HidePromterActor();
	}
	
}
