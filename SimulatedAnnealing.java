package tspsa;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SimulatedAnnealing
{
	
	static ArrayList<Integer> xCoords = new ArrayList<Integer>();
	static ArrayList<Integer> yCoords = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> distances = new ArrayList<ArrayList<Integer>>();
	static ArrayList<Integer> solutionIndicies = new ArrayList<Integer>();
	static ArrayList<Integer> tempSols = new ArrayList<Integer>(solutionIndicies);
	static int initCost;
	static int newCost;
	
	public SimulatedAnnealing() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		System.out.println("Would you like to generate a new graph?");
		System.out.println("0: No / 1: Yes");
		
		
		Scanner input = new Scanner(System.in);
		String userIn = input.nextLine();
		
		Scanner fileReader;
		
		float initTemp = 1;
		float minTemp = 0;
		int schedule;
		//int time = 1;
		

		int numberOfCities = 0;
		while(true)
		{
			if(!userIn.equals("0") && !userIn.equals("1"))
			{
				System.out.println("Invalid Input.");
				input = new Scanner(System.in);
				userIn = input.nextLine();
			}
			else if(userIn.equals("0"))
			{
				break;
			}
			else if(userIn.equals("1"))
			{
				System.out.println("How many vertices would you like to add?");
				userIn = input.nextLine();
				numberOfCities = Integer.parseInt(userIn);
				Graph graph = new Graph(numberOfCities);
				break;
			}
			input.close();
		}
		
		try
		{
			fileReader = new Scanner(new File("xCoords.txt"));
			while(fileReader.hasNextLine())
			{
				xCoords.add(Integer.parseInt(fileReader.nextLine()));
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			fileReader = new Scanner(new File("yCoords.txt"));
			while(fileReader.hasNextLine())
			{
				yCoords.add(Integer.parseInt(fileReader.nextLine()));
			}
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(xCoords);
		System.out.println(yCoords);
		System.out.println("There are " + xCoords.size() + " x-coordinates and " + xCoords.size() + " y-coordinates.");
		System.out.println("The number of cities is: " + numberOfCities + " (0 if no new graph is generated).");
		
		//numberOfCities = xCoords.size();

		initialSolution();
		System.out.println(solutionIndicies);
		System.out.println(solutionIndicies.size());
		System.out.println(initCost);
		
		System.out.println("Set your initial temperature: ");
		input = new Scanner(System.in);
		initTemp = Float.parseFloat(input.nextLine());
		
		System.out.println("Set your minimum temperature: ");
		minTemp = Float.parseFloat(input.nextLine());
		
		System.out.println("Choose a cooling schedule:");
		System.out.println("1: Linear");
		System.out.println("2: Exponential");
		//System.out.println("3: Logarithmic");

		schedule = Integer.parseInt(input.nextLine());
		while(schedule > 3 || schedule < 1)
		{
			System.out.println("Please enter a valid option.");
			schedule = Integer.parseInt(input.nextLine());
		}
		
		float nextTemp, currTemp;
		Equations eqs = new Equations();
		int prevCost = initCost;
		
		
		//ArrayList<Integer> tempSols = new ArrayList<Integer>(solutionIndicies);
		int i = 1;
		int j = 0;
		numberOfCities = xCoords.size();
		
		
		currTemp = 1;
		newCost = eqs.cost(tempSols, xCoords, yCoords);
		nextTemp = eqs.coolingSchedule(schedule, initTemp, currTemp, minTemp, i, numberOfCities);
		currTemp = nextTemp;
		
		//simulated annealing
		while(nextTemp > minTemp)
		{
			i++;
			nextSolution(solutionIndicies);
			nextTemp = eqs.coolingSchedule(schedule, initTemp, currTemp, minTemp, i, numberOfCities);
			currTemp = nextTemp;
			newCost = eqs.cost(tempSols, xCoords, yCoords);
			//System.out.println(tempSols);
			if(newCost < prevCost)
			{ 
				solutionIndicies.clear();
				while(j < tempSols.size())
				{
					solutionIndicies.add(tempSols.get(j));
					j++;
				}
			}
			else
			{
				if(prevCost != newCost)
				{
					if(eqs.acceptance(prevCost, newCost, currTemp, initTemp))
					{
						solutionIndicies.clear();
						while(j < tempSols.size())
						{
							solutionIndicies.add(tempSols.get(j));
							j++;
						}					
					}					
				}

			}
			j = 0;
			//System.out.println(newCost);
			tempSols.clear();
			prevCost = newCost;
		}
		System.out.println("Final solution: ");
		System.out.println(solutionIndicies);
		System.out.println("Total cost: " + newCost);
		System.out.println("Initial cost: " + initCost);
		System.out.println("Number of iterations: " + i);
	}
	
	private static void initialSolution()
	{
		int count = xCoords.size();
		int i = 0;
		int currentIndex = 0;
		int nextIndex = (int) (Math.random() * count);
		ArrayList<Integer> traversed = new ArrayList<Integer>();
		Equations eqs = new Equations();
		
		while(i < count)
		{
			traversed.add(0);
			i++;
		}
		
		i = 0;
		//1 to indicate city has been visited. starting at first city at index 0.
		solutionIndicies.add(i);
		
		while (i < count-1)
		{
			traversed.set(currentIndex, 1);
			while(traversed.get(nextIndex) == 1 && traversed.contains(0)) //ensure only one city is being visited
			{
				nextIndex = (int) (Math.random() * count);
			}
			
			solutionIndicies.add(nextIndex);
			currentIndex = nextIndex;

			i++;
		}
		solutionIndicies.add(0);
		initCost = eqs.cost(solutionIndicies, xCoords, yCoords);
	}
	
	private static void nextSolution(ArrayList<Integer> Solutions)
	{
		ArrayList<Integer> tempArray = new ArrayList<Integer>(Solutions);
		
		int swapRandom = (int) (Math.random() * tempArray.size());

		if(swapRandom == tempArray.size() - 1)
		{
			Collections.swap(tempArray, swapRandom, 0);
		}
		else
		{
			Collections.swap(tempArray, swapRandom, swapRandom + 1);
		}
		
		tempSols = tempArray;
	}
}


