import models.Car;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import serdes.AppSerDes;


import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Application {

    public static void main(final String[] args)  {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "messageStream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 0);

        final StreamsBuilder builder = new StreamsBuilder();
        final KStream<String, Car> source = builder.stream(
                "ks.cars.input",
                Consumed.with(AppSerDes.String(), AppSerDes.Car())
                );

         source
                 .filter((k,v) -> v.getSpeed() > 100)
                 .groupBy(
                 (k,v) -> v.getCarNumber(),
                  Grouped.with(AppSerDes.String(), AppSerDes.Car())
                 )
                 .aggregate(
                 () -> 0,
                 // aggregator
                 (k, v, aggValue) -> aggValue + 1,
                 // Serializer
                 Materialized.with(AppSerDes.String(), AppSerDes.Integer())
              ).toStream()
                 .filter((k,v) -> v >= 3)
             .peek((k,v) -> System.out.println("car brand: "+ k))
                 .mapValues((m,v) -> m)
             .to(
                "ks.cars.output",
                 Produced.with(AppSerDes.String(), AppSerDes.String())
                );



        final Topology topology =  builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        System.out.println("Starting Kafka Streams application...");
        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }

        System.exit(0);
    }
}