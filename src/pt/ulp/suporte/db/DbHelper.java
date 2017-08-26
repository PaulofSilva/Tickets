package pt.ulp.suporte.db;

//Adicionar os imports necessários para a classe:
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*Esta classe será responsável por criar a base de dados no momento em que a 
 * aplicação é instalada (método onCreate) e actualizá-la para novas 
 * versões(método onUpgrade).
 */

// A classe DB expande a classe SQLiteOpenHelper, esta fornece os métodos
public class DbHelper extends SQLiteOpenHelper {

	// Adicionar as constantes necessárias
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "intervencoes";
	private static final String DATABASE_NAME = "bsuporte2.db";

	public static final String ID = "_id", NOME = "nome", EMAIL = "email",
			TELEFONE = "telefone", HORAI = "horai", HORAF = "horaf",
			TIPO = "tipo", PEDIDO = "pedido", RELATORIO = "relatorio", DATA = "data";

	private static final String DATABASE_CREATE = "create table " + TABLE_NAME
			+ "( " + ID + " integer primary key autoincrement, " + NOME
			+ " text not null, " + EMAIL + " text, " + TELEFONE + " text,"
			+ HORAI + " text, " + HORAF + " text, " + TIPO + " text, " + PEDIDO
			+ " text, " + RELATORIO + " text,"+ DATA + " text );";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Fazer @override das classes necessárias:
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}

}
