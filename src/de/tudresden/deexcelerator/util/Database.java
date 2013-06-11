package de.tudresden.deexcelerator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * <span class="de">Datenbank Verbindung und Funktionen zu PostgreSQL.</span>
 * <span class="en">Database connection and function to postgreSQL.</span>
 * @author Christopher Werner
 *
 */
public class Database
{
  private String     driver     = "org.postgresql.Driver";
 
  // --------------------------------------------------------------------------
  //Muss vom Benutzer eingeben werden um verwendet werden zu können
  //In Configuration kann Datenbank objekt erzeugt und Werte gesetzt werden
  public String     host       = ""; 
  public String     port       = ""; 
  public String     database   = ""; 
  public String     user       = ""; 
  public String     password   = "";
 
  // --------------------------------------------------------------------------
 
  private Connection connection = null; 
 
  /**
   * <span class="de">schließt die Verbindung</span>
   * <span class="en">close the connection</span>
   */
  public void closeConnection ()
  {
    try
    {
      connection.close ();
    }
    catch (SQLException e)
    {
      e.printStackTrace ();
      System.exit (1);
    } 
  }
  
  /**
   * @return <span class="de"></span>
   * <span class="en">Url-string for postgreSQL-database connection</span>
   */
  public String getUrl ()
  {
    // PostgreSQL takes one of the following url-forms:
    // ================================================
    // jdbc:postgresql:database
    // jdbc:postgresql://host/database
    // jdbc:postgresql://host:port/database	  
    return ("jdbc:postgresql:" + (host != null ? ("//" + host) + (port != null ? ":" + port : "") + "/" : "") + database);
  }
  
  /**
   * <span class="de">L&aumldt die JDBC Treiber</span>
   * <span class="en">loading the JDBC driver</span>
   */
  public void loadJdbcDriver ()
  {
    try
    {
      Class.forName (driver);
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace ();
      System.exit (1);
    } 
  }
  
  /**
   * <span class="de">&Oumlffnet die Verbindung</span>
   * <span class="en">opening the connection</span>
   */
  public void openConnection ()
  {
    try
    {
      connection = DriverManager.getConnection (getUrl (), user, password);
    }
    catch (SQLException e)
    {
      e.printStackTrace ();
      System.exit (1);
    } 
  }
 
  /**
   * <span class="de">Query an Datenbank senden.</span>
   * <span class="en">Send query to database.</span>
   * @param query (<span class="de">Abzusetzende Query</span>
   * <span class="en">query</span>)
   */
  public void sendStatement (String query)
  {
    try
    {
      Statement statement = connection.createStatement ();
      statement.execute(query);
      statement.close ();
    }
    catch (SQLException e)
    {
      e.printStackTrace ();
      System.exit (1);
    }
  }  
}
