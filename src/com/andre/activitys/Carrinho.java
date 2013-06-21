package com.andre.activitys;

import com.andre.produto.ProdCarrinhoListAdapter;
import com.andre.produto.ProdutoListAdapter;
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

public class Carrinho extends Activity {

	ListView lista;
	private static TextView tvtotal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho);
        
        lista = (ListView) findViewById(R.id.listaProdsCarrinho);
        
        TextView tvnome = (TextView)findViewById(R.id.tvNomeClienteCarrinho);
        tvnome.setText("Cliente: "+MenuProd.login);
        
        tvtotal = (TextView)findViewById(R.id.tvTotalCarrinho);
        
        MenuProd.aux = 1;
        
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
        	
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    	public void onItemClick(AdapterView<?> arg0, View arg1, 
    				int arg2, long arg3) {
    			//Se o usuário clickar em um produto, a activity contendo os dados
    		    //do produto selecionado é chamada
    		
    			//Pega os dados do produto
    			TextView nome = (TextView)arg1.findViewById(R.id.tvProdNomeCarrinho);
    			TextView preco = (TextView)arg1.findViewById(R.id.tvProdPrecoCarrinho);
    			TextView numero = (TextView)arg1.findViewById(R.id.tvProdNumCarrinho);
    			TextView descricao = (TextView)arg1.findViewById(R.id.tvDescProdCarrinho);
    			TextView quantidade = (TextView)arg1.findViewById(R.id.tvQuantProdCarrinho);
    			
    			//Manda para outra view
    			Intent i = new Intent("ProdutoCarrinho");
    			i.putExtra("NOME", nome.getText().toString());
    			i.putExtra("PRECO", preco.getText().toString());
    			i.putExtra("NUMERO", numero.getText().toString());
    			i.putExtra("DESCRICAO", descricao.getText().toString());
    			i.putExtra("QUANTIDADE", quantidade.getText().toString());
    			
    			startActivity(i);
    		}
        });
    	
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
    
    //Botão finalizar
    public void finalizar(View view){
    	if(MenuProd.carrinho.size() == 0){
    		Toast.makeText(this, "O carrinho está vazio", Toast.LENGTH_SHORT).show();
    	}else{
    		MenuProd.total = 0;
    		Intent i = new Intent("DadosCliente");
    		startActivity(i);
    		finish();
    	}
    }
}
