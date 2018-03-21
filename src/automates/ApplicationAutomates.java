package automates;

import java.util.*;

public class ApplicationAutomates {

  public static void main(String[] args) {
    List<Integer> eFinaux = new ArrayList<Integer>();
    eFinaux.add(1);
    eFinaux.add(12);
    Automate automate = new Automate(17, eFinaux, "A_exprArithm.txt");
    Analyseur analyseur = new Analyseur();
    // Scanner s = new Scanner(System.in);
    // System.out.println("Veillez saisir une expression arithmétique :");
    // String heure = s.nextLine();
    // System.out.println(heure);
    String expression = "2 ^ 5 + 6 * 9";
    boolean analyse = analyseur.analyser(automate, expression);
    System.out.println((analyse) ? "OK" : "KO");
  }
}
