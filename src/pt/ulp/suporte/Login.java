package pt.ulp.suporte;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import pt.ulp.suporte.db.ConexaoHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	EditText etNome, etSenha;
	Button btnEntrar;
	ProgressDialog dialogo;

	String urlPost = "http://192.168.1.12:8085/bSuporte/login.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		etNome = (EditText) findViewById(R.id.edtNome);
		etSenha = (EditText) findViewById(R.id.edtSenha);
		btnEntrar = (Button) findViewById(R.id.btnOk);

	}

	public void clickEntrar(View v) {
		TecnicoAsync async = new TecnicoAsync();
		String valor;
		try {
			valor = async.execute("").get();
			Toast.makeText(this, valor, Toast.LENGTH_LONG).show();
		} catch (InterruptedException e) {
			Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
		} catch (ExecutionException e) {
			Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
		}
	}

	private class TecnicoAsync extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// String texto = arg0[0];
			ArrayList<NameValuePair> parametrosPost = new ArrayList<NameValuePair>();

			parametrosPost.add(new BasicNameValuePair("nome", etNome.getText()
					.toString()));
			parametrosPost.add(new BasicNameValuePair("senha", etSenha
					.getText().toString()));

			ConexaoHttpClient con = new ConexaoHttpClient();
			String conteudo = "";
			try {
				conteudo = ConexaoHttpClient.executaHttpPost(urlPost,
						parametrosPost);
			} catch (Exception e) {
				Toast.makeText(Login.this, "Erro.: " + e.getMessage(),
						Toast.LENGTH_LONG).show();
			}

			return conteudo.toString();
		}

	}
}
