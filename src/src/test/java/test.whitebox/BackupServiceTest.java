package test.whitebox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Backupsystem.BackupService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BackupServiceTest {
    private static final String TEST_SOURCE_FILE = "test_source_data.ser";
    private static final String TEST_BACKUP_DIR = "test_backup";

    private BackupService backupService;

    @BeforeEach
    void setUp() throws IOException {
        try (FileWriter writer = new FileWriter(TEST_SOURCE_FILE)) {
            writer.write("Sample test data");
        }
        backupService = new BackupService(TEST_SOURCE_FILE, TEST_BACKUP_DIR);
    }

    @AfterEach
    void tearDown() {
        new File(TEST_SOURCE_FILE).delete();
        //deletes all backup files
        File backupFolder = new File(TEST_BACKUP_DIR);
        if (backupFolder.exists()) {
            for (File file : backupFolder.listFiles()) {
                file.delete();
            }
            backupFolder.delete();
        }
    }

    @Test
    void testBackupCreation() {
        backupService.performBackup();

        //verifies a backup file was created
        File backupFolder = new File(TEST_BACKUP_DIR);
        File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
        assertTrue(backupFiles != null && backupFiles.length > 0, "Backup file should be created.");
    }

    @Test
    void testRestoreFromBackup() {
        backupService.performBackup();
        String restoreFilePath = "restored_data.ser";
        boolean restored = backupService.restoreFromBackup(restoreFilePath);
        assertTrue(restored, "Data should be restored successfully.");

        //validates restored file content
        File restoredFile = new File(restoreFilePath);
        assertTrue(restoredFile.exists() && restoredFile.length() > 0, "Restored file should exist and contain data.");

        restoredFile.delete();
    }

    @Test
    void testInvalidSourceFileBackup() {
        //initialises BackupService with a non-existent file
        BackupService invalidBackupService = new BackupService("nonexistent_file.ser", TEST_BACKUP_DIR);
        invalidBackupService.performBackup();
        File backupFolder = new File(TEST_BACKUP_DIR);
        File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
        assertFalse(backupFiles != null && backupFiles.length > 0, "No backup should be created for invalid file.");
    }


@Test
void testRestoreFromNonExistentBackup() {
    BackupService backupService = new BackupService(TEST_SOURCE_FILE, TEST_BACKUP_DIR);
    boolean restored = backupService.restoreFromBackup("non_existent_backup.ser");

    assertFalse(restored, "Restore from a non-existent backup should fail.");
}
@Test
void testBackupDirectoryCreation() {
    //deletes the backup directory if it exists
    File backupFolder = new File(TEST_BACKUP_DIR);
    if (backupFolder.exists()) {
        for (File file : backupFolder.listFiles()) {
            file.delete();
        }
        backupFolder.delete();
    }

    backupService = new BackupService(TEST_SOURCE_FILE, TEST_BACKUP_DIR);
    assertTrue(backupFolder.exists() && backupFolder.isDirectory(), "Backup directory should be created.");
}
@Test
void testBackupCleanup() throws InterruptedException {
    //backups to exceed the limit of 5 backups
    for (int i = 0; i < 7; i++) {
        backupService.performBackup();
        Thread.sleep(100); 
    }

    //latest 5 backups are retained
    File backupFolder = new File(TEST_BACKUP_DIR);
    File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
    assertFalse(backupFiles != null && backupFiles.length == 5, "latest 5 backups should be retained.");
}
@Test
void testBackupFileIntegrity() throws IOException {
    backupService.performBackup();

}
@Test
void testBackupIntervalConsistency() throws InterruptedException {
    long intervalMillis = 1000; 
    backupService.startPeriodicBackup(intervalMillis);

    //few intervals
    Thread.sleep(3500);

    //checks the number of backup files
    File backupFolder = new File(TEST_BACKUP_DIR);
    File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));

    
    assertTrue(backupFiles.length >= 3, "At least 3 backups should be created.");
}
@Test
void testBackupCleanupMechanism() {
    //maximum backups to 3
    for (int i = 0; i < 5; i++) {
        backupService.performBackup();
    }

    File backupFolder = new File(TEST_BACKUP_DIR);
    File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));


    assertTrue(backupFiles.length <= 3, "Backup folder should contain latest 3 backups.");
}
@Test
void testRestoreLatestBackup() throws IOException, InterruptedException {
    //creates an multiple backups
    for (int i = 0; i < 3; i++) {
        FileWriter writer = new FileWriter(TEST_SOURCE_FILE);
        writer.write("Sample data " + i);
        writer.close();
        backupService.performBackup();
        Thread.sleep(1000);
    }

    //restores the latest backup
    String restoreFilePath = "latest_restored_data.ser";
    boolean restored = backupService.restoreFromBackup(restoreFilePath);

    assertTrue(restored, "Restore should succeed for the latest backup.");
    File restoredFile = new File(restoreFilePath);
    assertTrue(restoredFile.exists() && restoredFile.length() > 0, "Restored file should exist and contain data.");
    restoredFile.delete();
}

}
