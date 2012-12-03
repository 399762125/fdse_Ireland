package cn.edu.fudan.software.entity;

public class Album {

	private int id;
	private String title;
	private String introduction;
	private String creatTime;
	private String firstPageURL;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getFirstPageURL() {
		return firstPageURL;
	}

	public void setFirstPageURL(String firstPageURL) {
		this.firstPageURL = firstPageURL;
	}

}
