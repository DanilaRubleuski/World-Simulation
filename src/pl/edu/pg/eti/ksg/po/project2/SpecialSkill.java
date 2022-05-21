package pl.edu.pg.eti.ksg.po.project2;

public class SpecialSkill {
    private final int CZAS_TRWANIA_UMIEJETNOSCI = 5;
    private final int COOLDOWN_UMIEJETNOSCI = 10;
    private boolean czyMoznaAktywowac;
    private boolean czyJestAktywna;
    private int czasTrwania;
    private int cooldown;

    public SpecialSkill() {
        cooldown = 0;
        czasTrwania = 0;
        czyJestAktywna = false;
        czyMoznaAktywowac = true;
    }

    public void CheckResults() {
        if (cooldown > 0) cooldown--;
        if (czasTrwania > 0) czasTrwania--;
        if (czasTrwania == 0) desactivate();
        if (cooldown == 0) czyMoznaAktywowac = true;
    }

    public void Activate() {
        if (cooldown == 0) {
            czyJestAktywna = true;
            czyMoznaAktywowac = false;
            cooldown = COOLDOWN_UMIEJETNOSCI;
            czasTrwania = CZAS_TRWANIA_UMIEJETNOSCI;
        }
    }

    public void desactivate() {
        czyJestAktywna = false;
    }

    public boolean getCzyMoznaAktywowac() {
        return czyMoznaAktywowac;
    }

    public void setCzyMoznaAktywowac(boolean czyMoznaAktywowac) {
        this.czyMoznaAktywowac = czyMoznaAktywowac;
    }

    public boolean getCzyJestAktywna() {
        return czyJestAktywna;
    }

    public void setCzyJestAktywna(boolean czyJestAktywna) {
        this.czyJestAktywna = czyJestAktywna;
    }

    public int getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(int czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}