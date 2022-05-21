package pl.edu.pg.eti.ksg.po.project2;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import pl.edu.pg.eti.ksg.po.project2.animals.Human;

public class World {
    private int sizeX;
    private int sizeY;
    private int numerTury;
    private Organism[][] plansza;
    private boolean czyCzlowiekZyje;
    private boolean czyJestKoniecGry;
    private boolean pauza;
    private ArrayList <Organism> organizmy;
    private Human human;
    private Display swiatGUI;

    public World(Display swiatGUI) {
        this.sizeX = 0;
        this.sizeY = 0;
        numerTury = 0;
        czyCzlowiekZyje = true;
        czyJestKoniecGry = false;
        pauza = true;
        organizmy = new ArrayList < > ();
        this.swiatGUI = swiatGUI;
    }

    public World(int sizeX, int sizeY, Display swiatGUI) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        numerTury = 0;
        czyCzlowiekZyje = true;
        czyJestKoniecGry = false;
        pauza = true;
        plansza = new Organism[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                plansza[i][j] = null;
            }
        }
        organizmy = new ArrayList < > ();
        this.swiatGUI = swiatGUI;
    }

    public void SaveTheWorld(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);
            file.createNewFile();

            PrintWriter pw = new PrintWriter(file);
            pw.print(sizeX + " ");
            pw.print(sizeY + " ");
            pw.print(numerTury + " ");
            pw.print(czyCzlowiekZyje + " ");
            pw.print(czyJestKoniecGry + "\n");
            for (int i = 0; i < organizmy.size(); i++) {
                pw.print(organizmy.get(i).getTypOrganizmu() + " ");
                pw.print(organizmy.get(i).getPozycja().getX() + " ");
                pw.print(organizmy.get(i).getPozycja().getY() + " ");
                pw.print(organizmy.get(i).getSila() + " ");
                pw.print(organizmy.get(i).getTuraUrodzenia() + " ");
                pw.print(organizmy.get(i).getCzyUmarl());
                if (organizmy.get(i).getTypOrganizmu() == Organism.TypOrganizmu.HUMAN) {
                    pw.print(" " + human.GetSkill().getCzasTrwania() + " ");
                    pw.print(human.GetSkill().getCooldown() + " ");
                    pw.print(human.GetSkill().getCzyJestAktywna() + " ");
                    pw.print(human.GetSkill().getCzyMoznaAktywowac());
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Saving error :(" + e);
        }
    }

    public static World OpentheWorld(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            World tmpSwiat = new World(sizeX, sizeY, null);
            int numerTury = Integer.parseInt(properties[2]);
            tmpSwiat.numerTury = numerTury;
            boolean czyCzlowiekZyje = Boolean.parseBoolean(properties[3]);
            tmpSwiat.czyCzlowiekZyje = czyCzlowiekZyje;
            boolean czyJestKoniecGry = Boolean.parseBoolean(properties[4]);
            tmpSwiat.czyJestKoniecGry = czyJestKoniecGry;
            tmpSwiat.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organism.TypOrganizmu typOrganizmu = Organism.TypOrganizmu.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organism tmpOrganism = OrganismProducer.MakeNewOrg(typOrganizmu, tmpSwiat, new Point(x, y));
                int sila = Integer.parseInt(properties[3]);
                tmpOrganism.setSila(sila);
                int turaUrodzenia = Integer.parseInt(properties[4]);
                tmpOrganism.setTuraUrodzenia(turaUrodzenia);
                boolean czyUmarl = Boolean.parseBoolean(properties[5]);
                tmpOrganism.setCzyUmarl(czyUmarl);

                if (typOrganizmu == Organism.TypOrganizmu.HUMAN) {
                    tmpSwiat.human = (Human) tmpOrganism;
                    int czasTrwania = Integer.parseInt(properties[6]);
                    tmpSwiat.human.GetSkill().setCzasTrwania(czasTrwania);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpSwiat.human.GetSkill().setCooldown(cooldown);
                    boolean czyJestAktywna = Boolean.parseBoolean(properties[8]);
                    tmpSwiat.human.GetSkill().setCzyJestAktywna(czyJestAktywna);
                    boolean czyMoznaAktywowac = Boolean.parseBoolean(properties[9]);
                    tmpSwiat.human.GetSkill().setCzyMoznaAktywowac(czyMoznaAktywowac);
                }
                tmpSwiat.DodajOrganizm(tmpOrganism);
            }
            scanner.close();
            return tmpSwiat;
        } catch (
                IOException e) {
            System.out.println("Loading error :(" + e);
        }
        return null;
    }

    public void GenerateWorld(double zapelnienieSwiatu) {
        int liczbaOrganizmow = (int) Math.floor(sizeX * sizeY * zapelnienieSwiatu);
        Point pozycja = RandomFreePoint();
        Organism tmpOrganism = OrganismProducer.MakeNewOrg(Organism.TypOrganizmu.HUMAN, this, pozycja);
        DodajOrganizm(tmpOrganism);
        human = (Human) tmpOrganism;
        for (int i = 0; i < liczbaOrganizmow - 1; i++) {
            pozycja = RandomFreePoint();
            if (pozycja != new Point(-1, -1)) {
                DodajOrganizm(OrganismProducer.MakeNewOrg(Organism.RandomType(), this, pozycja));
            } else return;
        }
    }

    public void MakeTheTour() {
        if (czyJestKoniecGry) return;
        numerTury++;
        Commentator.AddComment("\nTour number: " + numerTury);
        System.out.println(numerTury);
        System.out.println(organizmy.size() + "\n");
        OrganismsSort();
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy.get(i).getTuraUrodzenia() != numerTury &&
                    organizmy.get(i).getCzyUmarl() == false) {
                organizmy.get(i).Action();
            }
        }
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy.get(i).getCzyUmarl() == true) {
                organizmy.remove(i);
                i--;
            }
        }
        for (int i = 0; i < organizmy.size(); i++) {
            organizmy.get(i).setCzyRozmnazalSie(false);
        }
    }

    private void OrganismsSort() {
        Collections.sort(organizmy, new Comparator <Organism> () {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.getInicjatywa() != o2.getInicjatywa())
                    return Integer.valueOf(o2.getInicjatywa()).compareTo(o1.getInicjatywa());
                else
                    return Integer.valueOf(o1.getTuraUrodzenia()).compareTo(o2.getTuraUrodzenia());
            }
        });
    }

    public Point RandomFreePoint() {
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (plansza[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (plansza[y][x] == null) return new Point(x, y);
                    }
                }
            }
        }
        return new Point(-1, -1);
    }

    public boolean CzyPoleJestZajete(Point pole) {
        if (plansza[pole.getY()][pole.getX()] == null) return false;
        else return true;
    }

    public Organism CoZnajdujeSieNaPolu(Point pole) {
        return plansza[pole.getY()][pole.getX()];
    }

    public void DodajOrganizm(Organism organism) {
        organizmy.add(organism);
        plansza[organism.getPozycja().getY()][organism.getPozycja().getX()] = organism;
    }

    public void UsunOrganizm(Organism organism) {
        plansza[organism.getPozycja().getY()][organism.getPozycja().getX()] = null;
        organism.setCzyUmarl(true);
        if (organism.getTypOrganizmu() == Organism.TypOrganizmu.HUMAN) {
            czyCzlowiekZyje = false;
            human = null;
        }
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getNumerTury() {
        return numerTury;
    }

    public Organism[][] getPlansza() {
        return plansza;
    }

    public boolean getCzyCzlowiekZyje() {
        return czyCzlowiekZyje;
    }

    public boolean getCzyJestKoniecGry() {
        return czyJestKoniecGry;
    }

    public ArrayList <Organism> getOrganizmy() {
        return organizmy;
    }

    public Human getCzlowiek() {
        return human;
    }

    public void setCzlowiek(Human human) {
        this.human = human;
    }

    public void setCzyCzlowiekZyje(boolean czyCzlowiekZyje) {
        this.czyCzlowiekZyje = czyCzlowiekZyje;
    }

    public void setCzyJestKoniecGry(boolean czyJestKoniecGry) {
        this.czyJestKoniecGry = czyJestKoniecGry;
    }

    public boolean isPauza() {
        return pauza;
    }

    public void setPauza(boolean pauza) {
        this.pauza = pauza;
    }

    public Display getSwiatGUI() {
        return swiatGUI;
    }

    public void setSwiatGUI(Display swiatGUI) {
        this.swiatGUI = swiatGUI;
    }

    public boolean czyIstniejeBarszczSosnowskiego() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (plansza[i][j] != null &&
                        plansza[i][j].getTypOrganizmu() == Organism.TypOrganizmu.HOGWEED) {
                    return true;
                }
            }
        }
        return false;
    }
}