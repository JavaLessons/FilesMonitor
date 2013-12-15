package file_manager;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import slogger.*;

/**
 * Created by frizzle on 08.12.13.
 */
public class FileInfo implements Serializable {
    private static SLogger logger = new SLogger(FileInfo.class.getName(), SLoggerPriority.SLoggerPriorityInfo);

    private File file;
    private byte[] hashMD5;
    private Date lastCheckDate;

    public FileInfo(File file) {
        this.file = file;
        //this.hashMD5 = "BAB17406A8E9CF7E288E5A2B4BC89618";
        this.hashMD5 = new byte[0];
        this.lastCheckDate = null;
    }

    public File getFile() {
        return file;
    }

    public byte[] getHashMD5() {
        return hashMD5;
    }

    public String getHashMD5String() {
        return this.toHex(hashMD5);
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public boolean isModified() throws IOException, NoSuchAlgorithmException {
        if (file.exists()) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                FileInputStream is = new FileInputStream(file);
                DigestInputStream dis = new DigestInputStream(is, md);
                while (dis.read() > -1);
            } catch (IOException e) {
                logger.logError("Can't read file. Error: " + e);
            }
            byte[] digest = md.digest();

            if (!Arrays.equals(hashMD5, digest)) {
                hashMD5 = digest;
                return true;
            }
        }
        return false;
    }

    private byte[] getMD5HashBytes() throws IOException, NoSuchAlgorithmException {
        if (file.exists()) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try {
                FileInputStream is = new FileInputStream(file);
                DigestInputStream dis = new DigestInputStream(is, md);
                while (dis.read() > -1);
            } catch (IOException e) {
                logger.logError("Can't read file. Error: " + e);
            }
            byte[] digest = md.digest();

            return digest;
        }
        return null;
    }

    private String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
}
