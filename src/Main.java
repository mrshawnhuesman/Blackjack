import Controller.Controller;
import View.CLIView;

/**
 * Description: Creates a new view and controller then starts Blackjack
 * @author shuesman
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        CLIView cliView = new CLIView();
        Controller controller = new Controller(cliView);
        controller.playBlackjack();
    }
}
