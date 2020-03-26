package pojo;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    public Engine() {
        this.hostList = new ArrayList<Host>();
    }

    @Override
    public String toString() {
        return "Engine{" +
                "hostList=" + hostList +
                '}';
    }

    private List<Host> hostList;

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

}
