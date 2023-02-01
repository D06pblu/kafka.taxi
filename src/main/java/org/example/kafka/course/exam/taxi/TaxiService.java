package org.example.kafka.course.exam.taxi;

public class TaxiService {

    public static double updateDistance(Taxi taxi) {
        TaxiStorage taxiStorage = TaxiStorage.getInstance();
        Taxi oldTaxiPosition = taxiStorage.getTaxi(taxi.getId());
        double newTaxiDistance = taxiStorage.getTaxiDistance(taxi.getId()) + calculateDistance(oldTaxiPosition, taxi);
        taxiStorage.updateTaxi(taxi, newTaxiDistance);
        return newTaxiDistance;
    }

    private static double calculateDistance(Taxi oldTaxiPosition, Taxi newTaxiPosition) {
        double latitudeDelta = Math.abs(newTaxiPosition.getLatitude()-oldTaxiPosition.getLatitude());
        double longitudeDelta = Math.abs(newTaxiPosition.getLongitude()-oldTaxiPosition.getLongitude());
        double distance = Math.hypot(latitudeDelta, longitudeDelta)*111D;

        return distance;
    }
}
