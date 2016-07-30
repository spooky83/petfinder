package gg.petfinder.petfinder;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import gg.petfinder.petfinder.Fragments.FlipAnimation;
import gg.petfinder.petfinder.servicios.CameraPreview;
import gg.petfinder.petfinder.servicios.MyLocationListener;
import gg.petfinder.petfinder.servicios.imgloader.ImageHelper;
import gg.petfinder.petfinder.servicios.sharePreference;
import gg.petfinder.petfinder.servicios.sqlHelper.BdHelper;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Alta.MostrarEncontrados} interface
 * to handle interaction events.
 * Use the {@link Fragment_Alta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Alta extends Fragment implements MyLocationListener.LocationListtener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Camera mCamera;
    private CameraPreview mPreview;
    private MapFragment mMapFragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static int totalImg = 0;
    private MostrarEncontrados mListener;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private double lat=0;
    private double lon=0;
    private ArrayList<byte[]> imagenes= new ArrayList<byte[]>();
    FragmentTransaction fragmentTransaction;
    EditText info;
    GoogleMap mapa;
    boolean gpsActivado=true;
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            try {
                File pictureFile = CameraPreview.getOutputMediaFile(MEDIA_TYPE_IMAGE);
                if (pictureFile == null) {
                    Log.d("GCMDemo", "Error creating media file, check storage permissions: )");
                    return;
                }
                //Le doy formato a la imagen tomada.
                Bitmap rotatedBitmap=ImageHelper.FormatearImagen(pictureFile,data,getActivity());

                //AGREGO A LA LISTA DE IMAGENES A GUARDAR EN LA BD
                imagenes.add(gg.petfinder.petfinder.servicios.sharePreference.getBytes(rotatedBitmap));
                sharePreference.ImgViewToView(getActivity(),rotatedBitmap,getView());

                totalImg++;
            } catch (Exception e) {
                Log.d("GCMDemo", "Error accessing file: " + e.getMessage());
            }
        }
    };


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Alta.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Alta newInstance(String param1, String param2) {
        Fragment_Alta fragment = new Fragment_Alta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_Alta() {
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
            if (mCamera==null)
                mCamera = CameraPreview.getCameraInstance();

            mMapFragment = MapFragment.newInstance();
            mPreview = new CameraPreview(getActivity(), mCamera);

        }catch (Exception   e)

        {
            Log.e("Fragment Alta","111"+e.toString());
        }


    }

    private void ObtenerCalles(double lat, double lon,View view)
    {
        Geocoder geocoder = new Geocoder(getActivity());

        try {
            List<android.location.Address> list = geocoder.getFromLocation(lat, lon, 1);

            //geocoder.getFromLocation(lat,lon, 1);

            if (!list.isEmpty()) {
                android.location.Address address = list.get(0);

//                Log.e("Calles"," "+address.getSubThoroughfare().toString());
                //1Log.e("Calles"," "+address.getAddressLine(0).toString()+" "+address.getAddressLine(0).toString());
                //messageTextView2.setText("Mi direcci—n es: \n" + address.getAddressLine(0));
                sharePreference.setText(view,R.id.coordenadas,address.getAddressLine(0).toString());
               // setTextCoordenada(view,address.getAddressLine(0).toString());

            }
            else
            {
                sharePreference.setText(view,R.id.coordenadas,"Dirección no Encontrada..");
                //setTextCoordenada(view,"Dirección no Encontrada.. ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }




    private void activarGps(final View view){

        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mlocListener = new MyLocationListener();
        //COMPRUEBO SI ESTA ACTIVADO EL GPS EN EL CELULAR.
        if(mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER )||mlocManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER))
        {
            //OBTENGO EL ULTIMIO LUGAR REGISTRADO


        }
        else
        {
            //Toast.makeText(getActivity(), "No activado", Toast.LENGTH_LONG);
            //setTextCoordenada(view,"Activa el GPS para localizar automaticamente.");
            sharePreference.setText(view,R.id.coordenadas,"Activa el GPS para localizar automaticamente.");

            do{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                builder.setMessage("Debe sacar al menos una foto.")
                        .setTitle("Petfinder").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        gpsActivado=false;
                        // FIRE ZE MISSILES!
                        //if(mlocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){}

                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

            }while (gpsActivado);

        }
        Location lastKnownLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lastKnownLocation == null) {
            lat=0;
            lon=0;
        }
        else {
            lat = lastKnownLocation.getLatitude();
            lon = lastKnownLocation.getLongitude();
        }
        ObtenerCalles(lat,lon,view);



        //CONFIGURO EL BOTON PARA MOSTRAR EL MAPA
        Button captureButton = (Button) view.findViewById(R.id.button_map);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //INSERTO EN EL FRAME EL FRAGMENTMAPA


                        fragmentTransaction =  getFragmentManager().beginTransaction();
                        // fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                        LinearLayout ll = (LinearLayout) view.findViewById(R.id.fotos);
                        //OCULTO ELEMENTOS DE LA CAMARA.
                        ll.setVisibility(View.INVISIBLE);

                        Button btonAceptar = (Button) view.findViewById(R.id.button_capture);
                        btonAceptar.setVisibility(View.INVISIBLE);
                        fragmentTransaction.replace(R.id.camera_preview, mMapFragment);



                        fragmentTransaction.commit();

                        mMapFragment.getMapAsync(new OnMapReadyCallback() {

                            @Override
                            public void onMapReady(final GoogleMap googleMap) {
                                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                    @Override
                                    public void onMapClick(LatLng latLng) {
                                        ObtenerCalles(latLng.latitude,latLng.longitude,view);
                                    }
                                });
                                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                                    @Override
                                    public void onMapLongClick(LatLng latLng) {
                                        googleMap.clear();
                                        googleMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(latLng.latitude, latLng.longitude))
                                                .title("Marker2"));
                                        ObtenerCalles( latLng.latitude,latLng.longitude,getView());

                                    }
                                });
                                googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat, lon))
                                        .title("Marker"));
                                float zoomLevel = 16; //This goes up to 21
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), zoomLevel));
                                ObtenerCalles(lat,lon,view);
                                mapa=googleMap;
                            }
                        });

                    }
                }
        );


        //COMPRUEBO LOS PERMISOS

        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("FragmentAlta","no activato el gps");







        }

        //REGISTRO EL EVENTO
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) mlocListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_alta, container, false);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.camera_preview);
        info = (EditText) view.findViewById(R.id.info);
        try
        {

            preview.addView(mPreview);

        }catch (Exception e)
        {

            preview.removeView(mPreview);
            Log.e("Fragment_alta","Error: "+e.toString());
        }

        activarGps(view);

        //Funcionalidad de aceptar el boton
        Button botonAceptar =(Button) view.findViewById(R.id.button);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.e("FragmentAlta", String.valueOf(imagenes.size()));
               if (imagenes.size() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


                    builder.setMessage("Debe sacar al menos una foto.")
                            .setTitle("Petfinder").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            // FIRE ZE MISSILES!
                        }
                    });


                    AlertDialog dialog = builder.create();
                    dialog.show();

                 }
                else
                {


                DataBaseManager dataBaseManager = new DataBaseManager(getActivity());
                EditText direccion = (EditText) getView().findViewById(R.id.coordenadas);
                LinearLayout ll = (LinearLayout) getView().findViewById(R.id.fotos);

                dataBaseManager.createRecords(String.valueOf(lat), String.valueOf(lon), info.getText().toString(), direccion.getText().toString(), sharePreference.getDatePhone(), imagenes);
                imagenes.clear();
                info.setText("Info..");
                mListener.MostrarEncontrados();
            }

            }
        });
        final EditText textoextra = (EditText) view.findViewById(R.id.info);
        textoextra.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textoextra.setText("");
                return false;
            }
        } );


        // Create an instance of Camera


        //configuro el evento boton de tomar la foto.
        Button captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        Log.e("GCMDemo", "Toma foto");
                        mCamera.takePicture(null, null, mPicture);

                    }
                }
        );
        // Create our Preview view and set it as the content of our activity.


        Button activaCamara = (Button) view.findViewById(R.id.activar_camara);
        activaCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //SACO DEL FRAGMENT EL MAPA
                fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.remove(mMapFragment);
                fragmentTransaction.commit();

                FrameLayout preview = (FrameLayout) getView().findViewById(R.id.camera_preview);
                //Y ACTIVO LOS BOTONES
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_in);
                LinearLayout ll = (LinearLayout) getView().findViewById(R.id.fotos);
                //OCULTO ELEMENTOS DE LA CAMARA.
                ll.setVisibility(View.VISIBLE);

                Button btonAceptar = (Button) getView().findViewById(R.id.button_capture);
                btonAceptar.setVisibility(View.VISIBLE);


                if(preview==null)
                    Log.e("Error","Error levantando camara: ");
               Log.e("Fragment_alta","pasa");

            }
        });



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.MostrarEncontrados();
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MostrarEncontrados) activity;
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

    @Override
    public void onLocationChanged(Location loc) {

        // Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la detecci—n de un cambio de ubicacion

        loc.getLatitude();
        loc.getLongitude();
        String Text = "Mi ubicaci—n actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();
        //   messageTextView.setText(Text);
        //this.mainActivity.setLocation(loc);
        Toast.makeText(getActivity(), Text, Toast.LENGTH_LONG);
        Log.e("GCMDemo","Fragment"+ Text);
        EditText coordenadasText = (EditText) getView().findViewById(R.id.coordenadas);
        coordenadasText.setText(Text);





        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title("Marker"));
        float zoomLevel = 16; //This goes up to 21
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), zoomLevel));
        ObtenerCalles(lat,lon,getView());


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
    public interface MostrarEncontrados {
        // TODO: Update argument type and name
        public void MostrarEncontrados();
    }









}
