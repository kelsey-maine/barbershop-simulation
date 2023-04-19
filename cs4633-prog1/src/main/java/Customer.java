public class Customer
{
    private double arrTime = 0;
    private double beginCutTime = 0;
    private double endCutTime = 0;

    //Constructor
    public Customer(double arrTime)
    {
        setArrTime(arrTime);
    }

    public String toString()
    {
        return "Customer arrived at " + getArrTime() + "\n";
    }

    public void setArrTime(double arrTime)
    {
        this.arrTime = arrTime;
    }

    public double getArrTime()
    {
        return arrTime;
    }

    public void setBeginCutTime(double beginCutTime)
    {
        this.beginCutTime = beginCutTime;
    }

    public double getBeginCutTime()
    {
        return beginCutTime;
    }

    public void setEndCutTime(double endCutTime)
    {
        this.endCutTime = endCutTime;
    }

    public double getEndCutTime()
    {
        return endCutTime;
    }
}
