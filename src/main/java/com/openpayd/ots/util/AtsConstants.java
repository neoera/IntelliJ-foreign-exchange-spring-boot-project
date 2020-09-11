package com.openpayd.ots.util;

public final class AtsConstants {

    public static String AMOUNT = "Amount";
    public static String ACCOUNT = "Account";
    public static String CLIENT = "Client";
    public static String TRANSFER_TYPE = "Transfer type";
    public static String BALANCE = "Balance";
    public static String BALANCE_TYPE = "Balance type";
    public static String BALANCE_STATUS = "Balance status";
    public static String NAME = "Name";
    public static String SURNAME = "Surname";
    public static String ADDRESS = "Address object";
    public static String ADDRESS_DETAIL = "Address city, country and info";
    public static String CANNOT_NULL = "can not be null";
    public static String NOT_COMPATIBLE = "type not compatible";
    public static String NOT_FOUND_DB = "data not found in database";
    public static String AMOUNT_CAN_NOT_HIGHER_THAN_CREDIT = "Credit amount can not higher than credit";
    public static String AMOUNT_CAN_NOT_HIGHER_THAN_DEBIT = "Debit amount can not higher than credit";

    public enum AccountType {

        CURRENT("Current", 1), SAVINGS("Savings", 2);

        private final String key;
        private final Integer value;

        AccountType(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        public Integer getValue() {
            return value;
        }
    }

    public enum BalanceStatus {

        DR("DR", 1), CR("CR", 2);

        private final String key;
        private final Integer value;

        BalanceStatus(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        public Integer getValue() {
            return value;
        }

        public static boolean contains(Integer value) {

            for (BalanceStatus balanceStatus : BalanceStatus.values()) {
                if (balanceStatus.value.equals(value)) {
                    return false;
                }
            }

            return true;
        }
    }
}
