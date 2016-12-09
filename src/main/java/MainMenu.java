import service.FileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @autor A_Nakonechnyi
 * @date 09.12.2016.
 */
public class MainMenu extends JPanel implements ActionListener {

    static private final String newline = "\n";
    JButton openButton, overwriteButton, deleteButton;
    JTextArea log;
    JFileChooser fc;

    public MainMenu() {
        super(new BorderLayout());

        log = new JTextArea(25,25);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        openButton = new JButton("Open a Directory...");
        openButton.addActionListener(this);

        overwriteButton = new JButton("Overwrite content");
        overwriteButton.addActionListener(this);

        deleteButton = new JButton("Overwrite&Delete content");
        deleteButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(overwriteButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    /**
     * Buttons listener.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(MainMenu.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = fc.getSelectedFile();
                log.append("Selected directory " + directory.getAbsolutePath() + " contains: "+ newline);
                log.append(FileService.printDirContent(directory));
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == overwriteButton) {
            File directory = fc.getSelectedFile();
            log.append("Override: " + directory.getName() + "." + newline);
            try {
                log.append(FileService.overrideDir(directory));
            } catch (IOException e1) {
                log.append("Fail overwriting." + e1 + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == deleteButton) {
            File directory = fc.getSelectedFile();
            log.append("Override&Delete: " + directory.getName() + "." + newline);
            try {
                log.append(FileService.overrideDir(directory));
                log.append(FileService.deleteContent(directory));
            } catch (IOException e1) {
                log.append("Fail overwriting." + e1 + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    /**
     * Create the GUI and show it.
     */
    static void createAndShowGUI() {
        JFrame frame = new JFrame("SecureDelete");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MainMenu());
        frame.pack();
        frame.setVisible(true);
    }

}
