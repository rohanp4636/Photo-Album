package model;

import java.util.ArrayList;

public class Admin {
	
	String userName = "admin";
	
	ArrayList<User> userList = new ArrayList<User>();
	
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
	
}
