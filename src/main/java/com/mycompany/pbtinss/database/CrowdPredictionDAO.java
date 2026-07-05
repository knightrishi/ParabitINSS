package  com.mycompany.pbtinss.database;

import com.mycompany.pbtinss.analytics.CrowdPredictionEngine.PredictionReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Handles persistence for crowd prediction metrics into the centralized system database.
 */
public class CrowdPredictionDAO {

    // Simple connection supplier (Replace with your project's actual database connection manager)
    private final Connection databaseConnection;

    public CrowdPredictionDAO(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Persists a newly calculated crowd prediction report directly into the database.
     */
    public boolean savePredictionReport(PredictionReport report) {
        String sql = "INSERT INTO sector_crowd_analytics (sector_id, predicted_count, confidence_score, calculated_at) " +
                     "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = databaseConnection.prepareStatement(sql)) {
            statement.setString(1, report.getSectorId());
            statement.setInt(2, report.getPredictedCount());
            statement.setFloat(3, report.getConfidence());
            statement.setLong(4, report.getTimestamp());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
            
        } catch (SQLException e) {
            System.err.println("[DB ERROR] Failed to save crowd prediction metrics for sector: " + report.getSectorId());
            e.printStackTrace();
            return false;
        }
    }
}