package model.sorting;

import exceptions.CarException;
import model.car.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MySort {

  private List<Comparator<Car>> comparators;

  private MySort(MySortBuilder mySortBuilder) {
    comparators = mySortBuilder.comparators;
  }

  public Comparator<Car> getComparator() {

    if (comparators.isEmpty()) {
      throw new CarException("NO COMPARATORS AVAILABLE");
    }
    Comparator<Car> comparator = comparators.get(0);

    comparators.stream().skip(1).forEach(comparator::thenComparing);

    return comparator;
  }

  public static class MySortBuilder {

    private List<Comparator<Car>> comparators = new ArrayList<>();

    public MySortBuilder color(boolean isAscendingOrder) {
      comparators.add(isAscendingOrder ? Comparator.comparing(Car::getColor) : Comparator.comparing(Car::getColor).reversed());
      return this;
    }

    public MySortBuilder model(boolean isAscendingOrder) {
      comparators.add(isAscendingOrder ? Comparator.comparing(Car::getModel) : Comparator.comparing(Car::getModel).reversed());
      return this;
    }

    public MySortBuilder mileage(boolean isAscendingOrder) {
      comparators.add(isAscendingOrder ? Comparator.comparing(Car::getMileage) : Comparator.comparing(Car::getMileage).reversed());
      return this;
    }

    public MySortBuilder price(boolean isAscendingOrder) {
      comparators.add(isAscendingOrder ? Comparator.comparing(Car::getPrice) : Comparator.comparing(Car::getPrice).reversed());
      return this;
    }

    public MySort build() {
      return new MySort(this);
    }
  }
}
