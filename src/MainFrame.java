import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class MainFrame extends JFrame {
    public static int value = 0;
    private static Page1_Notes panelPage1;
    private static Page2_To_Do panelPage2;
    private static boolean isTurnOn=false;


    public MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(Constants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        CardLayout cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        //Create Object From Two Page Class
        panelPage1 = new Page1_Notes();
        panelPage2 = new Page2_To_Do();
        System.out.println(value);
        if (!isTurnOn) {
            add(panelPage1,"Page1");
            add(panelPage2,"Page2");
            cardLayout.show(getContentPane(),"Page1");
            MainFrame.loadFromDatabase(Page1_Notes.notesPanel, "TblMainNotes", "NoteId", "NoteHeading", "NoteContent");
            isTurnOn = true;
        }
        Page1_Notes.toDoButton.addActionListener(e -> {
            cardLayout.show(getContentPane(),"Page2");
            getContentPane();
            MainFrame.loadFromDatabase(Page2_To_Do.toDoPanel,"TblToDoList","ToDoId","Date","ToDo");
        });
        Page2_To_Do.notesButton.addActionListener(e -> {
            cardLayout.show(getContentPane(),"Page1");
            MainFrame.loadFromDatabase(Page1_Notes.notesPanel,"TblMainNotes","NoteId","NoteHeading","NoteContent");
        });


        //SQL SERVER Part
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {

        }
        System.out.println("MySQL JDBC Driver Registered!");


    }

    public static int getNextAvailableId(Connection conn,String tableName, String idName) throws SQLException {
        String query = "SELECT MIN(t1."+idName+" + 1) AS next_id FROM "+tableName+" t1 " +
                "LEFT JOIN "+tableName+" t2 ON t1."+idName+" + 1 = t2."+idName+" " +
                "WHERE t2."+idName+" IS NULL;";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id"); // En küçük boş ID
            }
        }
        return 1;
    }
    public static void addDatabase(String title, String content, String tableName, String idName,String headingName,String contentName) {
        String insertQuery = "INSERT INTO "+tableName+" ("+idName+", "+headingName+", "+contentName+") VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            int newId = getNextAvailableId(conn,tableName,idName);

            stmt.setInt(1, newId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.executeUpdate();

            System.out.println("Not başarıyla eklendi! ID: " + newId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static void deleteFromDatabase(int noteId,String idName,String tableName) {
        String sql = "DELETE FROM "+tableName+" WHERE "+idName+" = ?";

        try (Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, noteId);
            pstmt.executeUpdate();
            System.out.println("Note deleted: " + noteId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadFromDatabase(JPanel panel,String tableName, String idName,String headingName,String contentName) {
        panel.removeAll();
        String sql = "SELECT * FROM "+tableName+" ORDER BY "+idName+" DESC";

        try (Connection conn = DriverManager.getConnection(Constants.CONNECTION_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            JButton details;

            while (rs.next()) {
                int id = rs.getInt(idName);
                String title = rs.getString(headingName);
                String content = rs.getString(contentName);


                JPanel notePanel = new JPanel();


                JTextArea titleTable = new JTextArea(title);
                titleTable.setEditable(false);
                titleTable.setLineWrap(true);
                titleTable.setWrapStyleWord(true);

                details = new JButton("Details");
                JButton deleteButton = new JButton("X");
                deleteButton.addActionListener(e -> {
                    deleteFromDatabase(id,idName,tableName);
                    loadFromDatabase(panel,tableName,idName,headingName,contentName);
                });
                details.addActionListener(e -> {
                    if (tableName.equals("TblMainNotes")){
                        showDetails(content);
                    }
                });

                if (tableName.equals("TblMainNotes")){
                    titleTable.setPreferredSize(new Dimension(150,25));
                    notePanel.add(titleTable);
                    notePanel.add(details);
                    details.setPreferredSize(new Dimension(100,25));
                } else if (tableName.equals("TblToDoList")) {
                    JLabel day =new JLabel(title+":");
                    JTextArea contentTable = new JTextArea(content);
                    contentTable.setEditable(false);
                    contentTable.setLineWrap(true);
                    contentTable.setWrapStyleWord(true);
                    day.setPreferredSize(new Dimension(50,25));
                    day.setFont(new Font("Arial",Font.BOLD,10));
                    contentTable.setPreferredSize(new Dimension(150,25));
                    notePanel.add(day);
                    notePanel.add(contentTable);
                }
                deleteButton.setPreferredSize(new Dimension(50,25));
                notePanel.add(deleteButton);

                notePanel.setPreferredSize(new Dimension(300,25));
                notePanel.setMaximumSize(new Dimension(400, 35));

                panel.add(notePanel);
            }
            panel.setPreferredSize(new Dimension(400, panel.getComponentCount() * 20));
            panel.setMinimumSize(new Dimension(400, 100));
            panel.revalidate();
            panel.repaint();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void showDetails(String content){
        JFrame details = new JFrame();
        details.setPreferredSize(Constants.DETAILS_SIZE);
        details.pack();
        details.setLocationRelativeTo(null);
        details.setResizable(false);
        details.setLayout(new GridLayout());

        //Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setPreferredSize(Constants.DETAILS_SIZE);
        detailsPanel.setBackground(new java.awt.Color(200,200,200));

        //Text Area Part
        JTextArea textArea = new JTextArea(content);
        JScrollPane scrollPaneDetails = new  JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPaneDetails.setPreferredSize(new Dimension(300,300));

        //Heading Part
        JLabel heading = new JLabel("Details:");
        heading.setFont(new Font("Arial",Font.BOLD,15));
        heading.setPreferredSize(new Dimension(300,25));

        //Add Items To Frame
        details.add(detailsPanel);
        detailsPanel.add(heading);
        detailsPanel.add(scrollPaneDetails);

        details.setVisible(true);
    }
}



