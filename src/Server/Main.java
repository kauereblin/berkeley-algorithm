package Server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Common.Constants.*;

public class Main {
    public static void main(String[] args) {
        List<Link> links = new ArrayList<>();
        links.add(new Link(PORT1));
        links.add(new Link(PORT2));
        links.add(new Link(PORT3));

        Server server = new Server(SERVER_TIME, links);
        List<LocalTime> times = new ArrayList<>(server.getClientsTime());
        times.add(server.getTime());

        long timeSum = 0;
        System.out.println("Tempo dos Clientes:");
        for (int idxTime = 0; idxTime < times.size(); idxTime++) {
            LocalTime time = times.get(idxTime);

            if (idxTime == times.size() - 1)
                System.out.println("Tempo do Servidor: ");

            System.out.println(time.format(DATE_FORMATTER));

            timeSum += server.getDifferenceByClient(time);
        }

        long difference = timeSum / times.size();

        server.sendDifferenceToClients(difference);
    }
}