package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import org.mozilla.universalchardet.CharsetListener;
import org.mozilla.universalchardet.UniversalDetector;

public class AppMethods {
    private static boolean isNecessaryToCreateRaport = true;
    private static File createdEmptyRaportFile = new File("Raport ze sprawdzenia kodowania.txt");
    public static List<String> txtFilesFoundInChoosenDirectory = new ArrayList();
    private static File[] listedFilesFromDirectory;
    private static List<String> encodingg = new ArrayList();
    public static int status;
    private static List<String> listOfAllFoundFiles = new ArrayList();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
    private static String data;
    private static File raportFileWithDataInTitle;
    public static String progress;

    static {
        data = dateFormat.format(Calendar.getInstance().getTime());
        raportFileWithDataInTitle = new File("Raport ze sprawdzenia kodowania " + data + ".txt");
    }

    public AppMethods() {
    }

    public static void onButtonExitEvent() {
        System.exit(0);
    }

    public static void onButtonSearchEvent(Label labelFilesNamesToShow, Label labelSetTextWhatUserDo) {
        labelSetTextWhatUserDo.setText("Wybierz katalog w którym chcesz sprawdzić kodowanie plików tekstowych");
        labelSetTextWhatUserDo.setWrapText(true);
        labelSetTextWhatUserDo.setTextAlignment(TextAlignment.CENTER);
        List<String> filesReadFromExistingRaport = new ArrayList();
        List<String> filesPathsToCompareWithChoosenFiles = new ArrayList();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File choosenDirectory = directoryChooser.showDialog((Window)null);
        directoryChooserMethod(labelFilesNamesToShow, labelSetTextWhatUserDo, choosenDirectory);
        filesReadFromExistingRaport = createRaportFileInformUserIfError(filesReadFromExistingRaport, labelSetTextWhatUserDo);

        try {
            for(int i = 3; i < filesReadFromExistingRaport.size(); ++i) {
                int lastIndexOfFilePathFromExistingRaport = ((String)filesReadFromExistingRaport.get(i)).lastIndexOf("sprawdzenia");
                String filePathFromExistingRaport = ((String)filesReadFromExistingRaport.get(i)).substring(0, lastIndexOfFilePathFromExistingRaport);
                filesPathsToCompareWithChoosenFiles.add(filePathFromExistingRaport);
            }
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        writtingFilesCheckedToFile(labelFilesNamesToShow, filesReadFromExistingRaport, filesPathsToCompareWithChoosenFiles);
        if (txtFilesFoundInChoosenDirectory.isEmpty()) {
            isNecessaryToCreateRaport = false;
        }

        if (isNecessaryToCreateRaport) {
            try {
                Files.write(Const.RAPORT_PATH, filesReadFromExistingRaport, StandardCharsets.UTF_8);
                Files.write(raportFileWithDataInTitle.toPath(), listOfAllFoundFiles, StandardCharsets.UTF_8);
            } catch (IOException var9) {
                var9.printStackTrace();
            }
        }

    }

    private static void writtingFilesCheckedToFile(Label labelFilesNamesToShow, List<String> filesReadFromExistingRaport, List<String> filesPathsToCompareWithChoosenFiles) {
        listOfAllFoundFiles.add("---Jest to raport wygenerowany przez program Quality Browsing System---");
        listOfAllFoundFiles.add("\n");

        for(int i = 0; i < txtFilesFoundInChoosenDirectory.size(); ++i) {
            String timeStamp = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(Calendar.getInstance().getTime());
            StringBuffer pathBuffer = new StringBuffer(Math.abs(150 - ((String)txtFilesFoundInChoosenDirectory.get(i)).length()));
            StringBuffer dateBuffer = new StringBuffer("       ");
            StringBuffer codingBuffer = new StringBuffer("       ");

            for(int j = 0; j < Math.abs(150 - ((String)txtFilesFoundInChoosenDirectory.get(i)).length()); ++j) {
                pathBuffer.append(" ");
            }

            dateBuffer.append("       ").toString();
            codingBuffer.append("       ").toString();
            pathBuffer.toString();
            if (!filesPathsToCompareWithChoosenFiles.contains((String)txtFilesFoundInChoosenDirectory.get(i) + pathBuffer + "Data sprawdzenia: ") && filesReadFromExistingRaport.size() != 0) {
                filesReadFromExistingRaport.add((String)txtFilesFoundInChoosenDirectory.get(i) + pathBuffer + "Data sprawdzenia: " + dateBuffer + timeStamp + codingBuffer + "Kodowanie: " + (String)encodingg.get(i));
                isNecessaryToCreateRaport = true;
            } else if (!filesPathsToCompareWithChoosenFiles.contains((String)txtFilesFoundInChoosenDirectory.get(i) + pathBuffer + "Data sprawdzenia: ") && filesReadFromExistingRaport.size() == 0) {
                filesReadFromExistingRaport.add("---Jest to raport wygenerowany przez program Quality Browsing System---");
                filesReadFromExistingRaport.add("\n");
                filesReadFromExistingRaport.add((String)txtFilesFoundInChoosenDirectory.get(i) + pathBuffer + "Data sprawdzenia: " + dateBuffer + timeStamp + codingBuffer + "Kodowanie: " + (String)encodingg.get(i));
                isNecessaryToCreateRaport = true;
            }

            try {
                raportFileWithDataInTitle.createNewFile();
            } catch (IOException var9) {
                var9.printStackTrace();
            }

            listOfAllFoundFiles.add((String)txtFilesFoundInChoosenDirectory.get(i) + pathBuffer + "Data sprawdzenia: " + dateBuffer + timeStamp + codingBuffer + "Kodowanie: " + (String)encodingg.get(i));
        }

    }

    private static void directoryChooserMethod(Label labelFilesNamesToShow, Label labelSetTextWhatUserDo, File choosenDirectory) {
        if (choosenDirectory != null) {
            List<String> directoriesFoundInChoosenDirectory = new ArrayList();
            directoriesFoundInChoosenDirectory.add(choosenDirectory.getPath());
            labelSetTextWhatUserDo.setText("Program sprawdził kodowanie plików we wskazanym katalogu");

            while(!directoriesFoundInChoosenDirectory.isEmpty()) {
                String pathOfFiristDirectoryFoundInChoosenDirectory = (String)directoriesFoundInChoosenDirectory.get(0);
                labelFilesNamesToShow.setText(pathOfFiristDirectoryFoundInChoosenDirectory);
                labelFilesNamesToShow.textProperty().setValue(pathOfFiristDirectoryFoundInChoosenDirectory);
                boolean isFileToBeRemoved = false;
                File FiristDirectoryFoundInChoosenDirectory = new File(pathOfFiristDirectoryFoundInChoosenDirectory);
                isFileToBeRemoved = searchingForTxtFiles(directoriesFoundInChoosenDirectory, labelSetTextWhatUserDo, FiristDirectoryFoundInChoosenDirectory);
                removingCheckedDirectoryFromList(directoriesFoundInChoosenDirectory, isFileToBeRemoved);
            }
        }

    }

    private static boolean searchingForTxtFiles(List<String> directoriesFoundInChoosenDirectory, Label labelSetTextWhatUserDo, File FiristDirectoryFoundInChoosenDirectory) {
        boolean isFileToBeRemoved = false;

        try {
            if (FiristDirectoryFoundInChoosenDirectory.list().length != 0) {
                listedFilesFromDirectory = FiristDirectoryFoundInChoosenDirectory.listFiles();

                for(int i = 0; i < listedFilesFromDirectory.length; ++i) {
                    try {
                        if (listedFilesFromDirectory[i].getName().contains(".")) {
                            int lastIndexOfDotInFilePath = listedFilesFromDirectory[i].getName().lastIndexOf(46);
                            String extensionFromFilePath = listedFilesFromDirectory[i].getName().substring(lastIndexOfDotInFilePath);
                            if (extensionFromFilePath.equals(".txt") || extensionFromFilePath.equals(".html") || extensionFromFilePath.equals(".ihtml")) {
                                txtFilesFoundInChoosenDirectory.add(listedFilesFromDirectory[i].getPath());
                                encodingCheck(listedFilesFromDirectory[i]);
                                status = i;
                            }
                        } else {
                            directoriesFoundInChoosenDirectory.add(listedFilesFromDirectory[i].getPath());
                        }
                    } catch (Exception var7) {
                        var7.printStackTrace();
                        labelSetTextWhatUserDo.setText("Error");
                    }
                }

                isFileToBeRemoved = true;
            } else {
                isFileToBeRemoved = true;
            }
        } catch (Exception var8) {
            isFileToBeRemoved = true;
        }

        return isFileToBeRemoved;
    }

    public static void encodingCheck(File file) throws FileNotFoundException {
        byte[] buf = new byte[4096];
        InputStream fis = new FileInputStream(file);
        UniversalDetector detector = new UniversalDetector((CharsetListener)null);

        int nread;
        try {
            while((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            encodingg.add(encoding);
        } else {
            encodingg.add("---");
        }

        detector.reset();
    }

    private static void removingCheckedDirectoryFromList(List<String> directoriesFoundInChoosenDirectory, boolean isFileToBeRemoved) {
        if (isFileToBeRemoved) {
            directoriesFoundInChoosenDirectory.remove(0);
        }

    }

    private static List<String> createRaportFileInformUserIfError(List<String> raportFile, Label labelSetTextWhatUserDo) {
        try {
            if (createdEmptyRaportFile.exists()) {
                raportFile = Files.readAllLines(Const.RAPORT_PATH);
            } else {
                createdEmptyRaportFile.createNewFile();
            }
        } catch (IOException var3) {
            var3.printStackTrace();
            labelSetTextWhatUserDo.setText("Error");
        }

        return raportFile;
    }

    public static boolean getInfoIfNecessaryToCreateRaport() {
        return isNecessaryToCreateRaport;
    }

    public static void onButtonOpenEvent() {
        try {
            Runtime.getRuntime().exec(new String[]{"cmd", "/K", "Raport ze sprawdzenia kodowania " + data + ".txt", "Start"});
        } catch (Exception var1) {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            var1.printStackTrace();
        }

    }
}
