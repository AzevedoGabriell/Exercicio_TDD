import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import models.Conta;
import models.Fatura;

import static enums.TipoPagamento.*;
import static enums.StatusPagamento.*;

public class FaturaAVLTest {

	// Teste comum para todos os tipos de pagamento

	@Test
	public void testProcessarPagamentosDeTodosOsTiposComValorAbaixoDoLimiteInferior() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 0.00, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 0.00, BOLETO),
			new Conta("2", LocalDate.of(2023, 2, 20), 0.00, CARTAO_CREDITO),
			new Conta("3", LocalDate.of(2023, 2, 20), 0.00, TRANSFERENCIA_BANCARIA)
		);

		fatura.processarPagamentos(contas);

		assertEquals(IRRELEVANTE, fatura.getStatus());
	}

	// Testes de contas de Boleto

	@Test
	public void testProcessarPagamentosDoTipoBoletoComValorLimiteInferior() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 0.01, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("2", LocalDate.of(2023, 2, 20), 0.01, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComValorMediano() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1750, "Cliente B");

		List<Conta> contas = List.of(
				new Conta("2", LocalDate.of(2023, 2, 20), 1000, BOLETO),
				new Conta("2", LocalDate.of(2023, 2, 20), 320, BOLETO),
				new Conta("2", LocalDate.of(2023, 2, 20), 430, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComValorLimiteSuperior() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 5000.00, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 5000.00, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComValorAcimaDoLimiteSuperior() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 5000.00, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 5000.01, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PENDENTE, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComDataAnteriorAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente B");

		List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, 19), 1000.00, BOLETO),
				new Conta("2", LocalDate.of(2023, 2, 19), 1000.00, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComDataIgualAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 1000.00, BOLETO),
			new Conta("2", LocalDate.of(2023, 2, 20), 1000.00, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoBoletoComDataPosteriorAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente B");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 21), 1000.00, BOLETO),
			new Conta("2", LocalDate.of(2023, 2, 22), 1000.00, BOLETO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	// Testes de contas de Cartão de Crédito

	@Test
	public void testProcessarPagamentosDoTipoCartaoDeCreditoComDataAnteriorAoLimiteDoPagamento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente C");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 3), 1000.00, CARTAO_CREDITO),
			new Conta("2", LocalDate.of(2023, 2, 4), 1000.00, CARTAO_CREDITO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoCartaoDeCreditoComDataIgualAoLimiteDoPagamento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente C");

		List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, 5), 1000.00, CARTAO_CREDITO),
				new Conta("2", LocalDate.of(2023, 2, 5), 1000.00, CARTAO_CREDITO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoCartaoDeCreditoComDataPosteriorAoLimiteDoPagamento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente C");

		List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, 6), 1000.00, CARTAO_CREDITO),
				new Conta("2", LocalDate.of(2023, 2, 7), 1000.00, CARTAO_CREDITO)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PENDENTE, fatura.getStatus());
	}

	// Testes de contas de Transferência Bancária

	@Test
	public void testProcessarPagamentosDoTipoTransferenciaBancariaComDataAnteriorAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente D");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 18), 1000.00, TRANSFERENCIA_BANCARIA),
			new Conta("2", LocalDate.of(2023, 2, 19), 1000.00, TRANSFERENCIA_BANCARIA)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoTransferenciaBancariaComDataIgualAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente D");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 20), 1000.00, TRANSFERENCIA_BANCARIA),
			new Conta("2", LocalDate.of(2023, 2, 20), 1000.00, TRANSFERENCIA_BANCARIA)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PAGO, fatura.getStatus());
	}

	@Test
	public void testProcessarPagamentosDoTipoTransferenciaBancariaComDataPosteriorAoVencimento() {
		Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente D");

		List<Conta> contas = List.of(
			new Conta("1", LocalDate.of(2023, 2, 21), 1000.00, TRANSFERENCIA_BANCARIA),
			new Conta("2", LocalDate.of(2023, 2, 21), 1000.00, TRANSFERENCIA_BANCARIA)
		);

		fatura.processarPagamentos(contas);

		assertEquals(PENDENTE, fatura.getStatus());
	}
}