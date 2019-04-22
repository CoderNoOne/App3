module convertersapp {
  requires gson;
  requires java.sql;
  requires exceptionsapp;
  requires modelapp;
  requires validatorapp;
  exports converters.others to serviceapp;
}