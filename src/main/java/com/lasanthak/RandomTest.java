package com.lasanthak;

public class RandomTest {
    public static void main(String[] args) {
        QuantumRandom qr = new QuantumRandom();
        for (int i = 0; i < 200; i++) {
            System.out.println(qr.nextLong(1000));
        }

    }
}
