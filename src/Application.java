package appli;

import java.util.*;
import math.*;

public class Application {

  public static String ajouterDevant(String str, char ajouterLui, char devantLui) {
    int nbrOccCharTrouve = 0;
    int pos = 0;
    int nbrOccChar = Application.nombreOccurenceCaractere(str, devantLui);
    while (nbrOccCharTrouve < nbrOccChar) {
      char currentChar = str.charAt(pos);
      if (currentChar == devantLui) {
        if (pos == 0) {
          str = ajouterLui + str;
        } else {
          String[] splitedStr = Application.split(str, pos);
          str = splitedStr[0] + ajouterLui + splitedStr[1];
        }
        nbrOccCharTrouve++;
        pos++;
      }
      pos++;
    }
    return str;
  }

  public static String[] split(String str, int pos) {
    String[] splitedStr = new String[2];
    splitedStr[0] = str.substring(0, pos);
    splitedStr[1] = str.substring(pos, str.length());
    return splitedStr;
  }

  public static int nombreOccurenceCaractere(String str, char caractere) {
    char[] strToCharArray = str.toCharArray();
    int nbrOccChar = 0;
    for (char c : strToCharArray) {
      if (c == caractere) {
        nbrOccChar++;
      }
    }
    return nbrOccChar;
  }




  public static void main(String[] args) {

    /*
    * Developper expression
    */

    // Scanner sc = new Scanner(System.in);
    // System.out.print("Votre Expression : E = ");
    // String expr = sc.nextLine();
    // String dev = Polynome.developper(expr);
    // System.out.println(dev);





    ///////////////////////////////////////
    ///////////////////////////////////////

    // Scanner s = new Scanner(System.in);
    // Scanner s2 = new Scanner(System.in);
    // String x = "", y = "";
    // List<Point> liste_point = new ArrayList<Point>();


    // Point p1 = new Point(7.5, 2.738613);
    // liste_point.add(p1);
    // Point p2 = new Point(9.1, 3.016621);
    // liste_point.add(p2);
    // Point p3 = new Point(12, 3.464102);
    // liste_point.add(p3);
    // Point p4 = new Point(3, 1.732051);
    // liste_point.add(p4);
    // Point p5 = new Point(1, 1);
    // liste_point.add(p5);


    /*
    * RACINES
    */

    // System.out.println("------Recherche des racines------");
    // System.out.print("Votre Polynome : P(x) = ");
    // Scanner sc = new Scanner(System.in);
    // String poly = sc.nextLine();
    // Polynome pol = Polynome.stringToPolynome(poly);
    // System.out.println(pol);
    // System.out.println("Saisissez un nombre proche de la racine recherché : ");
    // double racine = sc.nextDouble();
    // pol.racines();



    /*
    * DERIVEE
    */

    // List<Double> pol = new ArrayList<Double>();
    // System.out.print("Votre Polynome : P(x) = ");
    // String str = s.nextLine();
    // Polynome p = Polynome.stringToPolynome(str);
    // System.out.println("P'(x) = "+p.derivee().toString());


    /*
    * String to Polynome
    */

    // System.out.print("Votre Polynome : P(x) = ");
    // Scanner sc = new Scanner(System.in);
    // String poly = sc.nextLine();
    // Polynome pol = Polynome.stringToPolynome(poly);
    // System.out.println("P(x) = "+pol.toString());


    /*
    * Polynome de Newton
    */

    // System.out.println("\nEntrez n points et je vais trouver l'unique polynome passant par ces n points");
    // int i = 0;
    // int nbr_point = 0;
    // System.out.println("\nCombien de points voulez vous ?");
    // nbr_point = s.nextInt();
    // System.out.println();
    // while (i<nbr_point) {
    //   System.out.println("Point "+(i+1)+" : ");
    //   System.out.print("X = ");
    //   x = s2.nextLine();
    //   System.out.print("Y = ");
    //   y = s2.nextLine();
    //   liste_point.add(new Point(Double.parseDouble(x), Double.parseDouble(y)));
    //   System.out.println();
    //   i++;
    // }
    // System.out.println("P(x) = "+PolynomeDeNewton.getPolynomeDeNewton(liste_point)+"\n");


    /*
    * Divers méthodes
    */

    Scanner sc = new Scanner(System.in);
    System.out.print("Votre 1er Polynome : P(x) = ");
    String poly = sc.nextLine();
    Polynome pol = new Polynome(poly);
    System.out.println("P(x) = "+pol.toString());
    System.out.print("Votre 2eme Polynome : P(x) = ");
    String poly2 = sc.nextLine();
    Polynome pol2 = new Polynome(poly2);
    System.out.println("Q(x) = "+pol2.toString());
    Polynome p1 = pol.moins(pol2);
    Polynome p2 = pol.plus(pol2);
    Polynome p3 = pol.diviserPar(pol2);
    NombreX n1 = pol.getTermeDegreDominant();
    System.out.println("\n("+pol.toString()+")-("+pol2.toString()+") = "+p1.toString());
    System.out.println("\n("+pol.toString()+")+("+pol2.toString()+") = "+p2.toString());
    System.out.println("\n("+pol.toString()+")/("+pol2.toString()+") = "+p3.toString());
    System.out.println("\n("+pol.toString()+") : degré dominant = "+n1.toString());


  }
}
