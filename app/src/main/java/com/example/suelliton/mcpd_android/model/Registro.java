package com.example.suelliton.mcpd_android.model;


import java.util.Date;

public class Registro {

	private long id;
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String dataRegistro;

	private double latitude;

	private double longitude;

	private int escala;

	private boolean tratamento;

	private String dataTratamento;

	private String tipo;

	private String obs;

	private int usuarioId;

	private int culturaId;

	private int pragaId;

	public Registro(){}

	public Registro(String dataRegistro, double latitude, double longitude, int escala,
					boolean tratamento, String dataTratamento, String tipo, String obs,
					int usuarioId, int culturaId, int pragaId) {
		this.dataRegistro = dataRegistro;
		this.latitude = latitude;
		this.longitude = longitude;
		this.escala = escala;
		this.tratamento = tratamento;
		this.dataTratamento = dataTratamento;
		this.tipo = tipo;
		this.obs = obs;
		this.usuarioId = usuarioId;
		this.culturaId = culturaId;
		this.pragaId = pragaId;
		this.id=0;
	}

	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

	public boolean isTratamento() {
		return tratamento;
	}

	public void setTratamento(boolean tratamento) {
		this.tratamento = tratamento;
	}

	public String getDataTratamento() {
		return dataTratamento;
	}

	public void setDataTratamento(String dataTratamento) {
		this.dataTratamento = dataTratamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public int getUsuarioId() {	return usuarioId; }

	public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

	public int getCulturaId() { return culturaId; }

	public void setCulturaId(int culturaId) { this.culturaId = culturaId; }

	public int getPragaId() { return pragaId; }

	public void setPragaId(int pragaId) { this.pragaId = pragaId; }


	public Long getId() { return id; }

	@Override
	public String toString() {
		return "Registro{" +
				"id=" + id +
				", dataRegistro='" + dataRegistro + '\'' +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", escala=" + escala +
				", tratamento=" + tratamento +
				", dataTratamento='" + dataTratamento + '\'' +
				", tipo='" + tipo + '\'' +
				", obs='" + obs + '\'' +
				", usuarioId=" + usuarioId +
				", culturaId=" + culturaId +
				", pragaId=" + pragaId +
				'}';
	}
}
