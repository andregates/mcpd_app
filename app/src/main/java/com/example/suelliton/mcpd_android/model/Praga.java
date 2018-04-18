package com.example.suelliton.mcpd_android.model;


import java.util.ArrayList;
import java.util.List;

public class Praga  {

	private long id;

	private String nome;

	private String escala1;

	private String escala2;

	private String escala3;

	private String escala4;

	private String escala5;

	private int idCultura;

	public Praga(long id, String nome, String escala1, String escala2, String escala3, String escala4, String escala5, int idCultura) {
		this.id = id;
		this.nome = nome;
		this.escala1 = escala1;
		this.escala2 = escala2;
		this.escala3 = escala3;
		this.escala4 = escala4;
		this.escala5 = escala5;
		this.idCultura = idCultura;
	}

	public Praga(){

	}

	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEscala1() {
		return escala1;
	}

	public void setEscala1(String escala1) {
		this.escala1 = escala1;
	}

	public String getEscala2() {
		return escala2;
	}

	public void setEscala2(String escala2) {
		this.escala2 = escala2;
	}

	public String getEscala3() {
		return escala3;
	}

	public void setEscala3(String escala3) {
		this.escala3 = escala3;
	}

	public String getEscala4() {
		return escala4;
	}

	public void setEscala4(String escala4) {
		this.escala4 = escala4;
	}

	public String getEscala5() {
		return escala5;
	}

	public void setEscala5(String escala5) {
		this.escala5 = escala5;
	}

	public int getIdCultura() {
		return idCultura;
	}

	public void setIdCultura(int idCultura) {
		this.idCultura = idCultura;
	}
}
