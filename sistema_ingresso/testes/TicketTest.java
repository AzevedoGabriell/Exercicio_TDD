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

     @Test
    public void testMarkTicketAsSold() {
        Ticket ticket = new Ticket(4, TicketType.NORMAL);
        assertFalse(ticket.isSold());
        ticket.markAsSold();
        assertTrue(ticket.isSold());
    }

    @Test
    public void testVipTicketPrice() {
        Ticket vipTicket = new Ticket(5, TicketType.VIP);
        assertEquals(20.0, vipTicket.getPrice());
    }

    @Test
    public void testHalfPriceTicketPrice() {
        Ticket halfPriceTicket = new Ticket(6, TicketType.MEIA_ENTRADA);
        assertEquals(5.0, halfPriceTicket.getPrice());
    }
}
