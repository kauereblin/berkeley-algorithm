package Client;

import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.Objects;

public class ClientImpl extends UnicastRemoteObject implements ClientInterface {
    private LocalTime time = null;
    private long port = 0;

    public ClientImpl(LocalTime time, long port) throws RemoteException {
        this.setIP(port);
        this.setTime(Objects.requireNonNullElseGet(time, LocalTime::now));
    }

    public void adjustTime(long difference, LocalTime serverTime) {
        long serverTimeNano = serverTime.toNanoOfDay();
        long clientTimeNano = this.time.toNanoOfDay();
        long timeDifference = (clientTimeNano - serverTimeNano) * -1 + difference + clientTimeNano;
        this.setTime(LocalTime.ofNanoOfDay(timeDifference));
    }

    public LocalTime getTime() {
        return this.time;
    }

    public long getPort() {
        return this.port;
    }

    public void setTime(LocalTime time) {
        if (time != null) {
            this.time = time;
            System.out.println("Cliente IP : " + this.getPort() + " - Data definida para: " + this.time);
        }
    }

    public void setIP(long port) {
        this.port = port;
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(2345);
            ClientInterface clientRmi1 = new ClientImpl(LocalTime.of(16, 40, 0, 0), 2345);
            registry.rebind(ClientImpl.class.getSimpleName(), clientRmi1);
            System.out.println("Cliente 1: " + clientRmi1 + " conectado!");

            registry = LocateRegistry.createRegistry(3456);
            ClientInterface clientRmi2 = new ClientImpl(LocalTime.of(16, 0, 0, 0), 3456);
            registry.rebind(ClientImpl.class.getSimpleName(), clientRmi2);
            System.out.println("Cliente 2: " + clientRmi2 + " conectado!");

            registry = LocateRegistry.createRegistry(4567);
            ClientInterface clientRmi3 = new ClientImpl(LocalTime.of(6, 9, 0, 0), 4567);
            registry.rebind(ClientImpl.class.getSimpleName(), clientRmi3);
            System.out.println("Cliente 3: " + clientRmi3 + " conectado!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
