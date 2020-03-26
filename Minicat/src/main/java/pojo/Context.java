package pojo;

public class Context {
    private String wrapperName;

    @Override
    public String toString() {
        return "Context{" +
                "wrapperName='" + wrapperName + '\'' +
                '}';
    }

    public String getWrapperName() {
        return wrapperName;
    }

    public void setWrapperName(String wrapperName) {
        this.wrapperName = wrapperName;
    }
}
