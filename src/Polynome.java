package math;

import java.util.*;
import java.lang.*;
import appli.*;

public class Polynome {

  private List<NombreX> termes;

  public Polynome(List<NombreX> termes) {
    this.termes = termes;
  }

  //On considere que le polynome est développé et réduit au maximum pour le moment
  public Polynome(String polynome) {
    this.termes = this.stringToListeNombreX(polynome);
  }

  public List<NombreX> developper(Polynome polynome) {
    List<NombreX> termes = new ArrayList<NombreX>();
    for (NombreX nbrThis : this.termes) {
      for (NombreX nbrP : polynome.getTermes()) {
        termes.add(nbrThis.fois(nbrP));
      }
    }
    return termes;
  }

  public List<NombreX> reduire(List<NombreX> termes) {
    List<NombreX> termesRed = new ArrayList<NombreX>();
    Collections.sort(termes);
    Collections.reverse(termes);
    NombreX currentNbr = new NombreX(0);
    for (NombreX nbr : termes) {
      if (nbr.getDegreX() == currentNbr.getDegreX()) {
        currentNbr = currentNbr.plus(nbr);
      } else {
        if (currentNbr.getCoeff() != 0) {
          termesRed.add(currentNbr);
        }
        currentNbr = nbr;
      }
    }
    if (currentNbr.getCoeff() != 0) {
      termesRed.add(currentNbr);
    }
    Collections.reverse(termesRed);
    return termesRed;
  }

  public List<NombreX> developperEtReduire(Polynome polynome) {
    return this.reduire(this.developper(polynome));
  }

  public List<NombreX> stringToListeNombreX(String polynome) {
    List<NombreX> termes = new ArrayList<NombreX>();
    char premierChar = polynome.charAt(0);
    String polynomeAjuste = (premierChar == '-' || premierChar == '+') ? Application.ajouterDevant(polynome.substring(1, polynome.length()), '+', '-') : Application.ajouterDevant(polynome, '+', '-');
    String[] splitedPolynome = polynomeAjuste.split("\\+");
    for (String nbr : splitedPolynome) {
      if (premierChar == '-' && termes.size() == 0) {
        termes.add(new NombreX("-"+nbr));
      } else {
        termes.add(new NombreX(nbr));
      }
    }
    return termes;
  }

  public List<NombreX> getTermes() {
    return this.termes;
  }

  public String toString() {
    String res = "";
    for (NombreX nbr : this.termes) {
      if (nbr.getCoeff() > 0 && !res.equals("")) {
        res += "+" + nbr.toString();
      } else {
        res += nbr.toString();
      }
    }
    return res;
  }

}
