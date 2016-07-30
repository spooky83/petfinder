package gg.petfinder.petfinder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import gg.petfinder.petfinder.Entidades.Usuario;
import gg.petfinder.petfinder.servicios.servicio;
import gg.petfinder.petfinder.servicios.sharePreference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Login.RegistrarUsuario} interface
 * to handle interaction events.
 * Use the {@link Fragment_Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    CallbackManager callbackManager;
    ProgressDialog progressDialog;

    private RegistrarUsuario mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Login newInstance(String param1, String param2) {
        Fragment_Login fragment = new Fragment_Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Registro", " 1");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Usuario.CargarUsuario(getActivity());
        FacebookSdk.sdkInitialize(getActivity());
       }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.login_button);
        authButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        authButton.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {



            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("petfinder", "Pasa");
                onLoginFb(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                //
                Log.e("petfinder", "Error; " + exception.getMessage() + " " + exception.toString());
            }

        });

        //authButton.set

        try {
            //view=
            Log.e("Registro", " 1");

            Log.e("Registro", " 2");
            //setContentView(R.layout.fragment_login);


            if(savedInstanceState == null) {
                String s="";
            }



            //TextView textMessage = (TextView)getView().findViewById(R.id.nombreusuario);


            Log.e("onCreate_fragmentlogin1", "Usuario.id_fb: " + Usuario.id_fb);
            if( Usuario.id_fb!=null || Usuario.id_fb !="")
            {

                //textMessage.setText("bienvenido "+Usuario.Nombre);
                //ocultaLogin();

            }





            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.e("petfinder", "Pasa");
                    onLoginFb(loginResult);


                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    //
                    Log.e("petfinder", "Error; " + exception.getMessage() + " " + exception.toString());
                }

            });

            AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    if (currentAccessToken == null) {
                        Usuario.ClearUsuario(getActivity());
                    }
                }
            };

        }
        catch (Exception e)
        {
            Log.e("petfinder", "Error: " + e.toString());
        }

        return view;
    }








    protected void onLoginFb(LoginResult loginResult){
        try {


            Log.e("petfinder", "Entra onLoginFb");
            System.out.println("onSuccess");
          //  progressDialog = new ProgressDialog(getActivity());

         //   progressDialog.setMessage("Procesando datos...");
          //  progressDialog.show();
            String accessToken = loginResult.getAccessToken().getToken();


            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(JSONObject object, GraphResponse response)
                {
                    try {

                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);

                        Usuario.id_fb = bFacebookData.getString("idFacebook");


                        //Usuario.imagen=bFacebookData.getString("profile_pic");
                        //   if (!bFacebookData.getString("email").isEmpty())
                        //   {
                        Usuario.Mail = bFacebookData.getString("email");
                        // }

                        Usuario.Nombre = bFacebookData.getString("first_name");
                        Usuario.imagen = bFacebookData.getString("profile_pic");
                        Usuario.Apellido = bFacebookData.getString("last_name");

                        // Intent i=  new Intent(getActivity(), servicio.class);
                        //startService(i);
                        mListener.RegistrarUsuario();
                    }catch (Exception e){

                        Log.e("FBcompleteError",e.toString());
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");// Parámetros que pedimos a facebook
            request.setParameters(parameters);
            request.executeAsync();

            //sharePreference.LlamarFragment(new Fragment_Opciones(),getFragmentManager().beginTransaction(),R.id.contendorFormulario);

            //FrameLayout frameLayout = (FrameLayout) getView().findViewById(R.id.contendorFormulario);
            //frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100));

            //Opciones detailFragment = new Opciones();
            //FragmentTransaction ft = getFragmentManager().beginTransaction();
            //ft.replace(R.id.contendorFormulario, detailFragment, "Detail_Fragment2");

            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            //ft.addToBackStack(+null);
            //ft.commit();
        }
        catch (Exception e)
        {
            Log.e("petfinder", e.toString());
        }
    }





    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {

            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.e("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            Log.e("petfinder", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
           // progressDialog.hide();
          //  Usuario.setUsuario(getActivity());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bundle;

    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.RegistrarUsuario();
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RegistrarUsuario) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface RegistrarUsuario {
        // TODO: Update argument type and name
        public void RegistrarUsuario();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
    }

}
