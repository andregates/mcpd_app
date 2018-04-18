package com.example.suelliton.mcpd_android.model;


import java.util.Date;

public class PragaCultura {

	private Date dataAssociacao;

	private Date dataInativacao;

	private Cultura cultura;

	private Praga praga;

	public PragaCultura(){}

	public PragaCultura(Date dataAssociacao, Date dataInativacao, Cultura cultura, Praga praga) {
		this.dataAssociacao = dataAssociacao;
		this.dataInativacao = dataInativacao;
		this.cultura = cultura;
		this.praga = praga;
	}

	public Date getDataAssociacao() {
		return dataAssociacao;
	}

	public void setDataAssociacao(Date dataAssociacao) {
		this.dataAssociacao = dataAssociacao;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}

	public Praga getPraga() {
		return praga;
	}

	public void setPraga(Praga praga) {
		this.praga = praga;
	}
}
