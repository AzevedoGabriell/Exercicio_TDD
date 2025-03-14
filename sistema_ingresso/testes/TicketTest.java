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
    public void testSetTicketTypeAndPrice() {
        Ticket ticket = new Ticket(5, TicketType.NORMAL);
        assertEquals(10.0, ticket.getPrice());
        
        ticket.setType(TicketType.VIP);
        assertEquals(TicketType.VIP, ticket.getType());
        assertEquals(20.0, ticket.getPrice());
        
        ticket.setType(TicketType.MEIA_ENTRADA);
        assertEquals(TicketType.MEIA_ENTRADA, ticket.getType());
        assertEquals(5.0, ticket.getPrice());
    }

    @Test
    public void testTicketId() {
        Ticket ticket = new Ticket(6, TicketType.NORMAL);
        assertEquals(6, ticket.getId());
    }

    @Test
    public void testInvalidDiscountOnHalfPriceTicket() {
        Ticket halfPriceTicket = new Ticket(7, TicketType.MEIA_ENTRADA);
        halfPriceTicket.applyDiscount(0.2); 
        assertEquals(5.0, halfPriceTicket.getPrice()); 
    }

    @Test
    public void testValidDiscountOnVipTicket() {
        Ticket vipTicket = new Ticket(8, TicketType.VIP);
        vipTicket.applyDiscount(0.25);
        assertEquals(15.0, vipTicket.getPrice());
    }
}
