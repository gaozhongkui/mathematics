package com.gaozhongkui.mathematics.widget;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class BaseTexture extends Texture {
	public BaseTexture(String internalPath) {
		this(Gdx.files.internal(internalPath));
	}

	public BaseTexture(FileHandle file) {
		super(file);
		setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}


}
