package com.andre.produto;

import java.util.List;

import com.andre.waiterdroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//Gera lista de produtos mostrados no menu de produtos
public class ProdutoListAdapter extends BaseAdapter {

	private Context contexto;
	private List<Produto> produtos;
	
	public ProdutoListAdapter(Context contexto, List<Produto> produtos) {
		super();
		this.contexto = contexto;
		this.produtos = produtos;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return produtos.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return produtos.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Produto p = produtos.get(position);
		
		LayoutInflater inflater = (LayoutInflater) 
				contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View view = inflater.inflate(R.layout.item_produto, null);
		
		TextView nome = (TextView) view.findViewById(R.id.tvProdNome);
		nome.setText(p.nome);
		
		TextView preco = (TextView) view.findViewById(R.id.tvProdPreco);
		preco.setText("Preco: R$"+String.valueOf(p.preco));
		
		TextView num = (TextView) view.findViewById(R.id.tvProdNum);
		num.setText(String.valueOf(p.numero));
		
		
		return view;
	}
}
