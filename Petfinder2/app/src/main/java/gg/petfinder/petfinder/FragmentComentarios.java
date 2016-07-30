package gg.petfinder.petfinder;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import gg.petfinder.petfinder.controles.AdapterComentarios;
import gg.petfinder.petfinder.Entidades.Comentarios;
import gg.petfinder.petfinder.Entidades.ListaPrincipal;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;

public  class FragmentComentarios extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView NewsList;
    private ArrayAdapter<String> colorAdapter;
    private AdapterComentarios adapter;
    private static int count=0;
    //private AdapterComentarios adComentarios;
    public FragmentComentarios() {
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
        ArrayList<String> listacomentarios = new ArrayList<String>();

        DataBaseManager dataBaseManager = new DataBaseManager(getActivity());
        Cursor cursor = dataBaseManager.selectRecords();

        listacomentarios.clear();

        adapter = new AdapterComentarios(getActivity(), listacomentarios);
        adapter.clear();





        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.e("FragmentOpciones",cursor.getString(0));
                    Comentarios lista = new Comentarios();
                    lista.comentario = "Comentario 1";

                    listacomentarios.add("lista.comentario");
                    //adapter.add(0,lista);
                    //  adapter.add(lista);

                    // ArrayList<Comentarios> listacomentarios = new ArrayList<Comentarios>();
                    //adComentarios = new AdapterComentarios(getActivity(),listacomentarios);
                    //Comentarios c = new Comentarios();
                    //c.comentario="Comentarios";
                    //adComentarios.add(c);
                } while (cursor.moveToNext());
            }
        } catch (Exception E) {
        }


        NewsList = new ListView(getActivity());

        NewsList.setDividerHeight(0);
        NewsList.setAdapter(adapter);
        //swipeRefreshLayout.addView(NewsList);
        swipeRefreshLayout.clearFocus();

        swipeRefreshLayout.addView(NewsList);

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



                        ArrayList<Comentarios> listacomentarios = new ArrayList<Comentarios>();

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
                                    //arrayOfUsers.add(0, lista);
                                    //adapter.add(lista);
                                    //adapter.add(lista);
                                } while (cursor.moveToNext());
                            }
                        }catch (Exception E){}

                        //adapter.addAll(arrayOfUsers);

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            };
        }.start();
    }
}
