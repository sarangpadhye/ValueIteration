package rl1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class MDP {

	private State[][] grid;
	private int m_row, m_col;
	private HashMap<State, ArrayList<Integer>> StateAction = new HashMap<State, ArrayList<Integer>>();
	private ArrayList<Integer> Actions = null;
	private ArrayList<Action> AllActions = null;
	private Vector<State> valid_states;

	static final int ACTION_UP = 0;
	static final int ACTION_DOWN = 1;
	static final int ACTION_LEFT = 2;
	static final int ACTION_RIGHT = 3;

	public MDP(int row, int col) {

		Action max_action = null;
		HashMap<Integer, Double> temp = new HashMap<Integer, Double>();
		// Save the Grid indexes
		this.m_row = row;
		this.m_col = col;

		// Create Grid
		grid = new State[m_row][m_col];

		// Set the Action
		Actions = new ArrayList<Integer>();
		Actions.add(ACTION_UP);
		Actions.add(ACTION_DOWN);
		Actions.add(ACTION_LEFT);
		Actions.add(ACTION_RIGHT);

		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				grid[i][j] = new State(i, j, 0, 0.0, max_action, "Undecided");

			}
		}

		// Initialize the Actions

		AllActions = new ArrayList<Action>();

		AllActions.add(new Action(0, 0.8, 0.2, 0.0, 0.0));
		AllActions.add(new Action(1, 0.0, 1.0, 0.0, 0.0));
		AllActions.add(new Action(2, 0.0, 0.0, 1.0, 0.0));
		AllActions.add(new Action(3, 0.0, 0.2, 0.0, 0.8));

		System.out.println();

	}

	public void printall() {

		System.out.println(m_row);
		System.out.println(m_col);
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				System.out.println("State(" + i + "," + j + ")");
				// System.out.println(grid[i][j].getReward());
				// System.out.println(grid[i][j].getValue());
				// System.out.println(grid[i][j].getType());
				System.out.println(grid[i][j].getMax_action());
			}
		}
	}

	public void printRewards() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				System.out.print(grid[i][j].getReward() + "\t");

			}
			System.out.println();
		}
	}

	public void printState() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				System.out.print(grid[i][j].getType() + "\t");

			}
			System.out.println();
		}
	}

	public void printValue() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				System.out.print(String.format("%.1f",grid[i][j].getValue()) + "\t");

			}
			System.out.println();
		}
	}

	public void printMaxAction() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {

				int action;
				String take_action = null;
				Action A = grid[i][j].getMax_action();
				if (A == null) {
					action = -1;
				} else
					action = A.dir;

				if (action == 0)
					take_action = "UP";
				else if (action == 1)
					take_action = "DOWN";
				else if (action == 2)
					take_action = "LEFT";
				else if (action == 3)
					take_action = "RIGHT";
				else if (action == -1)
					take_action = "NA";
				if (i == 0 && j == 3)
					take_action = "STAY";

				System.out.print(take_action + "\t");

			}
			System.out.println();
		}
	}

	public void decideStates() {
		// TODO Auto-generated method stub
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				// Set the Start State
				if (i == 4 && j == 0)
					grid[i][j].setType("S");
				// Set the Goal State
				else if (i == 0 && j == 3)
					grid[i][j].setType("G");

				// Set the Unreachable states
				else if (i == 3 && j == 1)

					grid[i][j].setType("UR");

				else if (i == 3 && j == 3)

					grid[i][j].setType("UR");

				else if (i == 1 && j == 1)
					grid[i][j].setType("P");

				else
					// Set rest Terminal states
					grid[i][j].setType("T");
			}

		}
	}

	// Reward Info
	// -1.0 for Terminal States
	// 0 for Unreacheable States
	// +10 for the Goal state
	// -50 for the Pit

	public void setZeroRewards() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if(i==0 && j ==3)
				grid[i][j].setReward(10);
				else if(i == 1 && j == 1)
					grid[i][j].setReward(-50);
				else
					grid[i][j].setReward(0);
			}

		}
	}

	public void setGivenRewards() {
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (i == 4 && j == 0)
					grid[i][j].setReward(-1);

				// Set the Goal State
				else if (i == 0 && j == 3)
					grid[i][j].setReward(10);

				// Set the Unreachable states
				else if (i == 3 && j == 1)

					grid[i][j].setReward(0);

				else if (i == 3 && j == 3)

					grid[i][j].setReward(0);

				else if (i == 1 && j == 1)
					grid[i][j].setReward(-50);

				else
					// Set rest Terminal states
					grid[i][j].setReward(-1);

			}
		}

	}

	public void set_reachableStates() {
		State s;
		valid_states = new Vector<State>();
		for (int i = 0; i < m_row; i++) {
			for (int j = 0; j < m_col; j++) {
				if (grid[i][j].getType().equals("UR"))
					continue;
				else {
					s = grid[i][j];
					valid_states.addElement(s);
				}
			}
		}

	}

	public Vector<State> get_reachableState() {
		return valid_states;

	}

	public void set_StateAction() {

		for (int i = 0; i < valid_states.size(); i++) {
			if (!StateAction.containsKey(valid_states.get(i))) {
				StateAction.put(valid_states.get(i), Actions);
			}

		}

	}

	public HashMap<State, ArrayList<Integer>> get_StateAction() {

		return StateAction;

	}

	public ArrayList<Action> get_Actions() {
		return AllActions;
	}

	public State get_nextState(State S, Action A, int dir) {
		State nextState = null;

		// get row and col coordinate of the current state

		int row, col;

		row = S.row_pos;
		col = S.col_pos;

		// if up is the action
		if (dir == 0) {
			if (row == 0)
				nextState = null;
			else {
				nextState = grid[row - 1][col];
				if (nextState.getType().equals("UR"))
					nextState = null;
			}
			// if down is the action
		} else if (dir == 1) {
			if (row == 4)
				nextState = null;
			else {
				nextState = grid[row + 1][col];
				// ignore the unreachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}
			// if left is the action
		} else if (dir == 2) {
			if (col == 0)
				nextState = null;
			else {
				nextState = grid[row][col - 1];
				// ignore the unreachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}

			// if right is the action
		} else if (dir == 3) {
			if (col == 3)
				nextState = null;
			else {
				nextState = grid[row][col + 1];
				// ignore the ureachable state
				if (nextState.getType().equals("UR"))
					nextState = null;
			}

		}

		return nextState;
	}

	public HashMap<Integer, Double> get_validProbOnly(Action A) {
		// HashMap<Integer, HashMap<Integer, Double>> FinalMap = new
		// HashMap<Integer, HashMap<Integer, Double>>();
		HashMap<Integer, Double> validProb = new HashMap<Integer, Double>();
		if (A.up_prob != 0.0) {
			validProb.put(0, A.up_prob);
		}
		if (A.down_prob != 0.0) {

			validProb.put(1, A.down_prob);
			// FinalMap.put(dir, validProb);
		}
		if (A.left_prob != 0.0) {

			validProb.put(2, A.left_prob);
			// FinalMap.put(dir, validProb);
		}
		if (A.right_prob != 0.0) {

			validProb.put(3, A.right_prob);
			// FinalMap.put(dir, validProb);
		}

		return validProb;
	}

}
