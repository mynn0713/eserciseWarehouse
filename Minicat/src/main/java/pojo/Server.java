package pojo;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public Server() {
        this.serviceList = new ArrayList<Service>();
    }

    @Override
    public String toString() {
        return "Server{" +
                "serviceList=" + serviceList +
                '}';
    }

    private List<Service> serviceList;

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
