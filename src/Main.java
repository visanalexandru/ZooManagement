import zoo.cli.MainMenu;
import zoo.db.Database;

public class Main {
    public static void main(String[] args) {
        Database db = Database.getDatabase();
        MainMenu menu = new MainMenu();
        menu.render();
    }
}