package com.example.hhh.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hhh.jwt.JwtUTil;
import com.example.hhh.model.AuthenticationRequest;
import com.example.hhh.service.MyUserDetailsService;

@RestController
public class JWTController {
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired 
	private MyUserDetailsService userDetailsService;
	
	@Autowired 
	private JwtUTil  jwtUTil;
	
	@GetMapping("/hello")
	public String hello()
	{
		return "puja is the great coder";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?>  createAuthenticationToken(@RequestBody AuthenticationRequest
			authenticationRequest)throws Exception
	{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(authenticationRequest.getUsername(),authenticationRequest.getPassword()));	
		}
		catch(BadCredentialsException e)
		{
			throw new Exception("Incorrect username and password",e);
		}
	final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	 
	final String jwt=jwtUTil.generateToken(userDetails);
	
	return ResponseEntity.ok(jwt);
	}

	
}
