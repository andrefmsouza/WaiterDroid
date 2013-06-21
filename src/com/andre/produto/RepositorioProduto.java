package com.andre.produto;

import java.util.ArrayList;
import java.util.List;

import com.andre.produto.Produto.Produtos;
import com.andre.waiterdroid.SQLHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

public class RepositorioProduto {
	public static final String NOME_BANCO = "waiterdroid";
	public static final String NOME_TABELA = "produtos";
	public static final int VERSAO = 1;
	
	private static final String DATABASE_DELETE = "DROP TABLE IF EXISTS produtos";
	private static final String[] DATABASE_CREATE = new String[]{
		"create table produtos (_id integer primary key autoincrement, " +
		"nome text not null, preco float not null, numero int not null," +
		"descricao text not null);"
	};
	
	private SQLHelper sqlHelper;
	private SQLiteDatabase db;
	
	public RepositorioProduto() {
		// TODO Auto-generated constructor stub
	}
	
	public RepositorioProduto(Context context) {
		// TODO Auto-generated constructor stub
		
		// Cria o SQLHelper para gerenciar a base de dados 
		sqlHelper = new SQLHelper(context, NOME_BANCO, VERSAO, 
				DATABASE_CREATE, DATABASE_DELETE);
		
		// Abre a base de dados para escrita
		db = sqlHelper.getWritableDatabase();
	}
	
	public void deletar(){
		db.delete(NOME_TABELA, null, null);
	}
	
	public long salvar(Produto produto){
		
		long id = produto.id;
		
		if(id != 0){
			atualizar(produto);
			
		}else{
			id = insert(produto);
		}
		return id;
	}
	
	//Insere produto no banco
	public long insert(Produto produto){
		ContentValues valores = new ContentValues();
		valores.put(Produtos.NOME, produto.nome);
		valores.put(Produtos.PRECO, produto.preco);
		valores.put(Produtos.NUMERO, produto.numero);
		valores.put(Produtos.DESCRICAO, produto.descricao);
		
		return db.insert(NOME_TABELA, "", valores);
	}
	
	//atualiza os dados do produto
	public int atualizar(Produto produto){
		ContentValues valores = new ContentValues();
		valores.put(Produtos.NOME, produto.nome);
		valores.put(Produtos.PRECO, produto.preco);
		valores.put(Produtos.NUMERO, produto.numero);
		valores.put(Produtos.DESCRICAO, produto.descricao);
		
		//String where = Produtos._ID + "=" + produto.id;
		String where = BaseColumns._ID + "=?";
		String[] whereArgs = new String[]{String.valueOf(produto.id)};
		
		return db.update(NOME_TABELA, valores, where, whereArgs);
	}
	
	//Deleta o produto
	public int deletar(long id){
		String where = BaseColumns._ID + "=?";
		String[] whereArgs = new String[]{String.valueOf(id)};
		
		return db.delete(NOME_TABELA, where, whereArgs);
	}
	
	//Busca por um produto a partir do id
	public Produto buscarProduto(long id){
		Cursor c = db.query(true, NOME_TABELA, Produto.colunas, 
				BaseColumns._ID + "=" + id, null, null, null, null, null);
		
		if(c.getCount() > 0){
			c.moveToFirst();
			Produto produto = new Produto();
			produto.nome = c.getString(c.getColumnIndex(Produtos.NOME));
			produto.id = c.getLong(c.getColumnIndex(BaseColumns._ID));
			produto.numero  = c.getInt(c.getColumnIndex(Produtos.NUMERO));
			produto.preco = c.getFloat(c.getColumnIndex(Produtos.PRECO));
			produto.descricao = c.getString(c.getColumnIndex(Produtos.DESCRICAO));
			return produto;
		}
		
		return null;
	}
	
	//Busca por um produto a partir do nome
	public Produto buscarProduto(String nome){
		Cursor c = db.query(NOME_TABELA, Produto.colunas, 
				Produtos.NOME + "='" + nome + "'", null, null, null, null);
		
		if(c.getCount() > 0){
			c.moveToFirst();
			Produto produto = new Produto();
			produto.nome = c.getString(c.getColumnIndex(Produtos.NOME));
			produto.id = c.getLong(c.getColumnIndex(BaseColumns._ID));
			produto.numero  = c.getInt(c.getColumnIndex(Produtos.NUMERO));
			produto.preco = c.getFloat(c.getColumnIndex(Produtos.PRECO));
			produto.descricao = c.getString(c.getColumnIndex(Produtos.DESCRICAO));
			return produto;
		}
		
		return null;
	}
	
	//Busca por um produto a partir do numero
	public Produto buscarProduto(int numero){
			Cursor c = db.query(NOME_TABELA, Produto.colunas, 
					Produtos.NUMERO + "=" + numero + "", null, null, null, null);
			
			if(c.getCount() > 0){
				c.moveToFirst();
				Produto produto = new Produto();
				produto.nome = c.getString(c.getColumnIndex(Produtos.NOME));
				produto.id = c.getLong(c.getColumnIndex(BaseColumns._ID));
				produto.numero  = c.getInt(c.getColumnIndex(Produtos.NUMERO));
				produto.preco = c.getFloat(c.getColumnIndex(Produtos.PRECO));
				produto.descricao = c.getString(c.getColumnIndex(Produtos.DESCRICAO));
				return produto;
			}
			
			return null;
	}
	
	//Pega todos os produtos
	public Cursor getCursor(){
		try{
			return db.query(NOME_TABELA, Produto.colunas, 
							null, null, null, null, null);
		}catch(SQLException e){
			return null;
		}
	}
	
	//Lista todos os produtos
	public List<Produto> listarProdutos(){
		Cursor c = getCursor();
		List<Produto> produtos = new ArrayList<Produto>();
		
		if(c.moveToFirst()){
			int idxid = c.getColumnIndex(BaseColumns._ID);
			int idxnome = c.getColumnIndex(Produtos.NOME);
			int idxnumero = c.getColumnIndex(Produtos.NUMERO);
			int idxpreco = c.getColumnIndex(Produtos.PRECO);
			int idxdescricao = c.getColumnIndex(Produtos.DESCRICAO);
			
			do{
				Produto produto = new Produto();
				produto.id = c.getLong(idxid);
				produto.nome = c.getString(idxnome);
				produto.numero = c.getInt(idxnumero);
				produto.preco = c.getFloat(idxpreco);
				produto.descricao = c.getString(idxdescricao);			
				produtos.add(produto);
				
			}while(c.moveToNext());
		}
		
		return produtos;
	}
	
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, 
			String[] selectionArgs, String groupBy, String having, String orderBy){
		
		Cursor c = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, orderBy);
		
		return c;
	}
	
	//fecha o banco
	public void fecharDB(){
		
		if(db != null)
			db.close();
		
		if(sqlHelper != null)
			sqlHelper.close();
	}
}
