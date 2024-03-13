package com.example.studentbudgetigapp;

public class Mclass {

    int total,ftot,otot,etot,save;

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFtot() {
        return ftot;
    }

    public void setFtot(int ftot) {
        this.ftot = ftot;
    }

    public int getOtot() {
        return otot;
    }

    public void setOtot(int otot) {
        this.otot = otot;
    }

    public int getEtot() {
        return etot;
    }

    public void setEtot(int etot) {
        this.etot = etot;
    }

    public Mclass(int total, int ftot, int otot, int etot, int save) {
        this.total = total;
        this.ftot = ftot;
        this.otot = otot;
        this.etot = etot;
        this.save=save;
    }
}
