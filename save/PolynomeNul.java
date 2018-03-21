package math;

import java.util.*;

public class PolynomeNul {

  private List<Double> coefficients;
  private List<Double> racines;


  //indice i = degré
  public PolynomeNul(List<Double> coeff) {
    this.coefficients = coeff;
    this.racines = new ArrayList<Double>();
  }

  public PolynomeNul derivee() {
    List<Double> p2 = new ArrayList<Double>();
    for (int i = 1; i<this.getPol().size(); i++) {
      p2.add(this.getPol().get(i)*i);
    }
    PolynomeNul derivee = new PolynomeNul(p2);
    return derivee;
  }

  public List<Double> getPol() {
    return this.coefficients;
  }

  public double calculP(double x) {
    double res = 0;
    for (int i = 0; i<this.coefficients.size(); i++) {
      res += (this.coefficients.get(i) * Math.pow(x, i));
    }
    return res;
  }

  public double racine(double x) {
    System.out.println(x);
    for (int i = 0; i<10; i++) {
      x = x - (this.calculP(x)/this.derivee().calculP(x));
      // System.out.println(x);
    }
    // System.out.println("Une des racines de ce PolynomeNul est environ : "+x);
    return x;
  }

  public void racines() {
    double recherchedebut = -5000.0;
    double debut = 0.0;
    boolean stop = false;
    double rac = 0.0;
    double x = 0.0;
    double y = 0.0;
    while (recherchedebut < 5000.0) {
      stop = false;
      while (!stop && recherchedebut < 5000.0) {
        x = this.calculP(recherchedebut);
        recherchedebut += 0.005;
        y = this.calculP(recherchedebut);
        if ((x <= 0.0 && y >= 0.0) || (x >= 0.0 && y <= 0.0)) {
          debut = x;
          stop = true;
        }
      }
      rac = this.racine(debut);
      System.out.println(rac);
      if (!this.racines.contains(rac)) {
        recherchedebut = rac+0.005;
        this.racines.add(rac);
      }
    }
    System.out.println(this.racines);
  }

  public static PolynomeNul stringToPolynome(String str) {
    List<String> l = new ArrayList<String>();
    for (int i = 0; i<100; i++) {
      l.add("");
    }
    List<String> l2 = new ArrayList<String>();
    for (int i = 0; i<100; i++) {
      l2.add("");
    }
    List<Double> pol = new ArrayList<Double>();
    for (int i = 0; i<100; i++) {
      pol.add(0.0);
    }
    int i = 0;
    int cas = 0;
    char actuel = 'a';
    if (str.charAt(0) == '-') {
      l.add(0, String.valueOf(str.charAt(0)));
      i = 1;
      cas = 1;
    }
    actuel = str.charAt(i);
    while (i < str.length()) {
      while (actuel != '+' && actuel != '-' && i < str.length()) {
        l.set(cas, l.get(cas)+String.valueOf(actuel));
        i++;
        if (i < str.length()) {
          actuel = str.charAt(i);
        }
      }
      if (i < str.length()) {
        cas++;
        l.set(cas, l.get(cas)+String.valueOf(actuel));
        cas++;
        i++;
        actuel = str.charAt(i);
      }
    }
    i = 0;
    cas = 0;
    actuel = 'a';
    int it = 0;
    int taille = 0;
    int place = 0;
    while (!(l.get(i).equals(""))) {
      if (l.get(i).equals("+") || l.get(i).equals("-")) {
        l2.set(cas, l.get(i));
      } else {
        actuel = l.get(i).charAt(it);
        if (actuel == 'x') {
          l2.set(cas, l2.get(cas)+1);
        }
        while (actuel != 'x' && it < l.get(i).length()) {
          l2.set(cas, l2.get(cas)+String.valueOf(actuel));
          it++;
          if (it < l.get(i).length()) {
            actuel = l.get(i).charAt(it);
          }
        }
        if (actuel == 'x') {
          if (it < l.get(i).length()-1) {
            if (l.get(i).charAt(it+1) == '^') {
              int iteration = 2;
              String exposant = String.valueOf(l.get(i).charAt(it+iteration));
              while (it+iteration+1 < l.get(i).length()) {
                iteration++;
                exposant += String.valueOf(l.get(i).charAt(it+iteration));
              }
              place = Integer.parseInt(exposant); // on considere que le degré est inférieur à 10: à modifier si nécessaire
              pol.set(place, Double.parseDouble(l2.get(cas)));
            }
          } else {
            pol.set(1, Double.parseDouble(l2.get(cas)));
          }
        } else {
          pol.set(0, Double.parseDouble(l2.get(cas)));
        }
        cas++;
      }
      it = 0;
      i++;
    }
    int m = pol.size()-1;
    while (pol.get(m) == 0.0 && m>0) {
      pol.remove(m);
      m--;
    }
    PolynomeNul res = new PolynomeNul(pol);
    return res;
  }

  public String toString() {
    String str = "";
    for (int i = 0; i<this.coefficients.size(); i++) {
      String str2 = "";
      if (this.coefficients.get(i) > 0) {
        if (this.coefficients.get(i) == 1 && i != 0) {
          str2 += "+";
        } else {
          str2 += "+"+this.coefficients.get(i);
        }
      } else if (this.coefficients.get(i) < 0) {
        if (this.coefficients.get(i) == -1 && i != 0) {
          str2 += "-";
        } else {
          str2 += this.coefficients.get(i);
        }
      }
      if (this.coefficients.get(i) != 0) {
        if (i == 0 && this.coefficients.size() > 0) {
          str = str2;
        } else if (i == 1 && this.coefficients.size() > 1) {
          str = str2 + "x" + str;
        } else if (i == 2 && this.coefficients.size() > 2) {
          str = str2 + "x²" + str;
        } else if (i == this.coefficients.size()-1) {
          str = str2 + "x^" + i + str;
        } else {
          str = str2 + "x^" + i + str;
        }
      }
    }
    if (this.coefficients.get(this.coefficients.size()-1) < 0) {
      return str;
    } else {
      return str.substring(1, str.length());
    }
  }
}
