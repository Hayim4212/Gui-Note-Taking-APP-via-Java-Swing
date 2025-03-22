import javax.swing.*;
import java.awt.*;

public class Page1_Notes extends JPanel {

    private static JScrollPane scrollPanePage1;
    public static JPanel notesPanel;
    public static JButton toDoButton;
    public Page1_Notes(){
        //Panel Part
        setPreferredSize(Constants.GUI_SIZE);
        setBackground(new java.awt.Color(200,200,200));
        notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel,BoxLayout.Y_AXIS));
        scrollPanePage1 =new JScrollPane(notesPanel);
        scrollPanePage1.setPreferredSize(Constants.PANEL_SIZE);
        scrollPanePage1.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(5.0f)));
        scrollPanePage1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanePage1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        //Heading Part
        JLabel noteHeading = new JLabel("Notes",SwingConstants.CENTER);
        noteHeading.setPreferredSize(Constants.HEADER_SIZE);
        noteHeading.setFont(new Font("Arial",Font.BOLD,25));

        //Add Heading Part
        JLabel addHeadingLabel = new JLabel("Heading",SwingConstants.CENTER);
        addHeadingLabel.setPreferredSize(Constants.HEADER_SIZE);
        addHeadingLabel.setFont(new Font("Arial",Font.BOLD,20));
        JTextField addHeading = new JTextField();
        addHeading.setPreferredSize(Constants.TEXTFIELD_SIZE);
        addHeading.setEditable(true);

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

        //Add Note Button
        JButton addNoteButton = new JButton("Add Note");
        addNoteButton.setPreferredSize(Constants.BUTTON_SIZE);
        addNoteButton.addActionListener(e -> {
            MainFrame.addDatabase(addHeading.getText(),addContent.getText(),"TblMainNotes","NoteId","NoteHeading","NoteContent");
            MainFrame.loadFromDatabase(notesPanel,"TblMainNotes","NoteId","NoteHeading","NoteContent");
        });

        //To-Do List Button
        toDoButton = new JButton("To-Do List");
        toDoButton.setPreferredSize(Constants.BUTTON_SIZE);



        //Add Items To Panel
        add(noteHeading);
        add(scrollPanePage1);
        add(addHeadingLabel);
        add(addHeading);
        add(addContentLabel);
        add(scrollPaneAddContent);
        add(addNoteButton);
        add(toDoButton);

        //Load Page
        MainFrame.loadFromDatabase(Page1_Notes.notesPanel,"TblMainNotes","NoteId","NoteHeading","NoteContent");
    }

}
