package com.neusoft.elmboot.po;

public class Integration {

	private Integer integrationId;
	private String userId;
	private Integer points;
	private Integer usedPoints;
	private String integrationDate;
	private Integer integrationState;
	
	public Integer getUsedPoints() {
		return usedPoints;
	}
	
	public void setUsedPoints(Integer usedPoints) {
		this.usedPoints = usedPoints;
	}

	public Integer getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(Integer integrationId) {
		this.integrationId = integrationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getIntegrationDate() {
		return integrationDate;
	}

	public void setIntegrationDate(String integrationDate) {
		this.integrationDate = integrationDate;
	}

	public Integer getIntegrationState() {
		return integrationState;
	}

	public void setIntegrationState(Integer integrationState) {
		this.integrationState = integrationState;
	}

}
