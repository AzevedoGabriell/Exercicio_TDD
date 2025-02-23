package models;

import enums.StatusPagamento;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static enums.StatusPagamento.*;

class Fatura {

	private LocalDate data;
	private double valor;
	private String cliente;
	private StatusPagamento status;
	private List<Conta> pagamentos;

	public Fatura(LocalDate data, double valor, String cliente) {
		this.data = data;
		this.valor = valor;
		this.cliente = cliente;
		this.status = PENDENTE;
		this.pagamentos = new ArrayList<>();
	}

	public StatusPagamento getStatus() {
		return this.status;
	}

	public void processarPagamentos(List<Conta> contas) {
		double valorPago = 0;

		for (Conta conta : contas) {
			switch (conta.getTipo()) {
				case BOLETO -> {
					if (conta.getValor() >= 0.01 && conta.getValor() <= 5000.00) {
						if (conta.getData().isAfter(this.data)) {
							valorPago += conta.getValor() * 1.1;
						} else {
							valorPago += conta.getValor();
						}
					}
				}
				case CARTAO_CREDITO -> {
					if (conta.getData().plusDays(14).isBefore(this.data)) {
						valorPago += conta.getValor();
					}
				}
				case TRANSFERENCIA_BANCARIA -> {
					if (!conta.getData().isAfter(this.data)) {
						valorPago += conta.getValor();
					}
				}
			}

			this.pagamentos.add(conta);
		}

		if (valorPago >= this.valor) {
			this.status = PAGO;
		}
	}
}
