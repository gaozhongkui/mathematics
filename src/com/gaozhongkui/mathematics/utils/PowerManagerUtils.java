package com.gaozhongkui.mathematics.utils;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * @author gaozhongkui
 *
 */
public class PowerManagerUtils {
	private  PowerManager powerManager;
	private WakeLock mWakeLock;
	private static final String mTag="sengled";
	private static PowerManagerUtils managerUtils;
	private  PowerManagerUtils() {
		powerManager=(PowerManager) GameUtils.getInstance().getContext().getSystemService(Context.POWER_SERVICE);
		mWakeLock=powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, mTag);
	}
	public static synchronized PowerManagerUtils getInstance(){
			if(managerUtils==null){
				managerUtils= new PowerManagerUtils();
			}
		return managerUtils;
	}
	public void acquireWakeLock() {
        if (mWakeLock != null) {
            mWakeLock.acquire();
        }
    }
	public void releaseWakeLock() {
        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }
	

}
