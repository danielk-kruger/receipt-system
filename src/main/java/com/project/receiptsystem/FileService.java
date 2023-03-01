package com.project.receiptsystem;

import com.spire.pdf.PdfDocument;
import java.io.File;

public class FileService {

//    private final String resourcesPath = Application.class().getResource();

    public String getResourcesPath(String fileName) {
        return "src/main/resources/com/project/receiptsystem".concat(fileName);
    }


    public PdfDocument getInvoicePDF() {
        PdfDocument pdf = new PdfDocument();
        pdf.loadFromFile(getResourcesPath("/temp/Invoice.pdf"));

        return pdf;
    }

    public File getInvoiceFile() {
        return new File(getResourcesPath("/temp/Invoice.pdf"));
    }

    public File getPreviewImage() {
        try {
            File previewImage = new File(getResourcesPath("/temp/preview.png"));

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
