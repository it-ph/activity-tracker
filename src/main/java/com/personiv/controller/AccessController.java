package com.personiv.controller;

import com.personiv.model.User;
import com.personiv.security.JwtAuthenticationResponse;
import com.personiv.service.CustomUserDetailsService;
import com.personiv.utils.JwtTokenUtil;
import java.security.Principal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AccessController
{
  private final Log logger = LogFactory.getLog(getClass());
  @Autowired
  private CustomUserDetailsService userDetailsService;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @RequestMapping({"/user-claims"})
  public Principal getPrincipal(Principal principal)
  {
    return principal;
  }
  
  @RequestMapping(path={"/authenticate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, consumes={"application/json"}, produces={"application/json"})
  public ResponseEntity<?> createAuthenticationToken(@RequestBody User user, Device device)
    throws AuthenticationException
  {
    this.logger.info("Reading USERNAME:  " + user.getUsername());
    
    Authentication authentication = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
      user.getUsername(), 
      user.getPassword()));
    
    this.logger.info(authentication.getCredentials());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
    
    String token = this.jwtTokenUtil.generateToken(userDetails, device);
    Date expiration = this.jwtTokenUtil.getExpirationDateFromToken(token);
    
    return ResponseEntity.ok(new JwtAuthenticationResponse(token, expiration, "bearer"));
  }
  
  @RequestMapping(value={"/refresh-token"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request)
  {
    String requestHeader = request.getHeader("Authorization");
    if ((requestHeader != null) && (requestHeader.startsWith("Bearer ")))
    {
      String token = requestHeader.substring(7);
      if (this.jwtTokenUtil.canTokenBeRefreshed(token).booleanValue())
      {
        String refreshedToken = this.jwtTokenUtil.refreshToken(token);
        
        Date expiration = this.jwtTokenUtil.getExpirationDateFromToken(refreshedToken);
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken, expiration, "bearer"));
      }
      return ResponseEntity.badRequest().body(null);
    }
    return ResponseEntity.badRequest().body(null);
  }
}
