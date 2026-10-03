package org.example;

// Помехоустойчивое кодирование (2 примера - MajorityDecoding точно правильный)
public class Main {
    public static void main(String[] args) {
        //Код с мажоритарным декодированием - принцип большинства голосов
        var md = new MajorityDecoding();
        // Пример использования
        String data = "1011"; // Исходные данные
        int repetitions = 3;  // Количество повторений каждого бита

        // Кодирование
        String encodedData = md.encodeMajority(data, repetitions);
        System.out.println("Encoded data: " + encodedData);

        // Декодирование (без ошибок)
        String receivedData = encodedData;
        String decodedData = md.decodeMajority(receivedData, repetitions, data.length());
        System.out.println("Decoded data (no errors): " + decodedData);

        // Декодирование (с ошибкой)
        receivedData = "111000111111000111"; // Пример данных с ошибкой
        decodedData = md.decodeMajority(receivedData, repetitions, data.length());
        System.out.println("Decoded data (with errors): " + decodedData);
    }

//    public static void main(String[] args) {
//        var cc = new CyclicCode();
//        int message = 0b10101; // Пример исходного сообщения
//        int encodedMessage = cc.encode(message);
//        System.out.println("Encoded message: " + Integer.toBinaryString(encodedMessage));
//
//        // Внесем ошибку в кодовое слово
//        int receivedMessage = encodedMessage ^ 0b100; // Инвертируем третий бит
//        System.out.println("Received message (with error): " + Integer.toBinaryString(receivedMessage));
//
//        // Декодирование и исправление
//        int correctedMessage = cc.decodeAndCorrect(receivedMessage);
//        System.out.println("Corrected message: " + Integer.toBinaryString(correctedMessage));
//
//        // Извлечение исходного сообщения
//        int decodedMessage = cc.decodeMessage(correctedMessage);
//        System.out.println("Decoded original message: " + Integer.toBinaryString(decodedMessage));
//    }
}

