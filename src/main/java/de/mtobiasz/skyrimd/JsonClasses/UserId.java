package de.mtobiasz.skyrimd.JsonClasses;

import java.util.UUID;

public class UserId {
    private UUID id;

    public UserId(UUID id) {
        this.id = id;
    }

    public UUID getId(){
        return id;
    }
}
