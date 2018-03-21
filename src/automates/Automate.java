/**
* Cette classe est une classe représentant un automate
*
* @author CHATEAU Julien
*@version 1.0
*/

package automates;

import java.util.*;
import java.io.*;

public class Automate {

  private List<String> alphabet;
  private List<Etat> etats;
  private Etat eInitial;
  private List<Etat> eFinaux;

  /** Constructeur de la classe Automate
  @param nbrEtat , le nombre d'état de l'automate
  On considère que l'état inital est toujours le premier créé dans la liste d'états
  @param eFinaux , la liste des numéro des états finaux de l'automate
  @param name , le nom du fichier décrivant l'automate
  */
  public Automate(int nbrEtat, List<Integer> eFinaux, String name) {
    this.alphabet = new ArrayList<String>();
    this.etats = new ArrayList<Etat>();
    for (int i = 0; i<nbrEtat; i++) {
      this.etats.add(new Etat(i));
    }
    this.eInitial = this.etats.get(0);
    this.eFinaux = new ArrayList<Etat>();
    for (int j = 0; j<eFinaux.size(); j++) {
      this.eFinaux.add(this.etats.get(eFinaux.get(j)));
    }
    this.remplirAutomate(name);
  }


  /** Constructeur de la classe Automate
  @param nbrEtat , le nombre d'état de l'automate
  On considère que l'état inital est toujours le premier créé dans la liste d'états
  @param eFinal , le numéro de l'état final de l'automate
  @param name , le nom du fichier décrivant l'automate
  */
  public Automate(int nbrEtat, int eFinal, String name) {
    this.alphabet = new ArrayList<String>();
    this.etats = new ArrayList<Etat>();
    for (int i = 0; i<nbrEtat; i++) {
      this.etats.add(new Etat(i));
    }
    this.eInitial = this.etats.get(0);
    this.eFinaux = new ArrayList<Etat>();
    this.eFinaux.add(this.etats.get(eFinal));
    this.remplirAutomate(name);
  }


  /** Méthode qui permet de donner à chaque état de l'automate, ses associations
  @param name , le nom du fichier décrivant l'automate
  */
  public void remplirAutomate(String name) {
    try {
      Scanner s = new Scanner(new File("data/"+name));
      String alphabet = s.nextLine();
      int i = 0;
      while (i < alphabet.length()) {
        this.alphabet.add(String.valueOf(alphabet.charAt(i)));
        i = i + 2;
      }
      while (s.hasNextLine()) {
        String line = s.nextLine();
        int j = 0;
        String gauche = "";
        String droite = "";
        char courant = line.charAt(j);
        while (courant != ' ') {
          gauche += String.valueOf(courant);
          j++;
          courant = line.charAt(j);
        }
        j++;
        String milieu = String.valueOf(line.charAt(j));
        j = j + 2;
        courant = line.charAt(j);
        while (j < line.length()) {
          droite += String.valueOf(courant);
          j++;
          if (j < line.length()) {
            courant = line.charAt(j);
          }
        }
        int eNumero = Integer.parseInt(gauche);
        int eNumero2 = Integer.parseInt(droite);
        this.etats.get(eNumero).addKeyValue(milieu, this.etats.get(eNumero2));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  /** Méthode qui permet de récupérer l'état associé à l'état courant avec la chaîne de caractères "str"
  @param eCourant , l'état courant
  @param str , la chaîne de caractère désignée
  @return l'état associé à l'état courant avec la chaîne de caractères "str"
  */
  public Etat etatAssocie(Etat eCourant, String str) {
    return this.etats.get(eCourant.getNumero()).etatAssocie(str);
  }


  /** Méthode qui permet de savoir si l'état est un état final
  @param eCourant , l'état courant
  @return true si l'état est un état final, false sinon
  */
  public boolean isEtatFinal(Etat eCourant) {
    return this.eFinaux.contains(eCourant);
  }


  /** Méthode qui permet de récupérer l'état initial de l'automate
  @return l'état initial de l'automate
  */
  public Etat getEtatInitial() {
    return this.etats.get(0);
  }


}
