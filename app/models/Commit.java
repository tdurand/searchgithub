package models;

import play.*;
import play.db.jpa.Model;

import java.util.*;

public class Commit {
	
	private String message;
	private String id;
	private Date committed_date;
	private Contributor committer;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setCommitted_date(Date committed_date) {
		this.committed_date = committed_date;
	}

	public Date getCommitted_date() {
		return committed_date;
	}

	public void setCommitter(Contributor committer) {
		this.committer = committer;
	}

	public Contributor getCommitter() {
		return committer;
	}

	
	
	
	 
}
