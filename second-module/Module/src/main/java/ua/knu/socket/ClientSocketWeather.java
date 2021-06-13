package ua.knu.socket;

import ua.knu.event.GetAverageTemperatureInRegions;
import ua.knu.event.GetDatesWhenSnowingAndTemperatureLess;
import ua.knu.event.GetWeatherByRegion;
import ua.knu.model.Weather;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientSocketWeather {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocketClient socketClient = new SocketClient("localhost", 9002);
        try {
            socketClient.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Write your command:");
            System.out.println("1 |region| = Get weather by region");
            System.out.println("2 |region| |temperature| = Get dates when snowing and temperature was less than");
            System.out.println("3 |area| = Get average temperature in regions with area bigger than");
            var input = scanner.nextLine().split(" ");
            var commandName = input[0];

            switch (commandName) {
                case "1": {
                    var request = new GetWeatherByRegion()
                            .setRegion(input[1]);

                    var response = (List<Weather>) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                case "2": {
                    var request = new GetDatesWhenSnowingAndTemperatureLess()
                            .setRegion(input[1])
                            .setTemperature(Integer.valueOf(input[2]));

                    var response = (List<Weather>) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                case "3": {
                    var request = new GetAverageTemperatureInRegions()
                            .setArea(Integer.valueOf(input[1]));

                    var response = (Double) socketClient.sendRequest(request);
                    System.out.println(response);
                    break;
                }
                default: {
                    System.out.println("No such command");
                }
            }

        }
    }
}

class SocketClient {
    private String ip;
    private Integer port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SocketClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(ip, port);
    }

    public Object sendRequest(Object request) {
        try {
            sendMessage(request);
            return receiveMessage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error sending request");
        }
    }

    public Object receiveMessage() throws IOException, ClassNotFoundException {
        if (in == null) {
            in = new ObjectInputStream(socket.getInputStream());
        }
        Object response = in.readObject();

        return response;
    }

    public void sendMessage(Object request) throws IOException {
        if (out == null) {
            out = new ObjectOutputStream(socket.getOutputStream());
        }
        out.writeObject(request);
    }

    public void close() throws IOException {
        socket.close();
    }
}
