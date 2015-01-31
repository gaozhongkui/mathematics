package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.gaozhongkui.mathematics.widget.BasePrompterActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class FailedPrompterActor extends BasePrompterActor {
	private Texture  mTexture;
	private Dialog mDialog;
	public FailedPrompterActor(Stage  stage) {
		super(stage);
	}
	protected void initView() {
		mTexture=new BaseTexture("data/images/prompter/failed.png");
		setPosition(396, -2);
		mStage.addActor(this);
		mDialog=new Dialog(mStage);
		mDuration=1;
	}

	@Override
	protected void drawChild(Batch batch, float parentAlpha) {
		batch.draw(mTexture, getX(), getY());
	}

	@Override
	protected void actChild(float arg0) {
	}

	@Override
	protected void clearRes() {

	}
    @Override
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if(visible){
    		addAction();
    	}else{
    		mDialog.setVisible(visible);
    	}
    }
   
    private void addAction(){
    	DelayAction delayAction=Actions.delay(mDuration, Actions.run(new Runnable() {
			
			@Override
			public void run() {
				mDialog.setVisible(true);
			}
		}));
    	this.addAction(delayAction);
    }
}
