package Common;

import Client.ClientImpl;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public interface Constants {
    String SERVER_IP = "localhost";
    int PORT1 = 2345;
    int PORT2 = 3456;
    int PORT3 = 4567;
    LocalTime SERVER_TIME = LocalTime.of(16, 20, 0, 0);
    LocalTime TIME1 = LocalTime.of(16, 40, 0, 0);
    LocalTime TIME2 = LocalTime.of(16, 0, 0, 0);
    LocalTime TIME3 = LocalTime.of(6, 9, 0, 0);
    String CLIENT_REGISTRY = ClientImpl.class.getSimpleName();
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
}
