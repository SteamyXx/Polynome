package appli;

import java.util.*;
import math.*;

public class PolynomeDeNewton {

  public static String getPolynomeDeNewton(List<Point> liste_point) {
    List<Double> polynomeDeNewton = new ArrayList<Double>();
    double pdd = 0;
    double denominateur = 0;
    int taille = 0;
    List<Point> points = new ArrayList<Point>();
    for (int k = 0; k<liste_point.size(); k++) {
      points.add(liste_point.get(k));
    }
    polynomeDeNewton.add(liste_point.get(0).getY());
    for (int j = 0; j<liste_point.size()-1; j++) {
      taille = points.size();
      for (int i = 0; i<taille-1; i++) {
        if (j == 0) {
          pdd = PolynomeDeNewton.premiereDiffDiv(points.get(i+1), points.get(i));
        } else {
          pdd = (points.get(i+1).getY() - points.get(i).getY()) / PolynomeDeNewton.denominateurPDD(liste_point.get(i+j+1), liste_point.get(i));
        }
        if (i == 0) {
          polynomeDeNewton.add(pdd);
        }
        denominateur = PolynomeDeNewton.denominateurPDD(liste_point.get(i+j+1), liste_point.get(i));
        points.remove(i);
        points.add(i, new Point(denominateur, pdd));
      }
      points.remove(taille-1);
    }
    Polynome p = new Polynome(polynomeDeNewton);
    String res = "";
    for (int i = 0; i<p.getPol().size(); i++) {
      if (p.getPol().get(i) != 0) {
        if (p.getPol().get(i) > 0 && i != 0) {
          res += "+";
        }
        res += p.getPol().get(i);
        for (int j = 0; j<i; j++) {
          if (liste_point.get(j).getX() >= 0) {
            res += "(x-"+liste_point.get(j).getX()+")";
          } else {
            res += "(x"+liste_point.get(j).getX()+")";
          }
        }
      }
    }
    return res;
  }

  public static List<Double> calculNewton(List<Double> p, List<Point> liste_point) {
    return null;
  }

  public static double premiereDiffDiv(Point p1, Point p2) {
    return (p1.getY()-p2.getY())/(p1.getX()-p2.getX());
  }

  public static double denominateurPDD(Point p1, Point p2) {
    return (p1.getX()-p2.getX());
  }
}
