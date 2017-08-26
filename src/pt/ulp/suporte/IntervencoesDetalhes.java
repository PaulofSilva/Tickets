package pt.ulp.suporte;

import pt.ulp.suporte.db.DbAdapter;
import pt.ulp.suporte.model.Intervencao;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class IntervencoesDetalhes extends Activity {

	// Criar variáveis para aceder aos elementos da interface
	private int idIntervencao;
	private DbAdapter datasource;
	private Intervencao intervencao;
	private EditText edtNome;
	private EditText edtEmail;
	private EditText edtTelefone;
	private EditText edtPedido;
	private EditText edtData;
	private EditText edtHorai;
	private RadioGroup tipoServico;
	private RadioButton interno;
	private RadioButton externo;
	private Button btCancelar;
	private Button btGravar;
	private Button btEliminar;
	private String tipo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intervencao_detalhe);

		findAllViewsById();

		carregaDetalhesContacto();
		
		interno.setOnClickListener(radioButtonListener);
		externo.setOnClickListener(radioButtonListener);

		int id = tipoServico.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById(id);

		tipo = (String) radioButton.getText();

		// acção do botão Alterar
		btGravar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				datasource.open();
				// Log.i("Alterar Intervenção", "entrou no evento");

				Intervencao i = datasource.alterarIntervencao(
						edtNome.getText().toString(), 
						edtEmail.getText().toString(), 
						edtTelefone.getText().toString(), 
						edtHorai.getText().toString(),
						null, 
						tipo, 
						edtPedido.getText().toString(), 
						null, 
						edtData.getText().toString(),
						idIntervencao);

				datasource.close();

				AlertDialog.Builder dialogo = new AlertDialog.Builder(
						IntervencoesDetalhes.this);
				dialogo.setTitle("Aviso");
				dialogo.setMessage("Intervenção:" + i.getNome());
				dialogo.setNeutralButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});
				dialogo.show();
			}
		});

		// acção dos botões da interface
		btCancelar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btEliminar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialogo = new AlertDialog.Builder(
						IntervencoesDetalhes.this);
				dialogo.setTitle("Aviso");
				dialogo.setMessage("Eliminar Intervenção?");
				dialogo.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
							}
						});
				dialogo.setPositiveButton("Eliminar",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								datasource.open();
								datasource.EliminaIntervencao(idIntervencao);
								datasource.close();
								finish();
							}
						});
				dialogo.show();
			}
		});
		

	}

	// criar a ligação entre as variáveis da classe e os elementos da interface
	private void findAllViewsById() {

		
		edtNome = (EditText) findViewById(R.detalheintervencao.ednome);
		edtEmail = (EditText) findViewById(R.detalheintervencao.edEmail);
		edtTelefone = (EditText) findViewById(R.detalheintervencao.edTelefone);
		edtPedido = (EditText) findViewById(R.detalheintervencao.edPedido);
		edtData = (EditText) findViewById(R.detalheintervencao.edData);
		edtHorai = (EditText) findViewById(R.detalheintervencao.edHorai);		
		btGravar = (Button) findViewById(R.detalheintervencao.btGravar);
		btCancelar = (Button) findViewById(R.detalheintervencao.btCancelar);
		btEliminar = (Button) findViewById(R.detalheintervencao.btEliminar);

		interno = (RadioButton) findViewById(R.detalheintervencao.interno);
		externo = (RadioButton) findViewById(R.detalheintervencao.externo);
		tipoServico = (RadioGroup) findViewById(R.detalheintervencao.tipoServico);
	}
	
	private OnClickListener radioButtonListener = new OnClickListener() {
		public void onClick(View v) {
			RadioButton radioButton = (RadioButton) v;
			tipo = (String) radioButton.getText();
			//longToast(tipo);
		}
	};


	// função que irá colocar nos elementos da interface os detalhes da intervencao
	private void carregaDetalhesContacto() {
		idIntervencao = getIntent().getIntExtra("idIntervencao", 0);

		datasource = new DbAdapter(this);
		datasource.open();
		intervencao = datasource.getIntervencao(idIntervencao);
		datasource.close();

		edtNome.setText(intervencao.getNome());
		edtEmail.setText(intervencao.getEmail());
		edtTelefone.setText(intervencao.getTelefone());
		edtPedido.setText(intervencao.getPedido());
		edtData.setText(intervencao.getData());
		edtHorai.setText(intervencao.getHorai());
	}

}
