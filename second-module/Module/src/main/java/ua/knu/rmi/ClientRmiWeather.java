package ua.knu.rmi;

import ua.knu.event.GetAverageTemperatureInRegions;
import ua.knu.event.GetDatesWhenSnowingAndTemperatureLess;
import ua.knu.event.GetWeatherByRegion;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class ClientRmiWeather {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        var scanner = new Scanner(System.in);
        var registry = LocateRegistry.getRegistry(9001);
        var stub = (RemoteInterface) registry.lookup("RemoteInterface");

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

                    var response = stub.GetWeatherByRegion(request.getRegion());
                    System.out.println(response);
                    break;
                }
                case "2": {
                    var request = new GetDatesWhenSnowingAndTemperatureLess()
                            .setRegion(input[1])
                            .setTemperature(Integer.valueOf(input[2]));

                    var response = stub.GetDatesWhenSnowingAndTemperatureLess(request.getRegion(), request.getTemperature());
                    System.out.println(response);
                    break;
                }
                case "3": {
                    var request = new GetAverageTemperatureInRegions()
                            .setArea(Integer.valueOf(input[1]));

                    var response = stub.GetAverageTemperatureInRegions(request.getArea());
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
