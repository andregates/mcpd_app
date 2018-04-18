package com.example.suelliton.mcpd_android.model;


import java.util.Date;

public class CulturaPropriedade {

	private Date dataAssociacao;

	private Date dataInativacao;

	private Propriedade propriedade;

	private Cultura cultura;

    public CulturaPropriedade(){}

    public CulturaPropriedade(Date dataAssociacao, Date dataInativacao, Propriedade propriedade, Cultura cultura) {
        this.dataAssociacao = dataAssociacao;
        this.dataInativacao = dataInativacao;
        this.propriedade = propriedade;
        this.cultura = cultura;
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

	public Propriedade getPropriedade() {
		return propriedade;
	}

	public void setPropriedade(Propriedade propriedade) {
		this.propriedade = propriedade;
	}

	public Cultura getCultura() {
		return cultura;
	}

	public void setCultura(Cultura cultura) {
		this.cultura = cultura;
	}
}
