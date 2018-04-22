package org.wxy.express.model;

public class ProgressInfo {
	private int model = 1;  // 默认值1
	private int maximum = 100;
	private int currentValue = 0;
	
	public ProgressInfo(){}
	
	/**
	 * @param 1: 百分数   2: 比数
	 */
	public ProgressInfo(int model){
		this.model = model;
	}
	
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	public int getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}
	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public String getString() {
		String string = "";
		switch(model) {
		case 1:
			string = Math.floor(currentValue * 100 / maximum) + "%"; 
			break;  
		case 2:  
			string = currentValue + "/" + maximum;
			break;
		default:
			
			break;
		}
		
		return string;
	}

	
}
