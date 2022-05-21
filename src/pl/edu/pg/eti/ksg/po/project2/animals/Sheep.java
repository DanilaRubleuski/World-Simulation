package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.awt.*;

public class Sheep extends Animal {
    private static final int ZASIEG_RUCHU_OWCY = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_OWCA = 1;
    private static final int SILA_OWCY = 4;
    private static final int INICJATYWA_OWCY = 4;

    public Sheep(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.SHEEP, swiat, pozycja, turaUrodzenia, SILA_OWCY, INICJATYWA_OWCY);
        this.setZasiegRuchu(ZASIEG_RUCHU_OWCY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_OWCA);
        setKolor(new Color(199, 156, 185));
    }

    @Override
    public String OrganismTypeToString() {
        return "Sheep";
    }
}