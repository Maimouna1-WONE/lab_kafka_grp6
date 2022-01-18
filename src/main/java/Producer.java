import com.fasterxml.jackson.databind.ObjectMapper;
import models.Car;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import serdes.JsonSerializer;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


public class Producer {
    public static void main(String args[]){
        String bootstrapServers = "127.0.0.1:9092";
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        Car[] cars = new Car[0];
        ObjectMapper mapper = new ObjectMapper();
        try {
            cars = mapper.readValue(new File("C:\\atos labs\\kafka lab\\src\\main\\java\\cars_data.json"), Car[].class);
            System.out.println(cars[1].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        KafkaProducer<String, Car> producer =  new KafkaProducer<String, Car>(props);

        for(int i = 0; i < cars.length; i++){
            ProducerRecord<String, Car> record =  new ProducerRecord<String, Car>("ks.cars.input", cars[i]);
            producer.send(record);
            producer.flush();
            System.out.println("passage voiture :" + cars[i].getCarNumber() + " avec la vitesse de :" + cars[i].getSpeed());
        }
        producer.close();

    }
}
