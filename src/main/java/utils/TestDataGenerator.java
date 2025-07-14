package utils;

import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TestDataGenerator  {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConfigUtil.get("dateTimeFormat"));
    private static final Random random = new Random();
    private static final Faker faker = new Faker();



    // Unique timestamp
    public String getTimestamp() {
        return LocalDateTime.now().format(formatter);
    }

    // unique name for board / list etc
    public String generateName(String name) {
        return name + getTimestamp();
    }

    // card desc
    public String generateCardDescription() {
        return faker.lorem().paragraph(1);
    }

    // random int
    public int getRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
