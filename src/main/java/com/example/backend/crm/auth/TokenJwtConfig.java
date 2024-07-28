package com.example.backend.crm.auth;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class TokenJwtConfig {

    public final static SecretKey SECRECT_KEY = Jwts.SIG.HS256.key().build();
    //public final static Key SECRECT_KEY = Jwts.SIG.HS256.key().build();
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String HEADER_AUTHORIZATION = "Authorization";



}
