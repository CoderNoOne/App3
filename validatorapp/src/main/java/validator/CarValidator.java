package validator;

import model.car.Car;
import model.car.Color;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarValidator implements Validator <Car> {
  private Map<String, String> errors = new HashMap<>();

  public Map<String, String> validate(Car car) {
    errors.clear();

    if (car == null) {
      errors.put("car", "car object is null");
      return errors;
    }

    if (!isModelValid(car)) {
      errors.put("model", "model is not valid");
    }
    if (!areComponentsValid(car)) {
      errors.put("components", "components are not valid");
    }

    if (!isPriceValid(car)) {
      errors.put("price", "price is not valid");
    }

    if (!isMileageValid(car)) {
      errors.put("mileage", "mileage is not valid");
    }

    if (!isColorValid(car)) {
      errors.put("car", "car color is not valid");
    }
    return errors;
  }


  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  private boolean isMileageValid(Car car) {
    return car.getMileage() >= 0;
  }

  private boolean isModelValid(Car car) {

    return car.getModel().replaceAll("[A-Z]+|[\\s]+", "").length() == 0;
  }

  private boolean areComponentsValid(Car car) {

    return car.getComponents().stream()
            .flatMap(string -> Arrays.stream(string.split("")))
            .allMatch(string -> string.matches("[A-Z]|[\\s]"));
  }

  private boolean isPriceValid(Car car) {

    return car.getPrice().compareTo(BigDecimal.ZERO) >= 0;
  }

  private boolean isColorValid(Car car) {
    return Arrays.stream(Color.values()).anyMatch(color -> color.equals(car.getColor()));
  }
}
