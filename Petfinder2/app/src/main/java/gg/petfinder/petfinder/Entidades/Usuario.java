package gg.petfinder.petfinder.Entidades;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import gg.petfinder.petfinder.GcmHandler;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;

public class Usuario {

	public static String id_usuario;
	public static String id_google;
	public static String id_fb;
	public static String id_twt;
	public static String Mail;
	public static String Nombre;
	public static String Apellido;
	public static String imagen;
	public static String Password;
	private static final String ID_USUARIO = "ID_USUARIO";
	private static final String ID_GOOGLE = "ID_USUARIO";
	private static final String ID_FB = "ID_FB";
	private static final String ID_TWT = "ID_TWT";
	private static final String MAIL = "MAIL";
	private static final String NOMBRE = "NOMBRE";
	private static final String APELLIDO = "APELLIDO";
	private static final String IMAGEN = "IMAGEN";
	private static final String PASSWORD = "PASSWORD";
	private static final String REGSERV="REGSERV";
	
	public static void setUsuario(Activity act)
	    {
            try {
				if(act==null){
					Log.e("setUsuario1","errr");
				}
               // Context context = act.getApplicationContext();
                SharedPreferences prefs = act.getSharedPreferences("USUARIO", act.MODE_PRIVATE);


                SharedPreferences.Editor editor = prefs.edit();

                editor.putString(ID_USUARIO, Usuario.id_usuario);
                editor.putString(ID_GOOGLE, Usuario.id_google);
                editor.putString(ID_FB, Usuario.id_fb);
                editor.putString(ID_TWT, Usuario.id_twt);
                //editor.putString(MAIL, Usuario.Mail);
                Log.e("petfinder", "setusuario el mail" + Usuario.Mail);
                editor.putString(NOMBRE, Usuario.Nombre);
                editor.putString(APELLIDO, Usuario.Apellido);
                editor.putString(IMAGEN, Usuario.imagen);
                //ditor.putString(PASSWORD, "3434343");

                editor.commit();


				//dataBaseManager.
                // CargarUsuario(context);
            }catch (Exception e)
            {
                Log.e("setUsuario",e.toString());

            }
	    }


	public static boolean RegistrarGoogle(Context context)
	{
		boolean retval;
		try
		{
            //GcmHandler a = new GcmHandler (Usuario.Mail, (Activity) context);
            retval=true;

		}catch(Exception e)
		{
			retval=false;
		}
		return retval;
	}
    public static void CargarUsuario(Activity act)
    {
        try {
    	//Context context = act.getApplicationContext();
        SharedPreferences prefs = act.getSharedPreferences("USUARIO", act.MODE_PRIVATE);
       
      
    	Usuario.id_usuario= prefs.getString(ID_USUARIO, "");
    	Usuario.id_google= prefs.getString(ID_GOOGLE, "");
        Log.e("Registro",prefs.getString(ID_FB,""));
    	Usuario.id_fb= prefs.getString(ID_FB, "");

    	Usuario.id_twt= prefs.getString(ID_TWT, "");
    	Usuario.Mail= prefs.getString(MAIL, "");
    	
    	Usuario.Nombre= prefs.getString(NOMBRE, "");
    	Usuario.Apellido= prefs.getString(APELLIDO, "");
    	Usuario.imagen= prefs.getString(IMAGEN, "");
    	Usuario.Password= prefs.getString(PASSWORD,"");
    	
    	
    	Log.e("GCMDemo", "recuperando el mail " + Usuario.id_fb);
        }catch (Exception e)
        {
            Log.e("Usuario n",e.toString());

        }
    }

	   /*
	    public static void CargarUsuario(final Activity act ,final ProgressDialog pDialog)
	    {

	    	Context context = act.getApplicationContext();
	    	//
	    	sgfapp.secure.secureManager.getUsuario(context);
	    	CargarUsuario(act);
	    	
	    	Log.e("GCMDemo","CargarUsuario Usuario Activo :"+Usuario.Mail );
	    	if(Usuario.Mail==""||Usuario.Mail.isEmpty()){
				
		
			Session.openActiveSessionFromCache(context);
		    Session se = Session.getActiveSession();
		   
	        Request.newMeRequest(se, new Request.GraphUserCallback() {

	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	            	
	              if (user != null) {
	          	
	        	
	              String json=user.getInnerJSONObject().toString();
	          	  Log.e("GCMDemo","Respuesta de json" + user.getName() + "" );
		                try {
		                    JSONObject fbJson=new JSONObject(json);
		                    //Usuario.setUsuario(cont, Usuario.id_usuario, Usuario.id_google, fbJson.getString("id"), Usuario.id_twt, fbJson.getString("email"), fbJson.getString("name"), user.getLastName(),img Usuario.Password);

		                    Usuario.id_fb=fbJson.getString("id");
		                    Usuario.Mail=fbJson.getString("email");
		                    Log.e("GCMDemo","El mail que me da la aplicacion es :"+Usuario.Mail);
		                    Usuario.Nombre=fbJson.getString("name");
		                    Usuario.Apellido=user.getLastName();
		                    Usuario.Password="123456";
		                    Usuario.setUsuario(act);
		                    Log.e("Usuario GCMDemo","Entro a consultar a fb ");
		                    //pDialog.dismiss();
		                    /*
		                    handler.post(new Runnable() {
		                        public void run() {       
		                        	 new RegUser().execute();
		                        	 
		                        }
		                    });

		                    RegistrarGoogle(act);
		                    //RegistrarServer();
		                   
		                } catch (JSONException e) {
		                    // TODO Auto-generated catch block
		                	Log.e("GCMDemo","Error: "+e.toString());
		                }
	
	              }

	else
	              {
	              	Log.e("GCMDemo","No registrado" );
	                
	              }
	            }
	          }).executeAsync();
   

	        }
	        else
	        {
	        	  
	        }
	     
	        if (user.length() == 0)
	        {
	            Log.d("GCMDemo", "Registro no encontrado.");
	           return "";
	        }


	    }
	    */

	    public static void ClearUsuario(Activity act)
	    {
	    	
	    	//Context context = act.getApplicationContext();
	    	
	    	SharedPreferences prefs = act.getSharedPreferences("USUARIO",  act.MODE_PRIVATE);
	        
	        
	        Log.e("GCMDemo","borra " );
	        SharedPreferences.Editor editor = prefs.edit();
	        editor.putString(ID_USUARIO, "");
	        editor.putString(ID_GOOGLE, "");
	        editor.putString(ID_FB, "");
	        editor.putString(ID_TWT, "");
	        editor.putString(MAIL, "");
	        editor.putString(NOMBRE, "");
	        editor.putString(APELLIDO, "");
	        editor.putString(IMAGEN, "");
	        editor.putString(PASSWORD, "");
	     
	        editor.commit();

	    	
	    }

	   
	   
	   
}

