package pl.edu.pg.eti.ksg.po.project2;
import java.util.Random;

public abstract class Animal extends Organism {
    private int zasiegRuchu;
    private double szansaWykonywaniaRuchu;

    public Animal(TypOrganizmu typOrganizmu, World swiat,
                  Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        super(typOrganizmu, swiat, pozycja, turaUrodzenia, sila, inicjatywa);
        setCzyRozmnazalSie(false);
        setSzansaRozmnazania(0.5);
    }

    @Override
    public void Action() {
        for (int i = 0; i < zasiegRuchu; i++) {
            Point przyszlaPozycja = PlanTheMove();
            if (getSwiat().CzyPoleJestZajete(przyszlaPozycja) &&
                    getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) {
                Colision(getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja));
                break;
            } else if (getSwiat().CoZnajdujeSieNaPolu(przyszlaPozycja) != this) MakeMove(przyszlaPozycja);
        }
    }
    @Override
    public boolean IfAnimal() {
        return true;
    }

    protected Point PlanTheMove() {
        Random rand = new Random();
        int upperbound = 100;
        int tmpLosowanie = rand.nextInt(upperbound);
        if (tmpLosowanie >= (int)(szansaWykonywaniaRuchu * 100)) return getPozycja();
        else return TakeRandomPoint(getPozycja());
    }

    @Override
    public void Colision(Organism other) {
        if (getTypOrganizmu() == other.getTypOrganizmu()) {
            Random rand = new Random();
            int tmpLosowanie = rand.nextInt(100);
            if (tmpLosowanie < getSzansaRozmnazania() * 100) Rozmnazanie(other);
        } else {
            if (other.AttackSpecialAction(this, other)) return;
            if (AttackSpecialAction(this, other)) return;

            if (getSila() >= other.getSila()) {

                    if(other.getTypOrganizmu() == TypOrganizmu.HUMAN){
                        if(other.IfActive()){

                        }
                        else{
                            getSwiat().UsunOrganizm(other);
                            MakeMove(other.getPozycja());
                            Commentator.AddComment(OrganizmToSring() + " kills " + other.OrganizmToSring());
                        }
                    }
                    else{
                        getSwiat().UsunOrganizm(other);
                        MakeMove(other.getPozycja());
                        Commentator.AddComment(OrganizmToSring() + " kills " + other.OrganizmToSring());
                    }

            } else {
                if(getTypOrganizmu() == TypOrganizmu.HUMAN){
                    if(IfActive()){

                    }
                    else{
                        getSwiat().UsunOrganizm(this);
                        Commentator.AddComment(other.OrganizmToSring() + " kills " + OrganizmToSring());
                    }
                }
                else{
                    getSwiat().UsunOrganizm(this);
                    Commentator.AddComment(other.OrganizmToSring() + " kills " + OrganizmToSring());
                }
            }
        }
    }

    private void Rozmnazanie(Organism other) {
        if (this.getCzyRozmnazalSie() || other.getCzyRozmnazalSie()) return;
        Point tmp1Point = this.TakeFreePoint(getPozycja());
        if (tmp1Point.equals(getPozycja())) {
            Point tmp2Point = other.TakeFreePoint(other.getPozycja());
            if (tmp2Point.equals(other.getPozycja())) return;
            else {
                Organism tmpOrganism = OrganismProducer.MakeNewOrg(getTypOrganizmu(), this.getSwiat(), tmp2Point);
                Commentator.AddComment(tmpOrganism.OrganizmToSring() + "was born!");
                getSwiat().DodajOrganizm(tmpOrganism);
                setCzyRozmnazalSie(true);
                other.setCzyRozmnazalSie(true);
            }
        } else {
            Organism tmpOrganism = OrganismProducer.MakeNewOrg(getTypOrganizmu(), this.getSwiat(), tmp1Point);
            Commentator.AddComment(tmpOrganism.OrganizmToSring() + "was born!");
            getSwiat().DodajOrganizm(tmpOrganism);
            setCzyRozmnazalSie(true);
            other.setCzyRozmnazalSie(true);
        }
    }

    public int getZasiegRuchu() {
        return zasiegRuchu;
    }

    public void setZasiegRuchu(int zasiegRuchu) {
        this.zasiegRuchu = zasiegRuchu;
    }

    public double getSzansaWykonywaniaRuchu() {
        return szansaWykonywaniaRuchu;
    }

    public void setSzansaWykonywaniaRuchu(double szansaWykonywaniaRuchu) {
        this.szansaWykonywaniaRuchu = szansaWykonywaniaRuchu;
    }

}