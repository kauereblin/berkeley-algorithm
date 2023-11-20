package berkeley_algorithm;

import java.rmi.registry.*;

public class Main {
    public static void main(String[] args) {
        try {
            CalculadoraServerInterface sdrmi = new CalculadoraServerInterfaceImpl();
            Registry registry = LocateRegistry.createRegistry(6942);
            registry.rebind("CalculadoraServerInterfaceImpl", sdrmi);
            System.out.println("Servidor Calculaddora: " + sdrmi + " registrando e pronto para aceitar solicitações.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}