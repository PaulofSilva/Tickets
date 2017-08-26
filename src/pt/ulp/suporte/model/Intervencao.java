package pt.ulp.suporte.model;

public class Intervencao {
	private long _id;
	private String nome;
	private String email;
	private String telefone;
	private String horai;
	private String horaf;
	private String tipo = "";
	private String data = "";
	private String pedido = "";
	private String relatorio = "";


	public Intervencao(long id, String nome, String email, String telefone,
			String horai, String tipo, String pedido, String data) {
		this._id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.horai = horai;
		this.tipo = tipo;
		this.pedido = pedido;
		this.data = data;
	}

	public Intervencao(long id, String nome, String email, String telefone,
			String horai, String horaf, String tipo, String pedido,
			String relatorio, String data) {
		this._id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.horai = horai;
		this.horaf = horaf;
		this.tipo = tipo;
		this.pedido = pedido;
		this.relatorio = relatorio;
		this.data = data;

	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorai() {
		return horai;
	}

	public void setHorai(String horai) {
		this.horai = horai;
	}

	public String getHoraf() {
		return horaf;
	}

	public void setHoraf(String horaf) {
		this.horaf = horaf;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

}
