package org.example;

public class MajorityDecoding {

    // Метод для кодирования данных с мажоритарным декодированием
    public String encodeMajority(String data, int repetitions) {
        StringBuilder encodedData = new StringBuilder();
        for (char bit : data.toCharArray()) {
            // Повторяем каждый бит заданное количество раз
            for (int i = 0; i < repetitions; i++) {
                encodedData.append(bit);
            }
        }
        return encodedData.toString();
    }

    // Метод для декодирования данных с использованием мажоритарного принципа
    public String decodeMajority(String receivedData, int repetitions, int originalDataLength) {
        StringBuilder decodedData = new StringBuilder();
        int blockSize = repetitions; // Размер блока для мажоритарного декодирования

        // Вычисляем ожидаемое количество блоков на основе длины исходных данных
        int expectedBlocks = originalDataLength * repetitions;

        // Проверяем, что длина полученных данных соответствует ожидаемому количеству блоков
        if (receivedData.length() < expectedBlocks) {
            System.out.println("Error: Received data is shorter than expected.");
            return "";
        }

        // Обрезаем полученные данные до ожидаемого количества блоков
        receivedData = receivedData.substring(0, expectedBlocks);

        for (int i = 0; i < receivedData.length(); i += blockSize) {
            // Получаем текущий блок битов
            String block = receivedData.substring(i, i + blockSize);

            // Подсчитываем количество единиц и нулей в блоке
            int countOnes = 0;
            int countZeros = 0;
            for (char bit : block.toCharArray()) {
                if (bit == '1') {
                    countOnes++;
                } else if (bit == '0') {
                    countZeros++;
                }
            }

            // Выбираем бит, который встречается чаще
            char decodedBit = (countOnes > countZeros) ? '1' : '0';
            decodedData.append(decodedBit);
        }

        return decodedData.toString();
    }
}