package pojo;

public class Service {

    private Connector connector;

    @Override
    public String toString() {
        return "Service{" +
                "connector=" + connector +
                '}';
    }

    public Connector getConnector() {
        return connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }
}
