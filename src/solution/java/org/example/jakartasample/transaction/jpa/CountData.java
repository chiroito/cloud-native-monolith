package org.example.jakartasample.transaction.jpa;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "count_data")
public class CountData {

    @Id
    @Column(length = 30)
    private String name;

    private int count;

    private Date lastVisit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }
}
