package pojo;

import java.util.ArrayList;
import java.util.List;

public class Host {
    private String name;
    private String appBase;
    private List<Context> contextList;

    public Host() {
        this.contextList = new ArrayList<Context>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppBase() {
        return appBase;
    }

    public void setAppBase(String appBase) {
        this.appBase = appBase;
    }

    public List<Context> getContextList() {
        return contextList;
    }

    public void setContextList(List<Context> contextList) {
        this.contextList = contextList;
    }

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                ", appBase='" + appBase + '\'' +
                ", contextList=" + contextList +
                '}';
    }
}
