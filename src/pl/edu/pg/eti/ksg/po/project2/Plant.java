package pl.edu.pg.eti.ksg.po.project2;

import java.util.Random;

public abstract class Plant extends Organism {

    protected Plant(TypOrganizmu typOrganizmu, World swiat,
                    Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setSzansaRozmnazania(0.3);
    }

    @Override
    public void Action() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie < getSzansaRozmnazania() * 100) Spreading();
    }
    @Override
    public void Colision(Organism other) {
    }
    @Override
    public boolean IfAnimal() {
        return false;
    }

    protected void Spreading() {
        Point tmp1Point = this.TakeFreePoint(getPozycja());
        if (tmp1Point.equals(getPozycja())) return;
        else {
            Organism tmpOrganism = OrganismProducer.MakeNewOrg(getTypOrganizmu(), this.getSwiat(), tmp1Point);
            Commentator.AddComment("New plant grows - " + tmpOrganism.OrganizmToSring());
            getSwiat().DodajOrganizm(tmpOrganism);
        }
    }
}