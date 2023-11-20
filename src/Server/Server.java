package Server;

import Client.ClientInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Common.Constants.CLIENT_REGISTRY;
import static Common.Constants.SERVER_IP;

public class Server {
    private LocalTime time = null;
    private final List<ClientInterface> clients = new ArrayList<>();

    public Server(LocalTime time, List<Link> links) {
        this.setTime(Objects.requireNonNullElseGet(time, LocalTime::now));
        this.setClients(links);
    }

    public void sendDifferenceToClients(long difference) {
        System.out.println(this.clients);
        try {
            for (ClientInterface client : this.clients)
                client.adjustTime(difference, this.getTime());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public LocalTime getTime() {
        return this.time;
    }

    public List<LocalTime> getClientsTime() {
        List<LocalTime> times = new ArrayList<>();

        for (ClientInterface client : this.clients)
            times.add(this.getClientTime(client));

        return times;
    }

    public long getDifferenceByClient(LocalTime clientTime) {
        return clientTime.toNanoOfDay() - this.getTime().toNanoOfDay();
    }

    private Registry getRegistry(Link link) {
        try {
            return LocateRegistry.getRegistry(SERVER_IP, link.getPort());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientInterface getRegistryLookup(Registry registry) {
        try {
            return (ClientInterface) registry.lookup(CLIENT_REGISTRY);
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalTime getClientTime(ClientInterface client) {
        try {
            return client.getTime();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTime(LocalTime time) {
        if (time != null) {
            this.time = time;
        }
    }

    public void setClients(List<Link> links) {
        for (Link link : links) {
            Registry registry = getRegistry(link);
            ClientInterface client = getRegistryLookup(registry);

            this.clients.add(client);
        }
    }
}
