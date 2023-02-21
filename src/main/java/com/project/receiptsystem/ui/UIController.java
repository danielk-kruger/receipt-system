package com.project.receiptsystem.ui;

import com.project.receiptsystem.Application;
import com.project.receiptsystem.FileService;
import com.project.receiptsystem.docs.PrinterService;
import com.spire.pdf.PdfDocument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.print.PrintException;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    private FileService fileService = new FileService();

    @FXML
    private Label docNum;

    @FXML
    private ImageView imageContainer;

    @FXML
    protected void handleDocumentInspection() {
        try {
            File pdf = fileService.getInvoiceFile();

            if (pdf.exists()) {
                if (Desktop.isDesktopSupported())
                    Desktop.getDesktop().open(pdf);
                else
                    System.out.println("awt.Desktop is not supported");
            } else System.out.println("File does not exist");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handlePrintJob() {
        PrinterService printerService = new PrinterService();

        // Get the pdf invoice
        PdfDocument pdfInvoice = fileService.getInvoicePDF();

        try {
            printerService.setupPrinter(pdfInvoice).print();
        } catch (PrinterException e) {
            System.out.println("Printing error...");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void setInvoicePreview(File preview) {
        Image previewImage = new Image(preview.toURI().toString());
        imageContainer.setImage(previewImage);
    }

    @FXML
    public void setDocNum(String documentNumber) {
        docNum.setText(documentNumber);
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileService = new FileService();
    }
}
