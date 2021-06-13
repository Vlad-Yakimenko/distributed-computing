package ua.knu.repository;

import lombok.SneakyThrows;
import ua.knu.model.Precipitation;
import ua.knu.model.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WeatherRepository {

    @SneakyThrows
    public List<Weather> findWeatherByRegion(String region) {
        var command = "SELECT distinct wr.* FROM weather_record wr WHERE region=?";

        try (var connection = Objects.requireNonNull(ConnectionFactory.getConnection());
             var preparedStatement = connection.prepareStatement(command)) {

            preparedStatement.setString(1, region);

            var resultSet = preparedStatement.executeQuery();

            var weather = new ArrayList<Weather>();
            while (resultSet.next()) {
                weather.add(WeatherMapper.toEntity(resultSet));
            }

            return weather;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public List<Date> findDatesWhenSnowingAndTemperatureLessThan(String region, Integer temperature) {
        if (temperature >= 0) throw new IllegalArgumentException("Temperature value must be a negative number");
        var command = "SELECT distinct wr.* FROM weather_record wr WHERE region=? AND precipitation='SNOW' AND temperature<?";

        try (var connection = Objects.requireNonNull(ConnectionFactory.getConnection());
             var preparedStatement = connection.prepareStatement(command)) {

            preparedStatement.setString(1, region);
            preparedStatement.setInt(2, temperature);

            var resultSet = preparedStatement.executeQuery();

            var weather = new ArrayList<Weather>();
            while (resultSet.next()) {
                weather.add(WeatherMapper.toEntity(resultSet));
            }

            return weather.stream().map(Weather::getDate).collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public Double findAverageTemperatureInRegionWithAreaMoreThan(Integer area) {
        var command = "SELECT distinct wr.* FROM weather_record wr INNER JOIN region r on r.region_name = wr.region WHERE r.area>? AND wr.record_date > (now()::date - '7 days'::interval) AND wr.record_date <= (now()::date)";

        try (var connection = Objects.requireNonNull(ConnectionFactory.getConnection());
             var preparedStatement = connection.prepareStatement(command)) {

            preparedStatement.setInt(1, area);

            var resultSet = preparedStatement.executeQuery();

            var weather = new ArrayList<Weather>();
            while (resultSet.next()) {
                weather.add(WeatherMapper.toEntity(resultSet));
            }

            return weather.stream().mapToInt(Weather::getTemperature).average().orElseThrow();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

class ConnectionFactory {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/weather",
                    "postgres",
                    "Tsunami9");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class WeatherMapper {
    @SneakyThrows
    public static Weather toEntity(ResultSet resultSet) {
        return new Weather()
                .setDate(resultSet.getDate("record_date"))
                .setPrecipitation(Precipitation.valueOf(resultSet.getString("precipitation")))
                .setRegion(resultSet.getString("region"))
                .setTemperature(resultSet.getInt("temperature"));
    }
}
