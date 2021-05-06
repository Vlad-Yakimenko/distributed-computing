package ua.knu.util;

import java.util.LinkedList;
import java.util.List;

public class Result {

    private static Result instance = null;

    private static List<String> results;

    private Result() {
        results = new LinkedList<>();
    }

    public static synchronized Result getInstance() {
        if (instance == null)
            instance = new Result();
        return instance;
    }

    public synchronized void saveResult(String result) {
        results.add(result);
    }

    public synchronized String getResults() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String result : results) {
            stringBuilder.append(result);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
