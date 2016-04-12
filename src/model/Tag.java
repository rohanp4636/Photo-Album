package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tag Class - defines attributes of the Tag. 
 */
public class Tag implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The tag type. */
	String tagType;
	
	/** The tag value. */
	ArrayList<String> tagValue;
	
	/**
	 * Instantiates a new tag.
	 *
	 * @param name tag type
	 */
	public Tag(String name){
		tagType = name;
		tagValue = new ArrayList<String>();
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType(){
		return tagType;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public ArrayList<String> getValue(){
		return tagValue;
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param name of tag
	 * @return boolean if added or not. 
	 */
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
	
	/**
	 * Delete tag.
	 *
	 * @param name of tag
	 * @return boolean if tag added or not. 
	 */
	public Boolean deleteTag(String name){
		for(String i: tagValue){
			if(i.equalsIgnoreCase(name)){
				tagValue.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * To String - Tag
	 */
	public String toString(){
		return tagType;
	}
}

