package models;

import play.*;
import play.db.jpa.Model;

import java.util.*;

public class Repository {

    
    private String description;
	private String url;
	private Date created_at;
	private String name;
	private String owner;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner() {
		return owner;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getCreated_at() {
		return created_at;
	}
	
	
	@Override
	public String toString() {
		return "Repository [description=" + description + ", url=" + url + "]";
	}
	

}
