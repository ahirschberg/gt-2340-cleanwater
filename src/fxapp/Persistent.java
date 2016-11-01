package fxapp;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles storing and retrieving a specific type of model
 * @param <M> the model to store
 */
public class Persistent<M> {
    private String tableName;
    private List<DataColumn> columns;
    private DatabaseManager dbManager;
    private Reviver<M> reviver;
    private Class<M> type;

    /**
     * Creates a new Persistent model
     * @param type the type to persist
     * @param dbManager the database manager
     * @param tableName the name of the table to link the model to
     * @param reviver the function to use when creating the POJO from a database row
     */
    public Persistent(Class<M> type, DatabaseManager dbManager, String tableName, Reviver<M> reviver) {
        this.type = type;
        this.tableName = tableName;
        columns = new LinkedList<>();
        this.dbManager = dbManager;
        this.reviver = reviver;
    }

    /**
     * Adds a column-property link during initialization of Persistent model.
     * @param schema the SQL property's schema
     * @param property a lambda that acts as a getter for the property
     */
    public void addColumn(String schema, Function<M, ?> property) {
        columns.add(new DataColumn(schema, property));
    }

    /**
     * Creates the model's table from the given schema if one does not already exist.
     */
    public void init() {
        try(Connection conn = dbManager.getConnection()) {
            PreparedStatement checkTableExists
                    = conn.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=(?);");
            checkTableExists.setString(1, tableName);
            ResultSet rs = checkTableExists.executeQuery();
            if (!rs.next()) {
                conn.createStatement().executeUpdate(
                        "create table " + tableName + "(" + getSQLStrings((DataColumn col) -> col.schema) + ");");
            }
            checkTableExists.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the generic type of the Persistence.
     * @return the generic type
     */
    public Class<M> getType() {
        return type;
    }

    /**
     * Stores the model in the database.
     * @param model the model to store
     * @throws SQLException
     */
    public int store(M model) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {

            try (PreparedStatement prep
                         = conn.prepareStatement("insert into " + tableName + " values ("
                    + getPlaceholders() + ");", Statement.RETURN_GENERATED_KEYS)) {
                int index = 1;
                for (DataColumn column : columns) {
                    if (column.property != null) {
                        prep.setObject(index, column.property.apply(model));
                    } else {
                        prep.setObject(index, null);
                    }
                    index++;
                }
                prep.addBatch();
                prep.executeBatch();
                ResultSet keys = prep.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    return -1;
                }
            }
        }
    }

    /**
     * Retrieves all models with the given data in the given column
     * @param columnName the column of the data to compare
     * @param data the data to compare
     * @return all matching data, instantiated as models
     * @throws SQLException
     */
    public List<M> retrieve(String columnName, Object data) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement prep = conn.prepareStatement("select * from " + tableName + " WHERE " + columnName + "=(?)");
            prep.setObject(1, data);
            return retrieveWithQuery(prep);
        }
    }

    /**
     * Retrieves the first model with the given data in the given column.
     * @param columnName the column of the data to compare
     * @param data the data to compare
     * @return the first match, instantiated as a model
     * @throws SQLException
     */
    public M retrieveOne(String columnName, Object data) throws SQLException {
        List<M> all = retrieve(columnName, data);
        if (all.size() == 0) {
            return null;
        } else {
            return all.get(0);
        }
    }

    /**
     * Retrieves all models
     * @return all data, instantiated as models.
     * @throws SQLException
     */
    public List<M> retrieveAll() throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName);
            return retrieveWithQuery(ps);
        }
    }

    private String getSQLStrings(Function<DataColumn, String> mapper) {
        return String.join(", ", columns.stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    private String getPlaceholders() {
        return getSQLStrings((DataColumn col) -> "?");
    }

    private List<M> retrieveWithQuery(PreparedStatement statement) throws SQLException {
        ResultSet model = statement.executeQuery();
        List<M> resultant = new LinkedList<>();
        while(model.next()) {
            resultant.add(reviver.make(model));
        }
        return resultant;
    }

    private class DataColumn {
        private String schema;

        private Function<? super M, ?> property;
        public DataColumn(String schema, Function<? super M, ?> property) {
            this.schema = schema;
            this.property = property;
        }
    }

    @FunctionalInterface
    public interface Reviver<N> {

        /**
         * Creates a model from a row in the database
         * @param modelRow the row of data to instantiate
         * @return a model instantiated with the row data
         * @throws SQLException
         */
        N make(ResultSet modelRow) throws SQLException;
    }
}