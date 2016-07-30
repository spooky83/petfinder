package gg.petfinder.petfinder.Entidades;

import com.veinhorn.scrollgalleryview.MediaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gscigliotto on 26/07/2016.
 */
public class Gallery {
    private String direccion;
    private List<byte[]> imagenes;
    private String idencontrado;
    private List<MediaInfo> mediaInfos = new ArrayList<>();

    public Gallery(String direccion, List<byte[]> imagenes, String idencontrado, List<MediaInfo> mediaInfos) {
        this.direccion = direccion;
        this.imagenes = imagenes;
        this.idencontrado = idencontrado;
        this.mediaInfos=mediaInfos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<byte[]>  getImagen() {
        return this.imagenes;
    }
    public void setImagen(List<byte[]> img) {
         this.imagenes=img;
    }
    public void setIconId(String id) {
        this.idencontrado = id;
    }
    public String getIdencontrado(){
        return this.idencontrado;
    }

    public void setMediaInfos( List<MediaInfo> id) {
        this.mediaInfos = id;
    }
    public  List<MediaInfo> getMediaInfos(){
        return this.mediaInfos;
    }



}
