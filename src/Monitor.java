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
	enum state {THINKING, EATING, HUNGRY} 
	state[] state_phil;
	private int chopsticks;
	int phil_num;
	
	
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state_phil = new state[piNumberOfPhilosophers];
		chopsticks = piNumberOfPhilosophers;
		phil_num = piNumberOfPhilosophers;
		for(int i = 0; i < piNumberOfPhilosophers; i++)
		{
			state_phil[i] = state.THINKING;
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
		state_phil[piTID -1] = state.HUNGRY;
		if((state_phil[((piTID -1) + (phil_num - 1)) % phil_num] != state.EATING) && (state_phil[(piTID) % phil_num] != state.EATING) && state_phil[piTID - 1] == state.HUNGRY)
		{
			state_phil[piTID - 1] = state.EATING;
		}
		
		if(state_phil[piTID - 1] != state.EATING)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
				
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		state_phil[piTID - 1] = state.THINKING;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF
