package gg.petfinder.petfinder.servicios.sqlHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


import gg.petfinder.petfinder.servicios.sqlHelper.BdHelper;

public class DataBaseManager {
	
	

	public static final String TABLE_NAME="encontrados";
	public static final String TABLE_NAME_IMAGENES="encontrados_imagenes";
	public static final String TABLE_NAME_COMENTARIOS="encontrados_comentarios";
	
	public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+ "(ID integer primary key autoincrement," +
			"lat text,"+
			"lon text,"+
			"direccion text,"+
			"texto text,"+
			"fecha text,"+
			"imagen BLOB);";


	public static final String CREATE_TABLE_IMAGENES="CREATE TABLE "+TABLE_NAME_IMAGENES+ "(ID integer primary key autoincrement," +
			"ID_IMAGEN REFERENCES "+TABLE_NAME+"(ID) ON DELETE CASCADE,"+
			"imagen BLOB);";

	public static final String CREATE_TABLE_COMENTARIOS="CREATE TABLE "+TABLE_NAME_COMENTARIOS+ "(ID integer primary key autoincrement," +
			"ID_ENCONTRADO REFERENCES "+TABLE_NAME+"(ID) ON DELETE CASCADE,"+
			"usuario text, comentario text);";

	private BdHelper helper;
	private SQLiteDatabase db;

	public DataBaseManager(Context context) {
    	helper=new BdHelper(context);
        db = helper.getWritableDatabase();

	}
    public void crearComentario(String usuario,String comentario,int id_encontrado)
    {
        try {
            ContentValues values = new ContentValues();
            values.put("ID_ENCONTRADO", id_encontrado);
            values.put("usuario", usuario);
            values.put("comentario", comentario);


            long id = db.insert(TABLE_NAME_COMENTARIOS, null, values);
            Log.e("IDDB",""+String.valueOf(id));




        }catch (Exception e){
            Log.e("alta errpr",""+e.toString());
        }

    }

	public long createRecords(String lat, String lon, String texto,String direccion,String fecha,ArrayList<byte[]> imagenes){
		try {
			ContentValues values = new ContentValues();
			values.put("lat", lat);
			values.put("lon", lon);
			values.put("texto", texto);
			values.put("direccion", direccion);
			values.put("fecha", fecha);
			values.put("imagen", imagenes.get(0));
			Log.e("alta errpr","Pasa por el alta"+lat+" "+lon+" "+texto+" "+direccion+" fecha"+fecha);
			long id = db.insert(TABLE_NAME, null, values);
			Log.e("IDDB",""+String.valueOf(id));

            for(int i=0;i<imagenes.size();i++)
                insertarImg(imagenes.get(i),id);


			return id;
		}catch (Exception e){
			Log.e("alta errpr",""+e.toString());
		}
		   return 0;
		}  
	private void insertarImg(byte[] img,long id)
    {
        try {
            ContentValues values = new ContentValues();
            values.put("ID_IMAGEN", id);
            values.put("imagen", img);
            Log.e("insertandoimg",String.valueOf(id));
            db.insert(TABLE_NAME_IMAGENES, null, values);
        }catch (Exception e)
        {
            Log.e("Insertar imagen error ",e.toString());
        }



    }

	public Cursor selectRecords() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery,null);

		//com.android.internal.R.integer.config_cursorWindowSize= R.integer.config_cursorWindowSize;

		Log.e("Database cuantas col", String.valueOf(cursor.getColumnCount())+"-"+ String.valueOf(cursor.getCount()));

		return cursor;
		}

	public Cursor selectComentarios(int id_encontrado) {

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME_COMENTARIOS+" WHERE ID_ENCONTRADO="+String.valueOf(id_encontrado);

		Cursor cursor = db.rawQuery(selectQuery,null);
        Log.e("Database cuant comentar", selectQuery);

		Log.e("Database cuant comentar", String.valueOf(cursor.getColumnCount())+"-"+ String.valueOf(cursor.getCount()));

		return cursor;
	}
	public Cursor selectImagenes(int id_encontrado) {

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME_IMAGENES+" WHERE ID_IMAGEN="+String.valueOf(id_encontrado);

		Cursor cursor = db.rawQuery(selectQuery,null);
		Log.e("Database cuant comentar", selectQuery);

		Log.e("Database cuant comentar", String.valueOf(cursor.getColumnCount())+"-"+ String.valueOf(cursor.getCount()));

		return cursor;
	}


}
