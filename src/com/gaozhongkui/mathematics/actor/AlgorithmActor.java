package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.GameResource;

public class AlgorithmActor extends Actor {
	private AddAlgorithmActor mAddAlgorithmActor;
	private MultiplyAlgorithmActor mMultiplyAlgorithmActor;
	private SwitchAlgorithListenter mAlgorithListenter;
	public AlgorithmActor(Stage stage) {
		mAddAlgorithmActor=new AddAlgorithmActor();
		mMultiplyAlgorithmActor=new MultiplyAlgorithmActor();
		stage.addActor(this);
		stage.addActor(mAddAlgorithmActor);
		stage.addActor(mMultiplyAlgorithmActor);
		mAddAlgorithmActor.setCheck(true);
		mAddAlgorithmActor.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(GameResource.mAlgorithState!=AlgorithState.Add){
					GameResource.mAlgorithState=AlgorithState.Add;
					mAddAlgorithmActor.setCheck(true);
					mMultiplyAlgorithmActor.setCheck(false);
					if(mAlgorithListenter!=null){
						mAlgorithListenter.swtichResult();
					}
				}
			}
		});
		mMultiplyAlgorithmActor.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(GameResource.mAlgorithState!=AlgorithState.Multiply){
					GameResource.mAlgorithState=AlgorithState.Multiply;
					mAddAlgorithmActor.setCheck(false);
					mMultiplyAlgorithmActor.setCheck(true);
					if(mAlgorithListenter!=null){
						mAlgorithListenter.swtichResult();
					}
				}
			}
		});
	}
	
	public void setmAlgorithListenter(SwitchAlgorithListenter mAlgorithListenter) {
		this.mAlgorithListenter = mAlgorithListenter;
	}

	public interface  SwitchAlgorithListenter{
		public void swtichResult();
	}
	
	public enum AlgorithState{
		Add,Multiply;
	}
}
