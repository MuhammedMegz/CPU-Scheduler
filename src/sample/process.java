package sample;

public class process implements Comparable<process> {

    private String name;
    private int duration;
    private int arrTime;
    private int priority;
    private int remainingTime;


    public process(String name, int duration, int arrTime, int priority) {
        this.name = name;
        this.duration = duration;
        this.arrTime = arrTime;
        this.priority = priority;
        this.remainingTime = duration;
    }

    public process(String name, int duration, int arrTime) {
        this.name = name;
        this.duration = duration;
        this.arrTime = arrTime;
        this.priority = -1;
        this.remainingTime = duration;
    }

    public process(String name, int duration) {
        this.name = name;
        this.duration = duration;
        this.arrTime = arrTime;
        this.priority = -1;
        this.remainingTime = duration;
    }


    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getArrTime() {
        return arrTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setArrTime(int arrTime) {
        this.arrTime = arrTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
    public int compareTo(process o) {
        return 0;
    }
}
