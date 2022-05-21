package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.Commentator;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.awt.*;

public class Turtle extends Animal {
    private static final int ZASIEG_RUCHU_ZOLWA = 1;
    private static final double SZANSA_WYKONYWANIA_RUCHU_ZOLWA = 0.25;
    private static final int SILA_ZOLWA = 2;
    private static final int INICJATYWA_ZOLWA = 1;

    public Turtle(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.TURTLE, swiat, pozycja, turaUrodzenia, SILA_ZOLWA, INICJATYWA_ZOLWA);
        this.setZasiegRuchu(ZASIEG_RUCHU_ZOLWA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_ZOLWA);
        setKolor(new Color(51, 102, 0));
    }

    @Override
    public String OrganismTypeToString() {
        return "Turtle";
    }

    @Override
    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        if (this == ofiara) {
            if (atakujacy.getSila() < 5 && atakujacy.IfAnimal()) {
                Commentator.AddComment(OrganizmToSring() + " stops attack of " + atakujacy.OrganizmToSring());
                return true;
            } else return false;
        } else {
            if (atakujacy.getSila() >= ofiara.getSila()) return false;
            else {
                if (ofiara.getSila() < 5 && ofiara.IfAnimal()) {
                    Commentator.AddComment(OrganizmToSring() + " stops attack of " + ofiara.OrganizmToSring());
                    return true;
                } else return false;
            }
        }
    }
}