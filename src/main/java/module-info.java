module scl.pdi.billpaid {
    requires javafx.controls;
    requires javafx.fxml;


    opens scl.pdi.billpaid to javafx.fxml;
    exports scl.pdi.billpaid;
}