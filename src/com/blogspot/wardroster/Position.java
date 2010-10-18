package com.blogspot.wardroster;

public class Position {
	private long id;
	private long sequence;
	private String name;
	private long person;
	private String sustained;
	private String setApart;
	private long organization;
	
	public Position(){
		
	}
	public Position(long id, long sequence, String name, long person, String sustained, String setapart, long organization){
		this.id = id;
		this.sequence = sequence;
		this.name = name;
		this.person = person;
		this.sustained = sustained;
		this.setApart = setapart;
		this.organization = organization;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getPerson() {
		return person;
	}
	public void setPerson(long person) {
		this.person = person;
	}
	public String getSustained() {
		return sustained;
	}
	public void setSustained(String sustained) {
		this.sustained = sustained;
	}
	public String getSetApart() {
		return setApart;
	}
	public void setSetApart(String setApart) {
		this.setApart = setApart;
	}
	public long getOrganization() {
		return organization;
	}
	public void setOrganization(long organization) {
		this.organization = organization;
	}

}
