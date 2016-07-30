package gg.petfinder.petfinder.servicios;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gg.petfinder.petfinder.Fragment_Alta;
import gg.petfinder.petfinder.MainActivity;
import gg.petfinder.petfinder.R;

public class sharePreference {
	private static final String FLAG_LOGIN = "";
	private static final String IDUSR = "0";
	
    private void setRegistrationId(Context context,  String regId)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
     
       
     
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(IDUSR, regId);

     
        editor.commit();
    }
    private String getRegistrationId(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
       
        String registrationId = prefs.getString(IDUSR, "");

     
        return registrationId;
    }
    public static String getRegistrationFlag(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
       
        String registrationId = prefs.getString(IDUSR, "");

     
        return registrationId;
    }
 
    public static void setRegistrationFlagOff(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
     
       
     
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(FLAG_LOGIN, "0");


     
        editor.commit();
    }
    
    
    

    

    public static void setRegistrationFlagOn(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(
        MainActivity.class.getSimpleName(),
            Context.MODE_PRIVATE);
     
       
     
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(FLAG_LOGIN, "1");


     
        editor.commit();
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public static String getDatePhone()

    {

        Calendar cal = new GregorianCalendar();

        Date date = cal.getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String formatteDate = df.format(date);

        return formatteDate;

    }


    public static boolean LlamarFragment(android.app.Fragment fragment, FragmentTransaction fst,int destino)
    {
        try {
            Log.e("petfinder","ok");
            fst.replace(destino, fragment, "Detail_Fragment2");
            fst.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //ft.addToBackStack(+null);
            fst.commit();

        }catch (Exception e)
        {

            Log.e("petfinder","Error: "+e.toString());
        }
        //FragmentTransaction fst = getFragmentManager().beginTransaction();
        return true;
    }






    public static void ImgViewToView(Activity activity, Bitmap rotatedBitmap, View view) {
        ImageView image = new ImageView(activity);


        image.setLayoutParams(new android.view.ViewGroup.LayoutParams(150, 150));
        image.setMaxHeight(150);
        image.setMaxWidth(120);
        image.setPadding(3, 1, 1, 1);
        image.setImageBitmap(rotatedBitmap);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.fotos);
        ll.addView(image, 0);
    }

    public static void setText(View v, int resource, String Value)
    {
        EditText genText = (EditText) v.findViewById(resource);
        genText.setText(Value);
    }

    public static String getText(View v, int resource)
    {
        EditText genText = (EditText) v.findViewById(resource);
        return genText.getText().toString();
    }
    public static void setImage(View v, int resource, Bitmap img)
    {

    }


}
