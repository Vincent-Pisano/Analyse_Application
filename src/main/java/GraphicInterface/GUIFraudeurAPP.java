package GraphicInterface;

import ConsoleInterface.FraudeApp;

import ConsoleInterface.Fraudeur;
import ConsoleInterface.Statut;
import Exception.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class GUIFraudeurAPP extends JFrame {

    private FraudeApp fraudeApp;
    private DefaultListModel<Fraudeur> itemsListModel;
    private JList itemsList;
    private ArrayList<Fraudeur> listFraudeurs;

    public GUIFraudeurAPP(FraudeApp fraudeApp)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fraudeApp = fraudeApp;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        ImageIcon logo = new ImageIcon("icons/logo.png");
        setIconImage(logo.getImage());

        add(createTitlePane(), BorderLayout.NORTH);
        add(createContentPane(), BorderLayout.CENTER);

        setSize(400, 300);
        setVisible(true);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setBorder(border());
        contentPane.setLayout(new BorderLayout());

        contentPane.add(createFraudeActions(), BorderLayout.NORTH);
        contentPane.add(createFraudeList(), BorderLayout.CENTER);
        contentPane.add(createQuitterButton(), BorderLayout.SOUTH);

        return contentPane;
    }

    private JPanel createTitlePane() {
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.setBorder(border());

        JLabel title = new JLabel("Recherche de fraudeurs", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setBorder(titleBorder());
        titlePane.add(title);
        titlePane.add(new JSeparator());

        return titlePane;
    }

    private JScrollPane createFraudeList()
    {
        itemsListModel = new DefaultListModel<>();

        if (listFraudeurs != null)
        {
            for (Fraudeur fraudeur : listFraudeurs) {
                itemsListModel.addElement(fraudeur);
            }
        }
        itemsList = new JList(itemsListModel);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(itemsList);
        return listScroller;
    }

    private JPanel createFraudeActions() {
        JPanel itemButtons = new JPanel();
        itemButtons.setLayout(new BoxLayout(itemButtons, BoxLayout.X_AXIS));

        itemButtons.add(createChargementButton());
        itemButtons.add(createAnalyseButton());
        itemButtons.add(createStatutButton());
        itemButtons.add(createAfficherButton());
        itemButtons.add(createStopButton());

        return itemButtons;
    }

    private JButton createChargementButton() {
        JButton button = new JButton(new ImageIcon("icons/chargement.png"));
        button.setBorder(buttonBorder());

        button.addActionListener(event -> {
            try {
                fraudeApp.lancementChargement();
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exChargementRunning) {
                exChargementRunning.printStackTrace();
            }
        });
        return button;
    }

    private JButton createAnalyseButton() {
        JButton button = new JButton(new ImageIcon("icons/analyse.png"));
        button.setBorder(buttonBorder());

        button.addActionListener(event -> {
            try {
                fraudeApp.lancementAnalyse();
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exChargementRunning) {
                exChargementRunning.printStackTrace();
            }
        });
        return button;
    }

    private JButton createStatutButton() {
        JButton button = new JButton(new ImageIcon("icons/statut.png"));
        button.setBorder(buttonBorder());

        button.addActionListener(event -> {
            Statut statut = fraudeApp.afficherStatut();
            new GUIStatutDialog(this, statut);
        });

        return button;
    }

    private JButton createAfficherButton() {
        JButton button = new JButton(new ImageIcon("icons/afficher.png"));
        button.setBorder(buttonBorder());

        button.addActionListener(event -> {
            try {
                listFraudeurs =  fraudeApp.afficherFraudeurs();
                getContentPane().removeAll();
                createAndShowGUI();
                repaint();
                revalidate();
            } catch (ExceptionAnalyseIsRunning | ExceptionListeFraudeursIsNull exChargementRunning) {
                exChargementRunning.printStackTrace();
            }
        });
        return button;
    }

    private JButton createStopButton() {
        JButton button = new JButton(new ImageIcon("icons/stop.png"));
        button.setBorder(buttonBorder());

        button.addActionListener(event -> {
            try {
                fraudeApp.interrompre();
            } catch (ExceptionNoOperationAInterrompre exceptionNoOperationAInterrompre) {
                exceptionNoOperationAInterrompre.printStackTrace();
            }
        });

        return button;
    }

    private JPanel createQuitterButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton button = new JButton(new ImageIcon("icons/quitter.png"));

        button.addActionListener(event -> {
            try {
                fraudeApp.quitter();
                System.exit(0);
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exceptionChargementIsRunning) {
                exceptionChargementIsRunning.printStackTrace();
            }
        });

        button.setBorder(buttonQuitterBorder());
        buttonPanel.add(button);

        return buttonPanel;
    }

    private Border border() {
        return BorderFactory.createEmptyBorder(5, 10, 10, 10);
    }

    private Border titleBorder() {
        return BorderFactory.createEmptyBorder(5, 0, 10, 10);
    }

    private Border buttonQuitterBorder() {
        return BorderFactory.createEmptyBorder(7, 5, 7, 5);
    }

    private Border buttonBorder() {
        return BorderFactory.createEmptyBorder(5, 5, 10 , 5);
    }
}
