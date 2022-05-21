package pl.edu.pg.eti.ksg.po.project2.plants;

import pl.edu.pg.eti.ksg.po.project2.Plant;
import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.Commentator;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import java.awt.*;

public class Guarana extends Plant {
    private static final int ZWIEKSZENIE_SILY = 3;
    private static final int SILA_GUARANY = 0;
    private static final int INICJATYWA_GUARANY = 0;

    public Guarana(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.GUARANA, swiat, pozycja,
                turaUrodzenia, SILA_GUARANY, INICJATYWA_GUARANY);
        setKolor(new Color(189, 0, 0));
    }

    @Override
    public String OrganismTypeToString() {
        return "Guarana";
    }

    @Override
    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        Point tmpPozycja = this.getPozycja();
        getSwiat().UsunOrganizm(this);
        atakujacy.MakeMove(tmpPozycja);
        Commentator.AddComment(atakujacy.OrganizmToSring() + " eats " + this.OrganizmToSring() +
                "  and gets plus " + Integer.toString(ZWIEKSZENIE_SILY) + " of strength ");
        atakujacy.setSila(atakujacy.getSila() + ZWIEKSZENIE_SILY);
        return true;
    }
}