package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static enums.StatusPagamento.*;
import static enums.TipoPagamento.*;

class FaturaTest {

	private List<Fatura> faturas;

	@BeforeEach
	void setUp() {
		this.faturas = List.of(
			new Fatura(LocalDate.of(2023, 2, 20), 1500, "João"),
			new Fatura(LocalDate.of(2023, 2, 20), 1500, "Maria"),
			new Fatura(LocalDate.of(2023, 2, 20), 1500, "José")
		);
	}

	@Test
	void getStatus() {
		assertEquals(PENDENTE, this.faturas.get(0).getStatus());
		assertEquals(PENDENTE, this.faturas.get(1).getStatus());
		assertEquals(PENDENTE, this.faturas.get(2).getStatus());
	}

	@Test
	void processarPagamentosFatura0() {
		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 500, BOLETO),
			new Conta("2", LocalDate.of(2023, 2, 20), 400, BOLETO),
			new Conta("3", LocalDate.of(2023, 2, 20), 600, BOLETO)
		);

		this.faturas.get(0).processarPagamentos(contas);

		assertEquals(PAGO, this.faturas.getFirst().getStatus());
	}

	@Test
	void processarPagamentosFatura1() {
		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 5), 700, CARTAO_CREDITO),
			new Conta("2", LocalDate.of(2023, 2, 17), 800, TRANSFERENCIA_BANCARIA)
		);

		this.faturas.get(1).processarPagamentos(contas);

		assertEquals(PAGO, this.faturas.get(1).getStatus());
	}

	@Test
	void processarPagamentosFatura2() {
		List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, 6), 700, CARTAO_CREDITO),
				new Conta("2", LocalDate.of(2023, 2, 17), 800, TRANSFERENCIA_BANCARIA)
		);

		this.faturas.get(2).processarPagamentos(contas);

		assertEquals(PENDENTE, this.faturas.get(2).getStatus());
	}
}