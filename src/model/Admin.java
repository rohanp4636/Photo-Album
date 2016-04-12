package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Admin Class - defines attributes of the admin. 
 */
public class Admin implements Serializable{
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	String userName = "admin";
	
	/** The user list. */
	ArrayList<User> userList = new ArrayList<User>();
	
	/** The Constant storeDir. */
	public static final String storeDir = "data";
	
	/** The Constant storeFile. */
	public static final String storeFile = "userData.dat";
	
	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public ArrayList<User> getUsers(){
		return userList;
	}
	
	/**
	 * Delete user.
	 *
	 * @param name the user delete
	 * @return the isdeleted
	 */
	public Boolean deleteUser(String name){
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).userName.equals(name)){
				userList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Write the admin.
	 *
	 * @param admin the admin object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeAdmin(Admin admin) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir+File.separator+storeFile));
		oos.writeObject(admin);
		oos.close();
	}
	
	/**
	 * Read admin.
	 *
	 * @return the admin and all the stuff from userData.dat
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Admin readAdmin() throws IOException, ClassNotFoundException{
		File file = new File("data/userData.dat");
		if(!file.exists()){
			return new Admin();
		}
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir+File.separator+storeFile));
		Admin admin = (Admin) ois.readObject();
		ois.close();
		return admin;
	}
	
	
	
}
