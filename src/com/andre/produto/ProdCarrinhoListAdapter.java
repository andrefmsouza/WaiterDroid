package com.andre.produto;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andre.activitys.Carrinho;
import com.andre.activitys.Finalizar;
import com.andre.activitys.MenuProd;
import com.andre.waiterdroid.R;

//Lista de produtos mostrada na Activity Carrinho e na Activity Finalizar
public class ProdCarrinhoListAdapter extends BaseAdapter {
	private Context contexto;
	private List<Produto> produtos;
	
	public ProdCarrinhoListAdapter(Context contexto, List<Produto> produtos) {
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
		
		View view = inflater.inflate(R.layout.item_carrinho, null);
		
		TextView nome = (TextView) view.findViewById(R.id.tvProdNomeCarrinho);
		nome.setText(p.nome);
		
		TextView preco = (TextView) view.findViewById(R.id.tvProdPrecoCarrinho);
		preco.setText("Preco: R$"+String.valueOf(p.preco));
		
		TextView num = (TextView) view.findViewById(R.id.tvProdNumCarrinho);
		num.setText(String.valueOf(p.numero));
		
		TextView descricao = (TextView) view.findViewById(R.id.tvDescProdCarrinho);
		descricao.setText(p.descricao);
		
		TextView quantidade = (TextView) view.findViewById(R.id.tvQuantProdCarrinho);
		quantidade.setText("Quantidade: "+p.quantidade);
		MenuProd.total += p.preco * p.quantidade;
		if(MenuProd.aux == 1)
			Carrinho.atualizarPreco();
		else
			Finalizar.atualizarPreco();
		
		return view;
	}
}
