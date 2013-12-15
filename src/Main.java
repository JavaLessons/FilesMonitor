/**
 * Created by frizzle on 04.12.13.
 */

import file_manager.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //MainWindow window = new MainWindow("Test form");
        //window.setVisible(true);


        try {
            FileManager fileManager = new FileManager();
            fileManagerContentDisplay(fileManager);

            FileInfo fileInfo = fileManager.getFileInfo("D:\\media\\test.txt");
            fileManager.addFile(fileInfo);
            fileInfo = fileManager.getFileInfo("D:\\media\\test1.txt");
            fileManager.addFile(fileInfo);

            fileManagerContentDisplay(fileManager);
            //fileManager.deleteFile(fileInfo);
        } catch (IOException e) {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
    }

    private static void fileManagerContentDisplay(FileManager fileManager) throws IOException, NoSuchAlgorithmException {
        Map<String, FileInfo> fileListMap = fileManager.getFilesListMap();
        for (String fInfoKey:fileListMap.keySet()) {
            FileInfo finfo = fileListMap.get(fInfoKey);
            System.out.println(finfo.getFile().getAbsolutePath() + " - isModified:" + finfo.isModified());
        }
    }

}
