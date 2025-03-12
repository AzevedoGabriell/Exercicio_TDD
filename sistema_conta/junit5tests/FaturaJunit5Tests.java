import enums.StatusPagamento;
import enums.TipoPagamento;
import models.Conta;
import models.Fatura;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static enums.StatusPagamento.*;
import static enums.TipoPagamento.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaturaJunit5Tests {

	@Nested
	@DisplayName("Testes entre todos os meios de pagamento")
	class AllPaymentsMethodsTest {

		@ParameterizedTest
		@ValueSource(strings = {"BOLETO", "CARTAO_CREDITO", "TRANSFERENCIA_BANCARIA"})
		@DisplayName("Status IRRELEVANTE para pagamentos com valor abaixo do limite inferior")
		public void testProcessarPagamentosDeTodosOsTiposComValorAbaixoDoLimiteInferior(String tipoPagamento) {
			Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 0.00, "Cliente B");

			fatura.processarPagamentos(
				List.of(new Conta(tipoPagamento, LocalDate.of(2023, 2, 20), 0.00, TipoPagamento.valueOf(tipoPagamento)))
			);

			assertEquals(IRRELEVANTE, fatura.getStatus());
		}
	}

	@Nested
	@DisplayName("Testes de contas de Boleto")
	class BoletoPaymentsTest {

		@ParameterizedTest
		@CsvSource({
			"0.01, PAGO",
			"1750, PAGO",
			"5000, PAGO",
			"5001, PENDENTE"
		})
		@DisplayName("Status PAGO ou PENDENTE para pagamentos com valores limites e intermediários")
		public void testProcessarPagamentosDoTipoBoletoComValorVariavel(double valor, String statusPagamento) {
			Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), valor, "Cliente B");

			List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, 20), valor, BOLETO)
			);

			fatura.processarPagamentos(contas);

			assertEquals(StatusPagamento.valueOf(statusPagamento), fatura.getStatus());
		}

		@ParameterizedTest
		@ValueSource(ints = {18, 19, 20, 21, 22})
		@DisplayName("Status PAGO para pagamentos com data anterior, igual e posterior ao vencimento (20/02/2023)")
		public void testProcessarPagamentosDoTipoBoletoComDataVariavel(int dia) {
			Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente B");

			List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, dia), 1000.00, BOLETO),
				new Conta("2", LocalDate.of(2023, 2, dia), 1000.00, BOLETO)
			);

			fatura.processarPagamentos(contas);

			assertEquals(PAGO, fatura.getStatus());
		}
	}

	@Nested
	@DisplayName("Testes de contas de Cartão de Crédito")
	class CartaoDeCreditoPaymentsTest {

		@ParameterizedTest
		@CsvSource({
			"3, PAGO",
			"4, PAGO",
			"5, PAGO",
			"6, PENDENTE",
			"7, PENDENTE"
		})
		@DisplayName("Status PAGO ou PENDENTE para fatura com pagamento de cartão de crédito com data anterior, igual e posterior ao limite (20/02/2023)")
		public void testProcessarPagamentosDoTipoCartaoDeCreditoComDataVariavel(int dia, String statusPagamento) {
			Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente C");

			List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, dia), 1000.00, CARTAO_CREDITO),
				new Conta("2", LocalDate.of(2023, 2, dia), 1000.00, CARTAO_CREDITO)
			);

			fatura.processarPagamentos(contas);

			assertEquals(StatusPagamento.valueOf(statusPagamento), fatura.getStatus());
		}
	}

	@Nested
	@DisplayName("Testes de contas de Transferência Bancária")
	class TransferenciaBancariaPaymentsTest {

		@ParameterizedTest
		@CsvSource({
			"18, PAGO",
			"19, PAGO",
			"20, PAGO",
			"21, PENDENTE",
			"22, PENDENTE"
		})
		@DisplayName("Status PAGO ou PENDENTE para pagamentos com data anterior, igual e posterior ao vencimento (20/02/2023)")
		public void testProcessarPagamentosDoTipoTransferenciaBancariaComDataVariavel(int dia, String statusPagamento) {
			Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 2000.00, "Cliente D");

			List<Conta> contas = List.of(
				new Conta("1", LocalDate.of(2023, 2, dia), 1000.00, TRANSFERENCIA_BANCARIA),
				new Conta("2", LocalDate.of(2023, 2, dia), 1000.00, TRANSFERENCIA_BANCARIA)
			);

			fatura.processarPagamentos(contas);

			assertEquals(StatusPagamento.valueOf(statusPagamento), fatura.getStatus());
		}
	}
}
