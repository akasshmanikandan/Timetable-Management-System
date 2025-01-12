package timetable.Backupsystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BackupService {
    private final String sourceFilePath;
    private final String backupDirectory;
    private final Timer backupTimer;

    public BackupService(String sourceFilePath, String backupDirectory) {
        this.sourceFilePath = sourceFilePath;
        this.backupDirectory = backupDirectory;
        this.backupTimer = new Timer(true); 
        createBackupDirectory();
    }

    private void createBackupDirectory() {
        try {
            Files.createDirectories(Paths.get(backupDirectory));
            System.out.println("Backup directory validated/created: " + backupDirectory);
        } catch (IOException e) {
            System.err.println("Error creating backup directory: " + e.getMessage());
        }
    }


    private boolean validateSourceFile() {
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            System.err.println("Error: Source file does not exist: " + sourceFilePath);
            return false;
        }
        if (!sourceFile.canRead()) {
            System.err.println("Error: Cannot read the source file: " + sourceFilePath);
            return false;
        }
        System.out.println("Source file validated: " + sourceFilePath);
        return true;
    }


    public void performBackup() {
        System.out.println("Starting backup process...");

        if (!validateSourceFile()) {
            System.err.println("Backup process aborted due to invalid source file.");
            return;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFileName = backupDirectory + "/backup_" + timestamp + ".ser";

        try (FileInputStream fis = new FileInputStream(sourceFilePath);
             FileOutputStream fos = new FileOutputStream(backupFileName)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Backup created successfully: " + backupFileName);
        } catch (IOException e) {
            System.err.println("Error during backup: " + e.getMessage());
        }
    }

    // Log backup details
    private void logBackup(String backupFileName) {
        String logEntry = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + " - Backup created: " + backupFileName + "\n";
        try (FileOutputStream fos = new FileOutputStream(backupDirectory + "/backup_log.txt", true)) {
            fos.write(logEntry.getBytes());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    // Cleanup old backups, retaining only the latest N backups
    public void cleanupOldBackups(int maxBackups) {
        File[] backupFiles = new File(backupDirectory).listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
        if (backupFiles == null || backupFiles.length <= maxBackups) {
            return; // No cleanup needed
        }

        List<File> sortedFiles = List.of(backupFiles);
        sortedFiles.sort((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

        for (int i = 0; i < sortedFiles.size() - maxBackups; i++) {
            if (sortedFiles.get(i).delete()) {
                System.out.println("Deleted old backup: " + sortedFiles.get(i).getName());
            } else {
                System.err.println("Failed to delete old backup: " + sortedFiles.get(i).getName());
            }
        }
    }

    // Restore data from the latest backup
    public boolean restoreFromBackup(String restoreFilePath) {
        File backupFolder = new File(backupDirectory);
        File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));

        if (backupFiles == null || backupFiles.length == 0) {
            System.err.println("No backup files found to restore.");
            return false;
        }

        // Find the latest backup file
        File latestBackup = backupFiles[0];
        for (File file : backupFiles) {
            if (file.lastModified() > latestBackup.lastModified()) {
                latestBackup = file;
            }
        }

        try (FileInputStream fis = new FileInputStream(latestBackup);
             FileOutputStream fos = new FileOutputStream(restoreFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Data restored from: " + latestBackup.getName());
            return true;
        } catch (IOException e) {
            System.err.println("Error during restore: " + e.getMessage());
            return false;
        }
    }

    // Schedule periodic backups
    public void startPeriodicBackup(long intervalMillis) {
        backupTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    performBackup();
                } catch (Exception e) {
                    System.err.println("Error during periodic backup: " + e.getMessage());
                }
            }
        }, 0, intervalMillis);
        System.out.println("Periodic backup started. Interval: " + (intervalMillis / 1000) + " seconds.");
    }

    // Stop the periodic backup
    public void stopPeriodicBackup() {
        backupTimer.cancel();
        System.out.println("Periodic backup stopped.");
    }
}
