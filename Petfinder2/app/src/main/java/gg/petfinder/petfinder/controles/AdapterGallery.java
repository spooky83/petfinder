package gg.petfinder.petfinder.controles;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.List;

import gg.petfinder.petfinder.Entidades.DrawerItem;
import gg.petfinder.petfinder.Entidades.Gallery;
import gg.petfinder.petfinder.Entidades.Usuario;
import gg.petfinder.petfinder.R;
import gg.petfinder.petfinder.servicios.imgloader.ImageLoader;

/**
 * Created by gscigliotto on 26/07/2016.
 */
public class AdapterGallery extends ArrayAdapter<Gallery>  {
    Context lcontext;
    List<Gallery> obj;
    FragmentActivity Fragment;
    FrameLayout frameLayout;
    private Interface interf;

    public interface Interface{
        void OcultarGaleriaInterface();
    }

    public AdapterGallery(Context context, List<Gallery> objects, FragmentActivity frg) {
        super(context, 0, objects);
        obj=objects;
        lcontext=context;
        Fragment=frg;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(lcontext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_gallery, null);
            frameLayout= (FrameLayout)convertView.findViewById(R.id.galeria);
            frameLayout.setVisibility(View.VISIBLE);
            final View vista =convertView;
            ScrollGalleryView scrollGalleryView = new ScrollGalleryView(lcontext,null);
            scrollGalleryView
                    .setThumbnailSize(100)
                    .setZoom(true)
                    .setFragmentManager(Fragment.getSupportFragmentManager())
                    .addMedia(obj.get(0).getMediaInfos());
            //frameLayout.removeAllViews();
            //FrameLayout fl= (FrameLayout) parent.findViewById(R.id.galeriafrg);
            //fl.setVisibility(View.GONE);
           // if(frameLayout.getChildCount()>1)
           //     frameLayout.removeViewAt(0);
            frameLayout.addView(scrollGalleryView,0);




        }










        return convertView;
    }



}
