package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class User {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream out;
    private String name;
    private UUID uuid;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(socket.getOutputStream());
        this.is = new DataInputStream(socket.getInputStream());
        this.uuid = UUID.randomUUID();
    }

    public User() {
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

}

/**
 * решить и обосновать какие геттеры и сеттеры нужнф классу юзер
 * запретить отправку сообщения отправителю
 *
 *
 * Установить и получить сокет возможно понадобится (потеря связи и т.п.),
 * out и is нужны только getter, потому что их значения меняться не будут
 * и вычисляются от сокета.
 * установить имя нужны и геттеры и сеттеры, чтобы была возможность дать
 * имя/переименовать, и получить имя
 * uuid только геттер, чтобы были уникальными и их не могли поменять
 */
