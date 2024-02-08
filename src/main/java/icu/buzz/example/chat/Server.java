package icu.buzz.example.chat;

public class Server {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        new ChatServer(port).run();
    }
}
