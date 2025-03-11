import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TicketJUnit5Tests {

    @Nested
    @DisplayName("Testes de Desconto")
    class DiscountTests {

        @Test
        @DisplayName("Deve lançar exceção para desconto negativo")
        void testNegativeDiscount() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Lote(1, Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL)), -0.01);
            });
        }

        @Test
        @DisplayName("Deve aceitar desconto zero")
        void testZeroDiscount() {
            Lote lote = new Lote(1, Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL)), 0.00);
            assertEquals(0.00, lote.getDiscount());
        }

        @Test
        @DisplayName("Deve aceitar desconto de 25%")
        void testValidDiscount() {
            Lote lote = new Lote(2, Arrays.asList(new Ticket(2, Ticket.TicketType.VIP)), 0.25);
            assertEquals(0.25, lote.getDiscount());
        }

        @Test
        @DisplayName("Deve lançar exceção para desconto acima de 25%")
        void testDiscountAboveLimit() {
            assertThrows(IllegalArgumentException.class, () -> {
                new Lote(3, Arrays.asList(new Ticket(3, Ticket.TicketType.NORMAL)), 0.26);
            });
        }
    }

    @Nested
    @DisplayName("Testes de Preço do Ticket")
    class TicketPriceTests {

        @Test
        @DisplayName("Deve retornar o preço padrão para ticket normal")
        void testNormalTicketPrice() {
            Ticket ticket = new Ticket(1, Ticket.TicketType.NORMAL);
            assertEquals(10.0, ticket.getPrice());
        }

        @ParameterizedTest
        @MethodSource("invalidPrices")
        @DisplayName("Deve lançar exceção para preços inválidos")
        void testInvalidTicketPrices(double price) {
            Ticket ticket = new Ticket(2, Ticket.TicketType.NORMAL);
            assertThrows(IllegalArgumentException.class, () -> ticket.setPrice(price));
        }

        private static Stream<Arguments> invalidPrices() {
            return Stream.of(
                Arguments.of(-10.0),
                Arguments.of(1001.0)
            );
        }
    }

    @Nested
    @DisplayName("Testes de Relatório de Show")
    class ShowReportTests {

        @Test
        @DisplayName("Deve gerar relatório com lucro para ticket normal com desconto em data especial")
        void testNormalTicketWithDiscountSpecialDate() {
            Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, true);
            List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL));
            tickets.get(0).markAsSold();
            Lote lote = new Lote(1, tickets, 0.15);
            show.addLote(lote);

            String report = show.generateReport();
            assertTrue(report.contains("LUCRO"));
        }

        @Test
        @DisplayName("Deve gerar relatório com prejuízo para ticket meia entrada sem desconto em data especial")
        void testHalfPriceTicketNoDiscountSpecialDate() {
            Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, true);
            List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.MEIA_ENTRADA));
            tickets.get(0).markAsSold();
            Lote lote = new Lote(1, tickets, 0.00);
            show.addLote(lote);

            String report = show.generateReport();
            assertTrue(report.contains("PREJUÍZO"));
        }

        @Test
        @DisplayName("Deve gerar relatório com lucro para ticket VIP sem desconto em data não especial")
        void testVipTicketNoDiscountNoSpecialDate() {
            Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, false);
            List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.VIP));
            tickets.get(0).markAsSold();
            Lote lote = new Lote(1, tickets, 0.00);
            show.addLote(lote);

            String report = show.generateReport();
            assertTrue(report.contains("LUCRO"));
        }
    }
}