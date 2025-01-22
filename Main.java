import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JMenuItem open, save, exit, font, color, size;

    public Main() {
        //JFrame
        setTitle("Mikepad");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //TextArea
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setForeground(Color.black);
        textArea.setBackground(Color.white);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        //Menu Bar
        JMenuBar menu = new JMenuBar();

        //File
        JMenu file = new JMenu("File");

        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");


        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);

        file.add(open);
        file.add(save);
        file.add(exit);

        //Color
        JMenu colorMenu = new JMenu("Color");
        color = new JMenuItem("Color");
        color.addActionListener(this);
        colorMenu.add(color);

        //Font
        JMenu fontMenu = new JMenu("Font");
        font = new JMenuItem("Font");
        font.addActionListener(this);
        fontMenu.add(font);

        //Size
        JMenu fontSize = new JMenu("Size");
        size = new JMenuItem("Size");
        size.addActionListener(this);
        fontSize.add(size);

        menu.add(file);
        menu.add(Box.createHorizontalGlue());
        menu.add(color);
        menu.add(Box.createHorizontalGlue());
        menu.add(font);
        menu.add(Box.createHorizontalGlue());
        menu.add(size);
        menu.add(Box.createHorizontalGlue());


        setJMenuBar(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open){
            openFile();
        } else if (e.getSource() == save) {
            saveFile();
        } else if (e.getSource() == exit) {
            System.exit(0);
        } else if (e.getSource() == font) {
            font();
        } else if (e.getSource() == color) {
            color();
        } else if (e.getSource() == size) {
            fontSize();
        }
    }

    private void fontSize() {
        String size = JOptionPane.showInputDialog(this, "Enter Font Size:");
        try {
            int fontSize = Integer.parseInt(size);
            if (fontSize > 0){
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, fontSize));
            }else {
                JOptionPane.showMessageDialog(this, "Invalid Size!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void color() {
        Color newColor = JColorChooser.showDialog(this, "Choose a Text Color", textArea.getForeground());
        if (newColor != null){
            textArea.setForeground(newColor);
        }
    }

    private void font() {
        String[] fontTypes = {"Arial", "italic", "Times New Roman", "Courier New", "Verdana"};
        String selected = (String) JOptionPane.showInputDialog(this, "Select Font Type:", "Font Type", JOptionPane.PLAIN_MESSAGE, null, fontTypes, fontTypes[0]);

        if (selected != null) {
            textArea.setFont(new Font(selected, Font.PLAIN, textArea.getFont().getSize()));
        }

    }

    private void saveFile() {
        JFileChooser fileChoice = new JFileChooser();

        if (fileChoice.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try(BufferedWriter write = new BufferedWriter(new FileWriter(fileChoice.getSelectedFile()))) {
                textArea.write(write);
            }catch (IOException ex){
                JOptionPane.showMessageDialog(this, "Error saving", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openFile() {
        JFileChooser fileChoice = new JFileChooser();

        if (fileChoice.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try (BufferedReader read = new BufferedReader(new FileReader(fileChoice.getSelectedFile()))){
                textArea.read(read, null);
            }catch (IOException ex){
                JOptionPane.showMessageDialog(this, "Error Opening", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
