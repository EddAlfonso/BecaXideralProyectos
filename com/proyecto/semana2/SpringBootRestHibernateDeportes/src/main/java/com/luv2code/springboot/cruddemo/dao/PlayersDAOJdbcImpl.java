package com.luv2code.springboot.cruddemo.dao;

import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.luv2code.springboot.cruddemo.entity.Players;

@Repository
public class PlayersDAOJdbcImpl implements PlayersDAO {

		@Autowired
		DataSource dataSource;
	
		@Override
		public List<Players> findAll() {
				
				List<Players> listaPlayers = new ArrayList<>();
				
				try (Connection myConn=dataSource.getConnection(); Statement myStmt = myConn.createStatement(); 
											ResultSet myRs = myStmt.executeQuery("select * from players order by id")){
					
							// process result set
							while (myRs.next()) {
									// retrieve data from result set row
									int id = myRs.getInt("id");
									String firstName = myRs.getString("first_name");
									String lastName = myRs.getString("last_name");
									String email = myRs.getString("email");
									String sport = myRs.getString("sport");
									// create new player object
									Players tempPlayer = new Players(id, firstName, lastName, email, sport);
									// add it to the list of players
									listaPlayers.add(tempPlayer);				
							}	
							
						} catch (SQLException ex) {
							    System.out.println("An error occurred.");
							    ex.printStackTrace();
						}
				
				return listaPlayers;
		}
	
		@Override
		public Players findById(int theId) {
			
				Players thePlayer = null;
				int playerId;
				
				try (Connection myConn=dataSource.getConnection(); 
						PreparedStatement myStmt = myConn.prepareStatement("select  from players where id=?")) {
								// set params
								myStmt.setInt(1, theId);
								// retrieve data from result set row
								try(ResultSet myRs = myStmt.executeQuery();){
								
										if (myRs.next()) {
												String firstName = myRs.getString("first_name");
												String lastName = myRs.getString("last_name");
												String email = myRs.getString("email");
												String sport = myRs.getString("sport");
												// use the playerId during construction
												thePlayer = new Players(theId, firstName, lastName, email, sport);
										}	
								}
				} catch (SQLException ex) {
						System.out.println("An error occurred.");
						ex.printStackTrace();
				}
				
				return thePlayer;
		}
	
		@Override
		public void save(Players thePlayer) {
			
			if (thePlayer.getId()==0 ) {
				
					try(Connection myConn=dataSource.getConnection(); PreparedStatement myStmt = 
							myConn.prepareStatement("insert into players"+" (first_name, last_name, email, sport)"+" values (?, ?, ?, ?)"); ){
							// set the param values for the player
							myStmt.setString(1, thePlayer.getFirstName());
							myStmt.setString(2, thePlayer.getLastName());
							myStmt.setString(3, thePlayer.getEmail());
							myStmt.setString(4, thePlayer.getSport());
							// execute sql insert
							myStmt.execute();
						
				} catch (SQLException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				} 
				
			} else {
				
					try(Connection myConn=dataSource.getConnection(); PreparedStatement myStmt = 
							myConn.prepareStatement("update players set first_name=?, last_name=?, email=?, sport=? where id=?"); ){
					// set params
								myStmt.setString(1, thePlayer.getFirstName());
								myStmt.setString(2, thePlayer.getLastName());
								myStmt.setString(3, thePlayer.getEmail());
								myStmt.setString(4, thePlayer.getSport());
								myStmt.setInt(5, thePlayer.getId());
								// execute SQL statement
								myStmt.execute();
								
				} catch (SQLException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
				}
			}
		}
	
		@Override
		public void deleteById(int theId) {
			
			try(Connection myConn=dataSource.getConnection(); 
					PreparedStatement myStmt = myConn.prepareStatement("delete from players where id=?")){
			
						// set params
						myStmt.setInt(1, theId);
						// execute sql statement
						myStmt.execute();
							
			} catch (SQLException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
			}
		}

}
