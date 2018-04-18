package com.example.suelliton.mcpd_android.model;


import java.util.ArrayList;
import java.util.List;

public class Propriedade {

	private String nome;

	private String localidade;

	private String cidade;

	private String pais;

	private long id;

	public Propriedade(){}


	public Long getId() { return id; }

	public void setId(long id) { this.id = id; }

	public String getNome() {
		return nome;
	}

	public void setNome(String nomePropriedade) {
		this.nome = nomePropriedade;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}
