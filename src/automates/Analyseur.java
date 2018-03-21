/**
* Cette classe est une classe représentant un analyseur
*
* @author CHATEAU Julien
*@version 1.0
*/

package automates;

import java.util.*;
import java.io.*;

public class Analyseur {

  /**Constructeur de la classe Analyseur
  */
  public Analyseur() {

  }


  /**Méthode permettant d'analyser la chaîne de caractères "str"
  et savoir si elle respecte le paterne désigné par l'automate
  @param a , l'automate qui va analyser la chaîne de caractères
  @param str , la chaîne de caractères à analyser
  @return true si la chaîne de caractères respecte le paterne désigné par l'automate, false sinon
  */
  public boolean analyser(Automate a, String str) {
    System.out.println(str);
    Etat eCourant = a.getEtatInitial();
    if (eCourant != null) {
      System.out.print(eCourant.getName());
    }
    boolean stop = false;
    int i = 0;
    while (!stop) {
      String cCourant = String.valueOf(str.charAt(i));
      i++;
      eCourant = a.etatAssocie(eCourant, cCourant);
      if (eCourant != null) {
        System.out.print(", " + eCourant.getName());
      } else {
        System.out.print(", null");
      }
      stop = (eCourant == null) || (a.isEtatFinal(eCourant) && i == str.length() || i == str.length());
    }
    System.out.println();
    if (a.isEtatFinal(eCourant) && i == str.length()) {
      return true;
    } else {
      return false;
    }
  }
}
