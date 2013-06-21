package com.andre.activitys;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.andre.waiterdroid.Criptografa;
import com.andre.waiterdroid.Login;
import com.andre.waiterdroid.Persiste;
import com.andre.waiterdroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
	
	private String ARQUIVO = "ARQUIVOIP.txt";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Caso não exista o arquivo de configuração com o ip,
        //é criado um novo arquivo com o ip padrão
        if(!Persiste.abrir(ARQUIVO, this))
        	Persiste.gravar(ARQUIVO, "192.168.0.189", this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Calcula o md5 da senha fornecida pelo usuario
    public String md5(String s) throws NoSuchAlgorithmException{
        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(s.getBytes()));
        return hash.toString(16);
    }
    
    //Chamado quando o botao entrar for pressionado
    public void entrar(View view) throws NoSuchAlgorithmException{
    	//Cria a classe que irá comunicar com o servidor
    	Login l = new Login(this);
    	//Pega o login e a senha
    	EditText login = (EditText)findViewById(R.id.etLogin);
    	EditText senha = (EditText)findViewById(R.id.etSenha);
    	//Verifica se o campo login está em branco
    	if(login.getText().toString().equals("")){
    		Toast.makeText(this, "Digite o nome de usuário", Toast.LENGTH_LONG).show();
    		return;
    	}
    	//Verifica se o campo senha está em branco
    	if(senha.getText().toString().equals("")){
    		Toast.makeText(this, "Digite a senha do usuário", Toast.LENGTH_LONG).show();
    		return;
    	}
    	//Pega o md5 da senha
    	String md5 = md5(senha.getText().toString());
    	//Criptografa a senha com o proprio md5
    	String senhaCriptografada = Criptografa.criptografa(senha.getText().toString(), md5);
    	//Prepara o login e a senha criptografada para ser enviado
    	String s = login.getText().toString()+"::_::"+senhaCriptografada;
    	l.execute(s);
    	//Espera a thread acabar de ser executada
    	while(l.ok == -1);
    	//Verifica a resposta do Servidor
    	if(l.ok==0){
    		senha.setText("");
    		Toast.makeText(this, "Não foi possível efetuar o login", Toast.LENGTH_LONG);
    	}else
	    	if(l.ok == 1){
	    		senha.setText("");
	    		Intent i = new Intent("MenuProd");
	    		i.putExtra("LOGIN", login.getText().toString());
	        	startActivity(i);
	    	}
    		
    }
    
    //Botão mudarIP
    public void mudarIP(View view){
    	EditText senha = (EditText)findViewById(R.id.etSenha);
		senha.setText("");
    	Intent i = new Intent("MudarIP");
    	startActivity(i);
    }
    
}
