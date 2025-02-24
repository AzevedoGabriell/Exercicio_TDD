package models;

import enums.StatusPagamento;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static enums.StatusPagamento.*;

public class Fatura {

	private LocalDate data;
	private double valor;
	private String cliente;
	private StatusPagamento status;
	private List<Conta> pagamentos;

	public Fatura(LocalDate data, double valor, String cliente) {
		this.data = data;
		this.valor = valor >= 0 ? valor : 0;
		this.cliente = cliente;
		this.status = valor >= 0.01 ? PENDENTE : IRRELEVANTE;
		this.pagamentos = new ArrayList<>();
	}

	public StatusPagamento getStatus() {
		return this.status;
	}

	public void processarPagamentos(List<Conta> contas) {
		if (this.getStatus().equals(PENDENTE)) {
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

				if (conta.getValor() >= 0.01) {
					this.pagamentos.add(conta);
				}
			}

			if (!this.pagamentos.isEmpty() && valorPago >= this.valor) {
				this.status = PAGO;
			}
		}
	}
}
