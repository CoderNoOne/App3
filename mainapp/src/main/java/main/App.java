package main;

import service.CarService;

public class App {

  public static void main(String[] args) {


    CarService carService = new CarService(".\\modelapp\\jsonFile.json");
    new MenuService(".\\modelapp\\jsonFile.json").mainMenu();

  }
}
