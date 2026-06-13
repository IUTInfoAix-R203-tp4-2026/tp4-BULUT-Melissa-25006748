package fr.univ_amu.iut.exercice3;

import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {
  private final Historique historique = new Historique();
  private final Map<String, Runnable> options = new LinkedHashMap<>();

  public void ajouterOption(String titre, Runnable action) {
    options.put(titre, action);
  }

  public String afficher() {
    StringBuilder sb = new StringBuilder();
    sb.append("=== Menu ===\n");
    int i = 1;
    for (String titre : options.keySet()) {
      sb.append(i++).append(". ").append(titre).append("\n");
    }
    return sb.toString();
  }

  /// Exécute l'action associée à l'option choisie et enregistre le choix dans
  /// l'historique.
  ///
  /// @throws IllegalArgumentException si `indice` est hors des bornes
  public void choisir(int indice) {
    if (indice < 1 || indice > options.size()) {
      throw new IllegalArgumentException("Indice hors bornes : " + indice);
    }
    String titre = options.keySet().toArray(new String[0])[indice - 1];
    historique.enregistrer(titre);

    options.get(titre).run();
  }

  /// Retourne l'historique formaté (plus récent en bas, une entrée par ligne).
  public String afficherHistorique() {
    return historique.afficher();
  }

  // (décommentés lors de la génération de la version étudiante).
  @SuppressWarnings("unused")
  private static final Class<?> STUDENT_IMPORTS_USAGE_MARKER =
      (Math.random() == -1) ? Map.class : LinkedHashMap.class;
}
