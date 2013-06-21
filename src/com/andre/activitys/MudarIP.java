package com.andre.activitys;

import com.andre.waiterdroid.Persiste;
import com.andre.waiterdroid.R;
import com.andre.waiterdroid.R.id;
import com.andre.waiterdroid.R.layout;
import com.andre.waiterdroid.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MudarIP extends Activity {
	
	private String ARQUIVO = "ARQUIVOIP.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mudar_ip);
        
        //Se o arquivo contendo o ip do servidor existir, ele mostra o ip guardado
        //Caso não exista, é criado um novo arquivo com o ip padrão
        if(Persiste.abrir(ARQUIVO, this)){
        	EditText et = (EditText)findViewById(R.id.etMudarIP);
        	et.setHint(Persiste.ler(ARQUIVO, this));
        }else{
        	Persiste.gravar(ARQUIVO, "192.168.0.189", this);
        	EditText et = (EditText)findViewById(R.id.etMudarIP);
        	et.setHint(Persiste.ler(ARQUIVO, this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mudar_ip, menu);
        return true;
    }
    
    //Botao voltar
    public void voltar(View view){
    	finish();
    }
    
    //Botao mudar IP
    public void mudarIP(View view){
    	//Pega o ip digitado pelo usuario e salva no arquivo
    	EditText et = (EditText)findViewById(R.id.etMudarIP);
    	Persiste.gravar(ARQUIVO, et.getText().toString(), this);
    	Toast.makeText(this, "O IP do servidor foi alterado para "+
    			et.getText().toString(), Toast.LENGTH_SHORT).show();
    	finish();
    }
}
