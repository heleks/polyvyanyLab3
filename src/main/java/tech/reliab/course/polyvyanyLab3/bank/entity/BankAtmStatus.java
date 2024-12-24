package tech.reliab.course.polyvyanyLab3.bank.entity;

import java.util.Random;

public enum BankAtmStatus {

    WORKING,
    NOT_WORKING,
    NO_MONEY;

    private static final Random
            RANDOM = new Random();

    public static BankAtmStatus randomStatus()  {
        BankAtmStatus[] statuses = values();
        return statuses[RANDOM.nextInt(statuses.length)];
    }
}
