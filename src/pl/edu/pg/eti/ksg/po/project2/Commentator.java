package pl.edu.pg.eti.ksg.po.project2;

public class Commentator {
    private static String tekst = "";
    public static void AddComment(String komentarz) {
        tekst += komentarz + "\n";
    }
    public static String getTekst() {
        return tekst;
    }
    public static void ClearComments() {
        tekst = "";
    }
}