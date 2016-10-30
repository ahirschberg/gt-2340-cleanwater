package fxapp;


import model.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

class DatabaseManager {
    private List<Persistent> helpers;
    private Persistent<User> users;
    private Persistent<SourceReport> sourceReports;
    private Persistent<PurityReport> purityReports;

    private void initHelpers() {
        users = createPersistenceHelper(User.class, "users", (ResultSet rs) -> new User(
                    rs.getString("username"),
                    new Token(rs.getString("token")),
                    PermissionLevel.fromInt(rs.getInt("permission"))
        ));

        sourceReports = createPersistenceHelper(SourceReport.class, "source_reports", (rs) -> new SourceReport(
                rs.getInt("id"),
                new Location(rs.getDouble("latitude"), rs.getDouble("longitude")),
                rs.getString("water_type"),
                rs.getString("water_condition")
        ));

        purityReports = createPersistenceHelper(PurityReport.class, "purity_reports", (rs) -> new PurityReport(
                rs.getInt("id"),
                new Location(rs.getDouble("latitude"), rs.getDouble("longitude")),
                rs.getDouble("virus_ppm"),
                rs.getDouble("contaminant_ppm"),
                rs.getString("water_condition")
        ));
    }

    private <M> Persistent<M> createPersistenceHelper(Class<M> klass,
                                                      String tableName, Persistent.Reviver<M> reviver) {
        Persistent<M> p = new Persistent<>(klass, this, tableName, reviver);
        helpers.add(p);
        return p;
    }

    public DatabaseManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        helpers = new LinkedList<>();
        initHelpers();
        makePersistence();
    }

    private void makePersistence() {
        users.addColumn("username string UNIQUE", User::getUsername);
        users.addColumn("token string UNIQUE", User::getToken);
        users.addColumn("permission integer", (User u) -> u.getPermissionLevel().level);
        users.init();

        sourceReports.addColumn("id integer", SourceReport::getReportNum);
        sourceReports.addColumn("latitude real", (SourceReport sr) -> sr.getLocation().getLatitude());
        sourceReports.addColumn("longitude real", (SourceReport sr) -> sr.getLocation().getLongitude());
        sourceReports.addColumn("water_type string", SourceReport::getWaterType);
        sourceReports.addColumn("water_condition string", SourceReport::getWaterCondition);
        sourceReports.init();

        purityReports.addColumn("id integer", PurityReport::getReportNum);
        purityReports.addColumn("latitude real", (PurityReport sr) -> sr.getLocation().getLatitude());
        purityReports.addColumn("longitude real", (PurityReport sr) -> sr.getLocation().getLongitude());
        purityReports.addColumn("virus_ppm real", PurityReport::getVirusPPM);
        purityReports.addColumn("contaminant_ppm real", PurityReport::getContaminantPPM);
        purityReports.addColumn("water_condition string", PurityReport::getWaterCondition);
        purityReports.init();
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:cleanwater.db");
    }

    @SuppressWarnings("unchecked")
    public <M> Persistent<M> getPersistence(final Class c) {
        return helpers.stream().filter((p) -> p.getType().equals(c)).findFirst().get();
    }

}
