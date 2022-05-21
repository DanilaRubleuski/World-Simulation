package pl.edu.pg.eti.ksg.po.project2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Display implements ActionListener, KeyListener {
    private Toolkit toolkit;
    private Dimension dimension;
    private JFrame jFrame;
    private JMenu menu;
    private JMenuItem newGame, load, save, exit;
    private PlaneGraphics planszaGraphics = null;
    private KomentatorGraphics komentatorGraphics = null;
    private OrganismsBar oznaczenia = null;
    private JPanel mainPanel;
    private final int ODSTEP;
    private World swiat;

    public Display(String title) {
        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        ODSTEP = dimension.height / 100;

        jFrame = new JFrame(title);
        jFrame.setBounds((dimension.width - 1600) / 2, (dimension.height - 1000) / 2, 1400, 1000);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New game");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        jFrame.setJMenuBar(menuBar);
        jFrame.setLayout(new CardLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setForeground(Color.DARK_GRAY);
        mainPanel.setLayout(null);

        jFrame.addKeyListener(this);
        jFrame.add(mainPanel);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            Commentator.ClearComments();
            int sizeX = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Give the width of the board: ", "0"));
            int sizeY = Integer.parseInt(JOptionPane.showInputDialog(jFrame,
                    "Give the height of the board: ", "0"));
            double zapelnienieSwiatu = Double.parseDouble(JOptionPane.showInputDialog(jFrame, "Enter initial world population (value from 0 to 1)", "0"));
            swiat = new World(sizeX, sizeY, this);
            swiat.GenerateWorld(zapelnienieSwiatu);
            if (planszaGraphics != null)
                mainPanel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                mainPanel.remove(komentatorGraphics);
            if (oznaczenia != null)
                mainPanel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == load) {
            Commentator.ClearComments();
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Give the name of the file to open the world", "temp");
            swiat = World.OpentheWorld(nameOfFile);
            swiat.setSwiatGUI(this);
            planszaGraphics = new PlaneGraphics(swiat);
            komentatorGraphics = new KomentatorGraphics();
            oznaczenia = new OrganismsBar();
            if (planszaGraphics != null)
                mainPanel.remove(planszaGraphics);
            if (komentatorGraphics != null)
                mainPanel.remove(komentatorGraphics);
            if (oznaczenia != null)
                mainPanel.remove(oznaczenia);
            startGame();
        }
        if (e.getSource() == save) {
            String nameOfFile = JOptionPane.showInputDialog(jFrame, "Give the name of the file to save the game", "temp");
            swiat.SaveTheWorld(nameOfFile);
            Commentator.AddComment("The game state has been saved!");
            komentatorGraphics.RefreshComments();
        }
        if (e.getSource() == exit) {
            jFrame.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null && swiat.isPauza()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            } else if (swiat.getCzyCzlowiekZyje()) {
                if (keyCode == KeyEvent.VK_UP) {
                    swiat.getCzlowiek().SetMoveDirection(Organism.Direction.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    swiat.getCzlowiek().SetMoveDirection(Organism.Direction.DOWN);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    swiat.getCzlowiek().SetMoveDirection(Organism.Direction.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    swiat.getCzlowiek().SetMoveDirection(Organism.Direction.RIGHT);
                } else if (keyCode == KeyEvent.VK_F) {
                    SpecialSkill tmpUmiejetnosc = swiat.getCzlowiek().GetSkill();
                    if (tmpUmiejetnosc.getCzyMoznaAktywowac()) {
                        tmpUmiejetnosc.Activate();
                        Commentator.AddComment("'Undying' skill has been activated (Active time is " +
                                tmpUmiejetnosc.getCzasTrwania() + " tours)");

                    } else if (tmpUmiejetnosc.getCzyJestAktywna()) {
                        Commentator.AddComment("'Undying' skill has been activated (Active time is " +
                                tmpUmiejetnosc.getCzasTrwania() + " tours)");
                        komentatorGraphics.RefreshComments();
                        return;
                    } else {
                        Commentator.AddComment("'Undying' skill has been already activated (Cooldown time: " +
                                tmpUmiejetnosc.getCooldown() + " tours)");
                        komentatorGraphics.RefreshComments();
                        return;
                    }
                } else {
                    Commentator.AddComment("\nUnknown symbol, try again.");
                    komentatorGraphics.RefreshComments();
                    return;
                }
            } else if (!swiat.getCzyCzlowiekZyje() && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_F)) {
                Commentator.AddComment("The human is dead, you cannot move him anymore :(");
                komentatorGraphics.RefreshComments();
                return;
            } else {
                Commentator.AddComment("\nUnknown symbol, try again.");
                komentatorGraphics.RefreshComments();
                return;
            }
            Commentator.ClearComments();
            swiat.setPauza(false);
            swiat.MakeTheTour();
            WorldRefresh();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private class PlaneGraphics extends JPanel {
        private final int sizeX;
        private final int sizeY;
        private PolePlanszy[][] polaPlanszy;
        private World SWIAT;

        public PlaneGraphics(World swiat) {
            super();
            setBounds(mainPanel.getHeight() * 5 / 12 + 10 * ODSTEP, mainPanel.getHeight() * 1 / 16 + 5 * ODSTEP,
                    mainPanel.getHeight() * 5 / 6 - ODSTEP, mainPanel.getHeight() * 5 / 6 - ODSTEP);

            SWIAT = swiat;
            this.sizeX = swiat.getSizeX();
            this.sizeY = swiat.getSizeY();

            polaPlanszy = new PolePlanszy[sizeY][sizeX];
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    polaPlanszy[i][j] = new PolePlanszy(j, i);
                    polaPlanszy[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PolePlanszy) {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if (tmpPole.isEmpty == true) {
                                    OrganismsList listaOrganizmow = new OrganismsList(tmpPole.getX() + jFrame.getX(),
                                            tmpPole.getY() + jFrame.getY(),
                                            new Point(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }
                        }
                    });
                }
            }

            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    this.add(polaPlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(sizeY, sizeX));
        }

        private class PolePlanszy extends JButton {
            private boolean isEmpty;
            private Color kolor;
            private final int pozX;
            private final int pozY;

            public PolePlanszy(int X, int Y) {
                super();
                kolor = Color.LIGHT_GRAY;
                setBackground(kolor);
                isEmpty = true;
                pozX = X;
                pozY = Y;
            }

            public boolean isEmpty() {
                return isEmpty;
            }

            public void setEmpty(boolean empty) {
                isEmpty = empty;
            }

            public Color getKolor() {
                return kolor;
            }

            public void setKolor(Color kolor) {
                this.kolor = kolor;
                setBackground(kolor);

            }

            public int getPozX() {
                return pozX;
            }

            public int getPozY() {
                return pozY;
            }
        }

        public void RefreshPlane() {
            for (int i = 0; i < sizeY; i++) {
                for (int j = 0; j < sizeX; j++) {
                    Organism tmpOrganism = swiat.getPlansza()[i][j];
                    if (tmpOrganism != null) {
                        polaPlanszy[i][j].setEmpty(false);
                        polaPlanszy[i][j].setEnabled(false);
                        polaPlanszy[i][j].setKolor(tmpOrganism.getKolor());
                    } else {
                        polaPlanszy[i][j].setEmpty(true);
                        polaPlanszy[i][j].setEnabled(true);
                        polaPlanszy[i][j].setKolor(Color.LIGHT_GRAY);
                    }
                }
            }
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            return sizeY;
        }

        public PolePlanszy[][] getPolaPlanszy() {
            return polaPlanszy;
        }
    }

    private class KomentatorGraphics extends JPanel {
        private String tekst;
        private final String instriction = "Danila Rubleuski\n" +
                "<-------------Controls------------->\n" +
                "|  Arrows  |  Human movement  |\n" +
                "|   F or f     |      Skill activation      |\n" +
                "|   Enter    |   Go to the next tour   |\n" +
                "<-------------------------------------->\n";

        private JTextArea textArea;

        public KomentatorGraphics() {
            super();
            setBounds(mainPanel.getX() + 10 * ODSTEP, mainPanel.getHeight() * 1 / 16 + 5 * ODSTEP,
                    mainPanel.getHeight() * 5 / 12 - ODSTEP, mainPanel.getHeight() * 5 / 6 - ODSTEP);
            tekst = Commentator.getTekst();

            textArea = new JTextArea(tekst);
            textArea.setEditable(false);
            setLayout(new CardLayout());
            textArea.setBackground(Color.DARK_GRAY);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            textArea.setForeground(Color.WHITE);
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void RefreshComments() {
            tekst = instriction + Commentator.getTekst();
            textArea.setText(tekst);
        }
    }

    private class OrganismsList extends JFrame {
        private String[] listaOrganizmow;
        private Organism.TypOrganizmu[] typOrganizmuList;
        private JList jList;

        public OrganismsList(int x, int y, Point point) {
            super("Organisms List");
            setBounds(x, y, 200, 300);
            listaOrganizmow = new String[] {
                    "Wilk",
                    "Owca",
                    "Lis",
                    "Zolw",
                    "Antylopa",
                    "Cyber owca",
                    "Trawa",
                    "Mlecz",
                    "Guarana",
                    "Wilcze jagody",
                    "Barszcz Sosnowskiego"
            };
            typOrganizmuList = new Organism.TypOrganizmu[] {
                    Organism.TypOrganizmu.WOLF, Organism.TypOrganizmu.SHEEP,
                    Organism.TypOrganizmu.FOX, Organism.TypOrganizmu.TURTLE, Organism.TypOrganizmu.ANTELOPE,
                    Organism.TypOrganizmu.CYBER_SHEEP, Organism.TypOrganizmu.GRASS, Organism.TypOrganizmu.DANDELION,
                    Organism.TypOrganizmu.GUARANA, Organism.TypOrganizmu.DEADLY_NIGHTSHADE,
                    Organism.TypOrganizmu.HOGWEED
            };

            jList = new JList(listaOrganizmow);
            jList.setVisibleRowCount(listaOrganizmow.length);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.setBackground(Color.DARK_GRAY);
            jList.setForeground(Color.WHITE);
            jList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Organism tmpOrganism = OrganismProducer.MakeNewOrg(typOrganizmuList[jList.getSelectedIndex()], swiat, point);
                    swiat.DodajOrganizm(tmpOrganism);
                    Commentator.AddComment("New organism has been made!: " + tmpOrganism.OrganizmToSring());
                    WorldRefresh();
                    dispose();

                }
            });

            JScrollPane sp = new JScrollPane(jList);
            add(sp);
            setVisible(true);
        }
    }

    private class OrganismsBar extends JPanel {
        private final int ILOSC_TYPOW = 12;
        private JButton[] jButtons;

        public OrganismsBar() {
            super();
            setBounds(mainPanel.getX() + ODSTEP, mainPanel.getY() + ODSTEP,
                    mainPanel.getWidth() - ODSTEP * 2, mainPanel.getHeight() * 1 / 16 - 2 * ODSTEP);

            setBackground(Color.DARK_GRAY);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            jButtons = new JButton[ILOSC_TYPOW];

            jButtons[0] = new JButton("Human");
            jButtons[0].setBackground(new Color(0, 88, 255));

            jButtons[1] = new JButton("Wolf");
            jButtons[1].setBackground(new Color(56, 56, 56));

            jButtons[2] = new JButton("Sheep");
            jButtons[2].setBackground(new Color(199, 156, 185));

            jButtons[3] = new JButton("Fox");
            jButtons[3].setBackground(new Color(241, 80, 39));

            jButtons[4] = new JButton("Turtle");
            jButtons[4].setBackground(new Color(51, 102, 0));

            jButtons[5] = new JButton("Antelope");
            jButtons[5].setBackground(new Color(179, 95, 29));

            jButtons[6] = new JButton("CyberSheep");
            jButtons[6].setBackground(new Color(0, 0, 0));

            jButtons[7] = new JButton("Grass");
            jButtons[7].setBackground(new Color(132, 255, 0));

            jButtons[8] = new JButton("Dandelion");
            jButtons[8].setBackground(new Color(255, 210, 0));

            jButtons[9] = new JButton("Guarana");
            jButtons[9].setBackground(new Color(189, 0, 0));

            jButtons[10] = new JButton("DeadlyNightshade");
            jButtons[10].setBackground(new Color(120, 38, 120));

            jButtons[11] = new JButton("Hogweed");
            jButtons[11].setBackground(new Color(255, 0, 255));

            for (int i = 0; i < ILOSC_TYPOW; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }

        }
    }

    private void startGame() {
        planszaGraphics = new PlaneGraphics(swiat);
        mainPanel.add(planszaGraphics);

        komentatorGraphics = new KomentatorGraphics();
        mainPanel.add(komentatorGraphics);

        oznaczenia = new OrganismsBar();
        mainPanel.add(oznaczenia);

        WorldRefresh();
    }

    public void WorldRefresh() {

        planszaGraphics.RefreshPlane();
        komentatorGraphics.RefreshComments();
        SwingUtilities.updateComponentTreeUI(jFrame);
        jFrame.requestFocusInWindow();
    }

    public World getSwiat() {
        return swiat;
    }

    public PlaneGraphics getPlanszaGraphics() {
        return planszaGraphics;
    }

    public KomentatorGraphics getKomentatorGraphics() {
        return komentatorGraphics;
    }
}