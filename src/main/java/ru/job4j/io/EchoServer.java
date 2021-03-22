package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    String answer = "";
                    String current;
                    String str = in.readLine();
                    while (!str.isEmpty()) {
                        if (str.contains("GET /?msg=")) {
                            int index = str.indexOf("=");
                            current = str.substring(index + 1, str.indexOf(" ", index));
                            if (current.equals("Exit")) {
                                answer = "Server closed";
                            } else if (current.equals("Hello")) {
                                answer = "Hello";
                            } else {
                                answer = "What is: " + current + "?";
                            }
                        }
                        str = in.readLine();
                    }
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    out.write(answer.getBytes());
                    if (answer.equals("Server closed")) {
                        server.close();
                    }
                }
            }
        }
    }
}