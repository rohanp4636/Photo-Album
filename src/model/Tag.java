package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String tagType;
	ArrayList<String> tagValue;
	
	public Tag(String name){
		tagType = name;
		tagValue = new ArrayList<String>();
	}
	
	public String getType(){
		return tagType;
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
		if(tagValue.isEmpty()){
			tagValue.add(name);
			return true;
		}
		else{
			for(int i = 0;  i < tagValue.size(); i ++){
				if(tagValue.get(i).compareToIgnoreCase(name) > 0 ){
					tagValue.add(i,name);
					return true;
				}
				else if(tagValue.size()-1 == i && tagValue.get(i).compareToIgnoreCase(name) < 0){
					tagValue.add(name);
					return true;
				}
			}
		}
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

