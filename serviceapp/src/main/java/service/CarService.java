package service;

import converters.others.CarServiceConverter;
import exceptions.CarException;
import model.car.Car;
import model.car.Color;
import model.sorting.MySort;
import model.statistics.PriceCollector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class CarService {

  private final List<Car> cars;

  public CarService(final String jsonFilename) {
    cars = getCarsFromJson(jsonFilename);
  }

  private List<Car> getCarsFromJson(final String jsonFilename) {
    if (jsonFilename == null || !jsonFilename.matches(".+\\.json")) {
      throw new CarException("JSON FILE IS NOT CORRECT");
    }

    return new CarServiceConverter().toCarList(jsonFilename);
  }

  public List<Car> sort(MySort mySort) {

    return cars.stream()
            .sorted(mySort.getComparator())
            .collect(Collectors.toList());
  }

  public List<Car> carsWithMileAgeGreaterThan(int mileAge) {

    return cars.stream()
            .filter(car -> car.getMileage() > mileAge)
            .collect(Collectors.toList());
  }

  public Map<Color, Integer> colorMap() {

    return cars.stream()
            .collect(Collectors.groupingBy(Car::getColor, LinkedHashMap::new,
                    Collectors.summingInt(e -> 1)))
            .entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
            .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue)); /*toConcurrent?*/
  }

  public Map<String, Optional<Car>> mostExpensiveCarInSpecifiedModel() {

    return cars.stream()
            .collect(
                    Collectors.groupingBy(Car::getModel,
                            Collectors.reducing((car1, car2) -> car1.getPrice().compareTo(car2.getPrice()) >= 0 ? car1 : car2)));
  }


  public List<Car> mostExpensiveCars() {

    return cars.stream()
            .filter(car -> car.getPrice().compareTo(cars.stream().reduce((car1, car2) ->
                    car1.getPrice().compareTo(car2.getPrice()) > 0 ? car1 : car2).get().getPrice()) >= 0)
            .collect(Collectors.toList());
  }

  public List<Car> carsWithPriceWithinRange(BigDecimal minPrice, BigDecimal maxPrice) {
    return cars.stream()
            .filter(car -> car.getPrice().compareTo(minPrice) >= 0 && car.getPrice().compareTo(maxPrice) <= 0)
            .sorted(Comparator.comparing(Car::getModel))
            .collect(Collectors.toList());
  }


  public List<Car> carsWithTheirComponentsSorted2() {

    return cars
            .stream()
            .peek(car -> car.setComponents(car.getComponents().stream().sorted().collect(Collectors.toList())))
            .collect(Collectors.toList());

  }

  public Map<String, List<Car>> mapByComponents() {

    return cars.stream()
            .flatMap(car -> car.getComponents().stream())
            .distinct()
            .collect(Collectors.toMap(
                    component -> component,
                    component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())))
            .entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (v1, v2) -> v1,
                    LinkedHashMap::new));
  }


  public void carsStatictics() {

    String result = cars.stream()
            .map(car -> new Object[]{car.getMileage(), car.getPrice()})
            .collect(Collectors.collectingAndThen(Collectors.toList(),
                    list -> {
                      IntSummaryStatistics mileAgeStats = list.stream().map(el -> Integer.parseInt(String.valueOf(el[0]))).collect(Collectors.summarizingInt(in -> in));
                      PriceCollector.PriceSummary priceStats = list.stream().map(el -> new BigDecimal(String.valueOf(el[1]))).collect(new PriceCollector());
                      return "Mileage Statistics\n: MAX: " + mileAgeStats.getMax() + " MIN: " + mileAgeStats.getMin()
                              + " AVG: " + mileAgeStats.getAverage() +
                              "\nPrice Statistics\n: MAX: " + priceStats.getMax() + " MIN: " + priceStats.getMin() +
                              " AVG: " + priceStats.getSum().divide(BigDecimal.valueOf(priceStats.getN()), 2, RoundingMode.HALF_UP);
                    }));

    System.out.println(result);
  }

  @Override
  public String toString() {
    return cars.toString();
  }
}
