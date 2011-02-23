package models;

import play.*;
import play.db.jpa.Model;

import java.util.*;

public class Contributors {
	
	private List<Contributor> contributors;

	public void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}

	public List<Contributor> getContributors() {
		return contributors;
	}

	 
	
	 
}
