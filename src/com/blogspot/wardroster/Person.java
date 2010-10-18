package com.blogspot.wardroster;

public class Person {
	
	private long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private long age;
	private String gender;
	private String preferredName;
	
	private long teachingFamily1;
	private long teachingFamily2;
	private long teachingFamily3;
	private long teachingFamily4;
	
	private long sort;
	
	private long family;
	
	private String birth;
	private String baptized;
	private String confirmed;
	private String endowed;
	
	private String priesthood;
	private String mission;
	
	private String membershipNumber;
	private String phoneNumber;
	private String email;
	private String recommendExpiration;
	private String marriageStatus;
	
	private String spouseMember;
	private String sealedToSpouse;
	
	private String position;
	private long visitingTeacher1;
	private long visitingTeacher2;
	
	public Person(){
		
		
	}
	
	public Person(long id){
		
		
	}
	
	public Person(long id, String firstName, String middleName, String lastName, long age, String gender, long tf1, long tf2, long tf3, long tf4, String birth, String baptized, String confirmed, String endowed, String priesthood, String mission, String membershipnumber, String phoneNumber, String email, String recommendExpiration, String marriageStatus, String spouseMember, String sealedToSpouse, String preferredName, long sort, long family, long visitingTeacher1, long visitingTeacher2){
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.teachingFamily1 = tf1;
		this.teachingFamily2 = tf2;
		this.teachingFamily3 = tf3;
		this.teachingFamily4 = tf4;
		this.birth = birth;
		this.confirmed = confirmed;
		this.endowed = endowed;
		this.priesthood = priesthood;
		this.mission = mission;
		this.baptized = baptized;
		this.membershipNumber = membershipnumber;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.recommendExpiration = recommendExpiration;
		this.marriageStatus = marriageStatus;
		this.spouseMember = spouseMember;
		this.sealedToSpouse = sealedToSpouse;
		this.preferredName = preferredName;
		this.sort = sort;
		this.family = family;
		this.visitingTeacher1 = visitingTeacher1;
		this.visitingTeacher2 = visitingTeacher2;
	}  
	
	public Person (long id, String position, String first, String last, long family, long sort){
		this.id = id;
		this.position = position;
		this.firstName = first;
		this.lastName = last;
		this.family = family;
		this.sort = sort;
	}

	public Person (long id, String first, String last){
		this.id = id;
		this.firstName = first;
		this.lastName = last;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public long getAge() {
		return age;
	}


	public void setAge(long age) {
		this.age = age;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public long getTeachingFamily1() {
		return teachingFamily1;
	}


	public void setTeachingFamily1(long teachingFamily1) {
		this.teachingFamily1 = teachingFamily1;
	}


	public long getTeachingFamily2() {
		return teachingFamily2;
	}


	public void setTeachingFamily2(long teachingFamily2) {
		this.teachingFamily2 = teachingFamily2;
	}


	public long getTeachingFamily3() {
		return teachingFamily3;
	}


	public void setTeachingFamily3(long teachingFamily3) {
		this.teachingFamily3 = teachingFamily3;
	}


	public long getTeachingFamily4() {
		return teachingFamily4;
	}


	public void setTeachingFamily4(long teachingFamily4) {
		this.teachingFamily4 = teachingFamily4;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}

	public long getFamily() {
		return family;
	}

	public void setFamily(long family) {
		this.family = family;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getBaptized() {
		return baptized;
	}

	public void setBaptized(String baptized) {
		this.baptized = baptized;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getEndowed() {
		return endowed;
	}

	public void setEndowed(String endowed) {
		this.endowed = endowed;
	}

	public String getPriesthood() {
		return priesthood;
	}

	public void setPriesthood(String priesthood) {
		this.priesthood = priesthood;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecommendExpiration() {
		return recommendExpiration;
	}

	public void setRecommendExpiration(String recommendExpiration) {
		this.recommendExpiration = recommendExpiration;
	}

	public String getMarriageStatus() {
		return marriageStatus;
	}

	public void setMarriageStatus(String marriageStatus) {
		this.marriageStatus = marriageStatus;
	}

	public String getSpouseMember() {
		return spouseMember;
	}

	public void setSpouseMember(String spouseMember) {
		this.spouseMember = spouseMember;
	}

	public String getSealedToSpouse() {
		return sealedToSpouse;
	}

	public void setSealedToSpouse(String sealedToSpouse) {
		this.sealedToSpouse = sealedToSpouse;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPreferredName() {
		return preferredName;
	}

	public void setPreferredName(String preferredName) {
		this.preferredName = preferredName;
	}

	public long getVisitingTeacher1() {
		return visitingTeacher1;
	}

	public void setVisitingTeacher1(long visitingTeacher1) {
		this.visitingTeacher1 = visitingTeacher1;
	}

	public long getVisitingTeacher2() {
		return visitingTeacher2;
	}

	public void setVisitingTeacher2(long visitingTeacher2) {
		this.visitingTeacher2 = visitingTeacher2;
	}

}
