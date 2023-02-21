package com.project.receiptsystem.receipt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Product {

    LinkedHashMap<ProductData, String> product;

    public Product(ResultSet res) {
        this.product = new LinkedHashMap<>();

        try {
            ResultSetMetaData metadata = res.getMetaData();
            ProductData[] constants = ProductData.values();

            for (int i = 1; i < metadata.getColumnCount() - 1; i++)
                product.put(constants[i - 1], res.getString(metadata.getColumnName(i + 1)));

        } catch (SQLException e) {
            System.out.println("Could not fetch from database");
            e.printStackTrace();
        }
    }

    public LinkedHashMap<ProductData, String> getProductList() {
        return this.product;
    }

    public String getProduct(ProductData selection) {
        return this.product.get(selection);
    }

    public void setProduct(LinkedHashMap<ProductData, String> product) {
        this.product = product;
    }

}
