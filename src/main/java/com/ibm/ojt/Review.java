package com.ibm.ojt;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Review {
	@Id
	private String _id = new ObjectId().toHexString();
	private String userid;
	private String username;
	private String reviewstring;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
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
