package com.project.receiptsystem;

import com.spire.pdf.PdfDocument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileService {

    public String createTemplate() {
        try {
            // copy contents from jar file and move to outside directory
            InputStream inputStream = getClass().getResourceAsStream("/com/project/receiptsystem/Invoice-Template.docx");
            Path tempFile = Files.createTempFile("Invoice-Template", ".docx");

            if (inputStream != null) {
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            }

            System.out.println(tempFile.toUri().getPath());
            return tempFile.toUri().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String appInstallDirectory(String additionalPath) {
        Path workingDirectory = Paths.get(System.getProperty("user.home"), "Receipt_System", additionalPath);
        System.out.println(workingDirectory.toUri().getPath());
        return workingDirectory.toUri().getPath();
    }

    public PdfDocument getInvoicePDF() {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(this.appInstallDirectory("temp/Invoice.pdf"));

        return pdf;
    }

    public File getInvoiceFile() {
        return new File("Invoice-Template.docx");
    }

    public File getPreviewImage() {
        try {
            File previewImage = new File(this.appInstallDirectory("temp/preview.png"));

            if (previewImage.exists() && previewImage.isFile())
                return previewImage;
            else
                System.out.println("File does not exist");

        } catch (Exception e) {
            System.out.println("Error: URL Syntax error");
            e.printStackTrace();
        }

        return null;
    }
}
