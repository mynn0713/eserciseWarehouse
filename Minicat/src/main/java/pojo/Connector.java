package pojo;

public class Connector {
    private Integer prot;

    private Engine engine;

    @Override
    public String toString() {
        return "Connector{" +
                "prot=" + prot +
                ", engine=" + engine +
                '}';
    }

    public Integer getProt() {
        return prot;
    }

    public void setProt(Integer prot) {
        this.prot = prot;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
