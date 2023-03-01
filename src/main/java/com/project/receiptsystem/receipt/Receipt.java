package com.project.receiptsystem.receipt;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Receipt {
    private LinkedHashMap<ReceiptData, String> receipt;

    public Receipt(ResultSet res) {
        // use a linked hashmap to manage the data and destructure when necessary.
        // Implemented this to make code cleaner and limit requests to the database
        this.receipt = new LinkedHashMap<>();

        try {
            ResultSetMetaData metadata = res.getMetaData();
            ReceiptData[] constants = ReceiptData.values();


            for (int i = 1; i <= metadata.getColumnCount() - 1; i++){
//                System.out.println(metadata.getColumnType(i+1));
                this.receipt.put(constants[i - 1], res.getString(metadata.getColumnName(i+1)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<ReceiptData, String> getReceipt() {
        return this.receipt;
    }

    public void setReceipt(LinkedHashMap<ReceiptData, String> receipt) {
        this.receipt = receipt;
    }
}