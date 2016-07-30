package gg.petfinder.petfinder.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import gg.petfinder.petfinder.Entidades.ListaPrincipal;
import gg.petfinder.petfinder.controles.AdapterComentarios;
import gg.petfinder.petfinder.controles.AdapterListaPrincipal;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;

/**
 * Created by gscigliotto on 24/07/2016.
 */
public class PlaceholderFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView NewsList;
    private ArrayAdapter<String> colorAdapter;
    private AdapterListaPrincipal adapter;
    private static int count=0;
    private AdapterComentarios adComentarios;
    public PlaceholderFragment() {
        count++;
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        swipeRefreshLayout = new SwipeRefreshLayout(getActivity());


        return swipeRefreshLayout;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Log.e("Crear Act opcion:",""+count);
        ArrayList<ListaPrincipal> ListaPrincipal = new ArrayList<ListaPrincipal>();

        DataBaseManager dataBaseManager = new DataBaseManager(getActivity());
        Cursor cursor = dataBaseManager.selectRecords();

        ListaPrincipal.clear();
        adapter = new AdapterListaPrincipal(getActivity(), ListaPrincipal);
        adapter.clear();





        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.e("FragmentOpciones",cursor.getString(0));
                    ListaPrincipal lista = new ListaPrincipal();
                    lista.Direccion = cursor.getString(3);
                    lista.Descripcion = cursor.getString(4);
                    lista.Fecha=cursor.getString(5);
                    lista.Imagen = cursor.getBlob(6);

                    ListaPrincipal.add(0,lista);

                } while (cursor.moveToNext());
            }
        } catch (Exception E) {
        }

        NewsList = new ListView(getActivity());



        NewsList.setDividerHeight(0);
        NewsList.setAdapter(adapter);


        NewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("CLICK",String.valueOf(position));
            }
        });
        swipeRefreshLayout.addView(NewsList);
            /*
            swipeRefreshLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("CLICK","");
                }
            });
            */





            /*

                           @Override
                public void onItemClick(View v) {
                    Log.e("swiperefreshlayout","Pasa por aca");
                    FragmentComentarios fragment2 = new FragmentComentarios();
                    android.app.FragmentManager fragmentManager = getFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frmcomentarios, fragment2);
                    fragmentTransaction.commit();
                }
             */
        swipeRefreshLayout.clearFocus();

//            swipeRefreshLayout.addView(NewsList);

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        new Thread() {
            public void run() {
                SystemClock.sleep(2000);

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {



                        ArrayList<ListaPrincipal> arrayOfUsers = new ArrayList<ListaPrincipal>();

                        adapter.clear();

                        DataBaseManager dataBaseManager = new DataBaseManager(getActivity());
                        Cursor cursor = dataBaseManager.selectRecords();

                        try {
                            if (cursor.moveToFirst()) {
                                do {
                                    ListaPrincipal lista = new ListaPrincipal();
                                    lista.Direccion = cursor.getString(3);
                                    lista.Descripcion = cursor.getString(4);
                                    lista.Fecha=cursor.getString(5);
                                    lista.Imagen = cursor.getBlob(6);
                                    arrayOfUsers.add(0, lista);
                                    //adapter.add(lista);
                                    //adapter.add(lista);
                                } while (cursor.moveToNext());
                            }
                        }catch (Exception E){}

                        adapter.addAll(arrayOfUsers);


                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            };
        }.start();
    }
}