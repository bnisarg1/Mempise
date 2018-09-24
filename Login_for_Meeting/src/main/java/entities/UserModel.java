package entities;

import javax.persistence.*;
import java.util.Set;


public class UserModel {
    private String username;
    private String email;
    private String passwords;
    
    //Getter and Setter Method
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	
	//Display
	@Override
	public String toString() {
		return "UserModel [username=" + username + ", email=" + email + ", passwords=" + passwords + "]";
	}
    
    
}
    
    
    

   

