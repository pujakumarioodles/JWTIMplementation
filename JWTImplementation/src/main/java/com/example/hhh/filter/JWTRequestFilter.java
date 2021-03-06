package com.example.hhh.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hhh.jwt.JwtUTil;
import com.example.hhh.service.MyUserDetailsService;

@Component
public class JWTRequestFilter extends OncePerRequestFilter{

	@Autowired 
	private MyUserDetailsService userDetailsService;
	
	@Autowired 
	private JwtUTil  jwtUTil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader =request.getHeader("Authorization");
		
		System.out.println(authorizationHeader+"pujahhghh______________________________");
		
		String username=null;
		String jwt=null;
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "));
		{
			System.out.println(authorizationHeader+"pujahhghh______________________________");
			
			jwt=authorizationHeader.substring(7);
			username=jwtUTil.extractUsername(jwt);
		}
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			
			if(jwtUTil.validateToken(jwt,userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new 
					UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().
						buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request,response);
		
	}
	
	

}
