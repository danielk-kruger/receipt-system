module com.project.receiptsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires cachejdbc;
    requires java.sql;
    requires spire.office.free;
    requires org.apache.commons.io;
    requires commons.cli;
    requires java.desktop;

    opens com.project.receiptsystem to javafx.fxml;
    opens com.project.receiptsystem.ui to javafx.fxml;
    exports com.project.receiptsystem;
    exports com.project.receiptsystem.receipt;
    opens com.project.receiptsystem.receipt to javafx.fxml;
}