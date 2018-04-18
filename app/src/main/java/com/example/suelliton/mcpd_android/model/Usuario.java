package com.example.suelliton.mcpd_android.model;

public class Usuario {

	private long id;
	private String nomeUsuario;
	private String cpf;


	public Usuario(){}

	public Usuario(long id, String nomeUsuario, String cpf) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.cpf = cpf;

	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Long getId() {
		return id;
	}


}
