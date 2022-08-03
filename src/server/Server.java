package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9445);
            System.out.println("Сервер запущен");
            while (true) {
                Socket socket = serverSocket.accept();
                User curentUser = new User(socket);
                users.add(curentUser);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("msg", "Введите имя:");
                            curentUser.getOut().writeUTF(jsonObject.toJSONString());
                            curentUser.setName(curentUser.getIs().readUTF());
                            sendOnlineUsers();
                            while (true) {
                                String massage = curentUser.getIs().readUTF();
                                jsonObject.put("msg", curentUser.getName() + ": " + massage);
                                for (User user : users) {
                                    //if (user.getUuid()==(curentUser.getUuid())){
                                    if (!curentUser.getUuid().toString().equals(user.getUuid().toString())) {
                                        user.getOut().writeUTF(jsonObject.toJSONString());
                                    }
                                }
                                System.out.println(curentUser.getName() + ": " + massage);
                            }
                        } catch (IOException e) {
                            users.remove(curentUser);
                            System.out.println(curentUser.getName() + ": отключился");
                            jsonObject.put("msg", curentUser.getName() + ": отключился");
                            for (User user : users) {
                                try {
                                    user.getOut().writeUTF(jsonObject.toJSONString());
                                    sendOnlineUsers();
                                } catch (IOException ex) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void sendOnlineUsers() throws IOException {
        JSONArray onlineUsersJSON = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (User user : users) {
            onlineUsersJSON.add(user.getName());
        }
        jsonObject.put("users", onlineUsersJSON);
        for (User user : users) {
            user.getOut().writeUTF(jsonObject.toJSONString());
        }
    }
}
