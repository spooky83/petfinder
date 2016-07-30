package gg.petfinder.petfinder;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;

//import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gg.petfinder.petfinder.Entidades.DrawerItem;
import gg.petfinder.petfinder.Entidades.Usuario;
import gg.petfinder.petfinder.controles.AdapterDrawerList;
import gg.petfinder.petfinder.servicios.sharePreference;

public class MainActivity  extends ActionBarActivity implements
                                                        Fragment_Login.RegistrarUsuario,
                                                        Fragment_Opciones.ClickAgregar,
                                                        Fragment_Alta.MostrarEncontrados,
                                                        Fragment_Opciones.AbrirMenu{
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    String[] tagTitles;
    Fragment_Alta activityAlta;

    Fragment_Opciones fragment_opciones;
    String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityAlta = new Fragment_Alta();
        fragment_opciones= new Fragment_Opciones();
        //BUSCA USUARIO CARGADO EN SHARED PREFERENCES
        Usuario.CargarUsuario(this);


        if (Usuario.id_fb !="" )
        {
            //ARMA EL MENU Y LLAMO AL FRAMENT DE OPCIONES
            armoMenu();
            sharePreference.LlamarFragment(fragment_opciones,getFragmentManager().beginTransaction(),R.id.contendorFormulario);
        }
        else
        {
            //SI NO ESTA LOGEADO LO ENVIO AL FRAGMENT DE LOGIN
            sharePreference.LlamarFragment(new Fragment_Login(),getFragmentManager().beginTransaction(),R.id.contendorFormulario);
        }


    }
    private void armoMenu()
    {
        String[] tagTitles = getResources().getStringArray(R.array.Tags);
        //Obtener drawer
        // DrawerLayout drawerLayout = (DrawerLayout)  view.findViewById(R.id.drawer_layout);
        //Obtener listview
        ListView drawerList = (ListView) findViewById(R.id.left_drawer);

        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                menuMger(position);

            }
        });
        //Nueva lista de drawer items

        items.add(new DrawerItem("",R.drawable.slide_img2, Usuario.imagen));
        items.add(new DrawerItem(tagTitles[2],R.drawable.ic_launcher));
        items.add(new DrawerItem(tagTitles[0],R.drawable.map_pointer));

        items.add(new DrawerItem(tagTitles[1],R.drawable.logout_xxl));



        DrawerLayout drawerLayout = (DrawerLayout)  findViewById(R.id.drawer_layout);


        drawerList.setAdapter(new AdapterDrawerList(this, items));

        TextView textMessage = (TextView)findViewById(R.id.nombreusuario);

    }
    private  void menuMger(int opcion)
    {
        switch(opcion) {
            case 0:
                Log.e("petfinder","menuop: "+opcion);
                CierraMenu();
                break;
            case 1:
                Log.e("petfinder","menuop: "+opcion);
                CierraMenu();
                sharePreference.LlamarFragment(fragment_opciones,getFragmentManager().beginTransaction(),R.id.contendorFormulario);

                break;
            case 2:
                Log.e("petfinder","menuop: "+opcion);
                sharePreference.LlamarFragment(fragment_opciones,getFragmentManager().beginTransaction(),R.id.contendorFormulario);
                CierraMenu();
                break;
            case 3:
                Log.e("petfinder","menuop: "+opcion);
                sharePreference.LlamarFragment(new Fragment_Login(),getFragmentManager().beginTransaction(),R.id.contendorFormulario);
                CierraMenu();
                break;
            default:
                Log.e("petfinder","menuop: "+opcion);
        }
    }

    protected void ocultaLogin()

    {

        sharePreference.LlamarFragment(fragment_opciones,getFragmentManager().beginTransaction(),R.id.contendorFormulario);
        Button btn_fb_login = (Button)findViewById(R.id.login_button);

    }




/*
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    */
    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
    }







    @Override
    public void onFragmentInteraction() {
        Log.e("GCMDemo", "Agregar click");




        FragmentTransaction fst = getFragmentManager().beginTransaction();

        fst.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right,0,0);
        //fst.show(activityAlta);
        fst.replace(R.id.contendorFormulario, activityAlta,"FramentAlta");


        //ft.addToBackStack(+null);
        fst.commit();



        Log.e("GCMDEMO", "Llamo a la activity");
        Toast.makeText(this, R.string.toast_agregar, Toast.LENGTH_LONG).show();
    }


    public void RegistrarUsuario()
    {   Log.e("RegistrarUsuario","RegistrarUsuario");
        Usuario.setUsuario(this);
        armoMenu();
        sharePreference.LlamarFragment(new Fragment_Opciones(),getFragmentManager().beginTransaction(),R.id.contendorFormulario);
}

    public void AbrirMenu() {
        Log.e("GCMDEMO","abris menu");
        android.support.v4.widget.DrawerLayout drawerLayout = (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(Gravity.LEFT);

        // If using in a fragment
        //loginButton.se.setFragment(this);
    }
    public void CierraMenu() {
        Log.e("GCMDEMO","abris menu");
        android.support.v4.widget.DrawerLayout drawerLayout = (android.support.v4.widget.DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();

        // If using in a fragment
        //loginButton.se.setFragment(this);
    }

    @Override
    public void MostrarEncontrados() {
        Log.e("GCMDEMO","Llamo a la activity");

        sharePreference.LlamarFragment(new Fragment_Opciones(),getFragmentManager().beginTransaction(),R.id.contendorFormulario);



        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }




}
