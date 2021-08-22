package Network;

public class IpPort {
    private String ip ;
    private int port ;
    private static IpPort instance;

    public static IpPort getInstance(){
        if (instance == null){
            instance = new IpPort();
            instance.ip = "185.239.106.151";
            instance.port = 8989;
        }
        return instance;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setPort(int port){
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
