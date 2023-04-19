public class Event
{
    private String eventType;
    private double occursAt;
    private Customer customer;

    //constructor
    public Event(String eventType, double occursAt)
    {
        setEventType(eventType);
        setOccursAt(occursAt);
        setCustomer(occursAt);
    }

    public String toString()
    {
        String toPrint = getOccursAt() + " - Processing " + getEventType();
        return toPrint;
    }

    public String testingEventQueueToString()
    {
        String toPrint = "Event " + getEventType() + " " + getOccursAt();
        return toPrint;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setOccursAt(double occursAt)
    {
        this.occursAt = occursAt;
    }

    public double getOccursAt()
    {
        return occursAt;
    }

    public void setCustomer(double occursAt) {
        if(getEventType().equals("Customer Arrival") || getEventType().equals("Exit"))
            customer = new Customer(occursAt);
        else
            customer = null;
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
