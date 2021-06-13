package ua.knu.socket;

import ua.knu.event.GetAverageTemperatureInRegions;
import ua.knu.event.GetDatesWhenSnowingAndTemperatureLess;
import ua.knu.event.GetWeatherByRegion;
import ua.knu.repository.WeatherRepository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWeather {
    private static final WeatherRepository repository = new WeatherRepository();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9002);
            while (true) {
                System.out.println("Waiting for connection");
                Socket socket = serverSocket.accept();
                System.out.println("Connection established. Listening");
                //after somebody connected, we are waiting for requests
                new Thread(() -> {
                    try {
                        handleRequests(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleRequests(Socket socket) throws IOException, ClassNotFoundException {
        var in = new ObjectInputStream(socket.getInputStream());
        var out = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            try {
                Object inObject = in.readObject();
                if (inObject instanceof GetWeatherByRegion request) {
                    var weather = repository.findWeatherByRegion(request.getRegion());
                    out.writeObject(weather);
                }
                if (inObject instanceof GetDatesWhenSnowingAndTemperatureLess request) {
                    var dates = repository.findDatesWhenSnowingAndTemperatureLessThan(request.getRegion(), request.getTemperature());
                    out.writeObject(dates);
                }
                if (inObject instanceof GetAverageTemperatureInRegions request) {
                    var averageTemperatureInRegions = repository.findAverageTemperatureInRegionWithAreaMoreThan(request.getArea());
                    out.writeObject(averageTemperatureInRegions);
                }
            } catch (RuntimeException e) {
                out.writeObject("ERROR");
            }
        }
    }
}
