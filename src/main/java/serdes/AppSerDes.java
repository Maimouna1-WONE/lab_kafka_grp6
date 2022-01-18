package serdes;
import models.Car;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import java.util.HashMap;
import java.util.Map;

public class AppSerDes extends Serdes {

    static public final class CarSerde extends WrapperSerde<Car> {
        public CarSerde() { super(new JsonSerializer<>(), new JsonDeserializer<>());}
    }

    static  public Serde<Car> Car(){
        CarSerde serde =  new CarSerde();
        Map<String, Object> serdeConfigs = new HashMap<>();
        serdeConfigs.put(JsonDeserializer.VALUE_CLASS_NAME_CONFIG, Car.class);
        serde.configure(serdeConfigs, false);
        return serde;
    }
}
