package ua.knu.rmi;


import ua.knu.model.Weather;
import ua.knu.repository.WeatherRepository;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.List;

interface RemoteInterface extends Remote {
    List<Date> GetDatesWhenSnowingAndTemperatureLess(String region, Integer temperature) throws RemoteException;

    List<Weather> GetWeatherByRegion(String region) throws RemoteException;

    Double GetAverageTemperatureInRegions(Integer area) throws RemoteException;
}

class RemoteInterfaceImpl implements RemoteInterface {

    private static final WeatherRepository repository = new WeatherRepository();

    @Override
    public List<Date> GetDatesWhenSnowingAndTemperatureLess(String region, Integer temperature) throws RemoteException {
        return repository.findDatesWhenSnowingAndTemperatureLessThan(region, temperature);
    }

    @Override
    public List<Weather> GetWeatherByRegion(String region) throws RemoteException {
        return repository.findWeatherByRegion(region);
    }

    @Override
    public Double GetAverageTemperatureInRegions(Integer area) throws RemoteException {
        return repository.findAverageTemperatureInRegionWithAreaMoreThan(area);
    }
}

public class ServerRmiWeather {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        RemoteInterfaceImpl remoteInterface = new RemoteInterfaceImpl();
        RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(remoteInterface, 9001);
        Registry registry = LocateRegistry.createRegistry(9001);
        registry.rebind("RemoteInterface", stub);
        System.err.println("Server ready");
    }
}
