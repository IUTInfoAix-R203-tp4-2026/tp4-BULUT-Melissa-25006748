package fr.univ_amu.iut.exercice3;

import java.util.ArrayList;
import java.util.List;

/// Exercice 3 - God Class : Extract Class.
///
/// Cette classe `Menu` fait **deux choses** qui n'ont rien à voir entre elles :
///
/// 1. gérer un menu d'options (enregistrer, afficher, exécuter un choix)
/// 2. mémoriser l'historique des 10 derniers choix (enregistrer, rogner à 10,
///    afficher)
///
/// Le mélange est typique d'un **Divergent Change** smell : le jour où l'on veut
/// changer l'affichage du menu, on touche `Menu`. Le jour où l'on veut changer
/// la taille de l'historique (ou son format), on touche aussi `Menu`. Deux axes
/// de changement qui devraient vivre dans deux classes distinctes.
///
/// Refactoring attendu :
///
/// - **Extract Class** : créer une classe `Historique` dans le même paquet, qui
///   encapsule la liste, la taille max, et les méthodes `enregistrer(String)` /
///   `asList()` / `afficher()`. Dans `Menu`, remplacer le champ `List<String>
///   historique` par un champ `Historique historique` et déléguer. La
///   **signature publique de `Menu` ne doit pas changer** (les tests de
///   caractérisation vérifient le comportement observable).
public class Historique {

  private static final int TAILLE_MAX_HISTORIQUE = 10;
  private final List<String> elements = new ArrayList<>();

  public void enregistrer(String titre) {
    elements.add(titre);
    if (elements.size() > TAILLE_MAX_HISTORIQUE) {
      elements.remove(0);
    }
  }

  public String afficher() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- Historique ---\n");
    for (String h : elements) {
      sb.append("- ").append(h).append("\n");
    }
    return sb.toString();
  }

  public List<String> asList() {
    return elements;
  }
}
