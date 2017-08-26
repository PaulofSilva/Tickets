package pt.ulp.suporte;


import pt.ulp.suporte.db.DbAdapter;
import pt.ulp.suporte.model.Intervencao;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class IntervencaoNova extends Activity {

	// Criar variáveis para aceder aos elementos da interface Nova Intervencao
	private ImageButton btGravar;
	private ImageButton btCancel;
	private EditText edtNome;
	private EditText edtEmail;
	private EditText edtTelefone;
	private EditText edtPedido;
	private EditText edtData;
	private EditText edtHorai;	
	private RadioGroup tipoServico;
	private RadioButton interno;
	private RadioButton externo;
	private String tipo;

	private DbAdapter datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intervencao_nova);

		// criar a ligação entre as variáveis e os elementos da interface
		datasource = new DbAdapter(this);

		findAllViewsById();
		
		interno.setOnClickListener(radioButtonListener);
		externo.setOnClickListener(radioButtonListener);

		int id = tipoServico.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById(id);

		tipo = (String) radioButton.getText();

		// acção do botão Adicionar
		btGravar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				datasource.open();
				// Log.i("criar Intervenção", "entrou no evento");

				Intervencao i = datasource.criarIntervencao(
						edtNome.getText().toString(), 
						edtEmail.getText().toString(), 
						edtTelefone.getText().toString(), 
						edtHorai.getText().toString(),
						null, 
						tipo, 
						edtPedido.getText().toString(), 
						null, 
						edtData.getText().toString());

				datasource.close();

				AlertDialog.Builder dialogo = new AlertDialog.Builder(
						IntervencaoNova.this);
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

		btCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//longToast(tipo);
				finish();
			}
		});

	}

	private void findAllViewsById() {
		edtNome = (EditText) findViewById(R.novaintervencao.ednome);
		edtEmail = (EditText) findViewById(R.novaintervencao.edEmail);
		edtTelefone = (EditText) findViewById(R.novaintervencao.edTelefone);
		edtPedido = (EditText) findViewById(R.novaintervencao.edPedido);
		edtData = (EditText) findViewById(R.novaintervencao.edData);
		edtHorai = (EditText) findViewById(R.novaintervencao.edHorai);		
		btGravar = (ImageButton) findViewById(R.novaintervencao.btGravar);
		btCancel = (ImageButton) findViewById(R.novaintervencao.btCancelar);

		interno = (RadioButton) findViewById(R.novaintervencao.interno);
		externo = (RadioButton) findViewById(R.novaintervencao.externo);
		tipoServico = (RadioGroup) findViewById(R.novaintervencao.tipoServico);
	}

	private OnClickListener radioButtonListener = new OnClickListener() {
		public void onClick(View v) {
			RadioButton radioButton = (RadioButton) v;
			tipo = (String) radioButton.getText();
			//longToast(tipo);
		}
	};

	public void longToast(CharSequence message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
