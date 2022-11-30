package ilya.example.TaxiTask.taxi;

import org.example.LICourse.TaxiTask.services.Taxi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TaxiStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxiStorage.class);

    public static final TaxiStorage INSTANCE = new TaxiStorage();

    private TaxiStorage() {
    }

    private final Map<String, Taxi> taxiStorage = new HashMap<>();
    private final Map<String, Double> distanceStorage = new HashMap<>();

    public void putTaxi(Taxi taxi) {
        taxiStorage.put(taxi.getId(), taxi);
        distanceStorage.put(taxi.getId(), 0d);
    }

    public void updateTaxi(Taxi taxi, Double newDistance) {
        taxiStorage.put(taxi.getId(), taxi);
        distanceStorage.put(taxi.getId(), newDistance);
    }

    public Taxi getTaxi(String taxiKey) {
        return taxiStorage.get(taxiKey);
    }

    public Double getTaxiDistance(String taxiKey) {
        return distanceStorage.get(taxiKey);
    }

    public void print() {
        for (Taxi taxi : taxiStorage.values()) {
            Double distance = distanceStorage.get(taxi.getId());
            LOGGER.info("Taxi '{}' has passed distance = {}", taxi, distance);
        }
    }

    public static TaxiStorage getInstance() {
        return INSTANCE;
    }
}
