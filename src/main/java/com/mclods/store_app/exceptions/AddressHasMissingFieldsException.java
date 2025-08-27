package com.mclods.store_app.exceptions;

import com.mclods.store_app.domain.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressHasMissingFieldsException extends Exception {
    String message;

    public AddressHasMissingFieldsException(Address address) {
        List<String> missingFields = new ArrayList<>();

        if(address.getStreet() == null) {
            missingFields.add("street");
        }

        if(address.getCity() == null) {
            missingFields.add("city");
        }

        if(address.getZip() == null) {
            missingFields.add("zip");
        }

        if(address.getState() == null) {
            missingFields.add("state");
        }

        message = String.format("Address has missing fields %s", missingFields);
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
