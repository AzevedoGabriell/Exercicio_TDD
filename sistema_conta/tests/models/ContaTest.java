package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import java.util.List;

import enums.TipoPagamento;

class ContaTest {

	private List<Conta> contas;

	@BeforeEach
	void setUp() {
		this.contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 500, TipoPagamento.BOLETO),
			new Conta("2", LocalDate.of(2025, 10, 14), 2435.99, TipoPagamento.TRANSFERENCIA_BANCARIA),
			new Conta("3", LocalDate.of(2024, 3, 10), 683.74, TipoPagamento.CARTAO_CREDITO)
		);
	}

	@Test
	void getData() {
		assertEquals(LocalDate.of(2023, 2, 20), this.contas.get(0).getData());
		assertEquals(LocalDate.of(2025, 10, 14), this.contas.get(1).getData());
		assertEquals(LocalDate.of(2024, 3, 10), this.contas.get(2).getData());
	}

	@Test
	void getValor() {
		assertEquals(500, this.contas.get(0).getValor());
		assertEquals(2435.99, this.contas.get(1).getValor());
		assertEquals(683.74, this.contas.get(2).getValor());
	}

	@Test
	void getTipo() {
		assertEquals(TipoPagamento.BOLETO, this.contas.get(0).getTipo());
		assertEquals(TipoPagamento.TRANSFERENCIA_BANCARIA, this.contas.get(1).getTipo());
		assertEquals(TipoPagamento.CARTAO_CREDITO, this.contas.get(2).getTipo());
	}
}