package com.example.warsztat_samochodowy.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Mechanics")
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mechanicId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String ifEmployed;

    @OneToMany(mappedBy = "mechanic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repair> repairs;


    public Mechanic(String firstName, String secondName, String login, String password) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.ifEmployed = "YES";
        this.username = login;
        this.password = password;
    }

    public Mechanic() {
    }

    public int getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(int mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getIfEmployed() {
        return ifEmployed;
    }

    public void setIfEmployed(String ifEmployed) {
        this.ifEmployed = ifEmployed;
    }
}
