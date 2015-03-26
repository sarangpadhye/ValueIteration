package rl1;

import java.util.ArrayList;
import java.util.HashMap;

public class Action {

	public int dir;
	public Double up_prob;
	public Double down_prob;
	public Double left_prob;
	public Double right_prob;
	ArrayList<Double> validProb = null;

	public Action(int direction, Double u, Double d, Double l, Double r) {
		this.dir = direction;
		this.up_prob = u;
		this.down_prob = d;
		this.left_prob = l;
		this.right_prob = r;
	}

	

}
