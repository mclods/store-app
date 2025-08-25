package com.mclods.store_app.exceptions;

import com.mclods.store_app.domain.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressHasMissingFieldsException extends Exception {
    Address address;
    List<String> missingFields = new ArrayList<>();

    public AddressHasMissingFieldsException(Address address) {
        this.address = address;

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
    }

    @Override
    public String getMessage() {
        return String.format("Address has missing fields %s", missingFields);
    }
}
