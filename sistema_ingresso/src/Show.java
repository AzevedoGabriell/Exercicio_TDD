import java.util.ArrayList;
import java.util.List;

public class Show {
    private String date;
    private String artist;
    private double artistFee;
    private double infrastructureCost;
    private boolean isSpecialDate;
    private List<Lote> lotes;

    public Show(String date, String artist, double artistFee, double infrastructureCost, boolean isSpecialDate) {
        this.date = date;
        this.artist = artist;
        this.artistFee = artistFee;
        this.infrastructureCost = infrastructureCost;
        this.isSpecialDate = isSpecialDate;
        this.lotes = new ArrayList<>();
    }

    public void addLote(Lote lote) {
        lotes.add(lote);
    }

    public double calculateNetRevenue() {
        double totalRevenue = 0;
        for (Lote lote : lotes) {
            for (Ticket ticket : lote.getTickets()) {
                if (ticket.isSold()) {
                    totalRevenue += ticket.getPrice();
                }
            }
        }

        double totalCost = artistFee + infrastructureCost;
        if (isSpecialDate) {
            totalCost += infrastructureCost * 0.15;
        }

        return totalRevenue - totalCost;
    }

    public String generateReport() {
        int normalSold = 0;
        int vipSold = 0;
        int halfPriceSold = 0;

        for (Lote lote : lotes) {
            for (Ticket ticket : lote.getTickets()) {
                if (ticket.isSold()) {
                    switch (ticket.getType()) {
                        case NORMAL:
                            normalSold++;
                            break;
                        case VIP:
                            vipSold++;
                            break;
                        case MEIA_ENTRADA:
                            halfPriceSold++;
                            break;
                    }
                }
            }
        }

        double netRevenue = calculateNetRevenue();
        String financialStatus;
        if (netRevenue > 0) {
            financialStatus = "LUCRO";
        } else if (netRevenue == 0) {
            financialStatus = "ESTÁVEL";
        } else {
            financialStatus = "PREJUÍZO";
        }

        return String.format(
            "Relatório do Show:\n" +
            "Ingressos NORMAL vendidos: %d\n" +
            "Ingressos VIP vendidos: %d\n" +
            "Ingressos MEIA_ENTRADA vendidos: %d\n" +
            "Receita Líquida: R$ %.2f\n" +
            "Status Financeiro: %s\n",
            normalSold, vipSold, halfPriceSold, netRevenue, financialStatus
        );
    }
}