package ilya.example.TaxiTask;

import ilya.example.TaxiTask.log.LoggingConsumer;
import ilya.example.TaxiTask.log.LoggingProducer;
import ilya.example.TaxiTask.taxi.TaxiConsumer;
import ilya.example.TaxiTask.taxi.TaxiProducer;
import org.example.LICourse.TaxiTask.services.Taxi;
import ilya.example.TaxiTask.taxi.TaxiStorage;

public class Main {

    public static void main(String[] args) throws Exception {
        LoggingProducer loggingProducer = new LoggingProducer();
        TaxiProducer taxiProducer = new TaxiProducer();

        Thread taxiConsumer = new Thread(new TaxiConsumer(loggingProducer));
        Thread loggingConsumer = new Thread(new LoggingConsumer(loggingProducer));

        taxiConsumer.start();
        loggingConsumer.start();

        Thread.sleep(1000 * 20); // Спим 20 секунд, чтобы продьюсеры и консьюмеры расчехлились

        for (int i = 0; i < 25; i++) {
            Taxi taxi = new Taxi(Integer.toString(i));
            TaxiStorage.getInstance().putTaxi(taxi);
            taxiProducer.sendDataToTopic(taxi);
        }

        // Делаем тот же самый цикл чтобы у такси сгенерировались новые latitude и longitude
        for (int i = 0; i < 25; i++) {
            Taxi taxi = new Taxi(Integer.toString(i));
            TaxiStorage.getInstance().putTaxi(taxi);
            taxiProducer.sendDataToTopic(taxi);
        }

        taxiConsumer.interrupt();
        loggingConsumer.interrupt();

        loggingProducer.close();
        taxiProducer.close();

        TaxiStorage.getInstance().print();
    }
}
