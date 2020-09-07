package com.example.customerclient;

class Customer {
    private Long id;
    private String surname;

    public Customer(Long id, String surname) {
        this.id = id;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

