package com.orionsoft.vsafe.model;

public class Medical {

    private String disease;
    private String timePeriod;
    private String underTreat;

    public Medical(String disease, String timePeriod, String underTreat) {
        this.disease = disease;
        this.timePeriod = timePeriod;
        this.underTreat = underTreat;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getUnderTreat() {
        return underTreat;
    }

    public void setUnderTreat(String underTreat) {
        this.underTreat = underTreat;
    }
}
