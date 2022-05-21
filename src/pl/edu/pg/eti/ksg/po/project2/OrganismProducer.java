package pl.edu.pg.eti.ksg.po.project2;

import pl.edu.pg.eti.ksg.po.project2.plants.*;
import pl.edu.pg.eti.ksg.po.project2.animals.*;
public class OrganismProducer {
    public static Organism MakeNewOrg
            (Organism.TypOrganizmu typOrganizmu, World swiat, Point pozycja) {
        switch (typOrganizmu) {
            case WOLF:
                return new Wolf(swiat, pozycja, swiat.getNumerTury());
            case SHEEP:
                return new Sheep(swiat, pozycja, swiat.getNumerTury());
            case FOX:
                return new Fox(swiat, pozycja, swiat.getNumerTury());
            case TURTLE:
                return new Turtle(swiat, pozycja, swiat.getNumerTury());
            case ANTELOPE:
                return new Antelope(swiat, pozycja, swiat.getNumerTury());
            case HUMAN:
                return new Human(swiat, pozycja, swiat.getNumerTury());
            case GRASS:
                return new Grass(swiat, pozycja, swiat.getNumerTury());
            case DANDELION:
                return new Dandelion(swiat, pozycja, swiat.getNumerTury());
            case GUARANA:
                return new Guarana(swiat, pozycja, swiat.getNumerTury());
            case DEADLY_NIGHTSHADE:
                return new DeadlyNightshade(swiat, pozycja, swiat.getNumerTury());
            case HOGWEED:
                return new Hogweed(swiat, pozycja, swiat.getNumerTury());
            case CYBER_SHEEP:
                return new CyberSheep(swiat, pozycja, swiat.getNumerTury());
            default:
                return null;
        }
    }
}