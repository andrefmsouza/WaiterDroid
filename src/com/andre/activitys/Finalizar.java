package com.andre.activitys;

import com.andre.produto.ProdCarrinhoListAdapter;
import com.andre.produto.ProdutoListAdapter;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Finalizar extends Activity {

	ListView lista;
	private static TextView tvtotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalizar);
        
        //Verifica se o carrinho está vazio
        if(MenuProd.carrinho.size() == 0){
    		Toast.makeText(this, "O carrinho está vazio", Toast.LENGTH_SHORT).show();
    		finish();
    	}
        
        MenuProd.aux = 0;
        
        lista = (ListView) findViewById(R.id.listaProdsCarrinhoFinalizar);
        
        TextView tvnome = (TextView)findViewById(R.id.tvNomeClienteFinalizar);
        tvnome.setText("Cliente: "+MenuProd.login);
        
        tvtotal = (TextView)findViewById(R.id.tvTotalFinalizar);
        
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
    	
    	MenuProd.total = 0;
    	
    	//Prepara a lista com os produtos no carrinho
        lista.setAdapter(new ProdCarrinhoListAdapter(this, 
        		MenuProd.carrinho));
    	
    }
    
    //Usado para atualizar o preco total
    public static void atualizarPreco(){
    	if(MenuProd.total==0)
    		tvtotal.setText("");
    	else
    		tvtotal.setText("Total: R$"+String.valueOf(MenuProd.total));
    }

    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	MenuProd.total = 0;
    }
    
    //Botão voltar
    public void voltar(View view){
    	MenuProd.total = 0;
    	finish();
    }
    
    //Botao confirmar
    public void confirmar(View view){
    	MenuProd.total = 0;
    	Intent i = new Intent("DadosCliente");
    	startActivity(i);
    	finish();
    }
}
