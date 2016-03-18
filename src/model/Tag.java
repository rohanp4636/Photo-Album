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
		if(tagValue.contains(name.toUpperCase())){
			return false;
		}
		tagValue.add(name.toUpperCase());
		return true;
		
	}
	
	public Boolean deleteTag(String name){
		for(String i: tagValue){
			if(i.equals(name.toUpperCase())){
				tagValue.remove(i);
				return true;
			}
		}
		return false;
	}
	
}

