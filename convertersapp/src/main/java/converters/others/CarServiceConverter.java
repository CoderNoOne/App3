package converters.others;

import converters.json.CarJsonConverter;
import exceptions.CarException;
import model.car.Car;
import validator.CarValidator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CarServiceConverter {

  public List<Car> toCarList(String jsonFile) {

    if (jsonFile == null || !jsonFile.matches(".+\\.json"))
      throw new CarException("THE FILE SHOULD HAVE FORMAT OF .json");

    CarValidator carValidator = new CarValidator();
    AtomicInteger atomicInteger = new AtomicInteger(1);

    return new CarJsonConverter(jsonFile)
            .fromJson()
            .orElseThrow(() -> new CarException("FILE " + jsonFile + "is empty"))
            .stream()
            .filter(car -> {
              Map<String, String> errors = carValidator.validate(car);

              if (carValidator.hasErrors()) {
                System.out.println("CAR NO. " + atomicInteger.get());
                System.out.println(errors.entrySet().stream().map(e -> e.getKey() + " : " + e.getValue()).collect(Collectors.joining("\n")));
              }
              atomicInteger.incrementAndGet();

              return !carValidator.hasErrors();
            })
            .collect(Collectors.toList());
  }
}
