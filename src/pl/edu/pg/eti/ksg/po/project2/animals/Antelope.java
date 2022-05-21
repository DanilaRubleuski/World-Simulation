package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.Commentator;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.util.Random;
import java.awt.*;

public class Antelope extends Animal {
    private static final int ZASIEG_RUCHU_ANTYLOPY = 2;
    private static final int SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY = 1;
    private static final int SILA_ANTYLOPY = 4;
    private static final int INICJATYWA_ANTYLOPY = 4;

    public Antelope(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.ANTELOPE, swiat, pozycja, turaUrodzenia, SILA_ANTYLOPY, INICJATYWA_ANTYLOPY);
        this.setZasiegRuchu(ZASIEG_RUCHU_ANTYLOPY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ANTYLOPY);
        setKolor(new Color(179, 95, 29));
    }

    @Override
    public String OrganismTypeToString() {
        return "Antelope";
    }

    @Override
    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < 50) {
            if (this == atakujacy) {
                Commentator.AddComment(OrganizmToSring() + " runs from " + ofiara.OrganizmToSring());
                Point tmpPozycja = TakeFreePoint(ofiara.getPozycja());
                if (!tmpPozycja.equals(ofiara.getPozycja()))
                    MakeMove(tmpPozycja);
            } else if (this == ofiara) {
                Commentator.AddComment(OrganizmToSring() + " runs from " + atakujacy.OrganizmToSring());
                Point tmpPozycja = this.getPozycja();
                MakeMove(TakeFreePoint(this.getPozycja()));
                if (getPozycja().equals(tmpPozycja)) {
                    getSwiat().UsunOrganizm(this);
                    Commentator.AddComment(atakujacy.OrganizmToSring() + " kills " + OrganizmToSring());
                }
                atakujacy.MakeMove(tmpPozycja);
            }
            return true;
        } else return false;
    }
}