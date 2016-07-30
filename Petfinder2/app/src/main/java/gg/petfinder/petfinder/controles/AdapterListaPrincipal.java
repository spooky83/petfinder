package gg.petfinder.petfinder.controles;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cr5315.socialbuttons.FacebookShareButton;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;

import gg.petfinder.petfinder.Entidades.ListaPrincipal;
import gg.petfinder.petfinder.Entidades.Usuario;
import gg.petfinder.petfinder.R;
import gg.petfinder.petfinder.controles.AdapterComentarios;
import gg.petfinder.petfinder.servicios.sharePreference;
import gg.petfinder.petfinder.servicios.sqlHelper.DataBaseManager;


/**
 * Created by gsciglioto on 24/02/2016.
 */
public class AdapterListaPrincipal extends ArrayAdapter<ListaPrincipal> {

 private int lastFocussedPosition = -1;
    private Handler handler = new Handler();
    public MostrarEncontrados Mostrar;
    private FrameLayout fl ;
    public ListaPrincipal listaPrincipal;

        public AdapterListaPrincipal(Context context, ArrayList<ListaPrincipal> listaPrincipal) {
            super(context, 0, listaPrincipal);

        }




        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final EditText edittext;



            final View ConvertFinalView;
            final DataBaseManager dataBaseManager;
            // Get the data item for this position
            listaPrincipal = getItem(position);


             // Check if an existing view is being reused, otherwise inflate the view
            try {
                if (convertView == null) {

                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem, parent, false);
                    ConvertFinalView = convertView;


                    // Lookup view for data population
                    de.hdodenhof.circleimageview.CircleImageView  icon = (de.hdodenhof.circleimageview.CircleImageView ) convertView.findViewById(R.id.icon);


                    TextView Dir = (TextView) convertView.findViewById(R.id.dir);
                    final TextView Fecha = (TextView) convertView.findViewById(R.id.fecha);
                    TextView Desc = (TextView) convertView.findViewById(R.id.descripcion);



                    com.cr5315.socialbuttons.FacebookShareButton fbbtn = (com.cr5315.socialbuttons.FacebookShareButton) convertView.findViewById(R.id.fbbtn);
                    fbbtn.setShareUrl("http://www.clarin.com/");


                    // Populate the data into the template view using the data object
                    Dir.setText(listaPrincipal.Direccion);
                    Fecha.setText(listaPrincipal.Fecha);
                    Desc.setText(listaPrincipal.Descripcion);
                    //icon.setImageResource(R.drawable.ic_launcher);
                    if (listaPrincipal.Imagen == null) {
                        icon.setImageResource(R.drawable.ic_launcher);
                    } else {
                        icon.setImageBitmap(sharePreference.getImage(listaPrincipal.Imagen));
                    }
                     final TextView comentarios = (TextView) getView(getPosition(listaPrincipal), ConvertFinalView, parent).findViewById(R.id.comentarios);
                    comentarios.setText(String.valueOf(listaPrincipal.comentarios)+" "+comentarios.getText());
                    comentarios.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                    Mostrar = (MostrarEncontrados) getContext();
//                    Mostrar.MostrarEncontrados();

                            Log.e("AdapterListPrincipal", "Click");
                            fl = (FrameLayout) getView(position, ConvertFinalView, parent).findViewById(R.id.frmcomentar);
                            //getView(0,v,parent)
                            EditText text = (EditText) getView(position, ConvertFinalView, parent).findViewById(R.id.comentario);
                            text.setFocusable(true);
                            if (fl.getVisibility() == View.GONE) {
                                fl.setVisibility(View.VISIBLE);

                                DataBaseManager dataBaseManager = new DataBaseManager(getContext());
                                Cursor cursor = dataBaseManager.selectComentarios(listaPrincipal.id_encontrado);
                                // ListView lv = new ListView(getContext());

                                ListView lista = (ListView)   getView(position, ConvertFinalView, parent).findViewById(R.id.listviewiew);
                                ArrayList<String> comentario=new ArrayList<String> ();
                                final AdapterComentarios adapterComentarios = new AdapterComentarios(getContext(), comentario);
                                // adapterComentarios.clear();
                                try {
                                    if (cursor.moveToFirst()) {
                                        FrameLayout fl = (FrameLayout) getView(position, v, parent).findViewById(R.id.frmcomentar);


                                        do {

                                            EditText textcomentario = new EditText(getContext());
                                            //adapterComentarios.add(cursor.getString(2) + ": " + cursor.getString(3));
                                            //  textcomentario.setText(cursor.getString(2)+": "+cursor.getString(3));
                                            comentario.add(cursor.getString(2) + ": " + cursor.getString(3));
                                            textcomentario.setTextSize(10, 10);
                                            //comentario
                                            Log.e("Obtiene comentarios", cursor.getString(1) + ": " + cursor.getString(3));
                                            //fl.addView(textcomentario);
                                            //lista.addView(textcomentario,0);
                                            //lista.addView(textcomentario);


                                        } while (cursor.moveToNext());


                                        //com.notifyDataSetChanged();

                                        lista.setAdapter(adapterComentarios);

                                    }
                                } catch (Exception E) {
                                    Log.e("Error",E.toString());
                                }

                            } else {
                                fl.setVisibility(View.GONE);
                            }

                        }
                    });


                    edittext = (EditText) getView(position, ConvertFinalView, parent).findViewById(R.id.comentario);
                    dataBaseManager = new DataBaseManager(getContext());
                    Cursor cursor = dataBaseManager.selectComentarios(listaPrincipal.id_encontrado);
                    Button btn = (Button) getView(position, ConvertFinalView, parent).findViewById(R.id.comentar);


                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText edittextv = (EditText) getView(position, ConvertFinalView, parent).findViewById(R.id.comentario);
                            Log.e("Va falla","falla");
                            ArrayList<String> comentario=new ArrayList<String> ();
                            comentario.add(Usuario.Nombre + " " + Usuario.Apellido + ": " + String.valueOf(edittextv.getText()));
                            AdapterComentarios coment = new AdapterComentarios(getContext(),comentario);

                            InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            dataBaseManager.crearComentario(Usuario.Nombre + " " + Usuario.Apellido, String.valueOf(edittextv.getText()), listaPrincipal.id_encontrado);
                            ListView lista2 = (ListView)  getView(position, ConvertFinalView, parent).findViewById(R.id.listviewiew);
                            lista2.setAdapter(coment);
                            edittextv.clearFocus();
                            edittextv.clearComposingText();
                            edittextv.setText("");
                            inputManager.hideSoftInputFromWindow(getView(position, ConvertFinalView, parent).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            fl.setVisibility(View.GONE);
                            listaPrincipal.comentarios++;
                            comentarios.setText("Comentarios: "+String.valueOf(listaPrincipal.comentarios));


                        }
                    });


                } else {

                }
            }catch (Exception e)
            {
                Log.e("Exception",e.toString());
            }

                // Return the completed view to render on screen
            return convertView;
        }
    public interface MostrarEncontrados {
        // TODO: Update argument type and name
        public void MostrarEncontrados();
    }
}
