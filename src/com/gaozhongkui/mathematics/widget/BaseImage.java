package com.gaozhongkui.mathematics.widget;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gaozhongkui.mathematics.utils.TextureUtils;

public class BaseImage extends Image {

	public BaseImage(String path) {
		super(new BaseTexture(path));
	}
	
	public BaseImage(TextureRegion textureRegion){
		super(getTextureRegion(textureRegion));
	}
	
	private static TextureRegion getTextureRegion(TextureRegion region){
		TextureUtils.setTextureRegionSmoothFilter(region);
		return region;
	}
}
