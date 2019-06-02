package ru.otus.hw011;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        String hashed = args[0];

        HashFunction hashFunction = Hashing.sha256();
        HashCode hash = hashFunction.newHasher()
                .putString(hashed, Charset.forName("UTF-8"))
                .hash();

        System.out.println(hashed + " -> sha256: " + hash);

    }

}
