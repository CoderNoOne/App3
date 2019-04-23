package model.car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Car {

  private String model;
  private BigDecimal price;
  private Color color;
  private int mileage;
  private List<String> components;

  public Car(String model, BigDecimal price, Color color, int mileage, List<String> components) {
    this.model = model;
    this.price = price;
    this.color = color;
    this.mileage = mileage;
    this.components = components;
  }

  public Car() {
  }

  public String getModel() {
    return model;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Color getColor() {
    return color;
  }

  public int getMileage() {
    return mileage;
  }

  public List<String> getComponents() {
    return components;
  }

  public void setComponents(List<String> components) {
    this.components = components;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Car car = (Car) o;
    return mileage == car.mileage &&
            Objects.equals(model, car.model) &&
            Objects.equals(price, car.price) &&
            color == car.color &&
            Objects.equals(components, car.components);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, price, color, mileage, components);
  }

  @Override
  public String toString() {
    return "model: " + model + '\'' +
            ", price: " + price +
            ", color: " + color +
            ", mileage: " + mileage +
            ", components: " + components;
  }

}

