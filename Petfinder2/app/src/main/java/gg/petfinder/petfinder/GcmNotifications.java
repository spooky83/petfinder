package gg.petfinder.petfinder;


import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GcmNotifications {
	static NotificationManager nm;
	private static final int ID_NOTIFICATION=1;
	
	public static void AltaNotificacion (String Cab, String det, Context cont)
	{
		nm =(NotificationManager)cont.getSystemService(cont.NOTIFICATION_SERVICE);
		String cab = Cab;
		String cuerpo = det;
		Log.e("GCMDemo","data cab:"+cab);
		Log.e("GCMDemo","data det:"+cuerpo);
	    Notification notification = new Notification(R.drawable.ic_dialog_info,cab, System.currentTimeMillis());
	    PendingIntent intentpen= PendingIntent.getActivity(cont,0, new Intent(cont, MainActivity.class), 0);
	   // notification.setLatestEventInfo(cont, cab, cuerpo, intentpen);
	    nm.notify(ID_NOTIFICATION,notification);	
		
	}

}
