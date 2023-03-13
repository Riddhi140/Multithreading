package ProducerConsumer;

public class Message {
    private int id;
    private double data;

    public Message(int id, double data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
