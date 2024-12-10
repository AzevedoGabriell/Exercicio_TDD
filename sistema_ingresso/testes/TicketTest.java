import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TicketTest {

    @Test
    public void testCreateNormalTicket() {
        Ticket normalTicket = new Ticket(1, TicketType.NORMAL);
        assertEquals(10.0, normalTicket.getPrice());
        assertEquals(TicketType.NORMAL, normalTicket.getType());
        assertFalse(normalTicket.isSold());
    }

    @Test
    public void testCreateVipTicket() {
        Ticket vipTicket = new Ticket(2, TicketType.VIP);
        assertEquals(20.0, vipTicket.getPrice());
        assertEquals(TicketType.VIP, vipTicket.getType());
        assertFalse(vipTicket.isSold());
    }

    @Test
    public void testCreateHalfPriceTicket() {
        Ticket halfPriceTicket = new Ticket(3, TicketType.MEIA_ENTRADA);
        assertEquals(5.0, halfPriceTicket.getPrice());
        assertEquals(TicketType.MEIA_ENTRADA, halfPriceTicket.getType());
        assertFalse(halfPriceTicket.isSold());
    }
}
