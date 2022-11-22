package org.example.LICourse.TaxiTask;

import java.util.Map;

public class Taxi {
    private String id;
    private double latitude;
    private double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Taxi(String id) {
        this.id = id;
        this.latitude = setInitialLatitude();
        this.longitude = setInitialLongitude();
    }

    public Taxi(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id='" + id + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public double setInitialLatitude() {
        double delta = ((Math.random() * 1000) + (Math.random() * 100) + (Math.random() * 10)) / 1000;
        double initialLatitude;
        if (delta > 0.5) {
            initialLatitude = 53.5200 + delta;
        } else {
            initialLatitude = 53.5200 - delta;
        }
        return initialLatitude;
    }

    public double setInitialLongitude() {
        double delta = ((Math.random() * 1000) + (Math.random() * 100) + (Math.random() * 10)) / 1000;
        double initialLongitude;
        if (delta > 0.5) {
            initialLongitude = 27.2600 + delta;
        } else {
            initialLongitude = 27.2600 - delta;
        }
        return initialLongitude;
    }

    public static void main(String[] args) {
        TaxiSignalsConsumer taxiSignalsConsumer = new TaxiSignalsConsumer();
        TaxiDistanceCounter taxiDistanceCounter = new TaxiDistanceCounter();
        Map<String, Double> distances = taxiDistanceCounter.getAllDistance(taxiSignalsConsumer.getTaxiConsumerList());
        System.out.println("-*-*-*-*- \n"+distances);
        TaxiLastDistanceProducer taxiLastDistanceProducer = new TaxiLastDistanceProducer();
        taxiLastDistanceProducer.sendDataToTopic(distances);

    }
}
