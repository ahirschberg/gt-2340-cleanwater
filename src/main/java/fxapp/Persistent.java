package fxapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Handles storing and retrieving a specific type of model
 *
 * @param <M> the model to store
 */
public class Persistent<M> {
    private final String tableName;
    private final List<DataColumn> columns;
    private final DatabaseManager dbManager;
    private final Reviver<M> reviver;
    private final Class<M> type;

    /**
     * Creates a new Persistent model
     *
     * @param type      the type to persist
     * @param dbManager the database manager
     * @param tableName the name of the table to link the model to
     * @param reviver   the function to use when creating the POJO
     *                  from a database row
     */
    public Persistent(Class<M> type, DatabaseManager dbManager,
                      String tableName, Reviver<M> reviver) {
        this.type = type;
        this.tableName = tableName;
        columns = new LinkedList<>();
        this.dbManager = dbManager;
        this.reviver = reviver;
    }

    /**
     * Adds a column-property link during initialization of Persistent model.
     *
     * @param schema   the SQL property's schema
     * @param property a lambda that acts as a getter for the property
     */
    public void addColumn(String schema, Function<M, ?> property) {
        addColumn(schema, property, false);
    }
    /**
     * Adds a column-property link during initialization of Persistent model.
     *
     * @param schema   the SQL property's schema
     * @param property a lambda that acts as a getter for the property
     * @param isUnique whether the value can be used as an id
     */
    public void addColumn(String schema,
            Function<M, ?> property, boolean isUnique) {
        columns.add(new DataColumn(schema, property, isUnique));
    }

    /**
     * Creates the model's table from the given schema if
     * one does not already exist.
     */
    public void init() {
        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement checkTableExists
                    = conn.prepareStatement("SELECT name FROM sqlite_master "
                    + "WHERE type='table' AND name=(?);");
            checkTableExists.setString(1, tableName);
            ResultSet rs = checkTableExists.executeQuery();
            if (!rs.next()) {
                conn.createStatement().executeUpdate(
                        "create table " + tableName + "("
                                + getSQLStrings((DataColumn col)
                                    -> col.schema) + ");");
            }
            checkTableExists.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the generic type of the Persistence.
     *
     * @return the generic type
     */
    public Class<M> getType() {
        return type;
    }

    /**
     * Stores the model in the database.
     *
     * @param model the model to store
     * @throws SQLException exception
     * @return Key of stored model
     */
    public int store(M model) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {

            try (PreparedStatement prep
                         = conn.prepareStatement("insert into "
                    + tableName + " values ("
                    + getPlaceholders() + ");", Statement
                    .RETURN_GENERATED_KEYS)) {
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

    public void update(M model, String fieldName, Object value) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            DataColumn pk = getPKColumn();
            try (PreparedStatement prep = conn.prepareStatement("UPDATE "
                        + tableName + " SET "
                        + fieldName + "=(?)"
                        + " WHERE " + fieldNameFromSchema(pk.schema) + "=(?)")) {
                prep.setObject(1, value);
                prep.setObject(2, pk.property.apply(model));
                prep.execute();
                        }
        }
    }

    public void delete(M model) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            DataColumn pk = getPKColumn();
            try (PreparedStatement prep = conn.prepareStatement(
                        "DELETE FROM " + tableName 
                        + " WHERE " + fieldNameFromSchema(pk.schema)
                        + "=(?)")) {
                prep.setObject(1, pk.property.apply(model));
                prep.execute();
                        }
        }
    }

    /**
     * Retrieves all models with the given data in the given column
     *
     * @param columnName the column of the data to compare
     * @param data       the data to compare
     * @return all matching data, instantiated as models
     * @throws SQLException exception
     */
    private List<M> retrieve(String columnName, Object data)
    throws SQLException {
    try (Connection conn = dbManager.getConnection()) {
        PreparedStatement prep = conn.prepareStatement("select * from "
                + tableName + " WHERE " + columnName + "=(?)");
        prep.setObject(1, data);
        return retrieveWithQuery(prep);
    }
    }

    /**
     * Retrieves the first model with the given data in the given column.
     *
     * @param columnName the column of the data to compare
     * @param data       the data to compare
     * @return the first match, instantiated as a model
     * @throws SQLException exception
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
     *
     * @return all data, instantiated as models.
     * @throws SQLException exception
     */
    public List<M> retrieveAll() throws SQLException {
        try (Connection conn = dbManager.getConnection()) {
            PreparedStatement ps = conn
                .prepareStatement("SELECT * FROM " + tableName);
            return retrieveWithQuery(ps);
        }
    }

    /**
     * Gets data from SQL table in string form.
     * @param mapper Function to map data columns and strings.
     * @return The string requested.
     */
    private String getSQLStrings(Function<DataColumn, String> mapper) {
        return String.join(", ", columns.stream()
                .map(mapper)
                .collect(Collectors.toList()));
    }

    /**
     * Get's SQL string placeholders.
     * @return the placeholders.
     */
    private String getPlaceholders() {
        return getSQLStrings((DataColumn col) -> "?");
    }

    /**
     * Gets a list based on a SQL statement
     * @param statement query statement
     * @return the list requested
     * @throws SQLException on an invalid SQL statement
     */
    private List<M> retrieveWithQuery(PreparedStatement statement)
        throws SQLException {
        ResultSet model = statement.executeQuery();
        List<M> resultant = new LinkedList<>();
        while (model.next()) {
            resultant.add(reviver.make(model));
        }
        return resultant;
    }

    private String fieldNameFromSchema(String schema) {
        return schema.split(" ")[0];
    }
    private DataColumn getPKColumn() {
        return columns.stream().filter(col -> col.isUnique)
            .findFirst().get();
    }
    private class DataColumn {
        private final String schema;
        private final Function<? super M, ?> property;
        private final boolean isUnique;

        /**
         * Initializes the data column
         * @param schema SQL schema to use
         * @param property function to retrieve data to store.
         */
        public DataColumn(String schema, Function<? super M, ?> property,
                boolean isUnique) {
            this.schema = schema;
            this.property = property;
            this.isUnique = isUnique;
        }
    }

    @FunctionalInterface
    public interface Reviver<N> {

        /**
         * Creates a model from a row in the database
         *
         * @param modelRow the row of data to instantiate
         * @return a model instantiated with the row data
         * @throws SQLException exception
         */
        N make(ResultSet modelRow) throws SQLException;
    }
}
