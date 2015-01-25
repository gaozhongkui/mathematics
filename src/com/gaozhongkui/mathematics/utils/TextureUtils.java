package com.gaozhongkui.mathematics.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureUtils {
	
	/**
	 * ����ƽ��
	 * @param texture
	 */
	public static void setTextureSmoothFilter(Texture texture){
		if(texture!=null){
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	/**
	 * ����ƽ��
	 * @param texture
	 */
	public static void setTextureRegionSmoothFilter(TextureRegion textureRegion){
		if(textureRegion!=null){
			textureRegion.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
}
