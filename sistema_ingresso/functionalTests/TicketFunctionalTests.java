import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class TicketFunctionalTests {

    @Test
    public void testDiscountBoundaryValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Lote(1, Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL)), -0.01);
        });

        Lote lote1 = new Lote(1, Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL)), 0.00);
        assertEquals(0.00, lote1.getDiscount());

        Lote lote2 = new Lote(2, Arrays.asList(new Ticket(2, Ticket.TicketType.VIP)), 0.25);
        assertEquals(0.25, lote2.getDiscount());

        assertThrows(IllegalArgumentException.class, () -> {
            new Lote(3, Arrays.asList(new Ticket(3, Ticket.TicketType.NORMAL)), 0.26);
        });
    }

    @Test
    public void testTicketPriceEquivalencePartitions() {
        Ticket ticket1 = new Ticket(1, Ticket.TicketType.NORMAL);
        assertEquals(10.0, ticket1.getPrice());

        assertThrows(IllegalArgumentException.class, () -> {
            Ticket ticket2 = new Ticket(2, Ticket.TicketType.NORMAL);
            ticket2.setPrice(-10.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Ticket ticket3 = new Ticket(3, Ticket.TicketType.NORMAL);
            ticket3.setPrice(1001.0);
        });
    }

     @Test
    public void testNormalTicketWithDiscountSpecialDate() {
        Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, true);
        List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.NORMAL));
        tickets.get(0).markAsSold();
        Lote lote = new Lote(1, tickets, 0.15);
        show.addLote(lote);

        String report = show.generateReport();
        assertTrue(report.contains("LUCRO"));
    }

    @Test
    public void testHalfPriceTicketNoDiscountSpecialDate() {
        Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, true);
        List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.MEIA_ENTRADA));
        tickets.get(0).markAsSold();
        Lote lote = new Lote(1, tickets, 0.00);
        show.addLote(lote);

        String report = show.generateReport();
        assertTrue(report.contains("PREJUÍZO"));
    }

    @Test
    public void testVipTicketNoDiscountNoSpecialDate() {
        Show show = new Show("2023-12-25", "Artista X", 1000.0, 2000.0, false);
        List<Ticket> tickets = Arrays.asList(new Ticket(1, Ticket.TicketType.VIP));
        tickets.get(0).markAsSold();
        Lote lote = new Lote(1, tickets, 0.00);
        show.addLote(lote);

        String report = show.generateReport();
        assertTrue(report.contains("LUCRO"));
    }
}