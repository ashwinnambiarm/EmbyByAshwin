package com.ashwin.embybyashwin.Fragment.Login.Model;

import java.util.ArrayList;

public class Server {

    private final String serverName;
    private final String serverAddress;

    public String getServerName() {
        return serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public Server(String name, String address){

        serverName = name;
        serverAddress = address;
    }

    private static int lastServerId = 0;

    public static ArrayList<Server> createServerList(int numContacts) {
        ArrayList<Server> server = new ArrayList<Server>();

        for (int i = 1; i <= numContacts; i++) {
            server.add(new Server("Server  " + ++lastServerId, "0.0.0.0"));
        }
        return server;
    }
}
