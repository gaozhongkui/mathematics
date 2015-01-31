package com.gaozhongkui.mathematics.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaozhongkui.mathematics.GameResource;
import com.gaozhongkui.mathematics.widget.BaseButton;
import com.gaozhongkui.mathematics.widget.BasePrompterActor;
import com.gaozhongkui.mathematics.widget.BaseTexture;

public class WinPrompterActor extends BasePrompterActor {
	private Texture mTexture;
	private BaseButton mNextLevelBut;

	public WinPrompterActor(Stage stage) {
		super(stage);
	}

	protected void initView() {
		mTexture = new BaseTexture("data/images/prompter/passatestrenz.png");
		setPosition(388, -2);
		mNextLevelBut = new BaseButton("data/images/nextlevel.png","data/images/nextlevel.png");
		mNextLevelBut.setPosition(433, 176);
		mNextLevelBut.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameResource.mMainHandler.sendEmptyMessage(GameResource.WinMake);
			}

		});
		mStage.addActor(this);
		mStage.addActor(mNextLevelBut);
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
		mNextLevelBut.setVisible(visible);
	}

}
