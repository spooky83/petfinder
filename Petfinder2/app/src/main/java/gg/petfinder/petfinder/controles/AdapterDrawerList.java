package gg.petfinder.petfinder.controles;
import gg.petfinder.petfinder.R;
import gg.petfinder.petfinder.servicios.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import gg.petfinder.petfinder.Entidades.DrawerItem;
import gg.petfinder.petfinder.Entidades.Usuario;
import gg.petfinder.petfinder.servicios.imgloader.ImageLoader;


import com.facebook.FacebookSdk;

import com.facebook.login.widget.LoginButton;









public class AdapterDrawerList extends ArrayAdapter<DrawerItem> {


    public AdapterDrawerList(Context context, List<DrawerItem> objects) {
        super(context, 0, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_menu_item, null);


        }

        if(position==0)
        {


          // img_top.setMaxHeight(0);
            //img_top.setMaxWidth(0);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            //ImageView usricon= (ImageView) convertView.findViewById(R.id.usr_img);
            //usricon.setImageURI(null);
            //usricon.setImageURI(Uri.ge.parse(Usuario.imagen));


            int loader = R.drawable.ic_launcher;



            // Image url
            //String image_url = "http://api.androidhive.info/images/sample.jpg";

            // ImageLoader class instance
            Button btnusr = (Button) convertView.findViewById(R.id.button);
                       ImageLoader imgLoader = new ImageLoader(getContext());

            // whenever you want to load an image from url
            // call DisplayImage function
            // url - image url to load
            // loader - loader image, will be displayed before getting image
            // image - ImageView
            try {
                de.hdodenhof.circleimageview.CircleImageView circleImageView = (de.hdodenhof.circleimageview.CircleImageView) convertView.findViewById(R.id.profile_image);
                circleImageView.setImageBitmap(imgLoader.getBitmap(Usuario.imagen));
                EditText text = (EditText) convertView.findViewById(R.id.editText);
                text.setText(Usuario.Nombre+" "+Usuario.Apellido);

                //Log.e("Graph",Usuario.imagen);
                icon.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
            }catch (Exception e){
                Log.e("",e.toString());
            }
                // imgLoader.DisplayImage(Usuario.imagen, loader, usricon,btnusr);


        }

        else
        {
            LinearLayout img_top = (LinearLayout) convertView.findViewById(R.id.top_img_menu);
            img_top.setVisibility(View.GONE);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            TextView name = (TextView) convertView.findViewById(R.id.name);

            DrawerItem item = getItem(position);
            icon.setImageResource(item.getIconId());
            name.setText(item.getName());

        }








        return convertView;
    }



}
