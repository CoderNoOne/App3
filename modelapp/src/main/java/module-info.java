module modelapp {
  exports model.car to convertersapp, serviceapp, validatorapp, mainapp;
  exports model.sorting to mainapp, serviceapp;
  exports model.statistics to serviceapp;
  requires exceptionsapp;
  opens model.car to gson;
}