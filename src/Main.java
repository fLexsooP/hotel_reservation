import api.*;
import model.*;
import ui.*;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        boolean isRunning = true;
        while (isRunning) {
            isRunning = MainMenu.runMain();
        }
    }
}
