package com.orderflow.login_service.dto.response;

import java.util.Objects;

public class StoreSelectResponse {
    private final String storeToken;
    private final String storeName;

    public StoreSelectResponse(String storeToken, String storeName) {
        this.storeToken = storeToken;
        this.storeName = storeName;
    }

    public String getStoreToken() {
        return storeToken;
    }

    public String getStoreName() {
        return storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreSelectResponse that = (StoreSelectResponse) o;
        return Objects.equals(storeToken, that.storeToken) &&
                Objects.equals(storeName, that.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeToken, storeName);
    }

    @Override
    public String toString() {
        return new StringBuilder("StoreSelectResponse{")
                .append("storeToken='")
                .append(storeToken)
                .append('\'')
                .append(", storeName='")
                .append(storeName)
                .append('\'')
                .append('}')
                .toString();
    }
}
