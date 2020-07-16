package application;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Const {
    public static final String FORMATED_SPACE = "       ";
    public static final int FILE_PATH_SIZE = 150;
    public static final int INDEX_OF_RAPORT_READING_LINE_START = 3;
    public static final String ENDING_STRING_OF_READ_LINE = "sprawdzenia";
    public static final String PROGRAM_TITLE = "Program sprawdzający kodowanie plików tekstowych";
    public static final String BUTTON_EXIT_TITLE = "Wyjdź";
    public static final String BUTTON_OPEN_TITLE = "Otwórz raport";
    public static final String BUTTON_SEARCH_TITLE = "Wybierz pliki";
    public static final String BUTTON_BACK_TITLE = "Zamknij";
    public static final String RAPORT_WINDOW_TITLE = "Wynik wyszukiwania";
    public static final String RAPORT_WINDOW_LOG = "Utworzono nowy raport!";
    public static final int PRIMARY_STAGE_WIDTH = 400;
    public static final int PRIMARY_STAGE_HEIGHT = 600;
    public static final int RAPORT_WINDOW_WIDTH = 300;
    public static final int RAPORT_WINDOW_HEIGHT = 100;
    public static final String RAPORT_DATA_PREFIX = "Data sprawdzenia: ";
    public static final String RAPORT_CODING_PREFIX = "Kodowanie: ";
    public static final String RAPORT_TITLE = "---Jest to raport wygenerowany przez program Quality Browsing System---";
    public static final String STRING_WHICH_END_DATA_TO_COMPARE = "Data sprawdzenia: ";
    public static final Path RAPORT_PATH = Paths.get("Raport ze sprawdzenia kodowania.txt");
}
