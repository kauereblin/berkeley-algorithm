package Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static Common.Constants.*;

public class Main {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT1);
            ClientInterface clientRmi1 = new ClientImpl(TIME1, PORT1);
            registry.rebind(CLIENT_REGISTRY, clientRmi1);
            System.out.println("Cliente 1: " + clientRmi1 + " conectado!");

            registry = LocateRegistry.createRegistry(PORT2);
            ClientInterface clientRmi2 = new ClientImpl(TIME2, PORT2);
            registry.rebind(CLIENT_REGISTRY, clientRmi2);
            System.out.println("Cliente 2: " + clientRmi2 + " conectado!");

            registry = LocateRegistry.createRegistry(PORT3);
            ClientInterface clientRmi3 = new ClientImpl(TIME3, PORT3);
            registry.rebind(CLIENT_REGISTRY, clientRmi3);
            System.out.println("Cliente 3: " + clientRmi3 + " conectado!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
