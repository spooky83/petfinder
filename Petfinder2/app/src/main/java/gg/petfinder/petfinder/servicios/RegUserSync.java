package gg.petfinder.petfinder.servicios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.login.widget.LoginButton;

import javax.net.ssl.HttpsURLConnection;

import gg.petfinder.petfinder.Entidades.Usuario;

public class RegUserSync extends AsyncTask<String, Void, String> {


	   // Progress Dialog
	    private ProgressDialog pDialog;

	    //static JSONParser jsonParser = new JSONParser();
	    public static Context context;

	    // url to create new product
	    private static String url_create_product = "http://192.168.0.101:100/buscador/registrar_usuario.php";

	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";

    @Override
    protected String doInBackground(String... params) {

        this.RegistrarUsuarioWeb();

        return "Executed!";

    }
    public void RegistrarUsuarioWeb(){
        URL url;
        String response = "";
        try {
            url = new URL("http://www.clarin.com/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                    Log.e("3",line);
                }
            }
            else {
                response="ok";
                Log.e("GCMDemo","ok");
            }
        } catch (Exception e) {
            Log.e("GCMDemo"," "+e.toString());
            e.printStackTrace();
        }

        //return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    /**
      * After completing background task Dismiss the progress dialog
      * **/
     protected void onPostExecute(String file_url) {
         // dismiss the dialog once done
         //pDialog.dismiss();
     }



}

