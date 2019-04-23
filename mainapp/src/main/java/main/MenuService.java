package main;

import exceptions.CarException;
import model.car.Car;
import model.sorting.MySort;
import service.CarService;
import service.UserDataService;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class MenuService {
  private final CarService carService;
  private final UserDataService userDataService = new UserDataService();


  public MenuService(final String jsonFilename) {
    carService = new CarService(jsonFilename);
  }

  public void mainMenu() {
    menuOptions();
    while (true) {
      try {
        int option = userDataService.getInt("INPUT YOUR OPTION: ");
        switch (option) {
          case 1:
            option1();
            break;
          case 2:
            option2();
            break;
          case 3:
            option3();
            break;
          case 4:
            option4();
            break;
          case 5:
            option5();
            break;
          case 6:
            option6();
            break;
          case 7:
            option7();
            break;
          case 8:
            option8();
            break;
          case 9:
            option9();
            break;
          case 10:
            option10();
            break;
          case 11:
            userDataService.close();
            return;
          case 12:
            menuOptions();
            break;
          default:
            throw new CarException("INPUT OPTION IS NOT DEFINED");
        }

      } catch (CarException e) {
        System.out.println(e.getExceptionMessage());
        System.err.println(Arrays.toString(e.getStackTrace()));
      }
    }
  }

  private void option10() {
    carService.carsStatictics();
  }

  private void option9() {
    carService.mapByComponents().forEach((component, carList) -> {
      System.out.println("\nComponent:" + component +
              ":\n ");
      carList.forEach(car -> System.out.println("Car: " + car));
    });
  }

  private void option8() {
    System.out.println(carService.carsWithTheirComponentsSorted2());
  }

  private void option7() {

    MySort sortingAlgorithms = userDataService.getSortingAlgorithm("INPUT YOUR SORTING ALGORITHMS");

    carService.sort(sortingAlgorithms).forEach(car -> System.out.println("Car: " + car));

  }

  private void option6() {
    carService.colorMap().forEach((color, count) -> System.out.println("color:\t" + color + "\t->\tcount: " + count));

  }

  private void option5() {
    carService.getCars().forEach(car -> System.out.println("Car: " + car));
  }

  private void option4() {
    carService.mostExpensiveCarInSpecifiedModel().forEach((model, carOptional) -> {
      System.out.print(model + " -> ");
      carOptional.ifPresentOrElse(System.out::println, () -> System.out.println("FOR THAT MODEL NO CAR WAS FOUND"));
    });
  }

  private void option3() {
    carService.mostExpensiveCars().forEach(car -> System.out.println("Car: " + car));
  }

  private void option2() {

    BigDecimal minPrice = userDataService.getBigDecimal("INPUT MIN PRICE: ");

    BigDecimal maxPrice = userDataService.getBigDecimal("INPUT MAX PRICE: ");

    if (minPrice.compareTo(maxPrice) > 0) throw new CarException("MIN PRICE CANNOT BE GREATER THAN MAX PRICE");

    System.out.println(carService.carsWithPriceWithinRange(minPrice, maxPrice));
  }

  private void option1() {
    int mileage = userDataService.getInt("Input minimum mileage: ");
    System.out.println(carService.carsWithMileAgeGreaterThan(mileage));
  }

  private static void menuOptions() {

    String menu = MessageFormat.format(
            "\nOption no. 1 - {0}\n" +
                    "Option no. 2 - {1}\n" +
                    "Option no. 3 - {2}\n" +
                    "Option no. 4 - {3}\n" +
                    "Option no. 5 - {4}\n" +
                    "Option no. 6 - {5}\n" +
                    "Option no. 7 - {6}\n" +
                    "Option no. 8 - {7}\n" +
                    "Option no. 9 - {8}\n" +
                    "Option no. 10 - {9}\n" +
                    "Option no. 11 - {10}\n" +
                    "Option no. 12  -{11}",

            "List cars with mileage greater than specified",
            "Cars with price within range",
            "List of most expensive cars",
            "Map of most expensive cars in regards to car's model",
            "Show all cars",
            "Map <Color, Integer>",
            "Sort cars in respect to specified criterion with sorting order(asc/desc)",
            "Car collection with sorted components list",
            "Car Components",
            "Car Statistics",
            "Exit the program",
            "Show the menu options"
    );
    System.out.println(menu);
  }

}




