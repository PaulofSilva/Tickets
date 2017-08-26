package pt.ulp.suporte.db;

//Adicionar os imports necessários para a aplicação:
import pt.ulp.suporte.model.Intervencao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/*Criar nova classe DbAdapter que será a classe base para trabalhar com SQLite. 
 * Esta irá permitir  abrir, fechar, fazer queries e actualizar a base de dados.*/

public class DbAdapter {
	// Adicionar os Atributos necessários para a classe:
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = { DbHelper.ID, DbHelper.NOME, DbHelper.EMAIL,
			DbHelper.TELEFONE, DbHelper.HORAI, DbHelper.HORAF, DbHelper.TIPO,
			DbHelper.PEDIDO, DbHelper.RELATORIO, DbHelper.DATA};

	// Declarar construtor da classe que irá inicializar uma instância da classe
	// DbHelper.
	public DbAdapter(Context context) {
		dbHelper = new DbHelper(context);
		/*
		 * Um Context é um handle para o sistema, que fornece serviços como
		 * resources, acesso a base de dados e preferências. O contexto da
		 * aplicação (application context) é o repositório central para a
		 * funcionalidade de todas as aplicações de nível superior no Android.
		 * Usa-se este contexto quando queremos aceder a configurações e
		 * recursos compartilhados entre as várias janelas (activities) da
		 * aplicação.
		 */
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/* Criar método para criar uma nova intervencao na base de dados, este irá
	 * retornar um objecto Intervencao.
	 */
	public Intervencao criarIntervencao(String nome, String email,
			String telefone, String horai, String horaf, String tipo,
			String pedido, String relatorio, String data) 
	{
		ContentValues values = new ContentValues();
		values.put(DbHelper.NOME, nome);
		values.put(DbHelper.EMAIL, email);
		values.put(DbHelper.TELEFONE, telefone);
		values.put(DbHelper.HORAI, horai);
		values.put(DbHelper.HORAF, horaf);
		values.put(DbHelper.TIPO, tipo);
		values.put(DbHelper.PEDIDO, pedido);
		values.put(DbHelper.RELATORIO, relatorio);
		values.put(DbHelper.DATA, data);


		long insertId = database.insert(DbHelper.TABLE_NAME, null, values);

		
		Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns,
				DbHelper.ID + " = " + insertId, null, null, null, null);

		cursor.moveToFirst();
		/*
		 * Os cursores são “apontadores de dados” da base de dados – ou seja,
		 * uma interface que permite o acesso aos dados retornados pela query. O
		 * objecto ContentValues permite definir os valores a inserir.
		 */
		return cursorToIntervencao(cursor);
	}
	
	/* Criar método para criar uma nova intervencao na base de dados, este irá
	 * retornar um objecto Intervencao.
	 */
	public Intervencao alterarIntervencao(String nome, String email,
			String telefone, String horai, String horaf, String tipo,
			String pedido, String relatorio, String data, int id) 
	{
		ContentValues values = new ContentValues();
		values.put(DbHelper.NOME, nome);
		values.put(DbHelper.EMAIL, email);
		values.put(DbHelper.TELEFONE, telefone);
		values.put(DbHelper.HORAI, horai);
		values.put(DbHelper.HORAF, horaf);
		values.put(DbHelper.TIPO, tipo);
		values.put(DbHelper.PEDIDO, pedido);
		values.put(DbHelper.RELATORIO, relatorio);
		values.put(DbHelper.DATA, data);

		String where = "_id" + "=" + Integer.toString(id);

		
		long updateId = database.update(DbHelper.TABLE_NAME, values, where, null);

		
		Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns,
				DbHelper.ID + " = " + updateId, null, null, null, null);
		
		cursor.moveToFirst();
		/*
		 * Os cursores são “apontadores de dados” da base de dados – ou seja,
		 * uma interface que permite o acesso aos dados retornados pela query. O
		 * objecto ContentValues permite definir os valores a inserir.
		 */
		return cursorToIntervencao(cursor);
		
	}

	// Método para eliminar um Intervencao:
	public void EliminaIntervencao(int idIntervencao) {
		database.delete(DbHelper.TABLE_NAME, DbHelper.ID + " = "
				+ idIntervencao, null);
	}

	// Método para devolver uma Intervencao passando como parâmetro um cursor.
	private Intervencao cursorToIntervencao(Cursor cursor) {

		Intervencao intervencao = new Intervencao(
				cursor.getLong(cursor.getColumnIndex("_id")),
				cursor.getString(cursor.getColumnIndex("nome")), 
				cursor.getString(cursor.getColumnIndex("email")), 
				cursor.getString(cursor.getColumnIndex("telefone")),
				cursor.getString(cursor.getColumnIndex("horai")), 
				cursor.getString(cursor.getColumnIndex("tipo")), 
				cursor.getString(cursor.getColumnIndex("pedido")),
				cursor.getString(cursor.getColumnIndex("data")));

		return intervencao;
	}

	// Método para devolver todos os intervencões da tabela:
	public Cursor getIntervencoes() {
		Cursor cursor = database
				.rawQuery(
						"select _id, nome, telefone, horai, horaf, tipo, pedido, relatorio, data from intervencoes",
						null);

		return cursor;
	}

	// Método que devolve a intervencao passando como parâmetro o id do
	// intervencao pretendido
	public Intervencao getIntervencao(int idIntervencao) {
		Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns,
				DbHelper.ID + " = " + idIntervencao, null, null, null, null);
		cursor.moveToFirst();
		return cursorToIntervencao(cursor);
	}

}
