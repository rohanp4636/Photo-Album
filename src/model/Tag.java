package model;

import java.util.ArrayList;

public class Tag {
	String tagType;
	ArrayList<String> tagValue;
	
	public Tag(String name){
		tagType = name;
		tagValue = new ArrayList<String>();
	}
	
	public Boolean addTag(String name){
		if(tagValue.contains(name.toLowerCase())){
			return false;
		}
		tagValue.add(name.toLowerCase());
		return true;
		
	}
	
	public Boolean deleteTag(String name){
		for(String i: tagValue){
			if(i.equals(name.toLowerCase())){
				tagValue.remove(i);
				return true;
			}
		}
		return false;
	}
	
}

