package com.gaozhongkui.mathematics;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gaozhongkui.mathematics.utils.GameUtils;
import com.gaozhongkui.mathematics.utils.PowerManagerUtils;

public class MainApplication extends AndroidApplication {
	public static final int SCREENWIDTH = 800;
	public static final int SCREENHEIGHT = 480;
    private static final String AppId="85aa56a59eac8b3d";
    private static final String AppSecret="a14006f66f58d5d7";
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
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		// 展示插播广告，可以不调用loadSpot独立使用
		SpotManager.getInstance(this).showSpotAds(this, new SpotDialogListener() {
					@Override
					public void onShowSuccess() {
						Log.i("YoumiAdDemo", "展示成功");
					}

					@Override
					public void onShowFailed() {
						Log.i("YoumiAdDemo", "展示失败");
					}

					@Override
					public void onSpotClosed() {
						Log.i("YoumiAdDemo", "展示关闭");
					}

				}); // //

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SpotManager.getInstance(this).onDestroy();
		PowerManagerUtils.getInstance().releaseWakeLock();
	}
	
	/*@Override
	public void onBackPressed() {
		// 如果有需要，可以点击后退关闭插播广告。
		if (!SpotManager.getInstance(this).disMiss()) {
			// 弹出退出窗口，可以使用自定义退屏弹出和回退动画,参照demo,若不使用动画，传入-1
			super.onBackPressed();
		}
	}*/
	@Override
	protected void onStop() {
		// 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}
	
}
