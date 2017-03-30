package com.androidbelieve.drawerwithswipetabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by pain on 30/3/17.
 */

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;
    public void sendLocationSMS(String phoneNumber, double lat,double lon) {
        SmsManager smsManager = SmsManager.getDefault();
        String smsBody = "http://maps.google.com/maps?q=loc:"+lat+","+lon;
        String l = smsBody;
        //StringBuffer smsBody = new StringBuffer();
        //smsBody.append("http://maps.google.com?q=");
        //smsBody.append(lat);
        //smsBody.append(",");
        //smsBody.append(lon);
        smsManager.sendTextMessage(phoneNumber, null, l, null, null);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            String format = intent.getStringExtra("format");
            SmsMessage[] msgs = null;
            String msg_from="",msg="";
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        msg = msg + " "+msgBody;
                    }
                }catch(Exception e){
                            Log.d("Exception caught",e.getMessage());
                }

                final SharedPreferences prefs = context.getSharedPreferences("carpool", MODE_PRIVATE);
                String kw = prefs.getString("keyword","get location");
                //Toast.makeText(context,kw+msg,Toast.LENGTH_SHORT).show();
                msg = msg.substring(1);
                if(msg.equals(kw)){
                GPSTracker gps = new GPSTracker(context);
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                Toast.makeText(context,"Location Sent !!",Toast.LENGTH_SHORT).show();
                sendLocationSMS(msg_from,latitude,longitude);}
            }
        }
    }
}