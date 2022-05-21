package pl.edu.pg.eti.ksg.po.project2;

import java.awt.*;
import java.util.Random;

public abstract class Organism {
    public enum TypOrganizmu {
        HUMAN,
        WOLF,
        SHEEP,
        FOX,
        TURTLE,
        ANTELOPE,
        CYBER_SHEEP,
        GRASS,
        DANDELION,
        GUARANA,
        DEADLY_NIGHTSHADE,
        HOGWEED;
    }

    public enum Direction {
        LEFT(0),
        RIGHT(1),
        UP(2),
        DOWN(3),
        NO_DIRECTION(4);

        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private int sila;
    private int inicjatywa;
    private int turaUrodzenia;
    private Color kolor;
    private boolean czyUmarl;
    private boolean[] kierunek;
    private boolean czyRozmnazalSie;
    private World swiat;
    private Point pozycja;
    private TypOrganizmu typOrganizmu;
    private double szansaRozmnazania;
    private static final int LICZBA_ROZNYCH_GATUNKOW = 12;

    public abstract String OrganismTypeToString();

    public abstract void Action();
    public abstract boolean IfAnimal();

    public Organism(TypOrganizmu typOrganizmu, World swiat,
                    Point pozycja, int turaUrodzenia, int sila, int inicjatywa) {
        this.typOrganizmu = typOrganizmu;
        this.swiat = swiat;
        this.pozycja = pozycja;
        this.turaUrodzenia = turaUrodzenia;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        czyUmarl = false;
        kierunek = new boolean[] {
                true,
                true,
                true,
                true
        };
    }

    public boolean AttackSpecialAction(Organism atakujacy, Organism ofiara) {
        return false;
    }

    public String OrganizmToSring() {
        return (OrganismTypeToString() + " x[" + pozycja.getX() + "] y[" +
                pozycja.getY() + "] strength[" + sila + "]");
    }

    public void MakeMove(Point przyszlaPozycja) {
        int x = przyszlaPozycja.getX();
        int y = przyszlaPozycja.getY();
        swiat.getPlansza()[pozycja.getY()][pozycja.getX()] = null;
        swiat.getPlansza()[y][x] = this;
        pozycja.setX(x);
        pozycja.setY(y);
    }

    static TypOrganizmu RandomType() {
        Random rand = new Random();
        int tmp = rand.nextInt(LICZBA_ROZNYCH_GATUNKOW - 1);
        if (tmp == 0) return TypOrganizmu.ANTELOPE;
        if (tmp == 1) return TypOrganizmu.HOGWEED;
        if (tmp == 2) return TypOrganizmu.GUARANA;
        if (tmp == 3) return TypOrganizmu.FOX;
        if (tmp == 4) return TypOrganizmu.DANDELION;
        if (tmp == 5) return TypOrganizmu.SHEEP;
        if (tmp == 6) return TypOrganizmu.GRASS;
        if (tmp == 7) return TypOrganizmu.DEADLY_NIGHTSHADE;
        if (tmp == 8) return TypOrganizmu.WOLF;
        if (tmp == 9) return TypOrganizmu.CYBER_SHEEP;
        else return TypOrganizmu.TURTLE;
    }

    public Point TakeRandomPoint(Point pozycja) {
        OpenAlldirections();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = swiat.getSizeX();
        int sizeY = swiat.getSizeY();
        int ileKierunkowMozliwych = 0;

        if (pozX == 0) BlockDirection(Direction.LEFT);
        else ileKierunkowMozliwych++;
        if (pozX == sizeX - 1) BlockDirection(Direction.RIGHT);
        else ileKierunkowMozliwych++;
        if (pozY == 0) BlockDirection(Direction.UP);
        else ileKierunkowMozliwych++;
        if (pozY == sizeY - 1) BlockDirection(Direction.DOWN);
        else ileKierunkowMozliwych++;

        if (ileKierunkowMozliwych == 0) return pozycja;
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

    public Point TakeFreePoint(Point pozycja) {
        OpenAlldirections();
        int pozX = pozycja.getX();
        int pozY = pozycja.getY();
        int sizeX = swiat.getSizeX();
        int sizeY = swiat.getSizeY();
        int ileKierunkowMozliwych = 0;

        if (pozX == 0) BlockDirection(Direction.LEFT);
        else {
            if (swiat.CzyPoleJestZajete(new Point(pozX - 1, pozY)) == false) ileKierunkowMozliwych++;
            else BlockDirection(Direction.LEFT);
        }

        if (pozX == sizeX - 1) BlockDirection(Direction.RIGHT);
        else {
            if (swiat.CzyPoleJestZajete(new Point(pozX + 1, pozY)) == false) ileKierunkowMozliwych++;
            else BlockDirection(Direction.RIGHT);
        }

        if (pozY == 0) BlockDirection(Direction.UP);
        else {
            if (swiat.CzyPoleJestZajete(new Point(pozX, pozY - 1)) == false) ileKierunkowMozliwych++;
            else BlockDirection(Direction.UP);
        }

        if (pozY == sizeY - 1) BlockDirection(Direction.DOWN);
        else {
            if (swiat.CzyPoleJestZajete(new Point(pozX, pozY + 1)) == false) ileKierunkowMozliwych++;
            else BlockDirection(Direction.DOWN);
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
    public abstract void Colision(Organism other);
    protected void BlockDirection(Direction kierunek) {
        this.kierunek[kierunek.getValue()] = false;
    }

    protected void OpenDirection(Direction kierunek) {
        this.kierunek[kierunek.getValue()] = true;
    }

    protected void OpenAlldirections() {
        OpenDirection(Direction.LEFT);
        OpenDirection(Direction.RIGHT);
        OpenDirection(Direction.UP);
        OpenDirection(Direction.DOWN);
    }

    protected boolean IfDirectionBlocked(Direction kierunek) {
        return !(this.kierunek[kierunek.getValue()]);
    }

    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public int getTuraUrodzenia() {
        return turaUrodzenia;
    }

    public boolean getCzyUmarl() {
        return czyUmarl;
    }

    public boolean getCzyRozmnazalSie() {
        return czyRozmnazalSie;
    }

    public World getSwiat() {
        return swiat;
    }

    public Point getPozycja() {
        return pozycja;
    }

    public TypOrganizmu getTypOrganizmu() {
        return typOrganizmu;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setTuraUrodzenia(int turaUrodzenia) {
        this.turaUrodzenia = turaUrodzenia;
    }

    public void setCzyUmarl(boolean czyUmarl) {
        this.czyUmarl = czyUmarl;
    }

    public void setCzyRozmnazalSie(boolean czyRozmnazalSie) {
        this.czyRozmnazalSie = czyRozmnazalSie;
    }

    public void setSwiat(World swiat) {
        this.swiat = swiat;
    }

    public void setPozycja(Point pozycja) {
        this.pozycja = pozycja;
    }

    public void setTypOrganizmu(TypOrganizmu typOrganizmu) {
        this.typOrganizmu = typOrganizmu;
    }

    public Color getKolor() {
        return kolor;
    }

    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }

    public double getSzansaRozmnazania() {
        return szansaRozmnazania;
    }

    public void setSzansaRozmnazania(double szansaRozmnazania) {
        this.szansaRozmnazania = szansaRozmnazania;
    }
    public boolean IfActive(){
        return false;
    }
}