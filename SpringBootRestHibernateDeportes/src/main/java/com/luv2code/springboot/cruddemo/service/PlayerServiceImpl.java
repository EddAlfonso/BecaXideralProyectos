package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.cruddemo.dao.PlayersDAO;
import com.luv2code.springboot.cruddemo.entity.Players;

@Service
public class PlayerServiceImpl implements PlayerService {

		private PlayersDAO playerDAO;
		
		@Autowired
		public PlayerServiceImpl(@Qualifier("playersDAOJdbcImpl") PlayersDAO thePlayerDAO) {
				playerDAO = thePlayerDAO;
		}
		
		@Override
		@Transactional
		public List<Players> findAll() {
				return playerDAO.findAll();
		}
	
		@Override
		@Transactional
		public Players findById(int theId) {
				return playerDAO.findById(theId);
		}
	
		@Override
		@Transactional
		public void save(Players thePlayer) {
				playerDAO.save(thePlayer);
		}
	
		@Override
		@Transactional
		public void deleteById(int theId) {
				playerDAO.deleteById(theId);
		}

}






