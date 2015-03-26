package rl1;

public class State {

	private String type;
	private int reward;
	private Double Value;
	private Action max_action;
	public int row_pos,col_pos;
	
	


	public State(int i, int j, int k, double d, Action max_action2, String string) {
		// TODO Auto-generated constructor stub
		this.row_pos = i;
		this.col_pos = j;
		this.setMax_action(max_action2);
		this.setReward(k);
		this.setValue(d);
		this.setType(string);
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getValue() {
		return Value;
	}

	public void setValue(Double value) {
		Value = value;
	}

	public Action getMax_action() {
		return max_action;
	}

	public void setMax_action(Action max_action) {
		this.max_action = max_action;
	}

}
