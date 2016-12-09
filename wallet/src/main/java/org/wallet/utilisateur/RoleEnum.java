package org.wallet.utilisateur;

public enum RoleEnum {
	
	ADMIN("ADMIN"),
	AGREGATEUR("AGREGATEUR"),
	LICENCIER("LICENCIER"),
	AGENT("AGENT"),
	CAISSE("CAISSE"),
	CLIENT("CLIENT");
	
	private String role ;  
    
    private RoleEnum(String role) {  
        this.role = role ;  
   }  
     
    public String getRole() {  
        return  this.role ;  
   } 
}
