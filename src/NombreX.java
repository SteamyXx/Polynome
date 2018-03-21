package math;

import java.util.*;
import java.lang.*;

public class NombreX implements Comparable<NombreX> {

  private double coeff;
  private int degreX;

  public NombreX(double coeff, int degreX) {
    this.coeff = coeff;
    this.degreX = degreX;
  }

  public NombreX(double coeff) {
    this.coeff = coeff;
    this.degreX = 0;
  }

  public NombreX(String nbr) {
    String[] splitedNbr = nbr.split("x");
    if (nbr.contains("x")) {
      if (nbr.charAt(0) == 'x') {
        this.coeff = 1;
      } else if (nbr.substring(0, 2).equals("-x")) {
        this.coeff = -1;
      } else {
        if (splitedNbr[0].contains("/")) {
          String[] splitedCoeff = splitedNbr[0].split("/");
          this.coeff = Double.parseDouble(splitedCoeff[0]) / Double.parseDouble(splitedCoeff[1]);
        } else {
          this.coeff = Double.parseDouble(splitedNbr[0]);
        }
      }
      if (nbr.charAt(nbr.length()-1) == 'x') {
        this.degreX = 1;
      } else {
        this.degreX = Integer.parseInt(splitedNbr[1].substring(1, splitedNbr[1].length()));
      }
    } else {
      if (nbr.contains("/")) {
        String[] splitedCoeff = nbr.split("/");
        this.coeff = Double.parseDouble(splitedCoeff[0]) / Double.parseDouble(splitedCoeff[1]);
      } else {
        this.coeff = Double.parseDouble(nbr);
      }
      this.degreX = 0;
    }
  }

  public NombreX inverse() {
    return new NombreX(-this.coeff, this.degreX);
  }

  public double calculP(int x) {
    return this.coeff * Math.pow(x, this.degreX);
  }

  public double getCoeff() {
    return this.coeff;
  }

  public int getDegreX() {
    return this.degreX;
  }

  public void setCoeff(int newCoeff) {
    this.coeff = newCoeff;
  }

  public void setDegreX(int newDegreX) {
    this.degreX = newDegreX;
  }

  public NombreX fois(NombreX nx) {
    return new NombreX(this.getCoeff() * nx.getCoeff(), this.getDegreX() + nx.getDegreX());
  }

  public NombreX derive() {
    return new NombreX(this.getCoeff()*this.getDegreX(), this.getDegreX()-1);
  }

  //NombreX(0, -1) ----> l'opération ne s'est pas passé comme prévu
  public NombreX plus(NombreX nx) {
    if (nx.getDegreX() == this.getDegreX()) {
      return new NombreX(this.getCoeff() + nx.getCoeff(), this.getDegreX());
    } else {
      return new NombreX(0, -1);
    }
  }

  //<>< Décroissant -------- ><> Croissant
  public int compareTo(NombreX nbr) {
    if (this == nbr) {
      return 0;
    } else if (this.equals(nbr)) {
      return 0;
    } else if (this.getDegreX() < nbr.getDegreX()) {
      return 1;
    } else if (this.getDegreX() > nbr.getDegreX()) {
      return -1;
    } else {
      if (this.getCoeff() > nbr.getCoeff()) {
        return 1;
      } else {
        return -1;
      }
    }
  }

  public boolean equals(NombreX nbr) {
    if (this == nbr) {
      return true;
    } else if (this.getCoeff() == nbr.getCoeff() && this.getDegreX() == nbr.getDegreX()) {
      return true;
    } else {
      return false;
    }
  }

  public String toString() {
    if (this.coeff == 0) {
      return "";
    } else if (this.coeff == 1 && this.degreX == 1) {
      return "x";
    } else if (this.coeff == -1 && this.degreX == 1) {
      return "-x";
    } else if (this.coeff == 1 && this.degreX > 1) {
      return "x^" + this.degreX;
    } else if (this.coeff == -1 && this.degreX > 1) {
      return "-x^" + this.degreX;
    } else if (this.degreX == 0) {
      return "" + this.coeff;
    } else if (this.degreX == 1) {
      return this.coeff + "x";
    } else {
      return this.coeff + "x^" + this.degreX;
    }
  }
}
