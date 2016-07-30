package gg.petfinder.petfinder;


import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class GcmBroadcastReceiver extends BroadcastReceiver {
	
	NotificationManager nm;
	private static final int ID_NOTIFICATION=1;
	
	public void onReceive(Context context, Intent intent) {
				
			String action = intent.getAction();
		            
			if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
				
		    String registrationId = intent.getStringExtra("registration_id");
			String error = intent.getStringExtra("error");
			Log.e("GCMDemo","registrationId:"+registrationId);
			Log.e("GCMDemo","error:"+error);
			String unregistered = intent.getStringExtra("unregistered"); 
			}else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
				
            
				GcmNotifications.AltaNotificacion(intent.getStringExtra("Cabecera"),intent.getStringExtra("Cuerpo"),context);
			        
				
				
			}
			//String data2 = intent.getStringExtra("data2");
			        
			//07-28 22:47:52.515: I/GCMDemo(21777): Error registro en GCM:SERVICE_NOT_AVAILABLE


	}
}
 