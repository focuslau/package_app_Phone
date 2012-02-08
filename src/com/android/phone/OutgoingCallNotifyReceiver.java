/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.Phone;

import android.util.Log;

/*
 * Handles OTA Start procedure at phone power up. At phone power up, if phone is not OTA
 * provisioned (check MIN value of the Phone) and 'device_provisioned' is not set,
 * OTA Activation screen is shown that helps user activate the phone
 */
public class OutgoingCallNotifyReceiver extends BroadcastReceiver {
	
    private static final String TAG = "OutgoingCallNotifyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        
    	if(intent.getAction().equals(NotificationMgr.ACTION_ENDCALL_BUTTON)) {
    		
	        PhoneApp app = PhoneApp.getInstance();
	        Phone phone = PhoneApp.getPhone();
	        
	        if (app.mCM.getServiceState() != ServiceState.STATE_IN_SERVICE) {
	            Log.w(TAG, "Network is not ready. Registering to receive notification.");
	        }else {      	
	        	//end the call
				Log.w(TAG, "try to End the current Call,Lp add");
				Phone.State state = app.mCM.getState();
				Log.i(TAG,"Hangup current active call...  phone state = " + state);
	
		        PhoneUtils.hangup(app.mCM);
	
		        if (state == Phone.State.IDLE) {
		            // The user asked us to hang up, but the phone was (already) idle!
		            Log.w(TAG, "Hangup current active call: phone is already IDLE!");
		        }
	        }
    	}
        
        
    }
}
