package Client;

import java.rmi.RemoteException;
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

    public void adjustTime(long difference, LocalTime serverTime) throws RemoteException {
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
}
