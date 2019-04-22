package converters.json;

import model.car.Car;

import java.util.List;

public class CarJsonConverter extends JsonConverter<List<Car>> {

  public CarJsonConverter(String jsonFilename) {
    super(jsonFilename);
  }
}
