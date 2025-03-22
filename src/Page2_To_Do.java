import javax.swing.*;
import java.awt.*;

public class Page2_To_Do extends JPanel {
    private static JScrollPane scrollPanePage2;
    public static JPanel toDoPanel;
    public static JButton notesButton;
    public Page2_To_Do(){
        //Panel Part
        setPreferredSize(Constants.GUI_SIZE);
        setBackground(new java.awt.Color(200,200,200));
        toDoPanel = new JPanel();
        scrollPanePage2 =new JScrollPane(toDoPanel);
        toDoPanel.setLayout(new BoxLayout(toDoPanel,BoxLayout.Y_AXIS));
        scrollPanePage2.setPreferredSize(Constants.PANEL_SIZE);
        scrollPanePage2.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        scrollPanePage2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanePage2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Heading Part
        JLabel toDoHeading = new JLabel("To-Do List",SwingConstants.CENTER);
        toDoHeading.setPreferredSize(Constants.HEADER_SIZE);
        toDoHeading.setFont(new Font("Arial",Font.BOLD,25));

        //Add Date Part
        JLabel addDateLabel = new JLabel("Date",SwingConstants.CENTER);
        addDateLabel.setPreferredSize(Constants.HEADER_SIZE);
        addDateLabel.setFont(new Font("Arial",Font.BOLD,20));
        JTextField addDate = new JTextField();
        addDate.setPreferredSize(Constants.TEXTFIELD_SIZE);
        addDate.setEditable(true);

        //Add Content Part
        JLabel addContentLabel = new JLabel("Content",SwingConstants.CENTER);
        addContentLabel.setPreferredSize(Constants.HEADER_SIZE);
        addContentLabel.setFont(new Font("Arial",Font.BOLD,20));
        JTextArea addContent = new JTextArea();
        addContent.setLineWrap(true);
        addContent.setWrapStyleWord(true);
        addContent.setEditable(true);
        JScrollPane scrollPaneAddContent = new JScrollPane(addContent);
        scrollPaneAddContent.setPreferredSize(Constants.TEXTAREA_SIZE);
        scrollPaneAddContent.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneAddContent.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Add To-Do Button
        JButton addToDoButton = new JButton("Add To-Do");
        addToDoButton.setPreferredSize(Constants.BUTTON_SIZE);
        addToDoButton.addActionListener(e -> {
            MainFrame.addDatabase(addDate.getText(),addContent.getText(),"TblToDoList","ToDoId","Date","ToDo");
            MainFrame.loadFromDatabase(Page2_To_Do.toDoPanel,"TblToDoList","ToDoId","Date","ToDo");
        });

        //Notes Button
        notesButton = new JButton("Notes");
        notesButton.setPreferredSize(Constants.BUTTON_SIZE);


        //Add Items to Panel
        add(toDoHeading);
        add(scrollPanePage2);
        add(addDateLabel);
        add(addDate);
        add(addContentLabel);
        add(scrollPaneAddContent);
        add(addToDoButton);
        add(notesButton);

        //Load Page
        MainFrame.loadFromDatabase(Page2_To_Do.toDoPanel,"TblToDoList","ToDoId","Date","ToDo");
    }
}
