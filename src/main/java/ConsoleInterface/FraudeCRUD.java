package ConsoleInterface;

import java.sql.*;
import java.util.ArrayList;

public class FraudeCRUD {

    private Connection conn;

    public FraudeCRUD() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/analyse?&serverTimezone=EST",
                    "Analyse", "AnalysePW");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ajouterFraudeur(String compagnie, int nbrFacture, int nbrFactureDuplique, Double ratio)
    {
        String sql = "INSERT INTO fraudeur VALUES (?, ?, ?, ?)";

        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);

            statement.setString(1, compagnie);
            statement.setInt(2, nbrFacture);
            statement.setInt(3, nbrFactureDuplique);
            statement.setDouble(4, ratio);

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void supprimerFraudeur(String compagnie)
    {
        String sql = "DELETE FROM fraudeur WHERE id_compagnie = ?";

        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);

            statement.setString(1, compagnie);

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Fraudeur readFraudeur(String compagnie)
    {
        Fraudeur fraudeur = null;
        Statement statement;
        try {
            statement = conn.createStatement();

            String sql = "SELECT * FROM fraudeur WHERE id_compagnie = " + compagnie;
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next())
            {
                fraudeur = new Fraudeur(resultSet.getString("id_compagnie"),
                        resultSet.getDouble("pourcentage_dupli"));
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fraudeur;
    }

    public void supprimerAllFraudeurs()
    {
        String sql = "DELETE FROM fraudeur";

        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Fraudeur> readAllFraudeur()
    {
        ArrayList<Fraudeur> fraudeurs = null;
        boolean hasPassed = false;
        Statement statement;
        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM fraudeur ORDER BY pourcentage_dupli DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (!hasPassed) {
                    fraudeurs = new ArrayList<>();
                    hasPassed = true;
                }
                fraudeurs.add(new Fraudeur(resultSet.getString("id_compagnie"),
                              resultSet.getDouble("pourcentage_dupli")));
            }
            if (!hasPassed)
                return null;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return fraudeurs;
    }

    public int readNbrFraudeurs()
    {
        int nbrFraudeurs = 0;
        Statement statement;
        try {
            statement = conn.createStatement();
            String sql = "SELECT COUNT(*) FROM fraudeur";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                nbrFraudeurs = resultSet.getInt("COUNT(*)");
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nbrFraudeurs;
    }

}
