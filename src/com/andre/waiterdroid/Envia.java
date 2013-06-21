package com.andre.waiterdroid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class Envia extends AsyncTask<String, Void, Void>{
	 private ProgressDialog progressDialog;
	 public static Context CONTEXT;
	 public static Socket SOCKET;
	 public static ObjectOutputStream OUT;
	 public static ObjectInputStream IN;
	 private static final String IP = "ARQUIVOIP.txt";
	 public static int ok;
	 
	 public Envia(Context c){
		 this.CONTEXT = c;
		 this.ok = -1;
	 }
	 
	 @Override
	 protected void onPreExecute() {
		//Mostra uma mensagem de "Aguarde..." enquanto o aplicativo
		//tenta fazer o login
	    progressDialog = new ProgressDialog(CONTEXT);
	    progressDialog.setMessage("Aguarde...");
	    progressDialog.show();

	 }
	 
	 /*
	  * Protocolo utilizado:
	  * 000 - Fim da comunicação
	  * 100 - Erro
	  * 200 - OK
	  * 201 - Final de envio
	  * 300 - Novo pedido
	  * 301 - Envio de pedidos
	  * 
	  * ::_:: - Separador
	  */
	 @Override
	 protected Void doInBackground(String... s) {
		SOCKET = null;
		ok = -1;
		try {
			//Faz a conexao com o servidor
            SOCKET = new Socket(Persiste.ler(IP, CONTEXT), 5436);
            OUT = new ObjectOutputStream(SOCKET.getOutputStream());
            
            //Envia a string contendo o login e a senha
            OUT.flush();
            OUT.writeObject(s[0]); 
            
            //Pega a mensagem do servidor
            IN = new ObjectInputStream(SOCKET.getInputStream());
            String msg = (String)IN.readObject();
            //Verifica se foi feito o login
            if(msg.equals("200")){
            	//Pega a data da ultima atualização dos produtos no servidor
            	msg = (String)IN.readObject();
            	
            	//Prepara para enviar os pedidos para o servidor
            	OUT.flush();
                OUT.writeObject("301");
                
                //Envia os pedidos para o servidor
            	OUT.flush();
                OUT.writeObject(s[1]);
                
                //Pega a resposta do servidor
            	msg = (String)IN.readObject();
            	
            	if(msg.equals("200"))
            		//Caso tenha feito o login e feito o pedido, a variavel ok
            		//recebe o valor 1 para confirmação na thread principal
            		ok = 1;
            	else
            		//Caso tenha feito o login, mas não tenha feito o pedido,
            		//a variavel ok recebe o valor 2 para confirmação na thread
            		//principal
            		ok = 2;
            	
            	//Envia os pedidos para o servidor
            	OUT.flush();
                OUT.writeObject("000");
                
                //Fecha a conexão com o servidor
                IN.close();
        	    OUT.close();
        	    SOCKET.close();
            	return null;
            }//if(Verifica se foi feito o login)
            //Fecha a conexão com o servidor
            IN.close();
    	    OUT.close();
    	    SOCKET.close();
        } catch (UnknownHostException ex) {
        	Log.d("TAG", ex.toString());
        } catch (IOException ex) {
        	Log.d("TAG", ex.toString());
        } catch (ClassNotFoundException e) {
        	Log.d("TAG", e.toString());
		}
		//Caso tenha dado algum erro a variavel ok recebe o valor 0
		//para confirmação na thread principal
		ok=0;
	    return null;
	 }

	 @Override
	 protected void onPostExecute(Void result) {
		//Encessa a mensagem de "Aguarde..." na tela
	    progressDialog.dismiss();
	 }
}
