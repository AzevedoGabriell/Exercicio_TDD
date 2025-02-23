package models;

import java.time.LocalDate;

import enums.TipoPagamento;

public class Conta {

	private String codigo;
	private LocalDate data;
	private double valor;
	private TipoPagamento tipo;

	public Conta(String codigo, LocalDate data, double valor, TipoPagamento tipo) {
		this.codigo = codigo;
		this.data = data;
		this.valor = valor;
		this.tipo = tipo;
	}

	public LocalDate getData() {
		return this.data;
	}

	public double getValor() {
		return this.valor;
	}

	public TipoPagamento getTipo() {
		return this.tipo;
	}
}