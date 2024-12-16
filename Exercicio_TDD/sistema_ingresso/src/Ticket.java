public class Ticket {
    private int id;
    private TicketType type;
    private double price;
    private boolean sold;

    enum TicketType {
        NORMAL,
        VIP,
        MEIA_ENTRADA
    }

    public Ticket(int id, TicketType type) {
        this.id = id;
        this.type = type;
        this.sold = false;
        setPrice();
    }

    private void setPrice() {
        switch (type) {
            case VIP:
                this.price = 20.0;
                break;
            case MEIA_ENTRADA:
                this.price = 5.0;
                break;
            case NORMAL:
                this.price = 10.0;
                break;
        }
    }

    public double getPrice() {
        return price;
    }

    public TicketType getType() {
        return type;
    }

    public boolean isSold() {
        return sold;
    }

    public void markAsSold() {
        this.sold = true;
    }

    public int getId() {
        return id;
    }

    public void setType(TicketType type) {
        this.type = type;
        setPrice();
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
