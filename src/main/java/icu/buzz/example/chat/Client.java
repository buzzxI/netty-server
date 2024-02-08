package icu.buzz.example.chat;

public class Client {
    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new ChatClient(host, port).run();
    }
}
