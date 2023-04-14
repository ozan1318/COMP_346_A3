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
	boolean is_talking = false;
	int[] phil_counter;
	
	


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		state_phil = new state[piNumberOfPhilosophers];
		chopsticks = piNumberOfPhilosophers;
		phil_num = piNumberOfPhilosophers;
		phil_counter = new int[piNumberOfPhilosophers];
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
		
		while(Test(piTID))
		{
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		phil_counter[piTID - 1] = phil_counter[piTID - 1] + 1;
		System.out.println("Counter of philosopher " + piTID + ": " + phil_counter[piTID -1]);
		state_phil[piTID - 1] = state.EATING;
				
	}

	/**
	 * When a given philosopher's done eating, they put the chopsticks/forks down
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
		if(is_talking)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			is_talking = true;
			
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		is_talking = false;
		notifyAll();
	}
	
	public boolean Test(final int piTID)
	{
		if(((state_phil[((piTID - 1) + (phil_num - 1)) % phil_num] == state.EATING) || (state_phil[(piTID) % phil_num] == state.EATING)) && state_phil[piTID - 1] == state.HUNGRY)
		{
			return true;
		}
		else if((phil_counter[((piTID -1) + (phil_num - 1)) % phil_num] < phil_counter[piTID - 1]) || (phil_counter[(piTID) % phil_num] < phil_counter[piTID -1]))
			return true;
		else
			return false;
	}
}

// EOF
