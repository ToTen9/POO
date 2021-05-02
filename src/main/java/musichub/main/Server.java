package musichub.main;

import musichub.business.*;

public class Server {
    public static void main(String[] args) {
        String ip = "localhost";
        AbstractServer as = new FirstServer();
        as.connect(ip);
    }
}