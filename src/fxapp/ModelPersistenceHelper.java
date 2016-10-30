package fxapp;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModelPersistenceHelper<M> {
    private String tableName;
    private List<DataColumn> columns;
    private DatabaseManager dbManager;

    public ModelPersistenceHelper(DatabaseManager dbManager, String tableName) {
        this.tableName = tableName;
        columns = new LinkedList<>();
        this.dbManager = dbManager;
    }

    public void addColumn(String schema, Function<M, ?> getter) {
        columns.add(new DataColumn(schema, getter));
    }

    public void init() {
        try(Connection conn = dbManager.getConnection()) {
            PreparedStatement checkTableExists
                    = conn.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name=(?);");
            checkTableExists.setString(1, tableName);
            ResultSet rs = checkTableExists.executeQuery();
            if (!rs.next()) {
                conn.createStatement().executeUpdate(
                        "create table users (" + getSQLStrings((DataColumn col) -> col.schema) + ");");
            }
            checkTableExists.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSQLStrings(Function<DataColumn, String> mapper) {
        return String.join(", ", columns.stream()
                    .map(mapper)
                    .collect(Collectors.toList()));
    }

    public void store(M model) throws SQLException {
        try (Connection conn = dbManager.getConnection()) {

            try (PreparedStatement prep
                         = conn.prepareStatement("insert into users values ("
                    + getSQLStrings((DataColumn col) -> "?") + ")")) {
                int index = 1;
                for (DataColumn column : columns) {
                    prep.setObject(index, column.getter.apply(model));
                    index++;
                }
                prep.addBatch();
                prep.executeBatch();
            }
        }
    }

    public class DataColumn {
        private String schema;

        private Function<? super M, ?> getter;
        public DataColumn(String schema, Function getter) {
            this.schema = schema;
            this.getter = getter;
        }
    }
}