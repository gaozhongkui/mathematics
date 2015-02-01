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
		// ��ʼ���ӿڣ�Ӧ��������ʱ�����
		// ������appId, appSecret, ����ģʽ
		AdManager.getInstance(this).init(AppId, AppSecret, false);
		// ���ز岥��Դ
		SpotManager.getInstance(this).loadSpotAds();
		// �������ֶ���Ч����0:ANIM_NONEΪ�޶�����1:ANIM_SIMPLEΪ�򵥶���Ч����2:ANIM_ADVANCEΪ�߼�����Ч��
		SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
		// ���ò��������ĺ�����չʾ��ʽ����������˺����������й����Դ������»�������ʹ�ú���ͼ��
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);
		GameResource.isFristApp=Gdx.app.getPreferences("isFristUseApp").getBoolean(isFristUseApp, true);
		mAlertDialog=new AlertDialog.Builder(this);
		mAlertDialog.setTitle("��ʾ");
		mAlertDialog.setMessage("�����Ҫ�˳���");
		mAlertDialog.setPositiveButton("�˳�", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				exitApp();
			}
		});
		mAlertDialog.setNegativeButton("ȡ��", null);
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
