package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Players;
import com.luv2code.springboot.cruddemo.service.PlayerService;

@RestController
@RequestMapping("/api")
public class PlayerRestController {

		private PlayerService playerService;
		
		@Autowired
		public PlayerRestController(PlayerService thePlayerService) {
				playerService = thePlayerService;
		}
		
		// expose "/players" and return list of players
		@GetMapping("/players")
		public List<Players> findAll() {
				return playerService.findAll();
		}
	
		// add mapping for GET /players/{playerId}
		
		@GetMapping("/players/{playerId}")
		public Players getPlayer(@PathVariable int playerId) {
			
				Players thePlayer = playerService.findById(playerId);
				
				if (thePlayer == null) {
					throw new RuntimeException("Player id not found - " + playerId);
				}
				
				return thePlayer;
		}
		
		// add mapping for POST /players - add new players
		
		@PostMapping("/players")
		public Players addPlayer(@RequestBody Players thePlayer) {
			
				// also just in case they pass an id in JSON ... set id to 0
				// this is to force a save of new item ... instead of update
				
				thePlayer.setId(0);
				
				playerService.save(thePlayer);
				
				return thePlayer;
		}
		
		// add mapping for PUT /players - update existing player
		
		@PutMapping("/players")
		public Players updatePlayer(@RequestBody Players thePlayer) {
			
				playerService.save(thePlayer);
				
				return thePlayer;
		}
		
		// add mapping for DELETE /players/{playerid} - delete player
		
		@DeleteMapping("/players/{playerId}")
		public String deletePlayer(@PathVariable int playerId) {
			
				Players tempPlayer = playerService.findById(playerId);
				
				// throw exception if null
				
				if (tempPlayer == null) {
						throw new RuntimeException("Player id not found - " + playerId);
				}
				
				playerService.deleteById(playerId);
				
				return "Deleted player id - " + playerId;
			}
	
}










