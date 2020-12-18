/**
 * 
 */
package cn.cf.entry;

import org.springframework.data.annotation.Id;

/**
 * @author bin
 * 
 */
public class RegionsMamge {
	@Id
	private String id;
	private String getRegionsNumber;
	private String content;
	private String insertTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGetRegionsNumber() {
		return getRegionsNumber;
	}

	public void setGetRegionsNumber(String getRegionsNumber) {
		this.getRegionsNumber = getRegionsNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	
	
}
