package gg.petfinder.petfinder.Entidades;

/**
 * Created by gsciglioto on 26/02/2016.
 */
public class DrawerItem {
    private String name;
    private int iconId;
    private String url;

    public DrawerItem(String name, int iconId,String url) {
        this.name = name;
        this.iconId = iconId;
        this.url = url;
}
    public DrawerItem(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getIconid(){
        return this.iconId;
    }
}