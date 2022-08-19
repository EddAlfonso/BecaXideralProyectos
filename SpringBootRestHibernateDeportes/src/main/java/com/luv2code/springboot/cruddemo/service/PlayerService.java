package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Players;

public interface PlayerService {

		public List<Players> findAll();
		
		public Players findById(int theId);
		
		public void save(Players thePlayer);
		
		public void deleteById(int theId);
	
}
