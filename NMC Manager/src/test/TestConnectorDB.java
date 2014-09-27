package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectorDB;

import org.junit.Test;
import org.postgresql.util.PSQLException;

public class TestConnectorDB {

//	@Test
//	public void testOpenConnection() {
//		ConnectorDB.openConnection("//localhost/nmc_db", "nmc_admin", "ephec2014");
//	}
//
//	@Test
//	public void testCloseConnection() {
//		ConnectorDB.openConnection("//localhost/nmc_db", "nmc_admin", "ephec2014");
//		ConnectorDB.closeConnection();
//	}

	@Test
	public void testSelect() {
		ConnectorDB.openConnection("//localhost/nmc_db", "nmc_admin", "ephec2014");
			ResultSet rs = ConnectorDB.select("SELECT * FROM nmc_permissions");
			try {
				if(rs.next() || rs.first()) {
					System.out.println("username: "+rs.getArray("login"));
				}
			} catch (SQLException e) {
				if(((PSQLException)e).getSQLState() == "24000"){
					
				} 
				else e.printStackTrace();
			}
		ConnectorDB.closeConnection();
	}

//	@Test
//	public void testModify() {
//		ConnectorDB.openConnection("//localhost/nmc_db", "nmc_admin", "ephec2014");
//		try {
//			ConnectorDB.modify("INSERT INTO nmc_users (login, password, mail)"
//					+ "VALUES ('Torgha', 'ephec', 'a.ceyssens@nukama.be')");
//		} catch (SQLException e) {
//			System.out.println("Insert foirééé");
//			
//			e.printStackTrace();
//		}
//		ConnectorDB.closeConnection();
//	}

}
