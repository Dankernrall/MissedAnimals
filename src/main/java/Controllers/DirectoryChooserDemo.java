package Controllers;

import java.io.File;

import javafx.application.Application;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class DirectoryChooserDemo extends Application {

    public static File dir;

    public static File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        configuringDirectoryChooser(directoryChooser);
        setDir(directoryChooser.showDialog(primaryStage));
        primaryStage.show();
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Выберите место сохранения");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}