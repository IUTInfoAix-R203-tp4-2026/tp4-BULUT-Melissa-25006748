package fr.univ_amu.iut.exercice6;

/// Exercice 6 - Gilded Rose (capstone).
///
/// Ceci est le code existant de gestion de l'inventaire de la Gilded Rose. La
/// direction veut ajouter les articles *Conjured* (qui se dégradent deux fois
/// plus vite que les articles normaux). Mais avant de le faire, elle demande
/// qu'on nettoie le code - parce qu'actuellement, personne n'ose y toucher.
///
/// Règles en vigueur :
///
/// - À la fin de chaque journée, `sellIn` et `quality` de chaque article sont
///   mis à jour
/// - Une fois `sellIn` passé (négatif), la qualité se dégrade **deux fois plus
///   vite**
/// - La qualité n'est jamais négative
/// - La qualité d'un article n'est jamais au-dessus de 50... sauf pour
///   "Sulfuras" qui est toujours à 80
/// - "Aged Brie" *augmente* en qualité avec le temps
/// - "Sulfuras, Hand of Ragnaros" ne doit jamais être vendu (sellIn ne change
///   pas) et jamais se dégrader (quality ne change pas)
/// - "Backstage passes to a TAFKAL80ETC concert" augmente en qualité :
///   - de 2 quand il reste 10 jours ou moins
///   - de 3 quand il reste 5 jours ou moins
///   - tombe à 0 après le concert (sellIn < 0)
///
/// Votre mission :
///
/// 1. Écrire des tests de caractérisation couvrant **toutes** les règles (déjà
///    fournis)
/// 2. Refactorer `updateQuality()` en gardant les tests verts - par exemple en
///    extrayant une classe par type d'article (polymorphisme)
/// 3. Ajouter le support des articles "Conjured" qui se dégradent deux fois plus
///    vite (test dédié à activer une fois votre refactoring prêt)
///
/// Contrainte : la classe [Item] ne doit pas changer (signature figée par
/// la direction).
public class GildedRose {

  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public void updateQuality() {
    for (Item item : items) {
      UpdaterFactory.create(item).update(item);
    }
  }

  private interface ItemUpdater {
    void update(Item item);
  }

  private static class UpdaterFactory {
    static ItemUpdater create(Item item) {
      if (item.name.equals("Aged Brie")) return new BrieUpdater();
      if (item.name.equals("Sulfuras, Hand of Ragnaros")) return new SulfurasUpdater();
      if (item.name.equals("Backstage passes to a TAFKAL80ETC concert"))
        return new BackstageUpdater();
      if (item.name.startsWith("Conjured")) return new ConjuredUpdater();
      return new NormalUpdater();
    }
  }

  private abstract static class AbstractUpdater implements ItemUpdater {
    protected void increaseQuality(Item item, int amount) {
      item.quality = Math.min(50, item.quality + amount);
    }

    protected void decreaseQuality(Item item, int amount) {
      item.quality = Math.max(0, item.quality - amount);
    }
  }

  private static class NormalUpdater extends AbstractUpdater {
    @Override
    public void update(Item item) {
      item.sellIn--;
      decreaseQuality(item, item.sellIn < 0 ? 2 : 1);
    }
  }

  private static class ConjuredUpdater extends AbstractUpdater {
    @Override
    public void update(Item item) {
      item.sellIn--;
      decreaseQuality(item, item.sellIn < 0 ? 4 : 2);
    }
  }

  private static class BrieUpdater extends AbstractUpdater {
    @Override
    public void update(Item item) {
      item.sellIn--;
      increaseQuality(item, item.sellIn < 0 ? 2 : 1);
    }
  }

  private static class SulfurasUpdater implements ItemUpdater {
    @Override
    public void update(Item item) {}
  }

  private static class BackstageUpdater extends AbstractUpdater {
    @Override
    public void update(Item item) {
      item.sellIn--;

      if (item.sellIn < 0) {
        item.quality = 0;
      } else if (item.sellIn < 5) {
        increaseQuality(item, 3);
      } else if (item.sellIn < 10) {
        increaseQuality(item, 2);
      } else {
        increaseQuality(item, 1);
      }
    }
  }
}
