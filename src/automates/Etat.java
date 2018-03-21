/**
* Cette classe est une classe représentant un état
*
* @author CHATEAU Julien
*@version 1.0
*/

package automates;

import java.util.*;

public class Etat {

  private String name;
  private int numero;
  private HashMap<String, Etat> delta;


  /** Constructeur de la classe Etat
  @param numero , le numéro de l'état
  */
  public Etat(int numero) {
    this.numero = numero;
    this.name = "E"+this.numero;
    this.delta = new HashMap<String, Etat>();
  }


  /** Méthode ajoutant un couple (clé; valeur) à la HashMap delta
  @param key , la chaîne de caractère correspondant à la clé
  @param value , l'état correspondant à la valeur
  On comprend par cette association que cette état courant va vers l'état "value" avec l'action "key"
  */
  public void addKeyValue(String key, Etat value) {
    this.delta.put(key, value);
  }


  /** Méthode qui permet de récupérer le numero de l'état
  @return le numero de l'état
  */
  public int getNumero() {
    return this.numero;
  }

  /** Méthode qui permet de récupérer le nom de l'état
  @return le nom de l'état
  */
  public String getName() {
    return this.name;
  }


  /** Méthode qui permet de récupérer l'état associé avec la chaîne de caractères "str"
  @param str , la chaîne de caractère désignée
  @return l'état associé avec la chaîne de caractères "str"
  */
  public Etat etatAssocie(String str) {
    return this.delta.get(str);
  }

}
