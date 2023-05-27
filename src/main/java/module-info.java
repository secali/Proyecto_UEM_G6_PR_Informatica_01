module scl.pdi.billpaid {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.io;
    requires java.json;
    requires json.simple;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.oracle.database.jdbc;
    requires lombok;
    requires org.mariadb.jdbc;


    opens scl.pdi.billpaid to javafx.fxml;
    exports scl.pdi.billpaid;
}