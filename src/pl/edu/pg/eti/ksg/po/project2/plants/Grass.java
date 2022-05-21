package pl.edu.pg.eti.ksg.po.project2.plants;

import pl.edu.pg.eti.ksg.po.project2.Plant;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.awt.*;

public class Grass extends Plant {
    private static final int SILA_TRAWY = 0;
    private static final int INICJATYWA_TRAWY = 0;

    public Grass(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.GRASS, swiat, pozycja, turaUrodzenia, SILA_TRAWY, INICJATYWA_TRAWY);
        setKolor(new Color(132, 255, 0));
    }

    @Override
    public String OrganismTypeToString() {
        return "Grass";
    }
}