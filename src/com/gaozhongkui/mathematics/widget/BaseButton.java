package com.gaozhongkui.mathematics.widget;

import android.text.TextUtils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gaozhongkui.mathematics.utils.TextureUtils;

public class BaseButton extends Button {

	public BaseButton(String up,String down) {
		super(getDefaultButtonStyle(up, down));
	}
	public BaseButton(String up,String down,String disabled) {
		super(getDefaultButtonStyle(up, down, disabled));
	}
	
	
	private static ButtonStyle getDefaultButtonStyle(String up,String down){
		return getDefaultButtonStyle(up, down,null);
	}
	 private static ButtonStyle getDefaultButtonStyle(String up,String down,String disabled){
		ButtonStyle buttonStyle=new ButtonStyle();
		TextureRegion upRegion=new TextureRegion(new Texture(up));
		TextureUtils.setTextureRegionSmoothFilter(upRegion);
		buttonStyle.up=new TextureRegionDrawable(upRegion);
		TextureRegion downRegion=new TextureRegion(new Texture(down));
		TextureUtils.setTextureRegionSmoothFilter(downRegion);
		buttonStyle.down=new TextureRegionDrawable(downRegion);
		if(!TextUtils.isEmpty(disabled)){
			TextureRegion disabledRegion=new TextureRegion(new Texture(disabled));
			TextureUtils.setTextureRegionSmoothFilter(disabledRegion);
			buttonStyle.disabled=new TextureRegionDrawable(disabledRegion);
		}
		return buttonStyle;
	}
}
