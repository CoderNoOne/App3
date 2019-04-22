package exceptions;

public class CarException extends RuntimeException {

  private String exceptionMessage;

  public CarException(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }
}
