package icu.buzz;

import icu.buzz.example.Server;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(8888);
        server.run();
    }
}