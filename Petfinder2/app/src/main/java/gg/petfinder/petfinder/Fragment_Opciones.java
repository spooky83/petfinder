package gg.petfinder.petfinder;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;
import com.veinhorn.scrollgalleryview.loader.DefaultVideoLoader;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gg.petfinder.petfinder.Entidades.DrawerItem;
import gg.petfinder.petfinder.Entidades.Gallery;
import gg.petfinder.petfinder.Entidades.ListaPrincipal;
import gg.petfinder.petfinder.Fragments.FlipAnimation;
import gg.petfinder.petfinder.controles.AdapterComentarios;
import gg.petfinder.petfinder.controles.AdapterDrawerList;
import gg.petfinder.petfinder.controles.AdapterGallery;
import gg.petfinder.petfinder.controles.AdapterListaPrincipal;
import gg.petfinder.petfinder.servicios.imgloader.PicassoImageLoader;
import gg.petfinder.petfinder.servicios.sharePreference;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;
import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;

import android.support.v4.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Opciones.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Opciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Opciones extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ClickAgregar mListener;
    private AbrirMenu Listener;
    private int count=0;


    private ListView NewsList;
    private AdapterListaPrincipal adapter;
    private FragmentActivity myContext;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Opciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Opciones newInstance(String param1, String param2) {
        Fragment_Opciones fragment = new Fragment_Opciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Opciones() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try {
            if (savedInstanceState == null) {
                count++;
            Log.e("Fragment_opciones_count",""+count);
                //getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }

        }catch (Exception e)
        {
            Log.e("GCMDemo",e.toString());
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opciones, container, false);
        final String TAG ="Fragment.onCreateView";

        //Genero la lista de items.
        ArrayList<ListaPrincipal> ListaPrincipal = new ArrayList<ListaPrincipal>();
        //Obtengo elementos de la View.
        ImageView menu =(ImageView) view.findViewById(R.id.open_menu);
        ImageView agregar = (ImageView) view.findViewById(R.id.agregar);
        FrameLayout fl = (FrameLayout)view.findViewById(R.id.container);


        //Seteo comportamiento de los botones.
        menu.setOnClickListener(new MostrarMenu(Listener));
        agregar.setOnClickListener(new Agregar(mListener,getActivity()));




        //obtengo los items a mosotrar.
        ListaPrincipal=ObtenerLista();

        //Instancio el view adaptaer la lista y el refresh
        adapter = new AdapterListaPrincipal(getActivity(), ListaPrincipal);
        NewsList = new ListView(getActivity());
        mSwipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        NewsList.setItemsCanFocus(true);
        NewsList.setDividerHeight(0);
        NewsList.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
        NewsList.setAdapter(adapter);
        mSwipeRefreshLayout.setDescendantFocusability(SwipeRefreshLayout.FOCUS_BLOCK_DESCENDANTS);

        //COMPORTAMIENTO CUANDO HAGO CLICK EN UN ITEM DE LA LISTA.
        NewsList.setOnItemClickListener(new ClickItemList(view,myContext,getActivity()));

        mSwipeRefreshLayout.addView(NewsList);

        fl.addView(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);




        // Inflate the layout for this fragment





        return view;
    }
    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }







    private ArrayList<ListaPrincipal> ObtenerLista()
    {

        ArrayList<ListaPrincipal> listaPrincipal = new ArrayList<>();
        DataBaseManager dataBaseManager = new DataBaseManager(getActivity());
        Cursor cursor = dataBaseManager.selectRecords();

        listaPrincipal.clear();

        try {
            if (cursor.moveToFirst()) {
                do {
                    Log.e("FragmentOpciones",cursor.getString(0));

                    ListaPrincipal lista = new ListaPrincipal();
                    lista.id_encontrado=cursor.getInt(0);
                    lista.Direccion = cursor.getString(3);
                    lista.Descripcion = cursor.getString(4);
                    lista.Fecha=cursor.getString(5);
                    lista.Imagen = cursor.getBlob(6);

                    Cursor cur = dataBaseManager.selectComentarios(lista.id_encontrado);
                    lista.comentarios=cur.getCount();
                    listaPrincipal.add(0,lista);
                } while (cursor.moveToNext());
            }
        } catch (Exception E) {
        }

    return listaPrincipal;
    }





    @Override
    public void onRefresh() {
        //Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            myContext=(FragmentActivity) activity;
            super.onAttach(activity);
            mListener = (ClickAgregar) activity;
            Listener=(AbrirMenu) activity;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public interface ClickAgregar {
        // TODO: Update argument type and name
        public void onFragmentInteraction();
    }
    public interface AbrirMenu {
        // TODO: Update argument type and name
        public void AbrirMenu();
    }


    public static class MostrarMenu implements View.OnClickListener {
        private static AbrirMenu Listen;


        public MostrarMenu(AbrirMenu Listener){
            Listen=Listener;
        }

        @Override
        public void onClick(View v) {
            try {
                Log.e("GCMDemo", "Agregar click");
                Listen.AbrirMenu();


            } catch (Exception e) {
                Log.e("GCMDemo", " " + e.getMessage());

            }
        }

    }



    public static class Agregar implements View.OnClickListener {

        private static ClickAgregar listener;
        private static Activity activity;
        public Agregar(ClickAgregar listen,Activity acti)
        {
            listener=listen;
            activity=acti;
        }


        @Override
        public void onClick(View v) {
            Log.e("GCMDemo", "Agregar click");
            listener.onFragmentInteraction();
            Toast.makeText(activity, "Agregar click", Toast.LENGTH_LONG);
        }
    }






    public static class ClickItemList implements AdapterView.OnItemClickListener,AdapterGallery.Interface
    {
        private static View v;


        private ScrollGalleryView scrollGalleryView;
        private FragmentActivity FragActivty;
        private Context context;
        ImageButton btn;
        FrameLayout frameLayout;
        public ClickItemList(View view,FragmentActivity FragmentActivty,Context ctx)
        {
            v=view;
            FragActivty=FragmentActivty;
            context=ctx;

        }
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            frameLayout= (FrameLayout)v.findViewById(R.id.galeriafrg);
            frameLayout.setVisibility(View.VISIBLE);
            ArrayList<Gallery> item = new ArrayList<Gallery>();

            ListView drawerList = (ListView) v.findViewById(R.id.itemsGallery);



            ListaPrincipal p=(ListaPrincipal)parent.getItemAtPosition(position);
            Log.e("El id de la foto",""+p.id_encontrado);
            DataBaseManager dataBaseManager = new DataBaseManager(FragActivty);
            Cursor cursor = dataBaseManager.selectImagenes(p.id_encontrado);
            List<byte[]> imagenes= new ArrayList<>();
            List<MediaInfo> mediaInfos = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                   // imagenes.add(cursor.getBlob(2));
                    mediaInfos.add(MediaInfo.mediaLoader(new DefaultImageLoader(sharePreference.getImage(cursor.getBlob(2)))));
                    Log.e("mediaInfos",""+cursor.getCount());


                } while (cursor.moveToNext());
            }

            item.add(new Gallery("direccion",imagenes, "1",mediaInfos));


            AdapterGallery adapterGallery= new AdapterGallery(context, item,FragActivty);


            drawerList.setAdapter(adapterGallery);


            YoYo.with(Techniques.DropOut)
                    .duration(700)
                    .playOn(v.findViewById(R.id.galeriafrg));

            btn =(ImageButton) v.findViewById(R.id.close_gallery_id);
            btn.setVisibility(View.VISIBLE);
            // btn.setVisibility(View.VISIBLE);
            if (null==btn) Log.e("btn","Drawenullo");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    frameLayout.setVisibility(View.GONE);

                }
            });




        }


        @Override
        public void OcultarGaleriaInterface()
        {


            FrameLayout frameLayout= (FrameLayout)v.findViewById(R.id.galeriafrg);
            YoYo.with(Techniques.FadeOutUp)
                    .duration(700)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            //FrameLayout frameLayout= (FrameLayout) vista.findViewById(R.id.galeria);

                            // frameLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .playOn(v.findViewById(R.id.galeria));


        }


    }


}
