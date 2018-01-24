package com.ibm.ojt;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Review {
	@Id
	private ObjectId _id = new ObjectId();
	private String userid;
	private String username;
	private String reviewstring;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getReviewstring() {
		return reviewstring;
	}
	public void setReviewstring(String reviewstring) {
		this.reviewstring = reviewstring;
	}
}
