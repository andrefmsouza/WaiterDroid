/*
 * Classe que abre e grava o arquivo de administrador
 */

package com.andre.waiterdroid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.content.Context;

public class Persiste implements Serializable {

	/**
	 * Método que checa a existência de um arquivo.
	 * @param String, Context
	 * @return true || false
	 */
	public static boolean abrir(String arq, Context context)
	{

			File f = context.getFilesDir();
	        File arquivo = new File(f +"/"+arq);
            if(arquivo.exists())
            	return true;
            else
            	return false;
	}

	/**
	 * Método que grava uma String no arquivo indicado.
	 * Retorna true em caso positivo, e false em caso negativo.
	 * @param String, String, Context
	 * @return true || false
	 */
	public static boolean gravar(String arq, String data, Context context)
	{
		try {
			FileOutputStream out = context.openFileOutput(arq, 0);
			out.write(data.getBytes());
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Método que lê uma String no arquivo indicado.
	 * Retorna a String da primeira linha em caso positivo, e null em caso negativo.
	 * @param String, Context
	 * @return String || null
	 */
	public static String ler(String arq, Context context)
	{
		try {
            File file = context.getFilesDir();
            File textFile = new File(file +"/"+arq);
            
            FileInputStream input = context.openFileInput(arq);
            
            byte[] buffer = new byte[(int)textFile.length()];
            
            input.read(buffer);
            
			return new String(buffer);
		} catch (IOException e) {
			return null;
		}
	}
}