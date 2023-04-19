import java.util.ArrayList;

public class Statistics
{
    private static ArrayList<Stats> statistic1 = new ArrayList<>();
    private static ArrayList<Stats> statistic2 = new ArrayList<>();
    private static ArrayList<Stats> statistic3 = new ArrayList<>();
    private static ArrayList<Stats> statistic4 = new ArrayList<>();
    private static ArrayList<Stats> statistic5 = new ArrayList<>();

    public static class Stats
    {
        private double duration;
        private double data;

        //constructor
        public Stats(double duration, double data)
        {
            this.duration = duration;
            this.data = data;
        }
    }

    //The average number of customers in the barbershop at any moment (weighted sum)
    //collected after every event
    //duration used
    //data will be the number of customers in the shop
    public static void recordStatistic1(Stats stat)
    {
        statistic1.add(stat);
    }

    public static double computeStatistic1()
    {
        double numerator = 0;
        double denominator = 0;
        for(int i = 0; i < statistic1.size(); i++)
        {
            numerator += statistic1.get(i).data * statistic1.get(i).duration;
            denominator += statistic1.get(i).duration;
        }
        return numerator / denominator;
    }


    //The average time each customer spent in the barbershop
    //collected when a finished haircut event is processed
    //duration not used
    //data will be the time spent in the shop
    public static void recordStatistic2(Stats stat)
    {
        statistic2.add(stat);
    }

    public static double computeStatistic2()
    {
        double totalTime = 0;
        for(int i = 0; i < statistic2.size(); i++)
        {
            totalTime += statistic2.get(i).data;
        }
        return totalTime / statistic2.size();
    }

    //The average time each customer waited before starting to get a haircut
    //collected when a finished haircut event is processed
    //duration not used
    //data will be the time spent in the waiting line
    public static void recordStatistic3(Stats stat)
    {
        statistic3.add(stat);
    }

    public static double computeStatistic3()
    {
        double totalTime = 0;
        for(int i = 0; i < statistic3.size(); i++)
        {
            totalTime += statistic3.get(i).data;
        }
        return totalTime / statistic3.size();
    }

    //The average number of customers waiting to get a haircut at any moment (weighted sum)
    //collected after every event
    //duration used
    //data will be the number of customers in the waiting line
    public static void recordStatistic4(Stats stat)
    {
        statistic4.add(stat);
    }

    public static double computeStatistic4()
    {
        double numerator = 0;
        double denominator = 0;
        for(int i = 0; i < statistic4.size(); i++)
        {
            numerator += statistic4.get(i).data * statistic4.get(i).duration;
            denominator += statistic4.get(i).duration;
        }
        return numerator / denominator;
    }

    //The fraction of time (as a decimal) that the barbershop had no customers
    //collected after every event
    //duration used only if shop is empty
    //data not used
    public static void recordStatistic5(Stats stat)
    {
        statistic5.add(stat);
    }

    public static double computeStatistic5(double simLength)
    {
        double totalTime = 0;
        for(int i = 0; i < statistic5.size(); i++)
        {
            totalTime += statistic5.get(i).duration;
        }
        return totalTime / simLength;
    }
}
