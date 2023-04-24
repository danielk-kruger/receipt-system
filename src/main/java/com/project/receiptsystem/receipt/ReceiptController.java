package com.project.receiptsystem.receipt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptController {
    private ArrayList<Receipt> allReceipts = new ArrayList<>();
    private ArrayList<Product> allProducts = new ArrayList<>();

    public ReceiptController(Connection dbConn) {
        this.setAllReceipts(this.fetchAllReceipts(dbConn));
        this.setAllProducts(this.fetchAllProducts(dbConn));
    }

    private ArrayList<Receipt> fetchAllReceipts(Connection dbConn) {
        try {
            Statement statement = dbConn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM SQLUser.FACTURAS");

            while (resultSet.next())
                this.allReceipts.add(new Receipt(resultSet));

        } catch (Exception e) {
            System.out.println("Error fetching all receipts.");
            e.printStackTrace();
        }

        return this.allReceipts;
    }

    private ArrayList<Product> fetchAllProducts(Connection dbConn) {
        try {
            Statement statement = dbConn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM SQLUser.PRODUTOSF");

            while (res.next())
                this.allProducts.add(new Product(res));

        } catch (SQLException e) {
            System.out.println("Error fetching products");
            e.printStackTrace();
        }

        return this.allProducts;
    }

    public Receipt fetchByClientId(String clientId) {
        try {
            for (Receipt rcp : this.getAllReceipts()) {
                String client = rcp.getReceipt().get(ReceiptData.CLIENT_ID);
                System.out.println(rcp.getReceipt().toString());

                if (client.equals(clientId))
                    return rcp;
            }
        } catch (Exception e) {
            System.out.println("Receipt does not exist");
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> findProductsByDocumentNumber(String docNumber) {
        try {
            return this.allProducts
                            .stream()
                            .filter(doc -> doc
                                    .getProduct(ProductData.DOC_NUMBER)
                                    .equals(docNumber))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println("Products with that Document NUmber does not exist");
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<Receipt> getAllReceipts() {
        return allReceipts;
    }

    public void setAllReceipts(ArrayList<Receipt> allReceipts) {
        this.allReceipts = allReceipts;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }


}
