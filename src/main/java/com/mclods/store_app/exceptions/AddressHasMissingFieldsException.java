package com.mclods.store_app.exceptions;

import com.mclods.store_app.domain.entities.Address;

public class AddressHasMissingFieldsException extends Exception {
    Address address;

    public AddressHasMissingFieldsException(Address address) {
        this.address = address;
    }

    @Override
    public String getMessage() {
        return String.format("%s has missing fields.", address);
    }

    @Override
    public String toString() {
        return String.format("%s has missing fields.", address);
    }
}
