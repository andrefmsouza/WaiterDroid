package com.andre.activitys;

import java.util.ArrayList;
import java.util.List;

import com.andre.produto.Produto;
import com.andre.produto.ProdutoListAdapter;
import com.andre.produto.RepositorioProduto;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuProd extends Activity {
	
	RepositorioProduto repositorio;
	ListView lista;
	public static float total = 0;
	public static int aux = 0;
	public static String login = "";
	public static List<Produto> carrinho;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprod);
        
        carrinho = new ArrayList<Produto>();
        
        repositorio = new RepositorioProduto(this);

        lista = (ListView) findViewById(R.id.listaProds);
        
        TextView tv = (TextView)findViewById(R.id.tvNomeCliente);
        
        //Mostra na tela qual o usuario está logado
        login = getIntent().getStringExtra("LOGIN");
        tv.setText("Cliente: "+login);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	
    	//Prepara a lista com os produtos cadastrados
    	lista.setAdapter(new ProdutoListAdapter(this, repositorio.listarProdutos()));
    	
    	lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, 
					int arg2, long arg3) {
				//Caso o usuário click em um produto, é chamada a activity
				//com os dados do produto
				TextView num = (TextView)arg1.findViewById(R.id.tvProdNum);
				Intent i = new Intent("Pedido");
				i.putExtra("NUMERO", num.getText().toString());
				
				startActivity(i);
			}
		});
    }

    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
    	repositorio.fecharDB();
    }
    
    //Botão Carrinho
    public void carrinho(View view){
		Intent i = new Intent("Carrinho");
		
		startActivity(i);
    }
    
    //Botão Finalizar
    public void finalizar(View view){
    	if(MenuProd.carrinho.size() == 0){
    		Toast.makeText(this, "O carrinho está vazio", Toast.LENGTH_SHORT).show();
    	}else{
    		Intent i = new Intent("Finalizar");
    		startActivity(i);
    	}
    }
    
    //Botão Sair
    public void sair(View view){
    	this.carrinho = null;
    	this.login = "";
    	repositorio.fecharDB();
    	finish();
    }
}
