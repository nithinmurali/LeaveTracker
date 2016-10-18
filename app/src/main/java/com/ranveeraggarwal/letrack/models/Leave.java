package com.ranveeraggarwal.letrack.models;

/**
 * Created by ranveer on 18/10/16.
 */

public class Leave {
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getFno() {
        return fno;
    }

    public void setFno(int fno) {
        this.fno = fno;
    }

    public Leave() {
        this.setDate(0);
        this.setFno(0);
    }

    public Leave(int pid, int date, int fno) {
        this.pid = pid;
        this.date = date;
        this.fno = fno;
    }

    private long date;
    private int pid;
    private int fno;
}
