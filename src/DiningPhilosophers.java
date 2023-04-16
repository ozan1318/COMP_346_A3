/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;
	

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
			boolean valid = true;
			String number_str = argv[0];
			char[] number_ar = number_str.toCharArray(); // Turning the string into a character array.
			
			// checking if the string is all numbers
			for(int i = 0; i < number_ar.length; i++)
			{
				if(((int) number_ar[i]) > 57 || ((int) number_ar[i]) < 48)
				{
					valid = false;
					break;
				}
			}
			
			// if the string are all numbers and the number is bigger than zero.
			if(valid && Integer.parseInt(number_str) > 0)
			{
				iPhilosophers = Integer.parseInt(number_str); // Number of philosophers is defined by the user
				
			}
			// use default value
			else
			{
				
				System.out.println("\"" + number_str + "\"" + " is not a positive decimal integer"); // use default value if number is not valid.
				System.out.println();
				System.out.println();
				System.out.println("Usage: java DininPhilosophers[" + iPhilosophers + "]");
				System.out.println();
			}
			

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher[] aoPhilosophers = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println
			(
				iPhilosophers +
				" philosopher(s) came in for a dinner."
			);

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
