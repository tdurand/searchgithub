package models;

import play.*;
import play.db.jpa.Model;

import java.util.*;

public class Commits {
	
	private List<Commit> commits;

	public void setCommits(List<Commit> commits) {
		this.commits = commits;
	}

	public List<Commit> getCommits() {
		return commits;
	}

			

	 
	
	 
}
