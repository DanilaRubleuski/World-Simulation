package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.Point;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.plants.Hogweed;
import java.awt.*;

public class CyberSheep extends Sheep {
    private static final int ZASIEG_RUCHU_CYBER_OWCY = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY = 1;
    private static final int SILA_CYBER_OWCY = 11;
    private static final int INICJATYWA_CYBER_OWCY = 4;

    public CyberSheep(World swiat, Point pozycja, int turaUrodzenia) {
        super(swiat, pozycja, turaUrodzenia);
        setTypOrganizmu(TypOrganizmu.CYBER_SHEEP);
        setSila(SILA_CYBER_OWCY);
        setInicjatywa(INICJATYWA_CYBER_OWCY);
        setSzansaRozmnazania(0.1);

        this.setZasiegRuchu(ZASIEG_RUCHU_CYBER_OWCY);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CYBER_OWCY);
        setKolor(Color.BLACK);
    }

    @Override
    public Point TakeRandomPoint(Point pozycja) {
        if (getSwiat().czyIstniejeBarszczSosnowskiego()) {

            Point cel = FindCloserBarszcz().getPozycja();
            int dx = Math.abs(pozycja.getX() - cel.getX());
            int dy = Math.abs(pozycja.getY() - cel.getY());
            if (dx >= dy) {
                if (pozycja.getX() > cel.getX()) {
                    return new Point(pozycja.getX() - 1, pozycja.getY());
                } else {
                    return new Point(pozycja.getX() + 1, pozycja.getY());
                }
            } else {
                if (pozycja.getY() > cel.getY()) {
                    return new Point(pozycja.getX(), pozycja.getY() - 1);
                } else {
                    return new Point(pozycja.getX(), pozycja.getY() + 1);
                }
            }
        } else return super.TakeRandomPoint(pozycja);
    }

    private Hogweed FindCloserBarszcz() {
        Hogweed tmpBarszcz = null;
        int najmniejszaOdleglosc = getSwiat().getSizeX() + getSwiat().getSizeY() + 1;
        for (int i = 0; i < getSwiat().getSizeY(); i++) {
            for (int j = 0; j < getSwiat().getSizeX(); j++) {
                Organism tmpOrganism = getSwiat().getPlansza()[i][j];
                if (tmpOrganism != null &&
                        tmpOrganism.getTypOrganizmu() == TypOrganizmu.HOGWEED) {
                    int tmpOdleglosc = FindLength(tmpOrganism.getPozycja());
                    if (najmniejszaOdleglosc > tmpOdleglosc) {
                        najmniejszaOdleglosc = tmpOdleglosc;
                        tmpBarszcz = (Hogweed) tmpOrganism;
                    }
                }
            }
        }
        return tmpBarszcz;
    }

    private int FindLength(Point otherPozycja) {
        int dx = Math.abs(getPozycja().getX() - otherPozycja.getX());
        int dy = Math.abs(getPozycja().getY() - otherPozycja.getY());
        return dx + dy;
    }

    @Override
    public String OrganismTypeToString() {
        return "CyberSheep";
    }
}