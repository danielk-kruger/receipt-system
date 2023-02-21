package com.project.receiptsystem;

import com.project.receiptsystem.db.DBService;
import com.project.receiptsystem.docs.InvoiceDocument;
import com.project.receiptsystem.receipt.Product;
import com.project.receiptsystem.receipt.ProductData;
import com.project.receiptsystem.receipt.Receipt;
import com.project.receiptsystem.receipt.ReceiptController;
import com.project.receiptsystem.receipt.ReceiptData;
import com.project.receiptsystem.ui.UIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application extends javafx.application.Application {

    static CommandLine cmd;

    @Override
    public void start(Stage stage) throws IOException {
        FileService fileService = new FileService();

        // load my custom fonts
        Font.loadFont(getClass().getResourceAsStream("fonts/Prompt-Regular.ttf"), 15);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("job.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        UIController ui = fxmlLoader.getController();
        ui.setInvoicePreview(fileService.getPreviewImage());
        ui.setDocNum(cmd.getOptionValue("doc"));

        stage.setTitle("Receipt Generator!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        DBService db = new DBService();
        InvoiceDocument doc;

        try {
            db.connect();
            ReceiptController controller = new ReceiptController(db.getConnection());

            //115240
            cmd = new Commands().parseCommands(args);
            System.out.println("Found Client ID: " + cmd.getOptionValue("id"));

            Receipt rcp = controller.fetchByClientId(cmd.getOptionValue("id"));
            displayReceipt(rcp);

//            List<Product> prods = controller.findProductsByDocumentNumber(rcp.getReceipt().get(ReceiptData.DOC_NUMBER));
            List<Product> prods = controller.findProductsByDocumentNumber(cmd.getOptionValue("doc"));
            displayProductData(prods);

            if (cmd.hasOption("p")) {
                doc = new InvoiceDocument(rcp, prods);
                doc.writeToDocument().exportToPDF().previewImage();

                launch(args);
            }

//            System.out.println(rcp.getReceipt().get(ReceiptData.DOC_NUMBER));

        } catch (Exception e) {
            System.out.println("An error occured while accessing the database");
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private static void displayData(ArrayList<Receipt> listData) {
        for (Receipt receipt : listData) {
            displayReceipt(receipt);

            System.out.println("------------------------------------------------------------------");
        }
    }

    private static void displayProductData(List<Product> listData) {
        for (Product product : listData) {
            displayProduct(product);

            System.out.println("------------------------------------------------------------------");
        }
    }

    private static void displayReceipt(Receipt receiptData) {
        if (receiptData == null) return;

        System.out.println("------------------------------------------------------------------");
        for (Map.Entry<ReceiptData, String> item : receiptData.getReceipt().entrySet())
            System.out.println(item.getKey() + " " + item.getValue());
    }

    private static void displayProduct(Product productData) {
        if (productData == null) return;

        System.out.println("------------------------------------------------------------------");
        for (Map.Entry<ProductData, String> item : productData.getProductList().entrySet())
            System.out.println(item.getKey() + " " + item.getValue());
    }
}