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
    this.termes = this.stringToListeNombreX(polynome).getTermes();
  }

  public Polynome(NombreX nbr) {
    this.termes = new ArrayList<NombreX>();
    this.termes.add(nbr);
  }

  public Polynome() {
    this.termes = new ArrayList<NombreX>();
  }


  public static String developper(String expression) {
    expression = expression.replace("-(", "-1(");
    Application.afficherDebug("entrée : "+expression);
    expression = Polynome.remplacerPuissance(expression);
    int posParenth = expression.indexOf("(");
    if (posParenth == 0) {
      posParenth = expression.indexOf("(", posParenth + 1);
    }
    if (posParenth != -1) {
      boolean stop = false;
      while (!stop) {
        if (expression.charAt(posParenth-1) == '+' || expression.charAt(posParenth-1) == '-') {
          posParenth = expression.indexOf("(", posParenth + 1);
        } else {
          stop = true;
        }
      }
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
        Application.afficherDebug("facteurDroit : "+facteurDroit);
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
            facteurGauche = currentChar + facteurGauche;
            if (currentChar != '+' && currentChar != '-') {
              cmp--;
            }
          }
        }
        Application.afficherDebug("facteurGauche : "+facteurGauche);
        if (cmp > 0) {
          resteGauche = Application.split(expression, cmp)[0];
        }
        Application.afficherDebug("resteGauche : "+resteGauche);
        Application.afficherDebug("resteDroit : "+resteDroit);
        facteurGauche = Polynome.developper(facteurGauche);
        facteurDroit = Polynome.developper(facteurDroit);
        Polynome fg = new Polynome(facteurGauche);
        Polynome fd = new Polynome(facteurDroit);
        Polynome fgxfd = fg.developperEtReduire(fd);
        char c = (resteDroit.length() > 0) ? resteDroit.charAt(0) : 'c';
        Application.afficherDebug("c : "+c);
        String toStirng = fgxfd.toString();
        if (c == '(') {
          if (resteGauche.charAt(resteGauche.length()-1) != '+' || toStirng.charAt(0) != '-') {
            resteGauche += "+";
          }
          expression = resteGauche + "(" + fgxfd.toString() + ")" + resteDroit;
        } else {
          if (resteGauche.length() > 0) {
            if (resteGauche.charAt(resteGauche.length()-1) != '+' || toStirng.charAt(0) != '-') {
              resteGauche += "+";
            }
            if (resteGauche.charAt(resteGauche.length()-1) == '+' && toStirng.charAt(0) == '-') {
              resteGauche = resteGauche.substring(0, resteGauche.length()-1);
            }
          }
          expression = resteGauche + toStirng + resteDroit;
        }
      }
    }
    Application.afficherDebug("expression avant test de récurrence : "+expression);
    if (!expression.contains("(") || (Application.nombreOccurenceCaractere(expression, '(') == 1 && expression.charAt(0) == '(') && expression.charAt(expression.length()-1) == ')') {
      Polynome res = Polynome.stringToListeNombreX(expression).reduire();
      expression = res.toString();
    } else {
      expression = Polynome.developper(expression);
    }
    Application.afficherDebug("return : "+expression);
    return expression;
  }


  public static String remplacerPuissance(String expression) {
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
          Application.afficherDebug("currentChar : "+currentChar);
          if (currentChar == ')') {
            nbrParenthOuvreTrouve++;
          } else if (currentChar != '(') {
            facteurGauche = currentChar + facteurGauche;
          } else if (currentChar == '(' && nbrParenthOuvreTrouve >= 1) {
            nbrParenthOuvreTrouve--;
          }
          cmp--;
        }
        Application.afficherDebug(facteurGauche);
        for (int i = 0; i<puissance-1; i++) {
          remplacement += "("+facteurGauche+")";
        }
        Application.afficherDebug("puissance : "+puissance);
        expression = expression.replace("^"+puissance, remplacement);
        Application.afficherDebug("expression : "+expression);
        posPuissance = expression.indexOf("^");
      } else {
        posPuissance = expression.indexOf("^", posPuissance+1);
      }
    }
    return expression;
  }


  public Polynome developper(Polynome facteurDroit) {
    List<NombreX> termes = new ArrayList<NombreX>();
    for (NombreX nbrThis : this.termes) {
      for (NombreX nbrP : facteurDroit.getTermes()) {
        termes.add(nbrThis.fois(nbrP));
      }
    }
    return new Polynome(termes);
  }


  public Polynome reduire() {
    List<NombreX> termes = this.termes;
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
    return new Polynome(termesRed);
  }


  public Polynome developperEtReduire(Polynome facteurDroit) {
    return this.developper(facteurDroit).reduire();
  }


  public Polynome derive() {
    List<NombreX> res = new ArrayList<NombreX>();
    for (NombreX nbr : this.termes) {
      NombreX nbrDerive = nbr.derive();
      if (nbrDerive.getCoeff() != -1) {
        res.add(nbrDerive);
      }
    }
    return new Polynome(res);
  }


  public static Polynome stringToListeNombreX(String polynome) {
    if (polynome.charAt(0) == '(' && polynome.charAt(polynome.length()-1) == ')') {
      polynome = polynome.substring(1, polynome.length()-1);
    }
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
    return new Polynome(termes);
  }


  public NombreX getTermeDegreDominant() {
    Collections.sort(this.termes);
    return this.termes.get(0);
  }

  public boolean estNulle() {
    return this.termes.size() == 0;
  }

  public void addNombreX(NombreX nbr) {
    this.termes.add(nbr);
  }

  public Polynome[] diviserPar(Polynome div) {
    Polynome dividende = new Polynome(this.termes);
    Polynome diviseur = new Polynome(div.getTermes());
    Polynome quotient = new Polynome();
    NombreX dvddeNbrDgrDom = dividende.getTermeDegreDominant();
    NombreX dvsrNbrDgrDom = diviseur.getTermeDegreDominant();
    while (!dividende.estNulle() && dvddeNbrDgrDom.getDegreX() >= dvsrNbrDgrDom.getDegreX()) {
      Application.afficherDebug("\n\n\ndvddeNbrDgrDom : "+dvddeNbrDgrDom);
      Application.afficherDebug("dvsrNbrDgrDom : "+dvsrNbrDgrDom);
      Application.afficherDebug("dividende : "+dividende);
      Application.afficherDebug("diviseur : "+diviseur);
      Application.afficherDebug("quotient : "+quotient);
      NombreX ajoutQuotient = new NombreX(dvddeNbrDgrDom.getCoeff()/dvsrNbrDgrDom.getCoeff(), dvddeNbrDgrDom.getDegreX()-dvsrNbrDgrDom.getDegreX());
      quotient.addNombreX(ajoutQuotient);
      dividende = dividende.moins(diviseur.developper(new Polynome(ajoutQuotient)));
      if (!dividende.estNulle() && dvddeNbrDgrDom.getDegreX() >= dvddeNbrDgrDom.getDegreX()) {
        dvddeNbrDgrDom = dividende.getTermeDegreDominant();
        dvsrNbrDgrDom = diviseur.getTermeDegreDominant();
      }
    }
    Polynome[] quotientReste = new Polynome[2];
    quotientReste[0] = quotient;
    quotientReste[1] = dividende;
    return quotientReste;
  }

  public Polynome moins(Polynome termeDroit) {
    Polynome p = termeDroit.inverse();
    return this.plus(p);
  }

  public Polynome plus(Polynome termeDroit) {
    String str = termeDroit.toString();
    String somme = this.toString() + ((str.charAt(0) == '-') ? "" : "+") + str;
    return Polynome.stringToListeNombreX(somme).reduire();
  }

  public Polynome inverse() {
    List<NombreX> res = new ArrayList<NombreX>();
    for (NombreX nbr : this.termes) {
      res.add(nbr.inverse());
    }
    return new Polynome(res);
  }


  public double calculP(double x) {
    double res = 0;
    for (NombreX nbr : this.termes) {
      res += nbr.calculP(x);
    }
    return res;
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
    return (res == "") ? "0" : res;
  }

}
