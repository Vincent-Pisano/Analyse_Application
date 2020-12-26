package GraphicInterface;

import ConsoleInterface.FraudeApp;

import ConsoleInterface.Fraudeur;
import Exception.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GUIFraudeurAPP extends JFrame {

    private FraudeApp fraudeApp;
    private DefaultListModel<Fraudeur> itemsListModel;
    private JList itemsList;

    public GUIFraudeurAPP(FraudeApp fraudeApp)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fraudeApp = fraudeApp;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        add(createTitlePane(), BorderLayout.NORTH);
        add(createContentPane(), BorderLayout.CENTER);

        setSize(400, 300);
        setVisible(true);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(border());
        contentPane.setLayout(new BorderLayout());

        return contentPane;
    }

    private JPanel createTitlePane() {
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBorder(border());

        JLabel title = new JLabel("Chercheur de fraudeurs", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBorder(titleBorder());
        titlePane.add(title);
        titlePane.add(new JSeparator());

        return titlePane;
    }


    private Border border() {
        return BorderFactory.createEmptyBorder(5, 10, 10, 10);
    }

    private Border titleBorder() {
        return BorderFactory.createEmptyBorder(5, 0, 10, 10);
    }
}
