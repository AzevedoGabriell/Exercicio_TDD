import java.util.ArrayList;
import java.util.List;

public class Lote {
    private int id;
    private List<Ticket> tickets;
    private double discount;

    public Lote(int id, List<Ticket> tickets, double discount) {
        this.id = id;
        this.tickets = tickets;
        this.discount = discount;
        applyDiscount();
    }

    private void applyDiscount() {
        if (discount > 0.25) {
            throw new IllegalArgumentException("O desconto máximo permitido é de 25%.");
        }
        for (Ticket ticket : tickets) {
            if (ticket.getType() == Ticket.TicketType.VIP || ticket.getType() == Ticket.TicketType.NORMAL) {
                double newPrice = ticket.getPrice() * (1 - discount);
                ticket.setPrice(newPrice);
            }
        }
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int getId() {
        return id;
    }

    public double getDiscount() {
        return discount;
    }
}