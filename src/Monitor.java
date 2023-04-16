/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	enum state {THINKING, EATING, HUNGRY} // defining three states.
	state[] state_phil; // array representing the state of each philospher.
	int phil_num; // number of philosophers.
	boolean is_talking = false; // variable that indicates if any philosphers is talking.
	int[] phil_counter; // array that keeps track of the number of times a philosopher ate.
	
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state_phil = new state[piNumberOfPhilosophers]; // creating the state array.
		
		phil_num = piNumberOfPhilosophers;
		phil_counter = new int[piNumberOfPhilosophers]; // creating the counter array.
		for(int i = 0; i < piNumberOfPhilosophers; i++)
		{
			state_phil[i] = state.THINKING; // initializing the state of each philosopher to "THINKING".
		}
		
	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		state_phil[piTID -1] = state.HUNGRY; // making the state of the philosopher piTID "HUNGRY".
		
		// The loop constantly checks if philosopher piTID - 1 and piTID + 1 are eating. 
		// The philosopher piTID waits as long as piTID - 1 and piTID + 1 are eating.
		// The philosopher piTID also waits if he ate more than philosophers piTID - 1 and piTID + 1.
		while(Test(piTID)) 
		{
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		phil_counter[piTID - 1] = phil_counter[piTID - 1] + 1; // increasing the counter of the philosopher piTID.
		System.out.println("Counter of philosopher " + piTID + ": " + phil_counter[piTID -1]);
		state_phil[piTID - 1] = state.EATING; // changing the state of the philosopher piTID "EATING".
				
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) 
	{
		state_phil[piTID - 1] = state.THINKING; // changing the state of the philosopher piTID to "THINKING".
		notifyAll(); // Notifying other philosophers that were waiting.
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// checking if any philosophers is talking.
		if(is_talking)
			try {
				wait(); // waiting if a philosopher is talking.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			is_talking = true; // allowed to talk if no philosopher is talking.
			
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// notifying other philosophers that were waiting to talk.
		is_talking = false;
		notifyAll(); 
	}
	
	public boolean Test(final int piTID)
	{
		// checking if the philosopher piTID - 1 and piTID + 1 are eating. 
		if(((state_phil[((piTID - 1) + (phil_num - 1)) % phil_num] == state.EATING) || (state_phil[(piTID) % phil_num] == state.EATING)) && state_phil[piTID - 1] == state.HUNGRY)
		{
			return true; 
		}
		// checking the counter of philosophers piTID - 1 and piTID + 1.
		else if((phil_counter[((piTID -1) + (phil_num - 1)) % phil_num] < phil_counter[piTID - 1]) || (phil_counter[(piTID) % phil_num] < phil_counter[piTID -1]))
			return true;
		else
			return false;
	}
}

// EOF
