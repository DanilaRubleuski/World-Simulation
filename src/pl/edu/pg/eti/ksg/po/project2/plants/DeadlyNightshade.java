package pl.edu.pg.eti.ksg.po.project2.plants;

import pl.edu.pg.eti.ksg.po.project2.*;
import pl.edu.pg.eti.ksg.po.project2.Point;

import java.awt.*;
import java.util.Random;

public class DeadlyNightshade extends Plant {
    private static final int SILA_WILCZE_JAGODY = 99;
    private static final int INICJATYWA_WILCZE_JAGODY = 0;

    public DeadlyNightshade(World swiat, Point pozycja, int turaUrodzenia) {
        super(TypOrganizmu.DEADLY_NIGHTSHADE, swiat, pozycja, turaUrodzenia, SILA_WILCZE_JAGODY, INICJATYWA_WILCZE_JAGODY);
        setKolor(new Color(120, 38, 120));
        setSzansaRozmnazania(0.05);
    }
    @Override
    public void Action() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Spreading();
    }
    @Override
    public String OrganismTypeToString() {
        return "Deadly Nightshade";
    }

    @Override
    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        Commentator.AddComment(atakujacy.OrganizmToSring() + " eats " + this.OrganizmToSring());
        if (atakujacy.getSila() >= 99) {
            getSwiat().UsunOrganizm(this);
            Commentator.AddComment(atakujacy.OrganizmToSring() + " destroys the Deadly Nightshade bush ");
        }
        if (atakujacy.IfAnimal()) {
            if(atakujacy.getTypOrganizmu() == TypOrganizmu.HUMAN){
                if(atakujacy.IfActive()){

                }
                else{
                    getSwiat().UsunOrganizm(atakujacy);
                    Commentator.AddComment(atakujacy.OrganizmToSring() + " dies of WilczeJagody");
                }
            }
            else{
                getSwiat().UsunOrganizm(atakujacy);
                Commentator.AddComment(atakujacy.OrganizmToSring() + " dies of WilczeJagody");
            }
        }

        return true;
    }
}