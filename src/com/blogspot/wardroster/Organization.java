package com.blogspot.wardroster;

public class Organization {
	private long id;
	private long sequence;
	private String name;
	
	public Organization(){
		
		
	}
	
	public Organization(long id, long sequence, String name ){
		this.id = id;
		this.sequence = sequence;
		this.name = name;
	}
	
	public long getSequence() {
		return sequence;
	}
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
