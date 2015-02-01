package com.gaozhongkui.mathematics;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gaozhongkui.mathematics.utils.GameUtils;
import com.gaozhongkui.mathematics.utils.PowerManagerUtils;

public class MainApplication extends AndroidApplication {
	public static final int SCREENWIDTH = 800;
	public static final int SCREENHEIGHT = 480;
    private static final String AppId="85aa56a59eac8b3d";
    private static final String AppSecret="a14006f66f58d5d7";
    private static final String isFristUseApp="fristuseapp";
    private AlertDialog.Builder  mAlertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GameUtils.getInstance().setContext(this);
		PowerManagerUtils.getInstance().acquireWakeLock();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGame(), config);
		// 初始化接口，应用启动的时候调用
		// 参数：appId, appSecret, 调试模式
		AdManager.getInstance(this).init(AppId, AppSecret, false);
		// 加载插播资源
		SpotManager.getInstance(this).loadSpotAds();
		// 插屏出现动画效果，0:ANIM_NONE为无动画，1:ANIM_SIMPLE为简单动画效果，2:ANIM_ADVANCE为高级动画效果
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// 设置插屏动画的横竖屏展示方式，如果设置了横屏，则在有广告资源的情况下会是优先使用横屏图。
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);
		GameResource.isFristApp=Gdx.app.getPreferences("isFristUseApp").getBoolean(isFristUseApp, true);
		mAlertDialog=new AlertDialog.Builder(this);
		mAlertDialog.setTitle("提示");
		mAlertDialog.setMessage("你真的要退出吗？");
		mAlertDialog.setPositiveButton("退出", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				exitApp();
			}
		});
		mAlertDialog.setNegativeButton("取消", null);
	}

	private void exitApp(){
		SpotManager.getInstance(this).onDestroy();
		PowerManagerUtils.getInstance().releaseWakeLock();
		Gdx.app.getPreferences("isFristUseApp").putBoolean(isFristUseApp, false).flush();
		Gdx.app.exit();
		System.exit(0);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		mAlertDialog.show();
	}
	@Override
	protected void onStop() {
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}
	
}
