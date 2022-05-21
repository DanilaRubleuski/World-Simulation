package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.awt.*;

public class Wolf extends Animal {
    private static final int ZASIEG_RUCHU_WILKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_WILKA = 1;
    private static final int SILA_WILKA = 9;
    private static final int INICJATYWA_WILKA = 5;

    public Wolf(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.WOLF, swiat, pozycja, turaUrodzenia, SILA_WILKA, INICJATYWA_WILKA);
        this.setZasiegRuchu(ZASIEG_RUCHU_WILKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_WILKA);
        setKolor(new Color(56, 56, 56));
    }

    @Override
    public String OrganismTypeToString() {
        return "Wolf";
    }
}