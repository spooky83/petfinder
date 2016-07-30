package gg.petfinder.petfinder.servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by gsciglioto on 16/02/2016.
 */
public class servicio extends Service {
    private Context ctx;
/*
    public servicio()
    {
        super();
        this.ctx=this.getApplicationContext();
    }

    public servicio(Context c)
    {
        super();
        this.ctx=c;
    }
    */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId)
    {
        RegUserSync reg = new RegUserSync();
        reg.execute();
        return START_STICKY;
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }





}
