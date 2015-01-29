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
		// ��ʼ���ӿڣ�Ӧ��������ʱ�����
		// ������appId, appSecret, ����ģʽ
		AdManager.getInstance(this).init(AppId, AppSecret, false);
		// ���ز岥��Դ
		SpotManager.getInstance(this).loadSpotAds();
		// �������ֶ���Ч����0:ANIM_NONEΪ�޶�����1:ANIM_SIMPLEΪ�򵥶���Ч����2:ANIM_ADVANCEΪ�߼�����Ч��
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// ���ò��������ĺ�����չʾ��ʽ����������˺����������й����Դ������»�������ʹ�ú���ͼ��
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SpotManager.getInstance(this).onDestroy();
		PowerManagerUtils.getInstance().releaseWakeLock();
	}
	
	@Override
	public void onBackPressed() {
		
	}
	@Override
	protected void onStop() {
		SpotManager.getInstance(this).onStop();
		super.onStop();
	}
	
}
