package de.mobilecomputing.ekrememre.medify.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medication")
public class Medication {
    @PrimaryKey(autoGenerate = true)
    public long medicationId;

    private final String name;
    private final String description;

    public Medication(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
