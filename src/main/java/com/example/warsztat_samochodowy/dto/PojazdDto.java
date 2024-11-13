package com.example.warsztat_samochodowy.dto;

import org.hibernate.sql.ast.SqlTreeCreationException;

public class PojazdDto {
    private String VIN;

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
