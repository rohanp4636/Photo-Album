package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
	String tagType;
	ArrayList<String> tagValue;
	
	public Tag(String name){
		tagType = name;
		tagValue = new ArrayList<String>();
	}
	
	public ArrayList<String> getValue(){
		return tagValue;
	}
	
	public Boolean addTag(String name){
		for(String i : tagValue){
			if(i.equalsIgnoreCase(name)){
				return false;
			}
		}
		tagValue.add(name);
		return true;
		
	}
	
	public Boolean deleteTag(String name){
		for(String i: tagValue){
			if(i.equalsIgnoreCase(name)){
				tagValue.remove(i);
				return true;
			}
		}
		return false;
	}

	public String toString(){
		return tagType;
	}
}

