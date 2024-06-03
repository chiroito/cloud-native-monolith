package org.example.jakartasample.transaction.jpa;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class RdbmsData {

    @Id
    @Column(length = 30)
    private String name;

    private int count;

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
}
