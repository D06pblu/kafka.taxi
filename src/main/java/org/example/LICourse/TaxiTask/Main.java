package org.example.LICourse.TaxiTask;

import org.example.LICourse.TaxiTask.consumers.TaxiCurrentDistanceConsumer;
import org.example.LICourse.TaxiTask.consumers.TaxiSignalsConsumer;
import org.example.LICourse.TaxiTask.producers.TaxiCurrentDistanceProducer;
import org.example.LICourse.TaxiTask.services.TaxiDistanceCounter;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TaxiSignalsConsumer taxiSignalsConsumer = new TaxiSignalsConsumer();
        TaxiDistanceCounter taxiDistanceCounter = new TaxiDistanceCounter();
        Map<String, Double> distances = taxiDistanceCounter.getAllDistance(taxiSignalsConsumer.getTaxiConsumerList());
        System.out.println("-*-*-*-*- \n"+distances);
        TaxiCurrentDistanceProducer taxiCastDistanceProducer = new TaxiCurrentDistanceProducer();
        taxiCastDistanceProducer.sendDataToTopic(distances);

        TaxiCurrentDistanceConsumer taxiCurrentDistanceConsumer = new TaxiCurrentDistanceConsumer();
        taxiCurrentDistanceConsumer.getTaxiCurrentDistances();
    }
}
