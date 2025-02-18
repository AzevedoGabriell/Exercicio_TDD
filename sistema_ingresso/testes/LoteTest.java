import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LoteTest {
    private List<Ticket> tickets;

    @BeforeEach
    void setUp() {
        tickets = new ArrayList<>();
        tickets.add(new Ticket(100.0, Ticket.TicketType.NORMAL));
        tickets.add(new Ticket(200.0, Ticket.TicketType.VIP));
        tickets.add(new Ticket(150.0, Ticket.TicketType.MEIA_ENTRADA));
    }

    @Test
    void testApplyDiscountValid() {
        Lote lote = new Lote(1, tickets, 0.2);
        assertEquals(80.0, tickets.get(0).getPrice(), 0.01);
        assertEquals(160.0, tickets.get(1).getPrice(), 0.01);
        assertEquals(150.0, tickets.get(2).getPrice(), 0.01);
    }

    @Test
    void testApplyDiscountExceedsLimit() {
        assertThrows(IllegalArgumentException.class, () -> new Lote(1, tickets, 0.3));
    }
}