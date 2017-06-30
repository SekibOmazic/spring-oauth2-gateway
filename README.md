# spring-oauth2-gateway

Start all 4 applications and point the browser to [http://localhost:8080](http://localhost:8080) Click on user link and after login as admin try to add a new user. The error shown is:

`There was an unexpected error (type=Forbidden, status=403).
 Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-XSRF-TOKEN'.`
 
 If you disable CSRF in gateway like this:
 
 ```
 @Override
 public void configure(HttpSecurity http) throws Exception {
   http
     .logout().and()
     ...
     .csrf().disable();
 }
 ```
 it will work fine.
 
 