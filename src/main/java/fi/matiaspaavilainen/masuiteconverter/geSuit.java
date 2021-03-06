package fi.matiaspaavilainen.masuiteconverter;

import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import fi.matiaspaavilainen.masuitecore.core.database.Database;
import fi.matiaspaavilainen.masuitecore.core.objects.Location;
import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
import fi.matiaspaavilainen.masuitehomes.core.Home;
import fi.matiaspaavilainen.masuiteportals.core.Portal;
import fi.matiaspaavilainen.masuiteteleports.bungee.managers.Spawn;
import fi.matiaspaavilainen.masuitewarps.bungee.Warp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class geSuit {

    private Database db = ConnectionManager.db;
    private Connection connection = null;
    private PreparedStatement statement = null;
    private BungeeConfiguration config = new BungeeConfiguration();
    private String gsPrefix = config.load("converter", "config.yml").getString("gesuit-prefix");

    private Set<Home> getHomes() {
        Set<Home> homes = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + gsPrefix + "homes");
            rs = statement.executeQuery();
            while (rs.next()) {
                Home home = new Home();
                home.setName(rs.getString("home_name"));
                home.setServer(rs.getString("server"));
                home.setOwner(UUID.fromString(rs.getString("player")));
                home.setLocation(new Location(rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch")));
                homes.add(home);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return homes;
    }

    public void convertHomes() {
        for (Home home : getHomes()) {
            home.create();
        }
        System.out.println("[MaSuite] [Converter] [Homes] Converting done");
    }

    private Set<Portal> getPortals() {
        Set<Portal> portals = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + gsPrefix + "portals");
            rs = statement.executeQuery();
            while (rs.next()) {
                Portal portal = new Portal();
                portal.setName(rs.getString("portalname"));
                portal.setServer(rs.getString("server"));
                portal.setType(rs.getString("type"));
                portal.setDestination(rs.getString("destination"));
                portal.setMinLoc(new Location(rs.getString("world"), rs.getDouble("xmin"), rs.getDouble("ymin"), rs.getDouble("zmin")));
                portal.setMaxLoc(new Location(rs.getString("world"), rs.getDouble("xmax"), rs.getDouble("ymax"), rs.getDouble("zmax")));
                portals.add(portal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return portals;
    }

    public void convertPortals() {
        for (Portal portal : getPortals()) {
            portal.save();
        }
        System.out.println("[MaSuite] [Converter] [Portals] Converting done");
    }

    private Set<Warp> getWarps() {
        Set<Warp> warps = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + gsPrefix + "warps");
            rs = statement.executeQuery();
            while (rs.next()) {
                Warp warp = new Warp();
                warp.setName(rs.getString("warpname"));
                warp.setServer(rs.getString("server"));
                warp.setLocation(new Location(rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch")));
                warp.setHidden(rs.getBoolean("hidden"));
                warp.setGlobal(rs.getBoolean("global"));
                warps.add(warp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return warps;
    }

    public void convertWarps() {
        for (Warp warp : getWarps()) {
            warp.create();
        }
        System.out.println("[MaSuite] [Converter] [Warps] Converting done");
    }

    private Set<Spawn> getSpawns() {
        Set<Spawn> spawns = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + gsPrefix + "spawns");
            rs = statement.executeQuery();
            while (rs.next()) {
                Spawn spawn = new Spawn();
                spawn.setServer(rs.getString("server"));
                spawn.setLocation(new Location(rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch")));
                spawn.setType(0);
                spawns.add(spawn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return spawns;
    }

    public void convertSpawns() {
        for (Spawn spawn : getSpawns()) {
            spawn.create(spawn);
        }

        System.out.println("[MaSuite] [Converter] [Spawns] Converting done");
    }

    private Set<MaSuitePlayer> getPlayers() {
        Set<MaSuitePlayer> players = new HashSet<>();
        ResultSet rs = null;
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + gsPrefix + "players");
            rs = statement.executeQuery();
            while (rs.next()) {
                MaSuitePlayer msp = new MaSuitePlayer();
                msp.setUniqueId(UUID.fromString(rs.getString("uuid")));
                msp.setUsername(rs.getString("playername"));
                msp.setNickname(null);
                LocalDate firstJoin = rs.getDate("firstonline").toLocalDate();
                LocalDate lastJoin = rs.getDate("lastonline").toLocalDate();
                msp.setFirstLogin(firstJoin.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
                msp.setLastLogin(lastJoin.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
                players.add(msp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return players;
    }

    public void convertPlayers() {
        for (MaSuitePlayer msp : getPlayers()) {
            msp.create();
        }
        System.out.println("[MaSuite] [Converter] [MaSuitePlayers] Converting done");
    }
}
