package gg.petfinder.petfinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import gg.petfinder.petfinder.Entidades.Usuario;

public class GcmHandler {
	 GoogleCloudMessaging gcm;
	
	String regid;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	Context context;

	final String TAG = "GCMDemo";
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_EXPIRATION_TIME = "onServerExpirationTimeMs";
	private static final String PROPERTY_USER = "user";

	private static final long EXPIRATION_TIME_MS = 604800000;
	static String SENDER_ID = "947342876922";		
    	
	public static String usuario; 
    
	



	public GcmHandler(String usu, Activity act )
	{	
		
		if (checkPlayServices(act)) {
			Log.e(TAG,"VALIDO GOOGLE PLAY");
					
			this.setUsuario(usu);
		
			context = act.getApplicationContext();
			gcm = GoogleCloudMessaging.getInstance(context);
			
			
            //Obtenemos el Registration ID guardado
                regid = getRegistrationId(context);
 
                //Si no disponemos de Registration ID comenzamos el registro
            if (regid.equals("")) {
          	  Log.i(TAG, "Registro vacio busca en server uno nuevo"+regid);
          	  		
                TareaRegistroGCM tarea = new TareaRegistroGCM();
                Log.i(TAG, "Registro 1"+regid);
          	  
                tarea.execute(usuario);
                try
                {
                	tarea.get();
                	Log.e(TAG, "Registra en servier");
                	//Usuario.RegistrarServer();
                	
                }catch(Exception e)
                {
                	Log.e(TAG,"Error: "+e.toString());
                }
              // RegistrarSinc();
            }
    		

    }
  

		
		
	}
    		

    public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	private boolean checkPlayServices(Activity act) {
		
		 Log.e(TAG,"3.0");
		 try
		 {
			 int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(act.getApplicationContext());
		        
		        if (resultCode != ConnectionResult.SUCCESS)
		        {
		        	Log.e(TAG,"3.3");
		            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
		            {
		            	 Log.e(TAG,"3.4");
		                GooglePlayServicesUtil.getErrorDialog(resultCode, act,
		                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
		            }
		            else
		            {
		            	Log.e(TAG,"3.5");
		                Log.i(TAG, "Dispositivo no soportado.");
		                act.finish();
		            }
		            return false;
		        }
		        
		        return true;

		 }catch (Exception e){
			 Log.e(TAG,"Exception:"+ e.toString());
			 return false;
		 }
        
    }
    
    
    
    
    private String getRegistrationId(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
       
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
     
        if (registrationId.length() == 0)
        {
            Log.d(TAG, "Registro GCM no encontrado.");
           return "";
        }
     
        String registeredUser =
        prefs.getString(PROPERTY_USER, "user");
     
        int registeredVersion =
        prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
     
        long expirationTime =
            prefs.getLong(PROPERTY_EXPIRATION_TIME, -1);
     
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String expirationDate = sdf.format(new Date(expirationTime));
     
        
        Log.d(TAG, "Registro GCM encontrado (usuario=" + registeredUser +
        ", version=" + registeredVersion +
        ", expira=" + expirationDate + "        , registrationId=" + registrationId + " )");
        Log.d(TAG, "1.0");
        Usuario.id_google=registrationId;
        //new RegUser().execute();
        int currentVersion = getAppVersion(context);
        Log.d(TAG, "1");   
        if (registeredVersion != currentVersion)
        {
            Log.d(TAG, "Nueva versin de la aplicacin.");
            return "";
        }
        else if (System.currentTimeMillis() > expirationTime)
        {
            Log.d(TAG, "Registro GCM expirado.");
            return "";
        }
        else if (!usuario.equals(registeredUser))
        {
            Log.d(TAG, "Nuevo nombre de usuario.");
           return "";
        }
        Log.d(TAG, "1");
        Log.d(TAG, "1"+usuario);
        return registrationId;
    }
     
    private static int getAppVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
     
            return packageInfo.versionCode;
        }
        catch (NameNotFoundException e)
        {
            throw new RuntimeException("Error al obtener versin: " + e);
        }
    }
    
    
    private void RegistrarSinc(){
       
        try
        
        {
            if (gcm == null)
            {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            Log.i(TAG, "Registro 5"+regid);

            //Nos registramos en los servidores de GCM
            Log.i(TAG,SENDER_ID);
            regid = gcm.register(SENDER_ID);

            Log.i(TAG, "Registrado en GCM: registration_id=" + regid);
            Usuario.id_google=regid;
            //Nos registramos en nuestro servidor
         //   setRegistrationId(context, params[0], regid);
        }
        catch (Exception ex)
        {
            Log.i(TAG, "Error registro en GCM:" + ex.getMessage());
        }

    }
    	
    
    
    private class TareaRegistroGCM extends AsyncTask<String,Integer,String>
    {
        @Override
            protected String doInBackground(String... params)
        {
                String msg = "";
     
                try
                
                {
                    if (gcm == null)
                    {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    Log.i(TAG, "Registro 5"+regid);
     
                    //Nos registramos en los servidores de GCM
                    Log.i(TAG,SENDER_ID);
                    regid = gcm.register(SENDER_ID);
                    Log.i(TAG, "Registro 6"+regid);
                    Log.i(TAG, "Registrado en GCM: registration_id=" + regid);
                    Usuario.id_google=regid;
                    //Nos registramos en nuestro servidor
                    boolean registrado = registroServidor(params[0], regid);
     
                    //Guardamos los datos del registro
                    if(registrado)
                    {
                        setRegistrationId(context, params[0], regid);
                    }
                }
                catch (Exception ex)
                {
                    Log.i(TAG, "Error registro en GCM:" + ex.getMessage());
                }
     
                return msg;
            }
    }
    
    private void setRegistrationId(Context context, String user, String regId)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
     
        int appVersion = getAppVersion(context);
     
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_USER, user);
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.putLong(PROPERTY_EXPIRATION_TIME,
        System.currentTimeMillis() + EXPIRATION_TIME_MS);
     
        editor.commit();
    }
    private boolean registroServidor(String usuario, String regId)
    {
    	Usuario.id_google=regId;
    	//RegUserSync.RegUserSyncNow();
    	/*
        boolean reg = false;
     
        final String NAMESPACE = "http://sgoliver.net/";
        final String URL="http://10.0.2.2:1634/ServicioRegistroGCM.asmx";
        final String METHOD_NAME = "RegistroCliente";
        final String SOAP_ACTION = "http://sgoliver.net/RegistroCliente";
     
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
     
        request.addProperty("usuario", usuario);
        request.addProperty("regGCM", regId);
     
        SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
     
        envelope.dotNet = true;
     
        envelope.setOutputSoapObject(request);
     
        HttpTransportSE transporte = new HttpTransportSE(URL);
     
        try
        {
            transporte.call(SOAP_ACTION, envelope);
            SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
            String res = resultado_xml.toString();
     
            if(res.equals("1"))
            {
                Log.d(TAG, "Registrado en mi servidor.");
                reg = true;
            }
        }
        catch (Exception e)
        {
            Log.d(TAG, "Error registro en mi servidor: " + e.getCause() + " || " + e.getMessage());
        }
     
        return reg;
        */
    	return true;
    }

	

	
}
