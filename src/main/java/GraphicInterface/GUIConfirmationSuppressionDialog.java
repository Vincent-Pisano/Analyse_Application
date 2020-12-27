package GraphicInterface;

import ConsoleInterface.FraudeApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIConfirmationSuppressionDialog extends JDialog{

    private boolean confirmation;
    private JFrame frame;

    public GUIConfirmationSuppressionDialog(JFrame frame) {
        super(frame, "Confirmation", Dialog.ModalityType.DOCUMENT_MODAL);
        this.confirmation = false;
        this.frame = frame;

        createAndShowGUI();
    }

    public void createAndShowGUI()
    {
        JButton yesButton = new JButton("Oui");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConfirmation(true);
                setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setConfirmation(false);
                setVisible(false);
            }
        });

        Container dialogContainer = getContentPane();
        dialogContainer.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(new JLabel("Voulez-vous confirmer la suppression ?"));
        dialogContainer.add(labelPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(yesButton);
        dialogContainer.add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(frame);
    }

    public boolean getConfirmation()
    {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation)
    {
        this.confirmation = confirmation;
    }

}