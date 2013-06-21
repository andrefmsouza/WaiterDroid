package com.andre.produto;

import android.provider.BaseColumns;


public class Produto {
	public static String[] colunas = 
			new String[]{Produtos._ID, Produtos.NOME, Produtos.PRECO, 
		Produtos.NUMERO, Produtos.DESCRICAO};
	
	public static String AUTHORITY = "com.andre.produto.produto";
	public long id;
	public int numero;
	public String nome;
	public float preco;
	public String descricao;
	public int quantidade;
		
	public Produto() {
		// TODO Auto-generated constructor stub
	}

	public Produto(String nome, int numero,float preco, 
			String descricao ) {
		super();
		this.nome = nome;
		this.numero = numero;
		this.preco = preco;
		this.descricao = descricao;
	}

	public Produto(long id, String nome, int numero,float preco, 
			String descricao ) {
		super();
		this.id = id;
		this.nome = nome;
		this.numero = numero;
		this.preco = preco;
		this.descricao = descricao;
	}
	
	public static final class Produtos implements BaseColumns{
		public Produtos() {
			// TODO Auto-generated constructor stub
		}
		
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		public static final String NOME = "nome";
		public static final String NUMERO = "numero";
		public static final String PRECO = "preco";
		public static final String DESCRICAO = "descricao";
		
	}
}
