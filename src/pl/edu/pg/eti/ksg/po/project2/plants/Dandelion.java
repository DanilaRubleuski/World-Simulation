package pl.edu.pg.eti.ksg.po.project2.plants;

import pl.edu.pg.eti.ksg.po.project2.Plant;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.util.Random;
import java.awt.*;

public class Dandelion extends Plant {
    private static final int SILA_MLECZ = 0;
    private static final int INICJATYWA_MLECZ = 0;
    private static final int ILE_PROB = 3;

    public Dandelion(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.DANDELION, swiat, pozycja,
                turaUrodzenia, SILA_MLECZ, INICJATYWA_MLECZ);
        setKolor(new Color(255, 210, 0));
    }

    @Override
    public void Action() {
        Random rand = new Random();
        for (int i = 0; i < ILE_PROB; i++) {
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < getSzansaRozmnazania()) Spreading();
        }
    }

    @Override
    public String OrganismTypeToString() {
        return "Dandelion";
    }
}