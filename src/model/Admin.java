package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Admin implements Serializable{
	
	String userName = "admin";
	
	ArrayList<User> userList = new ArrayList<User>();
	
	public static final String storeDir = "data";
	public static final String storeFile = "userData.dat";
	
	public ArrayList<User> getUsers(){
		return userList;
	}
	
	public Boolean deleteUser(String name){
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).userName.equals(name)){
				userList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static void writeAdmin(Admin admin) throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storeDir+File.separator+storeFile));
		oos.writeObject(admin);
	}
	
	public static Admin readAdmin() throws IOException, ClassNotFoundException{
		File file = new File("data/userData.dat");
		if(!file.exists()){
			return new Admin();
		}
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storeDir+File.separator+storeFile));
		Admin admin = (Admin) ois.readObject();
		return admin;
	}
	
	
	
}
