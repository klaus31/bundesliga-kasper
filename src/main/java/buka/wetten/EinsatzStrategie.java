package buka.wetten;

public interface EinsatzStrategie {

  Zahlung getEmpfohlenenEinsatz(Zahlung partieBudget);
}
