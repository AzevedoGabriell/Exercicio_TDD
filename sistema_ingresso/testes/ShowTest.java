import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ShowTest {
    private Show show;
    private Lote lote;

    @BeforeEach
    void setUp() {
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket(100.0, Ticket.TicketType.NORMAL);
        ticket1.sell();
        Ticket ticket2 = new Ticket(200.0, Ticket.TicketType.VIP);
        ticket2.sell();
        tickets.add(ticket1);
        tickets.add(ticket2);
        lote = new Lote(1, tickets, 0);
        show = new Show("2025-06-15", "Artista X", 5000, 2000, false);
        show.addLote(lote);
    }

    @Test
    void testCalculateNetRevenue() {
        assertEquals(-2700.0, show.calculateNetRevenue(), 0.01);
    }

    @Test
    void testGenerateReport() {
        String report = show.generateReport();
        assertTrue(report.contains("Ingressos NORMAL vendidos: 1"));
        assertTrue(report.contains("Ingressos VIP vendidos: 1"));
        assertTrue(report.contains("Receita Líquida: R$ -2700.00"));
        assertTrue(report.contains("Status Financeiro: PREJUÍZO"));
    }
}
