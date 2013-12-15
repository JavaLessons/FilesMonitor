package file_manager;

import slogger.*;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frizzle on 10.12.13.
 */
public class FileManager {
    private static SLogger logger = new SLogger(FileManager.class.getName(), SLoggerPriority.SLoggerPriorityDebug);

    private Map<String, FileInfo> filesListMap;
    private String storagePath;

    public FileManager()  throws IOException  {
        this("default_file_storage.dat");
    }

    public FileManager(String storagePath) throws IOException {
        this.storagePath = storagePath;
        this.filesListMap = null;
        this.loadStorage();

    }

    public Map<String, FileInfo> getFilesListMap() {
        return filesListMap;
    }

    public FileInfo getFileInfo(String filepath) {
        FileInfo result = this.filesListMap.get(filepath);
        if (result == null) {
            File file = new File(filepath);
            if (file.exists()) {
                result = new FileInfo(file);
            }
        }

        return result;
    }

    public boolean containsFileInfo(FileInfo fileInfo) {
        return (this.filesListMap.containsKey(fileInfo.getFile().getAbsolutePath()));
    }

    private void loadStorage() throws IOException {
        File storageFile = new File(this.storagePath);
        this.createStorageFileIfDoesntExist(storageFile);

        ObjectInputStream istream = new ObjectInputStream(new FileInputStream(storageFile));

        try {
            filesListMap = (HashMap)istream.readObject();
        } catch (ClassNotFoundException e) {
            logger.logError("Can't parse object from file storage. Error: " + e);
        } catch (IOException e) {
            logger.logError("Can't read object from file storage. Error: " + e);
        }

        if (filesListMap == null) {
            filesListMap = new HashMap<String, FileInfo>();
            this.saveToStorage();
        }
    }

    private void createStorageFileIfDoesntExist(File storageFile) throws IOException {
        if (!storageFile.exists()) {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(storageFile));
            outputStream.flush();
            outputStream.close();
        }
    }

    public FileInfo[] getFilesList() {
        return (FileInfo[])filesListMap.values().toArray();
    }

    public void addFile(FileInfo fileInfo) {
        if (fileInfo == null) {
            return;
        }
        if (!filesListMap.containsKey(fileInfo.getFile().getAbsolutePath()) ) {
            filesListMap.put(fileInfo.getFile().getAbsolutePath(), fileInfo);
            this.saveToStorage();
        } else {
            logger.logInfo("Try to add file for monitoring which has already added.");
        }
    }

    public void deleteFile(FileInfo fileInfo) {
        if (filesListMap.containsKey(fileInfo.getFile().getAbsolutePath()) ) {
            filesListMap.remove(fileInfo.getFile().getAbsolutePath());
            this.saveToStorage();
        } else {
            logger.logInfo("Try to add file from monitoring list which is not existed");
        }
    }

    private void saveToStorage() {
        File storageFile = new File(this.storagePath);

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(storageFile));
            outputStream.writeObject(filesListMap);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.logError("Failed save list of files to disk. Error: " + e);
        }
    }
}
