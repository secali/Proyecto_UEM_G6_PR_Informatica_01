package scl.pdi.billpaid.helper;

import javafx.scene.control.Alert;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AlertHelper {

public static String Error = "Error";
public static String Info = "Información";

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        if (alertType.name() == null ? Alert.AlertType.INFORMATION.name() == null : alertType.name().equals(Alert.AlertType.INFORMATION.name())) {
            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message).hideAfter(Duration.seconds(10))
                    .showInformation();
        } else if (alertType.name() == null ? Alert.AlertType.ERROR.name() == null : alertType.name().equals(Alert.AlertType.ERROR.name())) {

            Notifications.create()
                    .darkStyle()
                    .title(title)
                    .text(message).hideAfter(Duration.seconds(10))
                    .showError();
        }
    }
}
