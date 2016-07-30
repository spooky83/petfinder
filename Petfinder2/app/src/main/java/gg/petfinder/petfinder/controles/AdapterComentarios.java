package gg.petfinder.petfinder.controles;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gg.petfinder.petfinder.Entidades.ListaPrincipal;
import gg.petfinder.petfinder.R;

public class AdapterComentarios extends ArrayAdapter<String> {
    private final Context context;
    private final String val="";
    //private final String[] values;

    public AdapterComentarios(Context context, ArrayList<String> values) {
        super(context, -1, values);
        this.context = context;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    try {
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listcomentarios, parent, false);
            Log.e("Error", String.valueOf(position));
        } else {

        }
        Log.e("Error", String.valueOf(position));
        final String Value = getItem(position);
        //View rowView = convertView.inflate(R.layout.listcomentarios, parent, false);
        TextView comentario = (TextView) convertView.findViewById(R.id.nombre);

        comentario.setText(Value);
    }catch (Exception e)
    {
        Log.e("Error", e.toString());

    }


        return convertView;
    }
}
