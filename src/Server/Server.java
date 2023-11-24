// Autores: Arthur Bezerra Pinotti, Kaue Reblin, Luiz Gustavo Klitzke
package Server;

import Client.*;

import java.rmi.*;
import java.rmi.registry.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        } catch (Exception e) {
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
            return LocateRegistry.getRegistry("localhost", link.getPort());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientInterface getRegistryLookup(Registry registry) {
        try {
            return (ClientInterface) registry.lookup(ClientImpl.class.getSimpleName());
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalTime getClientTime(ClientInterface client) {
        try {
            return client.getTime();
        } catch (Exception e) {
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

    public static void main(String[] args) {
        List<Link> links = new ArrayList<>();
        links.add(new Link(2345));
        links.add(new Link(3456));
        links.add(new Link(4567));

        Server server = new Server(LocalTime.of(16, 20, 0, 0), links);
        List<LocalTime> times = new ArrayList<>(server.getClientsTime());
        times.add(server.getTime());

        long timeSum = 0;
        System.out.println("Tempo dos Clientes:");
        for (int idxTime = 0; idxTime < times.size(); idxTime++) {
            LocalTime time = times.get(idxTime);

            if (idxTime == times.size() - 1)
                System.out.println("Tempo do Servidor: ");

            System.out.println(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

            timeSum += server.getDifferenceByClient(time);
        }

        long difference = timeSum / times.size();

        server.sendDifferenceToClients(difference);
    }
}
