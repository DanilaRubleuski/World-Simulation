package pl.edu.pg.eti.ksg.po.project2.animals;

import pl.edu.pg.eti.ksg.po.project2.Animal;
import pl.edu.pg.eti.ksg.po.project2.Organism;
import pl.edu.pg.eti.ksg.po.project2.Commentator;
import pl.edu.pg.eti.ksg.po.project2.World;
import pl.edu.pg.eti.ksg.po.project2.Point;
import pl.edu.pg.eti.ksg.po.project2.SpecialSkill;

import java.awt.*;

public class Human extends Animal {
    private static final int ZASIEG_RUCHU_CZLOWIEKA = 1;
    private static final int SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA = 1;
    private static final int SILA_CZLOWIEKA = 5;
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private Direction kierunekRuchu;
    private SpecialSkill umiejetnosc;

    public Human(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.HUMAN, swiat, pozycja, turaUrodzenia, SILA_CZLOWIEKA, INICJATYWA_CZLOWIEKA);
        this.setZasiegRuchu(ZASIEG_RUCHU_CZLOWIEKA);
        this.setSzansaWykonywaniaRuchu(SZANSA_WYKONYWANIA_RUCHU_CZLOWIEKA);
        kierunekRuchu = Direction.NO_DIRECTION;
        setKolor(new Color(0, 88, 255));
        umiejetnosc = new SpecialSkill();
    }
    //
    private void Undying(){

        TakeRandomPoint(getPozycja());
        int x = getPozycja().getX();
        int y = getPozycja().getY();
        Organism tmpOrganism = null;
        for (int i = 0; i < 4; i++) {
            if (i == 0 && !IfDirectionBlocked(Direction.DOWN))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(x, y + 1));
            else if (i == 1 && !IfDirectionBlocked(Direction.UP))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(x, y - 1));
            else if (i == 2 && !IfDirectionBlocked(Direction.LEFT))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(x - 1, y));
            else if (i == 3 && !IfDirectionBlocked(Direction.RIGHT))
                tmpOrganism = getSwiat().CoZnajdujeSieNaPolu(new Point(x + 1, y));
            setCzyUmarl(false);
            if (tmpOrganism != null && getSila() >= tmpOrganism.getSila()) {
                TakeFreePoint(getPozycja());
            }
        }
    }
    @Override
    public boolean IfActive(){
        return umiejetnosc.getCzyJestAktywna();
    }

    @Override
    protected Point PlanTheMove() {
        int x = getPozycja().getX();
        int y = getPozycja().getY();
        TakeRandomPoint(getPozycja());
        if (kierunekRuchu == Direction.NO_DIRECTION ||
                IfDirectionBlocked(kierunekRuchu)) return getPozycja();
        else {
            if (kierunekRuchu == Direction.DOWN) return new Point(x, y + 1);
            if (kierunekRuchu == Direction.UP) return new Point(x, y - 1);
            if (kierunekRuchu == Direction.LEFT) return new Point(x - 1, y);
            if (kierunekRuchu == Direction.RIGHT) return new Point(x + 1, y);
            else return new Point(x, y);
        }
    }

    @Override
    public void Action() {
        if (umiejetnosc.getCzyJestAktywna()) {
            Commentator.AddComment(OrganizmToSring() + " Undying is active (Cooldown time " +
                    umiejetnosc.getCzasTrwania() + " tours)");
            Undying();
        }
        for (int i = 0; i < getZasiegRuchu(); i++) {
            Point przyszlaPozycja = PlanTheMove();

            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja) &&
                    getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Colision(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) MakeMove(przyszlaPozycja);
            if (umiejetnosc.getCzyJestAktywna()) Undying();
        }
        kierunekRuchu = Direction.NO_DIRECTION;
        umiejetnosc.CheckResults();
    }

    @Override
    public String OrganismTypeToString() {
        return "Human";
    }

    public SpecialSkill GetSkill() {
        return umiejetnosc;
    }

    public void SetMoveDirection(Direction kierunekRuchu) {
        this.kierunekRuchu = kierunekRuchu;
    }
}