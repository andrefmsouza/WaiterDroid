package com.andre.activitys;

import com.andre.produto.Produto;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProdutoCarrinho extends Activity {

	int numero;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_carrinho);
        
        //Mostra as informações do produto no carrinho
        TextView nome = (TextView)findViewById(R.id.tvNomePedidoCarrinho);
		TextView preco = (TextView)findViewById(R.id.tvPrecoPedidoCarrinho);
		TextView descricao = (TextView)findViewById(R.id.tvDescricaoPedidoCarrinho);
		TextView quantidade = (TextView)findViewById(R.id.tvQuantidadePedidoCarrinho);
		
		nome.setText(getIntent().getStringExtra("NOME"));
		preco.setText(getIntent().getStringExtra("PRECO"));
		numero = Integer.parseInt(getIntent().getStringExtra("NUMERO"));
		descricao.setText(getIntent().getStringExtra("DESCRICAO"));
		quantidade.setText(getIntent().getStringExtra("QUANTIDADE"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produto_carrinho, menu);
        return true;
    }
    
    //Botão voltar
    public void voltar(View view){
    	finish();
    }
    
    //Botão retirar
    public void retirar(View view){
    	//Retira o produto escolhido do carrinho
    	for(int i=0; i < MenuProd.carrinho.size();i++)
    		if(MenuProd.carrinho.get(i).numero == numero)
    			MenuProd.carrinho.remove(i);
    	Toast.makeText(this, "Produto retirado do carrinho", Toast.LENGTH_SHORT).show();
    	finish();
    }
}
