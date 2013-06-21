package com.andre.waiterdroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper{
	private String create[];
	private String delete;
	
	public SQLHelper(Context context, String name, int version, 
			String[] create, String delete) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		
		this.create = create;
		this.delete = delete;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < create.length; i++){
			db.execSQL(create[i]);
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		// Deleta todos os registros
		db.execSQL(delete);
		
		// Cria a nova base de dados
		onCreate(db);
	}

}
