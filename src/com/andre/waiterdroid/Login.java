package com.andre.waiterdroid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.andre.produto.Produto;
import com.andre.produto.RepositorioProduto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class Login extends AsyncTask<String, Void, Void>{

	 private ProgressDialog progressDialog;
	 public static Context CONTEXT;
	 public static Socket SOCKET;
	 public static ObjectOutputStream OUT;
	 public static ObjectInputStream IN;
	 private static final String ARQUIVO = "dataMod.txt";
	 private static final String IP = "ARQUIVOIP.txt";
	 public static int ok;
	 
	 public Login(Context c){
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
            	//Verifica se o arquivo existe no aplicativo
                if(Persiste.abrir(ARQUIVO, CONTEXT)){
                	String dataMod = Persiste.ler(ARQUIVO, CONTEXT);
                	//Verifica se a data do servidor é a mesma do aplicativo
                	//Caso seja a mesma o aplicativo não irá utilizar as informações
                	//do seu banco de dados
                	if(msg.equals(dataMod)){
                		//Caso esteja tudo correto, o aplicativo manda uma
                		//mensagem encerrando a conexao com o servidor
                		OUT.flush();
                		OUT.writeObject("000");
                		//Fecha conexao com o servidor
	                    this.IN.close();
	                    this.OUT.close();
	                    this.SOCKET.close();
                	//Caso não seja a mesma data, o aplicativo atualiza a data do
                	//arquivo e atualiza o seu banco de dados
                	}else{
                		dataMod = msg;
                    	//Pede os dados dos produtos para o servidor
                		OUT.flush();
                        OUT.writeObject("300");
                        msg = (String)IN.readObject();
                        //Verifica se o servidor confirmou o inicio do envio
                        if(msg.equals("200")){
                        	//Cria um novo repositorio de produtos
                        	RepositorioProduto repositorio = 
                        			new RepositorioProduto(CONTEXT);
                        	repositorio.deletar();
                        	//Recebe os produtos cadastrados no banco
                        	Produto p ;
    	                    while(!msg.equals("201")){
    	                    	msg = (String)IN.readObject();
    	                    	String prods[] = msg.split("::_::");
    	                    	p = new Produto(prods[0], Integer.parseInt(prods[1]),
    	                    			Float.parseFloat(prods[2]), prods[3]);
    	                    	//Insere os produtos no banco
    	                    	repositorio.insert(p);
    	                    	msg = (String)IN.readObject();
    	                    }//while
    	                    OUT.flush();
    	                    OUT.writeObject("000");
    	                    //Fecha conexao com o servidor
    	                    this.IN.close();
    	                    this.OUT.close();
    	                    this.SOCKET.close();
    	                    Persiste.gravar(ARQUIVO, msg, CONTEXT);
                        }//if
                	}
                //Caso nao exista, é criado um novo arquivo com a data atual
                //e adicionado os produtos no banco
                }else{
                	Persiste.gravar(ARQUIVO, msg, CONTEXT);
                	//Pede os dados dos produtos para o servidor
            		OUT.flush();
                    OUT.writeObject("300");
                    msg = (String)IN.readObject();
                    //Verifica se o servidor confirmou o inicio do envio
                    if(msg.equals("200")){
                    	//Cria um novo repositorio de produtos
                    	RepositorioProduto repositorio = 
                    			new RepositorioProduto(CONTEXT);
                    	repositorio.deletar();
                    	//Recebe os produtos cadastrados no banco
                    	Produto p ;
	                    while(!msg.equals("201")){
	                    	msg = (String)IN.readObject();
	                    	String prods[] = msg.split("::_::");
	                    	p = new Produto(prods[0], Integer.parseInt(prods[2]),
	                    			Float.parseFloat(prods[1]), prods[3]);
	                    	//Insere os produtos no banco
	                    	repositorio.insert(p);
	                    	msg = (String)IN.readObject();
	                    }//while
	                    OUT.flush();
	                    OUT.writeObject("000");
	                    //Fecha conexao com o servidor
	                    this.IN.close();
	                    this.OUT.close();
	                    this.SOCKET.close();
                    }//if
                }//else(Caso nao exista)
                //Caso tenha feito o login a variavel ok recebe o valor 1
            	//para confirmação na thread principal
            	ok = 1;
            	return null;
            }//if(Verifica se foi feito o login)
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
