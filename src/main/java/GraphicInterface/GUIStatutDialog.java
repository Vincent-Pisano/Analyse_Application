package GraphicInterface;

import ConsoleInterface.Statut;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIStatutDialog extends JDialog {
    private JFrame frame;
    private Statut statut;

    public GUIStatutDialog(JFrame frame, Statut statut)
    {
        super(frame, "Statut" , Dialog.ModalityType.DOCUMENT_MODAL);
        this.frame = frame;
        this.statut = statut;
        createAndShowGUI();
    }

    public void createAndShowGUI(){
        setLayout(new BorderLayout());


        JPanel field = createPanel();

        JTextField[] champs = createChamps();
        for (int i =0; i < champs.length; i++)
            field.add(champs[i]);

        JPanel label = createPanel();
        JLabel[] labels = createLabels();
        for (int i =0; i < labels.length; i++)
            label.add(labels[i]);

        add(field, BorderLayout.CENTER);
        add(label, BorderLayout.WEST);


        for (int i =0; i < champs.length; i++)
            champs[i].setEditable(false);

        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        pButton.add(okButton);
        add(pButton, BorderLayout.SOUTH);

        setSize(250,175);
        setVisible(true);
    }

    private JPanel createPanel()
    {
        JPanel panel = new JPanel();
        panel.setBorder(border());
        panel.setLayout(new GridLayout(0, 1,5,5));
        return panel;
    }

    private JTextField[] createChamps()
    {
        JTextField[] tabInfos = new JTextField[3];

        tabInfos[0] = new JTextField(statut.getEtat());
        tabInfos[1] = new JTextField(Integer.toString(statut.getNbrFacturesChargees()));
        tabInfos[2] = new JTextField(Integer.toString(statut.getNbrFraudeursTrouves()));

        return tabInfos;
    }
    private JLabel[] createLabels()
    {
        JLabel[] tabLabels = new JLabel[3];

        tabLabels[0] = new JLabel("Etat");
        tabLabels[1] = new JLabel("Factures chargees");
        tabLabels[2] = new JLabel("Fraudeurs trouves");
        return tabLabels;
    }

    private Border border() {
        return BorderFactory.createEmptyBorder(5, 10, 10, 10);
    }

}
