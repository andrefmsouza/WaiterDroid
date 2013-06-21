package com.andre.activitys;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.andre.produto.Produto;
import com.andre.waiterdroid.Criptografa;
import com.andre.waiterdroid.Envia;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DadosCliente extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_cliente);
        
        TextView login = (TextView)findViewById(R.id.tvLoginDados);
        login.setText(MenuProd.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dados_cliente, menu);
        return true;
    }
    
    //Botão voltar
    public void voltar(View view){
    	finish();
    }
    
    //Calcula o md5 da senha fornecida pelo usuario
    public String md5(String s) throws NoSuchAlgorithmException{
        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(s.getBytes()));
        return hash.toString(16);
    }
    
    //Botão enviar
    public void enviar(View view) throws NoSuchAlgorithmException{
    	//Verifica se o campo senha está vazio
    	EditText senha = (EditText)findViewById(R.id.etSenhaDados);
    	if(senha.getText().toString().equals("")){
    		Toast.makeText(this, "Digite sua senha", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	//Verifica se o campo mesa está vazio
    	EditText mesa = (EditText)findViewById(R.id.etMesaDados);
    	if(mesa.getText().toString().equals("")){
    		Toast.makeText(this, "Digite o número da mesa onde você está", 
    				Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	//Prepara para fazer o login com o servidor
    	String md5 = md5(senha.getText().toString());
    	String login = MenuProd.login+"::_::"+Criptografa.criptografa(senha.getText().toString(), md5);
    	
    	//Prepara a String contendo os dados do pedido
    	String pedido = mesa.getText().toString()+"::_::";
    	for(int i=0; i< MenuProd.carrinho.size(); i++){
    		pedido += MenuProd.carrinho.get(i).numero+",";
    		pedido += MenuProd.carrinho.get(i).quantidade+"::";
    	}
    	
    	//Criptografa o pedido
    	String pedidoCriptografado = Criptografa.criptografa(pedido, md5);
    	
    	Envia e = new Envia(this);
    	e.execute(login, pedidoCriptografado);
    	
    	//Espera a thread acabar de ser executada
    	while(e.ok == -1);
    	//Verifica a resposta do Servidor
    	if(e.ok==0)
    		Toast.makeText(this, "Não foi possível efetuar o login para o pedido", Toast.LENGTH_LONG).show();
    	else
	    	if(e.ok == 2)
	    		Toast.makeText(this, "Erro ao registrar o pedido no servidor", 
	    				Toast.LENGTH_LONG).show();
	    	else
	    		if(e.ok==1){
	    			MenuProd.carrinho = new ArrayList<Produto>();
	    			Toast.makeText(this, "Pedido Realizado! Aguarde alguns minutos...", 
	    					Toast.LENGTH_LONG).show();
	    			finish();
	    		}
    	
        
    }
}
