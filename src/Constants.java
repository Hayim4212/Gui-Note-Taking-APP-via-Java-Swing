import java.awt.*;

public class Constants {
    public static final Dimension GUI_SIZE = new Dimension(600,800);
    public static final Dimension PANEL_SIZE = new Dimension(400,450);
    public static final Dimension HEADER_SIZE = new Dimension(400,25);
    public static final Dimension TEXTFIELD_SIZE = new Dimension(400,25);
    public static final Dimension TEXTAREA_SIZE = new Dimension(400,75);
    public static final Dimension DETAILS_SIZE = new Dimension(400,400);
    public static final Dimension BUTTON_SIZE = new Dimension(400,25);
    public static final String CONNECTION_URL =
            "jdbc:sqlserver://localhost:1443;"
                    + "database=<DatabaseName>;"
                    + "user=<Username>;"
                    + "password=<Password>;"
                    + "encrypt=false;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";
}
