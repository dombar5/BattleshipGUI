/* doesn't work with source level 1.8:
module com.mycompany.battleshipgui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.battleshipgui to javafx.fxml;
    exports com.mycompany.battleshipgui;
}
*/
