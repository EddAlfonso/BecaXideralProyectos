package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Players;

@Repository
public class PlayersDAOHibernateImpl implements PlayersDAO {

		// define field for entitymanager	
		private EntityManager entityManager;
			
		// set up constructor injection
		@Autowired
		public PlayersDAOHibernateImpl(EntityManager theEntityManager) {
				entityManager = theEntityManager;
		}
	
	
		@Override
		public List<Players> findAll() {
	
				// get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);
				
				// create a query
				Query<Players> theQuery =
						currentSession.createQuery("from Players", Players.class);
				
				// execute query and get result list
				List<Players> players = theQuery.getResultList();
				
				// return the results		
				return players;
		}
	
		@Override
		public Players findById(int theId) {
	
				// get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);
				
				// get the employee
				Players thePlayer =
						currentSession.get(Players.class, theId);
				
				// return the employee
				return thePlayer;
				
		}
	
		@Override
		public void save(Players thePlayer) {
	
				// get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);
				
				// save employee
				currentSession.saveOrUpdate(thePlayer);
		}
	
		@Override
		public void deleteById(int theId) {
			
				// get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);
						
				// delete object with primary key
				Query theQuery = 
						currentSession.createQuery(
								"delete from Player where id=:playerId");
				theQuery.setParameter("playerId", theId);
				
				theQuery.executeUpdate();
				
		}

}







