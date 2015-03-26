package rl1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

public class valueIteration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Vector<State> valid_states = null;
		ArrayList<Action> Actions = null;
		HashMap<Integer, Double> FinalMap = new HashMap<Integer, Double>();
		Double gamma = 0.9;
		MDP mdp = new MDP(5, 4);

		
		mdp.decideStates();
		
		mdp.setGivenRewards();

		//mdp.setZeroRewards();

		mdp.set_reachableStates();

		valid_states = mdp.get_reachableState();
		mdp.set_StateAction();
		Actions = mdp.get_Actions();

		boolean done = false;
		System.out.println("Generating optimal policy....");
		int numIterations = 0;
		while (!done) {
			double maxError = -1.;
			for (State S : valid_states) {
				Double Value = S.getValue();
				int Reward = S.getReward();
				Double maxValue = -1e30;
				Action maxAction = null;

				for (Action action : Actions) {

					HashMap<Integer, Double> TransitMap = mdp
							.get_validProbOnly(action);
					java.util.Iterator<Entry<Integer, Double>> it = TransitMap
							.entrySet().iterator();
					Double next_value = 0.0;
					while (it.hasNext()) {
						Map.Entry pairs = it.next();
						int a = (int) pairs.getKey();
						Double prob_a = (Double) pairs.getValue();
						State sPrime = mdp.get_nextState(S, action, a);
						if (sPrime != null) {
							next_value += (prob_a * sPrime.getValue());
						}

					}
					if (next_value > maxValue) {
						maxValue = next_value;
						maxAction = action;
					}

				}

				maxValue = Reward + gamma * maxValue;
				S.setValue(maxValue);
				S.setMax_action(maxAction);

				Double currentError = Math.abs(maxValue - Value);
				if (currentError > maxError)
					maxError = currentError;
			}
			numIterations++;

			System.out.println("Iteration: " + numIterations + " error:"
					+ maxError);
			if (maxError == 0)
				done = true;
		}

		System.out.println("Done in " + numIterations + " Iterations");
		System.out.println();
		System.out.println("Final Values");
		mdp.printValue();
		System.out.println();
		System.out.println("Actions");
		mdp.printMaxAction();

	}

}
