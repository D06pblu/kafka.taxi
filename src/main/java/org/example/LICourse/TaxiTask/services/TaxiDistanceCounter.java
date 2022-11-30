package org.example.LICourse.TaxiTask.services;

import org.example.LICourse.TaxiTask.producers.TaxiSignalsProducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxiDistanceCounter {
    //взять полный List всех передвижений и разделить на отедльные таксишки с сохранением порядка движения
    public List<Taxi> getSeparateTaxiPath(List<Taxi> oldTaxiList, int taxiIDNumber){
        List<Taxi> tmpList = new ArrayList<>();

        for (Taxi taxi : oldTaxiList) {
            if (taxi.getId().equals("id" + taxiIDNumber)) {
                tmpList.add(taxi);
            }
        }
        System.out.println("-*-*-*-*-*-\nParsed all movements. " +
                "Taxi id"+ taxiIDNumber +" has such movement list:\n" + tmpList.toString());
        return tmpList;
    }

    //взять движение таксишек, посчитать сколько км пройдено между каждой точкой, сложить это последовательно
    public Map<String,Double> getAllDistance(List<Taxi> oldTaxiList){
        Map<String,Double> allDistances = new HashMap<>();

        for(int n = 0; n< TaxiSignalsProducer.n; n++){
            double path = 0;
            List<Taxi> tmpList1 = getSeparateTaxiPath(oldTaxiList, n);

            for(int j=0; j<tmpList1.size()-1; j++){
                float deltaPath; // с double слишком много знаков после запятой, смотреть сложно
                double longitudeDelta = tmpList1.get(j + 1).getLongitude() - tmpList1.get(j).getLongitude();
                double latitudeDelta = tmpList1.get(j + 1).getLatitude() - tmpList1.get(j).getLatitude();
                deltaPath = (float) ((Math.hypot(latitudeDelta, longitudeDelta))*111);
                path +=deltaPath;
                allDistances.put("id"+n, path);
                System.out.println("Taxi id" + n + " moved another " + deltaPath +"km. Total distance = "+ (float) path+"km.");
            }
        }
        return allDistances;
    }
}
