package fxapp;


import model.Location;
import model.PermissionLevel;
import model.Profile;
import model.PurityReport;
import model.SourceReport;
import model.Token;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {
    private List<Persistent> helpers;
    private Persistent<User> users;
    private Persistent<Profile> profiles;
    private Persistent<SourceReport> sourceReports;
    private Persistent<PurityReport> purityReports;

    /**
     * Initializes Helpers for users, profiles, and reports
     */
    private void initHelpers() {
        users = createPersistenceHelper(User.class, "users", (ResultSet rs) -> {
            Profile p = getPersistence(Profile.class).retrieveOne("rowid",
                    rs.getInt("profile"));
            return new User(
                    rs.getString("username"),
                    new Token(rs.getString("token")),
                    PermissionLevel.fromInt(rs.getInt("permission")),
                    (p == null) ? new Profile() : p
            );
        });

        profiles = createPersistenceHelper(Profile.class, "profiles",
            (ResultSet rs) ->
                new Profile(
                        rs.getString("name"), rs.getString("email"),
                        rs.getString("street"), rs.getString("city"),
                        rs.getString("state"), rs.getString("country"),
                        rs.getString("org"))
        );

        sourceReports = createPersistenceHelper(SourceReport.class,
                "source_reports", (rs) -> new SourceReport(
                rs.getInt("id"),
                new Location(rs.getDouble("latitude"),
                        rs.getDouble("longitude")),
                rs.getString("water_type"),
                rs.getString("water_condition"),
                new Time(rs.getLong("datetime"))
        ));

        purityReports = createPersistenceHelper(PurityReport.class,
                "purity_reports", (rs) -> new PurityReport(
                rs.getInt("id"),
                new Location(rs.getDouble("latitude"),
                        rs.getDouble("longitude")),
                rs.getDouble("virus_ppm"),
                rs.getDouble("contaminant_ppm"),
                rs.getString("water_condition"),
                new Time(rs.getLong("datetime"))
        ));
    }

    /**
     * Creates an object to allow persistance
     * @param klass Class which needs to persist
     * @param <M> the class which needs to persist
     * @param tableName name of table
     * @param reviver Persistance reviver
     * @return The created persistance helper
     */
    private <M> Persistent<M> createPersistenceHelper(Class<M> klass,
                                              String tableName,
                                              Persistent.Reviver<M> reviver) {
        Persistent<M> p = new Persistent<>(klass, this, tableName, reviver);
        helpers.add(p);
        return p;
    }

    /**
     * Initializes the database
     * @throws ClassNotFoundException if org.sqlite.JDBC is not found
     */
    public DatabaseManager() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        helpers = new LinkedList<>();
        initHelpers();
        makePersistence();
    }

    /**
     * makes data persist in sql tables
     */
    private void makePersistence() {
        users.addColumn("username string UNIQUE", User::getUsername);
        users.addColumn("token string UNIQUE", User::getToken);
        users.addColumn("permission integer",
            (User u) -> u.getPermissionLevel().level);
        users.addColumn("profile integer UNIQUE", null);
        users.init();

        profiles.addColumn("id integer PRIMARY KEY AUTOINCREMENT", null);
        profiles.addColumn("name string", Profile::getName);
        profiles.addColumn("email string", Profile::getEmail);
        profiles.addColumn("street string", Profile::getStreet);
        profiles.addColumn("city string", Profile::getCity);
        profiles.addColumn("state string", Profile::getState);
        profiles.addColumn("country string", Profile::getCountry);
        profiles.addColumn("org string", Profile::getOrg);
        profiles.init();

        sourceReports.addColumn("id integer", SourceReport::getReportNum);
        sourceReports.addColumn("latitude real",
            (SourceReport sr) -> sr.getLocation().getLatitude());
        sourceReports.addColumn("longitude real",
            (SourceReport sr) -> sr.getLocation().getLongitude());
        sourceReports.addColumn("water_type string",
            SourceReport::getWaterType);
        sourceReports.addColumn("water_condition string",
            SourceReport::getWaterCondition);
        sourceReports.addColumn("datetime integer",
            (SourceReport sr) -> sr.getCreationDatetime().getTime());
        sourceReports.init();

        purityReports.addColumn("id integer", PurityReport::getReportNum);
        purityReports.addColumn("latitude real",
            (PurityReport sr) -> sr.getLocation().getLatitude());
        purityReports.addColumn("longitude real",
            (PurityReport sr) -> sr.getLocation().getLongitude());
        purityReports.addColumn("virus_ppm real", PurityReport::getVirusPPM);
        purityReports.addColumn("contaminant_ppm real",
            PurityReport::getContaminantPPM);
        purityReports.addColumn("water_condition string",
            PurityReport::getWaterCondition);
        purityReports.addColumn("datetime integer",
            (PurityReport pr) -> pr.getCreationDatetime().getTime());
        purityReports.init();
    }

    /**
     * Loads database from file
     * @return connection to the database file
     * @throws SQLException If database cannot be loaded
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:cleanwater.db");
    }

    /**
     * Gets first persistence helper for a given class.
     * @param c the class which the persistence helper stores.
     * @param <M> the class which the persistance helper stores.
     * @return The persistence helper
     */
    @SuppressWarnings("unchecked")
    public <M> Persistent<M> getPersistence(final Class<M> c) {
        return helpers.stream().filter((p) ->
                p.getType().equals(c)).findFirst().get();
    }

}
