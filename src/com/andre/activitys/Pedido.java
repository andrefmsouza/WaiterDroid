package com.andre.activitys;

import com.andre.produto.Produto;
import com.andre.produto.RepositorioProduto;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Pedido extends Activity {

	RepositorioProduto repositorio;
	Produto p;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido);
        
        repositorio = new RepositorioProduto(this);
        
        //Pega o intent
        Intent intent = getIntent();
        int numero = Integer.parseInt(intent.getStringExtra("NUMERO"));
        
        //Pega o produto escolhido
        p = repositorio.buscarProduto(numero);
        
        //Adiciona as informações aos TextView's
        TextView nome = (TextView)findViewById(R.id.tvNomePedido);
        TextView preco = (TextView)findViewById(R.id.tvPrecoPedido);
        TextView descricao = (TextView)findViewById(R.id.tvDescricaoPedido);
        
        nome.setText(p.nome);
        preco.setText("Preço: R$"+p.preco);
        descricao.setText(p.descricao);
        
    }
    
    //Botão adicionar
    public void adicionar(View view){
    	//Pega a quantidade que o cliente quer comprar
    	EditText quant = (EditText)findViewById(R.id.etQuantPedido);
    	try{
    		//Verifica se o cliente digitou números inteiros
    		int quantidade = Integer.parseInt(quant.getText().toString());
    		//Verifica se já foi feito um pedido igual
    		int ok = 0;
    		for(int i=0; i < MenuProd.carrinho.size(); i++)
    			if(MenuProd.carrinho.get(i).numero == p.numero){
    				MenuProd.carrinho.get(i).quantidade += quantidade;
    				ok = 1;
    				break;
    			}
    		//Caso não tenha feito, é adicionado um novo item no carrinho
    		if(ok == 0){
    			p.quantidade = quantidade;
    			MenuProd.carrinho.add(p);
    		}
    	}catch(Exception e){
    		Toast.makeText(this, "Somente números inteiros no campo quantidade", 
    				Toast.LENGTH_SHORT).show();
    		return;
    	}
    	Toast.makeText(this, "Pedido adicionado ao carrinho", 
				Toast.LENGTH_SHORT).show();
    	finish();
    }
    
    //Botão voltar
    public void voltar(View view){
    	finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pedido, menu);
        return true;
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
    	repositorio.fecharDB();
    }
}
