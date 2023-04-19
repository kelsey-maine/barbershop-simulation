import java.util.ArrayList;
import java.util.Random;

public class Barbershop
{
    public static void main( String[] args )
    {
        //The mean arrival rate of customers where the units are customers per hour
        double avgArrRate = Double.parseDouble(args[0]);

        //The mean haircut rate where the units are haircuts per hour
        double avgCutRate = Double.parseDouble(args[1]);

        //The length of the simulation in hours
        double endTime = Double.parseDouble(args[2]);

        //A seed for the random number generation
        long seed = Long.parseLong(args[3]);

        //keep track of time
        double prevTime = 0;
        double currTime = 0;

        //keep track of where customers are
        ArrayList<Customer> waitingLine = new ArrayList<Customer>();
        Customer gettingCut = null;

        //make random number generator
        Random random = new Random(seed);

        //BEGIN SIMULATION
        newExitEvent(endTime);
        newArrivalEvent(random, avgArrRate, currTime);

        //PROCESS EVENTS
        while(true)
        {
            //POP NEXT EVENT FROM QUEUE
            Event processingEvent = EventQueue.pop();

            //SET CLOCKS
            prevTime = currTime;
            currTime = processingEvent.getOccursAt();

            //DEBUGGING PRINTS EVERY EVENT
            //System.out.println(processingEvent.toString());

            //CHECK IF ITS EXIT EVENT
            if(processingEvent.getEventType().equals("Exit"))
                break;

            //IF EVENT IS ARRIVAL
            if(processingEvent.getEventType().equals("Customer Arrival"))
            {
                //SET CUSTOMER ARRIVAL TIME
                processingEvent.getCustomer().setArrTime(currTime);
                //CHECK IF BARBER IS GIVING HAIRCUT
                if (gettingCut != null)
                {
                    waitingLine.add(processingEvent.getCustomer());
                }
                //NOT GIVING HAIRCUT
                else
                {
                    //PUT CUSTOMER IN GETTINGCUT, SET CUT TIME, AND MAKE NEW EVENT FOR HAIR COMPLETION
                    gettingCut = processingEvent.getCustomer();
                    gettingCut.setBeginCutTime(currTime);
                    newFinishCutEvent(random, avgCutRate, currTime);
                }

                //CREATE NEW ARRIVAL EVENT
                newArrivalEvent(random, avgArrRate, currTime);
            }
            //ELSE EVENT IS FINISHED HAIRCUT
            else
            {
                //SET FINISHED CUSTOMERS END HAIR CUT TIME
                gettingCut.setEndCutTime(currTime);
                //RECORD STATISTICS OF CUSTOMER LEAVING
                Statistics.recordStatistic2(new Statistics.Stats(0, gettingCut.getEndCutTime()-gettingCut.getArrTime()));
                Statistics.recordStatistic3(new Statistics.Stats(0, gettingCut.getBeginCutTime()-gettingCut.getArrTime()));
                //GET RID OF CUSTOMER
                gettingCut = null;
                //IF CUSTOMERS IN WAITING LINE, PLACE IN GETTING CUT, SET CUT TIME, REMOVE FROM LINE, CREATE NEW HAIR FINISH EVENT
                if(!waitingLine.isEmpty())
                {
                    gettingCut = waitingLine.get(0);
                    gettingCut.setBeginCutTime(currTime);
                    waitingLine.remove(0);
                    newFinishCutEvent(random, avgCutRate, currTime);
                }
            }

            //RECORDING STATISTICS
            double duration = currTime - prevTime;
            double custInStore = 0;
            if(gettingCut == null)
                custInStore = waitingLine.size();
            else
                custInStore = waitingLine.size() + 1;
            Statistics.recordStatistic1(new Statistics.Stats(duration, custInStore));
            Statistics.recordStatistic4(new Statistics.Stats(duration, waitingLine.size()));
            if(waitingLine.isEmpty() && gettingCut==null)
                Statistics.recordStatistic5(new Statistics.Stats(duration, 0));
        }

        //REMAINING IN BARBERSHOP TIME STATISTICS
        double duration = currTime - prevTime;
        double custInStore = 0;
        if(gettingCut == null)
            custInStore = waitingLine.size();
        else
            custInStore = waitingLine.size() + 1;
        Statistics.recordStatistic1(new Statistics.Stats(duration, custInStore));
        Statistics.recordStatistic4(new Statistics.Stats(duration, waitingLine.size()));
        if(waitingLine.isEmpty() && gettingCut==null)
            Statistics.recordStatistic5(new Statistics.Stats(duration, 0));
        //REMAINING CUSTOMER STATISTICS
        if(gettingCut != null)
        {
            Statistics.recordStatistic2(new Statistics.Stats(0, currTime-gettingCut.getArrTime()));
            Statistics.recordStatistic3(new Statistics.Stats(0, gettingCut.getBeginCutTime()-gettingCut.getArrTime()));
        }
        for(int i = 0; i < waitingLine.size(); i++)
        {
            Statistics.recordStatistic2(new Statistics.Stats(0, currTime-waitingLine.get(i).getArrTime()));
            Statistics.recordStatistic3(new Statistics.Stats(0, currTime-waitingLine.get(i).getArrTime()));
        }

        //PRINT STATISTICS
        System.out.println("\nSTATISTICS");
        System.out.println("Average number of customers in the barbershop at any moment: " + Statistics.computeStatistic1());
        System.out.println("Average time each customer spent in the barbershop: " + Statistics.computeStatistic2());
        System.out.println("Average time each customer waited before starting their haircut: " + Statistics.computeStatistic3());
        System.out.println("Average number of customers waiting to get a haircut at any moment: " + Statistics.computeStatistic4());
        System.out.println("The fraction of time that the barbershop had no customers: " + Statistics.computeStatistic5(endTime));

    }

    public static void newExitEvent(double endTime)
    {
        //Create Event (creates event that says exit) and Queue it
        Event exitEvent = new Event("Exit", endTime);
        EventQueue.push(exitEvent);
    }

    public static void newArrivalEvent(Random random, double avgArrRate, double currTime)
    {
        //Create Event (creates customer & stores in event) and Queue it
        Event arrEvent = new Event("Customer Arrival", currTime + getNextTimeToAdd(random, avgArrRate));
        EventQueue.push(arrEvent);
    }

    public static void newFinishCutEvent(Random random, double avgCutRate, double currTime)
    {
        //Create Event and Queue it
        Event cutFinishEvent = new Event("Finished Haircut", currTime + getNextTimeToAdd(random, avgCutRate));
        EventQueue.push(cutFinishEvent);
    }

    public static double getNextTimeToAdd(Random randomGenerator, double avgTime)
    {
        //Generate r, a uniformly distributed random number between 0 and 1 by using nextDouble
        double r = randomGenerator.nextDouble();

        //Convert r into x, an exponentially distributed random number with mean (avgTime) by using equation x=-ln(1-r)/avgTime
        double x = (-Math.log(1.0-r)) / avgTime;

        return x;
    }
}

        /*
        TESTING ARGUMENT INTAKE
        System.out.print(avgArrRate + " " + avgCutRate + " " + simLength + " " + seed);
        TESTING MATH FOR RANDOM NUMS
        double expDistRandNum = getNextTime(avgArrRate, seed);
        System.out.print(expDistRandNum);
        TESTING PRIORITY QUEUE WORKS
        Event event1 = new Event("Arrival", 0.5);
        Event event2 = new Event("Hair Cut Finish", 0.787);
        Event event3 = new Event("Arrival", 0.513456);
        Event event4 = new Event("Arrival", 0.1);
        EventQueue.pop();
        EventQueue.push(event1);
        EventQueue.push(event2);
        EventQueue.push(event3);
        EventQueue.pop();
        EventQueue.push(event4);
        EventQueue.pop();
        EventQueue.printAll();
        */
        /*
        //PRINT WAITING LINE FOR TESTING
        System.out.println("Waiting Line");
        for(int j = 0; j < waitingLine.size(); j++)
        {
            System.out.print(waitingLine.get(j).toString());
        }
        if(gettingCut == null)
            System.out.println("No one getting hair cut\n");
        else
            System.out.println("Someone getting hair cut\n");

        //PRINT EVENT QUEUE
        EventQueue.printAll();
        */
