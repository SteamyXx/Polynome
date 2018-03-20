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

  public static String retirerPuissance(String expression) {
    int posPuissance = expression.indexOf("^");
    int cmp = 0;
    char currentChar = '0';
    int nbrParenthOuvreTrouve = 0;
    String facteurGauche = "";
    String remplacement = "";
    int puissance = 0;
    while (posPuissance != -1) {
      if (expression.charAt(posPuissance-1) == ')') {
        currentChar = '0';
        nbrParenthOuvreTrouve = 0;
        facteurGauche = "";
        puissance = Integer.parseInt(String.valueOf(expression.charAt(posPuissance+1)));
        remplacement = "";
        cmp = posPuissance-2;
        while (currentChar != '(' || nbrParenthOuvreTrouve != 0) {
          currentChar = expression.charAt(cmp);
          System.out.println(currentChar);
          if (currentChar == ')') {
            nbrParenthOuvreTrouve++;
          } else if (currentChar != '(') {
            facteurGauche = currentChar + facteurGauche;
          } else if (currentChar == '(' && nbrParenthOuvreTrouve >= 1) {
            nbrParenthOuvreTrouve--;
          }
          cmp--;
        }
        System.out.println(facteurGauche);
        for (int i = 0; i<puissance-1; i++) {
          remplacement += "("+facteurGauche+")";
        }
        System.out.println("puissance : "+puissance);
        expression = expression.replace("^"+puissance, remplacement);
        System.out.println("expression : "+expression);
        posPuissance = expression.indexOf("^");
      } else {
        posPuissance = expression.indexOf("^", posPuissance+1);
      }
    }
    return expression;
  }

  public static String developper(String expression) {
    System.out.println("entrée : "+expression);
    expression = Application.retirerPuissance(expression);
    int posParenth = expression.indexOf("(");
    if (posParenth == 0) {
      posParenth = expression.indexOf("(", 1);
    }
    if (posParenth != -1) {
      if (expression.charAt(posParenth-1) != '+' && expression.charAt(posParenth-1) != '-') {
        String facteurGauche = "";
        String facteurDroit = "";
        String resteGauche = "";
        String resteDroit = "";
        char currentChar = '0';
        int nbrParenthOuvreTrouve = 0;
        int cmp = posParenth+1;
        while (currentChar != ')' || nbrParenthOuvreTrouve != 0) {
          currentChar = expression.charAt(cmp);
          if (currentChar == '(') {
            nbrParenthOuvreTrouve++;
          } else if (currentChar != ')') {
            facteurDroit += currentChar;
          } else if (currentChar == ')' && nbrParenthOuvreTrouve >= 1) {
            nbrParenthOuvreTrouve--;
          }
          cmp++;
        }
        System.out.println("facteurDroit : "+facteurDroit);
        resteDroit = Application.split(expression, cmp)[1];
        currentChar = '0';
        nbrParenthOuvreTrouve = 0;
        cmp = posParenth-1;
        if (expression.charAt(posParenth-1) == ')') {
          while (currentChar != '(' || nbrParenthOuvreTrouve != 0) {
            currentChar = expression.charAt(cmp);
            if (currentChar == ')') {
              nbrParenthOuvreTrouve++;
            } else if (currentChar != '(') {
              facteurGauche = currentChar + facteurGauche;
            } else if (currentChar == '(' && nbrParenthOuvreTrouve >= 1) {
              nbrParenthOuvreTrouve--;
            }
            if (currentChar != '(' || nbrParenthOuvreTrouve != 0) {
              cmp--;
            }
          }
        } else {
          while (currentChar != '+' && currentChar != '-' && cmp >= 0) {
            currentChar = expression.charAt(cmp);
            if (currentChar != '+' && currentChar != '-') {
              facteurGauche = currentChar + facteurGauche;
            }
            cmp--;
          }
        }
        System.out.println("facteurGauche : "+facteurGauche);
        resteGauche = Application.split(expression, cmp)[0];
        System.out.println("resteGauche : "+resteGauche);
        System.out.println("resteDroit : "+resteDroit);
        facteurGauche = Application.developper(facteurGauche);
        facteurDroit = Application.developper(facteurDroit);
        Polynome fg = new Polynome(facteurGauche);
        Polynome fd = new Polynome(facteurDroit);
        Polynome fgxfd = new Polynome(fg.developperEtReduire(fd));
        if (resteDroit.charAt(0) != '(') {
          expression = resteGauche + fgxfd.toString() + resteDroit;
        } else {
          expression = resteGauche + "(" + fgxfd.toString() + ")" + resteDroit;
        }
      }
    }
    System.out.println("expression avant test de récurrence : "+expression);
    if (!expression.contains("(") || (Application.nombreOccurenceCaractere(expression, '(') == 1 && expression.charAt(0) == '(') && expression.charAt(expression.length()-1) == ')') {
      Polynome res = new Polynome(Polynome.reduire(Polynome.stringToListeNombreX(expression)));
      expression = res.toString();
    } else {
      expression = Application.developper(expression);
    }
    System.out.println("return : "+expression);
    return expression;
  }


  public static void main(String[] args) {

    /*
    * Developper expression
    */

    Scanner sc = new Scanner(System.in);
    System.out.print("Votre Expression : E = ");
    String expr = sc.nextLine();
    String dev = Application.developper(expr);
    System.out.println(dev);

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
    * String to Polynome (new)
    */

    // Scanner sc = new Scanner(System.in);
    // System.out.print("Votre 1er Polynome : P(x) = ");
    // String poly = sc.nextLine();
    // Polynome pol = new Polynome(poly);
    // System.out.println("P(x) = "+pol.toString());
    // System.out.print("Votre 2eme Polynome : P(x) = ");
    // String poly2 = sc.nextLine();
    // Polynome pol2 = new Polynome(poly2);
    // System.out.println("P'(x) = "+pol2.toString());
    // Polynome devEtRed = new Polynome(pol.developperEtReduire(pol2));
    // System.out.println("\n("+pol.toString()+")*("+pol2.toString()+") = "+devEtRed.toString());


  }
}
