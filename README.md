# Description

This Project Uses the Customized Authentication Providers to achieve MultiFactor Authentication and generates a valid JWT upon the validation

# Working

* Step 1 : The user has to register (Users are stored in the local DB)
* Step 2 : The User has to provide the credentials which is validated by the UserDetailsService of Spring Security. If the user provides valid credentials
           it will generate a record for the OTP in the OTP Table (Generally it has to be sent as an SMS)
* Step 3 : The User has to provide the username and the OTP generated in the OTP table for the verification of the OTP. This uses a UsernamePasswordAuthentication.
* Step 4 : If these credentials are Valid then the JWT is generated and is send as the response Headers which is then submitted in the request Headers for the subsequent 
           requests to access the end points.
