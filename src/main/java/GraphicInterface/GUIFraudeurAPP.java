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
    private DefaultListModel<Fraudeur> fraudeursListModel;
    private JList fraudeursList;
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
        fraudeursListModel = new DefaultListModel<>();

        if (listFraudeurs != null)
        {
            for (Fraudeur fraudeur : listFraudeurs) {
                fraudeursListModel.addElement(fraudeur);
            }
        }
        fraudeursList = new JList(fraudeursListModel);
        fraudeursList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(fraudeursList);
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
        itemButtons.add(createSupprimerToutButton());

        return itemButtons;
    }

    private JButton createChargementButton() {
        JButton buttonChargement = new JButton(new ImageIcon("icons/chargement.png"));
        buttonChargement.setBorder(buttonBorder());

        buttonChargement.addActionListener(event -> {
            try {
                fraudeApp.lancementChargement();
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exChargementRunning) {
                GUIErreurDialog dialog = new GUIErreurDialog(this, exChargementRunning.getMessage());
                dialog.setVisible(true);
            }
        });
        return buttonChargement;
    }

    private JButton createAnalyseButton() {
        JButton buttonAnalyse = new JButton(new ImageIcon("icons/analyse.png"));
        buttonAnalyse.setBorder(buttonBorder());

        buttonAnalyse.addActionListener(event -> {
            try {
                fraudeApp.lancementAnalyse();
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exChargementRunning) {
                GUIErreurDialog dialog = new GUIErreurDialog(this, exChargementRunning.getMessage());
                dialog.setVisible(true);
            }
        });
        return buttonAnalyse;
    }

    private JButton createStatutButton() {
        JButton buttonStatut = new JButton(new ImageIcon("icons/statut.png"));
        buttonStatut.setBorder(buttonBorder());

        buttonStatut.addActionListener(event -> {
            Statut statut = fraudeApp.afficherStatut();
            new GUIStatutDialog(this, statut);
        });

        return buttonStatut;
    }

    private JButton createAfficherButton() {
        JButton buttonAfficher = new JButton(new ImageIcon("icons/afficher.png"));
        buttonAfficher.setBorder(buttonBorder());

        buttonAfficher.addActionListener(event -> {
            try {
                listFraudeurs =  fraudeApp.afficherFraudeurs();
            } catch (ExceptionAnalyseIsRunning exAnalyseRunning) {
                GUIErreurDialog dialog = new GUIErreurDialog(this, exAnalyseRunning.getMessage());
                dialog.setVisible(true);
            } catch (ExceptionListeFraudeursIsNull exListeFraudeursIsNull) {
                listFraudeurs = null;
                GUIErreurDialog dialog = new GUIErreurDialog(this, exListeFraudeursIsNull.getMessage());
                dialog.setVisible(true);
            }
            finally {
                getContentPane().removeAll();
                createAndShowGUI();
                repaint();
                revalidate();
            }
        });
        return buttonAfficher;
    }

    private JButton createStopButton() {
        JButton buttonStop = new JButton(new ImageIcon("icons/stop.png"));
        buttonStop.setBorder(buttonBorder());

        buttonStop.addActionListener(event -> {
            try {
                fraudeApp.interrompre();
            } catch (ExceptionNoOperationAInterrompre exceptionNoOperationAInterrompre) {
                GUIErreurDialog dialog = new GUIErreurDialog(this, exceptionNoOperationAInterrompre.getMessage());
                dialog.setVisible(true);
            }
        });

        return buttonStop;
    }

    private JButton createSupprimerToutButton() {
        JButton buttonSupprimer = new JButton(new ImageIcon("icons/trash.png"));
        buttonSupprimer.setBorder(buttonBorder());

        buttonSupprimer.addActionListener(event -> {
            GUIConfirmationSuppressionDialog confirmationSuppressionDialog = new GUIConfirmationSuppressionDialog(this);

            confirmationSuppressionDialog.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) { }

                @Override
                public void componentMoved(ComponentEvent e) { }

                @Override
                public void componentShown(ComponentEvent e) { }

                @Override
                public void componentHidden(ComponentEvent e) {
                    boolean confirmation = confirmationSuppressionDialog.getConfirmation();
                    if (confirmation)
                    {
                        fraudeApp.supprimerTout();
                        listFraudeurs = null;
                        getContentPane().removeAll();
                        createAndShowGUI();
                        repaint();
                        revalidate();

                    }
                }
            });
            confirmationSuppressionDialog.setVisible(true);
        });

        return buttonSupprimer;
    }

    private JPanel createQuitterButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton buttonQuitter = new JButton(new ImageIcon("icons/quitter.png"));

        buttonQuitter.addActionListener(event -> {
            try {
                fraudeApp.quitter();
                System.exit(0);
            } catch (ExceptionChargementIsRunning | ExceptionAnalyseIsRunning exceptionChargementIsRunning) {
                GUIErreurDialog dialog = new GUIErreurDialog(this, exceptionChargementIsRunning.getMessage());
                dialog.setVisible(true);
            }
        });

        buttonQuitter.setBorder(buttonQuitterBorder());
        buttonPanel.add(buttonQuitter);

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
