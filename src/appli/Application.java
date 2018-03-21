package appli;

import java.util.*;
import math.*;

public class Application {

  private static boolean verbose = false;

  public static boolean getVerbose() {
    return Application.verbose;
  }

  public static void setVerbose(boolean newVerbose) {
    Application.verbose = newVerbose;
  }

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

  public static void clearScreen() {
      System.out.print("\033[H\033[2J");
      System.out.flush();
  }

  public static void afficherDebug(String debug) {
    if (Application.verbose) {
      System.out.println(debug);
    }
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    if (args.length > 2) {
      System.out.println("Erreur : Trop de paramètres (utilisez -h ou --help pour connaître les paramètres possibles)");
    } else if (args.length == 0) {
      System.out.println("Erreur : Aucun paramètre (utilisez -h ou --help pour connaître les paramètres possibles)");
    } else if (args.length == 1 && (args[0].equals("-v") || args[0].equals("--verbose"))) {
      System.out.println("Erreur : le paramètre "+args[0]+" ne peut être utilisé seul");
    } else {
      String demande = "";
      boolean erreur = false;
      if (args.length == 1) {
        demande = args[0];
      } else if (args[0].equals("-v") || args[0].equals("--verbose")) {
        demande = args[1];
        Application.setVerbose(true);
      } else if (args[1].equals("-v") || args[1].equals("--verbose")) {
        demande = args[0];
        Application.setVerbose(true);
      } else {
        System.out.println("Erreur : le deuxième paramètre ne peut être que -v ou --verbose");
        erreur = true;
      }

      if (!erreur) {
        if (demande.equals("--dvlpExprArtih") || demande.equals("-dea")) {
          Application.clearScreen();
          System.out.println();
          /*
          * DEVELOPPEMENT D'UNE EXPRESSION ARITHMETIQUE
          */
          System.out.println("  ------ Développer une expression arithmétique ------\n\n");
          System.out.print("  Votre expression : E(x) = ");
          String expr = sc.nextLine();
          String dev = Polynome.developper(expr);
          System.out.println("\n  E(x) = "+dev);
        } else if (demande.equals("--racines") || demande.equals("-r")) {
          Application.clearScreen();
          System.out.println();
          /*
          * RACINES
          */
          System.out.println("  ------ Rechercher les racines d'un polynome ------\n\n");
          System.out.println("EN DEVELOPPEMENT");
          // System.out.print("  Votre Polynome : P(x) = ");
          // Scanner sc = new Scanner(System.in);
          // String poly = sc.nextLine();
          // Polynome pol = Polynome.stringToPolynome(poly);
          // System.out.println(pol);
          // System.out.println("  Saisissez un nombre proche de la racine recherché : ");
          // double racine = sc.nextDouble();
          // pol.racines();
        } else if (demande.equals("--deriver") || demande.equals("-dr")) {
          Application.clearScreen();
          System.out.println();
          /*
          * DERIVEE
          */
          System.out.println("  ------ Dériver un polynome ------  \n\n");
          System.out.print("  Votre polynome : P(x) = ");
          String str = sc.nextLine();
          Polynome p = new Polynome(str);
          System.out.println("\n  P'(x) = "+p.derive().toString());
        } else if (demande.equals("--newtonPol") || demande.equals("-np")) {
          Application.clearScreen();
          System.out.println();
          /*
          * Polynome de Newton
          */
          System.out.println("  ------ Trouver le polynome passant par n points ------  \n\n");
          Scanner s = new Scanner(System.in);
          Scanner s2 = new Scanner(System.in);
          String x = "", y = "";
          List<Point> liste_point = new ArrayList<Point>();
          List<String> liste_x = new ArrayList<String>();
          int i = 0;
          int nbr_point = 0;
          System.out.print("  Combien de points ?  ");
          nbr_point = s.nextInt();
          System.out.println();
          while (i<nbr_point) {
            System.out.println("  Point "+(i+1)+" : ");
            System.out.print("  X = ");
            x = s2.nextLine();
            while (liste_x.contains(x)) {
              System.out.println("\n  Un polynome ne peut pas passer par deux points ayant les mêmes abscisses !!!");
              System.out.print("  X = ");
              x = s2.nextLine();
            }
            System.out.print("  Y = ");
            y = s2.nextLine();
            liste_x.add(x);
            liste_point.add(new Point(Double.parseDouble(x), Double.parseDouble(y)));
            System.out.println();
            i++;
          }
          String polNewton = PolynomeDeNewton.getPolynomeDeNewton(liste_point);
          String polNewtonDev = Polynome.developper(polNewton);
          System.out.println("  Forme factorisée : P(x) = "+polNewton+"\n");
          System.out.println("  Forme développée : P(x) = "+polNewtonDev+"\n");
          System.out.println("\n\nVérification : ");
          Polynome polNewt = new Polynome(polNewtonDev);
          for (Point p : liste_point) {
            System.out.println("P("+p.getX()+") = "+polNewt.calculP(p.getX()));
          }
        } else if (demande.equals("--diviser") || demande.equals("-dv")) {
          Application.clearScreen();
          System.out.println();
          /*
          * DIVISER UN POLYNOME PAR UN AUTRE
          */
          System.out.println("  ------ Diviser un polynome par un autre ------ \n\n");
          System.out.print("  Votre polynome numérateur :     N(x) = ");
          String poly = sc.nextLine();
          Polynome pol = new Polynome(poly);
          System.out.print("  Votre polynome dénominateur :   D(x) = ");
          String poly2 = sc.nextLine();
          Polynome pol2 = new Polynome(poly2);
          Polynome[] p3 = pol.diviserPar(pol2);
          System.out.println("\n\n  N(x) = ("+p3[0].toString()+") * D(x) + "+p3[1].toString()+" : \n");
          System.out.println("\n  Quotient = "+p3[0].toString());
          System.out.println("  Reste = "+p3[1].toString());
        } else if (demande.equals("--help") || demande.equals("-h")) {
          System.out.println("Paramètres possibles : \n");
          System.out.println("-dea, --dvlpExprArtih");
          System.out.println("              Permet de développer un expression arithmétique");
          System.out.println("-r, --racines");
          System.out.println("              Permet de calculer les racines d'un polynome");
          System.out.println("-dr, --deriver");
          System.out.println("              Permet de calculer la dérivée d'un polynome");
          System.out.println("-np, --newtonPol");
          System.out.println("              Permet de calculer le polynome passant par n points (utilisation de la formule de Newton)");
          System.out.println("-dv, --diviser");
          System.out.println("              Permet de calculer la division d'un polynome par un autre");
          System.out.println("-h, --help");
          System.out.println("              Permet de connaître les paramètres utilisables");
        } else {
          System.out.println("Erreur : Paramètre erroné (utilisez -h ou --help pour connaître les paramètres possibles)");
        }
      }

      // Polynome p1 = pol.moins(pol2);
      // Polynome p2 = pol.plus(pol2);
      // NombreX n1 = pol.getTermeDegreDominant();
      // Polynome derive = pol.derive();
      // System.out.println("\n("+pol.toString()+")-("+pol2.toString()+") = "+p1.toString());
      // System.out.println("\n("+pol.toString()+")+("+pol2.toString()+") = "+p2.toString());
      // System.out.println("\n("+pol.toString()+")/("+pol2.toString()+") = Quotient : "+p3[0].toString()+" Reste : "+p3[1].toString());
      // System.out.println("\n("+pol.toString()+") : degré dominant = "+n1.toString());
      // System.out.println("\n("+pol.toString()+") : derive = "+derive.toString());
    }
  }
}
