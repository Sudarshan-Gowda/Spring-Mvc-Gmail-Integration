# Gmail-integration with Spring mvc

## Steps to integrate your Application with Gmail 

`Step 1` : Dowmload this repository & do maven import.         <br>                                                                     `Step 2` : Give ur `client id` & `client secret` (which is created in developer side) in LoginController.Java.<br>
`Step3 ` : Add the server and Run this Application. Once the project is successfully deployed you will be redirected to login page with Facebook Icon.   <br>                                                                                                     
`Step4 ` : Once you Clicked on the Gmail Icon, you will be redirected to Gmail Login Page. Enter your Gmail account credential or any Gmail account credential. If the credential is valid you will be redicted to welcome page with logged-in User details like `First Name` , `Last Name`, `Email Address`, etc..                                                           

## Steps to create Credential(tokens) in Gmail developer Site.

`Step 1`: Hit the URL in any browser `https://console.developers.google.com`.                                                                    
`Step 2`: Create new Project                                                                        

`Step 3`: Go to Credentail -> OAuth Consent screen -> Product Name & Save

`Step 4`: Credentail -> create credentails -> OAuth Client Id -> web application -> 
	    give authorized Js as [Ex (http://localhost:8085)] & redirect URI as [Ex:(http://localhost:8085/genie/gdb/google/result)]
              
`Step 5`: you will get a Id & Secret on the Screen. Note down that and use it for Gmail Integration with your Application.                                                                                                                

## Technology Used:    
  Java 8                                                                                                                                
  Spring MVC                                                                                                                             
                                                                                                                
  
 ## Tools Used
 Eclipse - Mars                                                                                                                        
 Sevrver - Wildfly 10                                                                                                                   

<img src="https://github.com/Sudarshan-Gowda/Spring-Mvc-Gmail-Integration/blob/master/docs/picture1.png"/>
