package com.andre.waiterdroid;

//Usada para criptografar/descriptografar uma String
public class Criptografa {
	
  public static String criptografa(String msg, String chave){
      int tamMsg=0, tamChave=0;
      tamMsg = msg.length();
      tamChave = chave.length();

      String aux="", saida="";
      int[] resultado= new int[tamMsg];

      for(int i=0, j; i<tamMsg; i++)
      {
          resultado[i] = msg.charAt(i);
          for(j=0; j<tamChave;j++)
          {
              resultado[i]+= chave.charAt(j);
          }
      }

      for(int i=0, x; i<tamMsg ;i++)
      {
          x = (int)(Math.random()*100)%90;
          if(x<32)
              x+=58;
          else
              if(x<58)
                  x+=32;
          resultado[i]+=x;
          aux+=resultado[i];
          aux+=(char)x;
      }

      saida = aux.charAt(0)+"";

      for(int i=0; ;i+=2)
      {
          if(i<aux.length()-2)
          {
              saida += aux.charAt(i+2);
              saida += aux.charAt(i+1);
          }
          else
          {
              if(i+1<aux.length())
                  saida += aux.charAt(i+1);
              break;
          }
      }

      return saida;
  }

  public static String descriptografa(String msg, String chave){
      int tamMsg=0, tamChave=0, i,j, fim;
      tamMsg = msg.length();
      tamChave = chave.length();

      String saida="", entrada, msgCifrada="";
      int[] resultado= new int [tamMsg];

      entrada=msg;
      msgCifrada = entrada.charAt(0)+"";

      for(i=0; ;i+=2)
      {
          if(i<entrada.length()-2)
          {
              msgCifrada += entrada.charAt(i+2);
              msgCifrada += entrada.charAt(i+1);
          }
          else
          {
              if(i+1<entrada.length())
                  msgCifrada += entrada.charAt(i+1);
              break;
          }
      }

      for(i=0, j=0; j<tamMsg; i++, j++)
      {
          resultado[i]=0;
          for(; j<tamMsg; j++)
          {

              if((int)msgCifrada.charAt(j) > 47 && (int)msgCifrada.charAt(j) < 58)
              {
                  resultado[i]*=10;
                  resultado[i]+=(int)msgCifrada.charAt(j)-48;
              }
              else
              {
                  resultado[i]-= (int)msgCifrada.charAt(j);
                  break;
              }
          }
      }
      fim = i;
      for(i=0; i < fim; i++)
      {
          for(j=0; j<tamChave;j++)
          {
              resultado[i]-= (int)chave.charAt(j);
          }
      }

      for(i=0; i<fim ;i++)
          saida+= (char)resultado[i];

      return saida;
  }
}

