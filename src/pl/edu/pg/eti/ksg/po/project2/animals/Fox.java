package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.util.Random;
import java.awt.*;

public class Fox extends Animal {
    private static final int ZASIEG_RUCHU_LISA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_LISA = 1;
    private static final int SILA_LISA = 3;
    private static final int INICJATYWA_LISA = 7;

    public Fox(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.FOX, swiat, pozycja, turaUrodzenia, SILA_LISA, INICJATYWA_LISA);
        this.setZasiegRuchu(ZASIEG_RUCHU_LISA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_LISA);
        setKolor(new Color(241, 80, 39));
    }

    @Override
    public String OrganismTypeToString() {
        return "Fox";
    }

    @Override
    public Point TakeRandomPoint(Point pozycja) {
        OpenAlldirections();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = getSwiat().getSizeX();
        int sizeY = getSwiat().getSizeY();
        int ileKierunkowMozliwych = 0;
        Organism tmpOrganism;

        if (pozX == 0) BlockDirection(Direction.LEFT);
        else {
            tmpOrganism = getSwiat().getPlansza()[pozY][pozX - 1];
            if (tmpOrganism != null && tmpOrganism.getSila() > this.getSila()) {
                BlockDirection(Direction.LEFT);
            } else ileKierunkowMozliwych++;
        }

        if (pozX == sizeX - 1) BlockDirection(Direction.RIGHT);
        else {
            tmpOrganism = getSwiat().getPlansza()[pozY][pozX + 1];
            if (tmpOrganism != null && tmpOrganism.getSila() > this.getSila()) {
                BlockDirection(Direction.RIGHT);
            } else ileKierunkowMozliwych++;
        }

        if (pozY == 0) BlockDirection(Direction.UP);
        else {
            tmpOrganism = getSwiat().getPlansza()[pozY - 1][pozX];
            if (tmpOrganism != null && tmpOrganism.getSila() > this.getSila()) {
                BlockDirection(Direction.UP);
            } else ileKierunkowMozliwych++;
        }

        if (pozY == sizeY - 1) BlockDirection(Direction.DOWN);
        else {
            tmpOrganism = getSwiat().getPlansza()[pozY + 1][pozX];
            if (tmpOrganism != null && tmpOrganism.getSila() > this.getSila()) {
                BlockDirection(Direction.DOWN);
            } else ileKierunkowMozliwych++;
        }

        if (ileKierunkowMozliwych == 0) return new Point(pozX, pozY);
        while (true) {
            Random rand = new Random();
            int upperbound = 100;
            int tmpLosowanie = rand.nextInt(upperbound);
            if (tmpLosowanie < 25 && !IfDirectionBlocked(Direction.LEFT))
                return new Point(pozX - 1, pozY);
            else if (tmpLosowanie >= 25 && tmpLosowanie < 50 && !IfDirectionBlocked(Direction.RIGHT))
                return new Point(pozX + 1, pozY);
            else if (tmpLosowanie >= 50 && tmpLosowanie < 75 && !IfDirectionBlocked(Direction.UP))
                return new Point(pozX, pozY - 1);
            else if (tmpLosowanie >= 75 && !IfDirectionBlocked(Direction.DOWN))
                return new Point(pozX, pozY + 1);
        }
    }
}