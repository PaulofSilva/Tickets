package pt.ulp.suporte;


import pt.ulp.suporte.db.DbAdapter;
import pt.ulp.suporte.model.Intervencao;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class IntervencoesMain extends ListActivity {

	// Criar variáveis para aceder aos elementos da interface
	ListAdapter adapter; // permite mostrar várias informações numa linha do ListView.
	DbAdapter datasource; // permite fazer operações na base de dados.
	Button btNovaIntervencao;

	int idIntervencao;
	Intervencao intervencao;

	private static final int nova=0, editar=1, sincronizar=2, parametros=3; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		datasource = new DbAdapter(this);
		datasource.open();
		Cursor cursor = datasource.getIntervencoes();
		
		String[] columns = new String[] { "nome", "telefone", "data", "horai", "tipo" };
		int[] to = new int[] { R.data_row.txtNome, R.data_row.txtTelefone, 
				R.data_row.txtData, R.data_row.txtHora, R.data_row.txtTipo };


		// listar os contactos existentes na base de dados
		adapter = new SimpleCursorAdapter(this, R.layout.intervencao_data_row,
				cursor, columns, to);
		this.setListAdapter(adapter);
		datasource.close();

	}

	// Criar menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		boolean result = super.onCreateOptionsMenu(menu);
    	super.onCreateOptionsMenu(menu);
    			
    	
    	menu.add(0, nova, 0, "Nova Intervenção").setIcon(R.drawable.add);
    	menu.add(0, editar, 0, "Editar Intervenção").setIcon(R.drawable.search);
				
    	
    	SubMenu utilitario = menu.addSubMenu("Utilitarios");
    	
    	utilitario.add(0, sincronizar, 0, "Sincronizar ").setIcon(R.drawable.search);
    	utilitario.add(0, parametros, 0, "Parametros").setIcon(R.drawable.settings);
		

    	return result;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case nova: 		// passar para o ecrã IntervencaoNova
			Intent novo = new Intent(".NovaIntervencao");
			startActivity(novo);
			return true;
		case editar:
			int position = getSelectedItemPosition();
			if (position==-1) {
				position = 0;
			}			
			Intent intent = new Intent(".IntervencoesDetalhes");
			Cursor cursor = (Cursor) adapter.getItem(position);
			intent.putExtra("idIntervencao",
					cursor.getInt(cursor.getColumnIndex("_id")));
			startActivity(intent);
			return true;
	    case sincronizar: 
	    	mensagemExibir("Sincronizar", "sincronizar");
	    	break;
	    case parametros: 
	    	mensagemExibir("Parametros", "parametros");
	    	break;

		}
		return false;
	}

	// Este metodo permite que no final de adicionarmos a intervenção esta já
	// seja apresentada na lista:
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		datasource.open();
		Cursor cursor = datasource.getIntervencoes();
		String[] columns = new String[] { "nome", "telefone", "data", "horai" };
		int[] to = new int[] { R.data_row.txtNome, R.data_row.txtTelefone, 
				R.data_row.txtData, R.data_row.txtHora };
		adapter = new SimpleCursorAdapter(this, R.layout.intervencao_data_row,
				cursor, columns, to);
		this.setListAdapter(adapter);
		datasource.close();
	}

	// método onListItemClick, método que é chamado sempre que é clicado num  item da lista
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Cursor cursor = (Cursor) adapter.getItem(position);
		idIntervencao = cursor.getInt(cursor.getColumnIndex("_id"));
		carregaDetalhesIntervencao(idIntervencao);
	}

	// função que irá colocar nos elementos da interface os detalhes da intervenção
	private void carregaDetalhesIntervencao(int idIntervencao) {
		datasource = new DbAdapter(this);
		datasource.open();
		intervencao = datasource.getIntervencao(idIntervencao);
		datasource.close();
		
		String nome = intervencao.getNome();
		String email = intervencao.getEmail();
		String telefone = intervencao.getTelefone();
		String pedido = intervencao.getPedido();
		String data = intervencao.getData();
		String horai = intervencao.getHorai();

		mostraIntervencoesOverview("Detalhe da Intervenção", nome, email,
				telefone, pedido, data, horai);
	}

	private void mostraIntervencoesOverview(final String title,
			final String nome, final String email, final String telefone,
			final String pedido, final String data, final String horai) {

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.intervencao_overview);

		dialog.setTitle(title);

		final TextView edtNome = (TextView) dialog.findViewById(R.overview.txtNome);
		edtNome.setText(nome);

		final TextView edtEmail = (TextView) dialog.findViewById(R.overview.txtEmail);
		edtEmail.setText(email);

		final TextView edtTelefone = (TextView) dialog.findViewById(R.overview.txtTelefone);
		edtTelefone.setText(telefone);

		/*final TextView edtPedido = (TextView) dialog.findViewById(R.overview.txtPedido);
		edtTelefone.setText(pedido);*/
		
		final TextView edtData = (TextView) dialog.findViewById(R.overview.txtData);
		edtTelefone.setText(data);
		
		final TextView edtHora = (TextView) dialog.findViewById(R.overview.txtHora);
		edtTelefone.setText(horai);		


		final Button btSair = (Button) dialog.findViewById(R.overview.btSair);

		btSair.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}
	
    public void mensagemExibir(String titulo, String texto)
    {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(IntervencoesMain.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK",null);
		mensagem.show();

    }
}
