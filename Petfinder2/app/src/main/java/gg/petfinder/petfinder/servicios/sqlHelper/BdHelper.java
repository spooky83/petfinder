package gg.petfinder.petfinder.servicios.sqlHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BdHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME="petfinder";
	private static final int DB_SCHEME_VERSION=2;

	
	@Override
	public SQLiteDatabase getWritableDatabase() {
		// TODO Auto-generated method stub
		return super.getWritableDatabase();
	}
	public BdHelper (Context context)
	{
		super(context,DB_NAME,null,DB_SCHEME_VERSION);

	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DataBaseManager.CREATE_TABLE);
		db.execSQL(DataBaseManager.CREATE_TABLE_IMAGENES);
		db.execSQL(DataBaseManager.CREATE_TABLE_COMENTARIOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	

}
