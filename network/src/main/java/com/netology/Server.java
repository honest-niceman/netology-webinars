package com.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


//https://github.com/netology-code/jd-homeworks/blob/master/network/README.md
public class Server {
    public static final Integer LOCALHOST_PORT = 8080;

    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(LOCALHOST_PORT);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (serverSocket != null) {
//                serverSocket.close();
//            }
//        }
        try (ServerSocket serverSocket = new ServerSocket(LOCALHOST_PORT)) {
            System.out.println("Сервер стартовал");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    String infoFromClient = in.readLine();
                    System.out.printf("Новое подключение принято. Информация: %s, порт: %d%n", infoFromClient, clientSocket.getPort());
                    out.println(clientSocket.getPort());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
