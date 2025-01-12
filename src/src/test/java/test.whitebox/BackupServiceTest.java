package test.whitebox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Backupsystem.BackupService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BackupServiceTest {
    private static final String TEST_SOURCE_FILE = "test_source_data.ser";
    private static final String TEST_BACKUP_DIR = "test_backup";

    private BackupService backupService;

    @BeforeEach
    void setUp() throws IOException {
        // Create test source file with sample data
        try (FileWriter writer = new FileWriter(TEST_SOURCE_FILE)) {
            writer.write("Sample test data");
        }

        // Initialize BackupService
        backupService = new BackupService(TEST_SOURCE_FILE, TEST_BACKUP_DIR);
    }

    @AfterEach
    void tearDown() {
        // Delete test source file
        new File(TEST_SOURCE_FILE).delete();

        // Delete all backup files and the backup directory
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

        // Verify a backup file was created
        File backupFolder = new File(TEST_BACKUP_DIR);
        File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
        assertTrue(backupFiles != null && backupFiles.length > 0, "Backup file should be created.");
    }

    @Test
    void testRestoreFromBackup() {
        backupService.performBackup();
        String restoreFilePath = "restored_data.ser";

        // Test restoration
        boolean restored = backupService.restoreFromBackup(restoreFilePath);
        assertTrue(restored, "Data should be restored successfully.");

        // Validate restored file content
        File restoredFile = new File(restoreFilePath);
        assertTrue(restoredFile.exists() && restoredFile.length() > 0, "Restored file should exist and contain data.");

        // Clean up restored file
        restoredFile.delete();
    }

    @Test
    void testInvalidSourceFileBackup() {
        // Initialize BackupService with a non-existent source file
        BackupService invalidBackupService = new BackupService("nonexistent_file.ser", TEST_BACKUP_DIR);

        // Perform backup and ensure no backup is created
        invalidBackupService.performBackup();
        File backupFolder = new File(TEST_BACKUP_DIR);
        File[] backupFiles = backupFolder.listFiles((dir, name) -> name.startsWith("backup_") && name.endsWith(".ser"));
        assertFalse(backupFiles != null && backupFiles.length > 0, "No backup should be created for invalid source file.");
    }

}