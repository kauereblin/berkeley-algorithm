// Autores: Arthur Bezerra Pinotti, Kaue Reblin, Luiz Gustavo Klitzke
package Client;

import java.rmi.*;
import java.time.LocalTime;

public interface ClientInterface extends Remote {
    void adjustTime(long difference, LocalTime serverTime) throws Exception;
    LocalTime getTime() throws Exception;
    void setTime(LocalTime time) throws Exception;
}
