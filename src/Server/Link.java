package Server;

public class Link {
    private int port = 0;

    public Link(int port) { this.setPort(port); }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
