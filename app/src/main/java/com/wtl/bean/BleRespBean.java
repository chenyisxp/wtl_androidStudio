package com.wtl.bean;



public class BleRespBean {
	private String dataStatus;
	private String preCurrentNum;
	private String realCurrentNum;
	private String preVoltageNum;
	private String realVoltageNum;
	
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getPreCurrentNum() {
		return preCurrentNum;
	}
	public void setPreCurrentNum(String preCurrentNum) {
		this.preCurrentNum = preCurrentNum;
	}
	public String getRealCurrentNum() {
		return realCurrentNum;
	}
	public void setRealCurrentNum(String realCurrentNum) {
		this.realCurrentNum = realCurrentNum;
	}
	public String getPreVoltageNum() {
		return preVoltageNum;
	}
	public void setPreVoltageNum(String preVoltageNum) {
		this.preVoltageNum = preVoltageNum;
	}
	public String getRealVoltageNum() {
		return realVoltageNum;
	}
	public void setRealVoltageNum(String realVoltageNum) {
		this.realVoltageNum = realVoltageNum;
	}
	@Override
	public String toString() {
		return "{dataStatus:'" + dataStatus + "', preCurrentNum:'"
				+ preCurrentNum + "', realCurrentNum:'" + realCurrentNum
				+ "', preVoltageNum:'" + preVoltageNum + "', realVoltageNum:'"
				+ realVoltageNum + "'}";
	}
}
