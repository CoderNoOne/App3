package service;

import exceptions.CarException;
import model.sorting.MySort;
import model.sorting.SortingCriterium;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserDataService {

  private Scanner sc = new Scanner(System.in);

  public int getInt(String message) {
    System.out.println(message);

    String text = sc.nextLine();
    if (!text.matches("[\\d]+")) {
      throw new CarException("INT VALUE IS NOT CORRECT: " + text);
    }

    return Integer.parseInt(text);
  }

  public BigDecimal getBigDecimal(String message) {
    System.out.println(message);

    String text = sc.nextLine();
    if (!text.matches("[\\d]+")) {
      throw new CarException("INPUT VALUE IS NOT CORRECT: " + text);
    }

    return new BigDecimal(text);
  }

  public MySort getSortingAlgorithm(String message) {

    List<String> sortingAlgorithms = Arrays.stream(SortingCriterium.values()).map(Enum::toString).collect(Collectors.toList());
    System.out.println(message);
    MySort.MySortBuilder builder = new MySort.MySortBuilder();

    while (true) {

      System.out.println("CHOOSE FROM ABOVE: " + sortingAlgorithms);
      String input = sc.nextLine().toUpperCase();

      if (!sortingAlgorithms.contains(input)) throw new CarException("BAD SORTING CRITERION");

      switch (input) {
        case "MODEL":
          String input1 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          sortingAlgorithms.remove("MODEL");
          builder = input1.equals("ASC") ? builder.model(true) : builder.model(false);
          break;
        case "PRICE":
          String input2 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          builder = input2.equals("ASC") ? builder.price(true) : builder.model(false);
          sortingAlgorithms.remove("PRICE");
          break;
        case "MILEAGE":
          String input3 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          builder = input3.equals("ASC") ? builder.mileage(true) : builder.mileage(false);
          sortingAlgorithms.remove("MILEAGE");
          break;
        case "COLOR":
          String input4 = getString("CHOOSE ORDERING. ASC/DESC").toUpperCase();
          builder = input4.equals("ASC") ? builder.color(true) : builder.color(false);
          sortingAlgorithms.remove("COLOR");
      }

      String input5 = getString("DO YOU WANT TO ADD NEW SORTING CRITERION? Y/N");
      if (!input5.equalsIgnoreCase("Y")) break;
    }
    return builder.build();
  }

  String getString(String inputMessage) {

    System.out.println(inputMessage);
    String input = sc.nextLine();

    if (input.length() == 0) {
      throw new CarException("YOU DIDN'T INPUT ANY VALUE");
    }
    return input;
  }

  public void close() {
    if (sc != null) {
      sc.close();
      sc = null;
    }
  }
}


