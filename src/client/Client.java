package client;

import server.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // localhost
        // 127.0.0.1
        try {
            Socket socket = new Socket("localhost", 9446);
            User user = new User(socket);
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataOutputStream out = user.getOut();
            //DataInputStream is = new DataInputStream(socket.getInputStream());
            DataInputStream is = user.getIs();
            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String responce = is.readUTF();
                            System.out.println(responce);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            thread.start();
            while (true) {
                String massage = scanner.nextLine();
                out.writeUTF(massage);

            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

}
