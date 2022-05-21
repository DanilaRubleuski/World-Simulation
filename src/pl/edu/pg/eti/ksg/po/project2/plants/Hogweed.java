package pl.edu.pg.eti.ksg.po.project2.plants;

import pl.edu.pg.eti.ksg.po.project2.*;
import pl.edu.pg.eti.ksg.po.project2.Point;

import java.awt.*;
import java.util.Random;

public class Hogweed extends Plant {
    private static final int SILA_BARSZCZ_SOSNOWSKIEGO = 10;
    private static final int INICJATYWA_BARSZCZ_SOSNOWSKIEGO = 0;
    public Hogweed(World swiat, Point pozycja, int turaUrodzenia) {

        super(TypOrganizmu.HOGWEED, swiat, pozycja,
                turaUrodzenia, SILA_BARSZCZ_SOSNOWSKIEGO, INICJATYWA_BARSZCZ_SOSNOWSKIEGO);
        setKolor(new Color(255, 0, 255));
        setSzansaRozmnazania(0.05);
    }

    @Override
    public void Action() {
        int pozX = getPozycja().getX();
        int pozY = getPozycja().getY();
        TakeRandomPoint(getPozycja());
        for (int i = 0; i < 4; i++) {
            Organism tmpOrganism = null;
            if (i == 0 && !IfDirectionBlocked(Direction.DOWN))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY + 1));
            else if (i == 1 && !IfDirectionBlocked(Direction.UP))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX, pozY - 1));
            else if (i == 2 && !IfDirectionBlocked(Direction.LEFT))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX - 1, pozY));
            else if (i == 3 && !IfDirectionBlocked(Direction.RIGHT))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(pozX + 1, pozY));

            if (tmpOrganism != null && tmpOrganism.IfAnimal()) {
                    if(tmpOrganism.getTypOrganizmu() == TypOrganizmu.HUMAN){
                        if(tmpOrganism.IfActive()){

                        }
                        else{
                            getSwiat().UsunOrganizm(tmpOrganism);
                            Commentator.AddComment(OrganizmToSring() + " murders " + tmpOrganism.OrganizmToSring());
                        }
                    }else if(tmpOrganism.getTypOrganizmu() == TypOrganizmu.CYBER_SHEEP){

                    }
                    else{
                        getSwiat().UsunOrganizm(tmpOrganism);
                        Commentator.AddComment(OrganizmToSring() + " kills " + tmpOrganism.OrganizmToSring());
                    }
            }

        }
        Random rand = new Random();
        int tmpLosowanie = rand.nextInt(100);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Spreading();
    }

    @Override
    public String OrganismTypeToString() {
        return "Hogweed";
    }

    @Override
    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        if (atakujacy.getSila() >= 10) {
            getSwiat().UsunOrganizm(this);
            Commentator.AddComment(atakujacy.OrganizmToSring() + " eats " + this.OrganizmToSring());
            atakujacy.MakeMove(ofiara.getPozycja());
        }
        if ((atakujacy.IfAnimal() && atakujacy.getTypOrganizmu() != TypOrganizmu.CYBER_SHEEP) ||
                atakujacy.getSila() < 10) {
            getSwiat().UsunOrganizm(atakujacy);
            Commentator.AddComment(this.OrganizmToSring() + " kills " + atakujacy.OrganizmToSring());
        }
        return true;
    }
}