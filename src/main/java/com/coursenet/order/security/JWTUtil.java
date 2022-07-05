package com.coursenet.order.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTUtil {
	@Autowired
	private Algorithm algorithm;
	
	public DecodedJWT decodeJWTToken(String jwtToken) throws Exception{
		JWTVerifier verifier = JWT.require(algorithm).build();
		try{
			return verifier.verify(jwtToken.replace("Bearer ", ""));
		}catch(Exception e) {
			
		}
		return null;
	}
}
