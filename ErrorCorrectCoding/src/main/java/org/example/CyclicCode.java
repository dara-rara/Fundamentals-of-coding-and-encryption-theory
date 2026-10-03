package org.example;

public class CyclicCode {

    private static final int GENERATOR = 0b1011; // Генераторный многочлен (x^3 + x + 1)
    private static final int MESSAGE_LENGTH = 4; // Количество информационных битов
    private static final int CODE_LENGTH = 7;    // Общее количество битов (информационных + проверочных)

    // Метод для кодирования сообщения
    public int encode(int message) {
        // Сдвигаем сообщение влево на количество проверочных битов
        int shiftedMessage = message << (CODE_LENGTH - MESSAGE_LENGTH);
        // Вычисляем остаток от деления сдвинутого сообщения на генераторный многочлен
        int remainder = modDivision(shiftedMessage, GENERATOR);
        // Объединяем сдвинутое сообщение с остатком, чтобы получить кодовое слово
        return shiftedMessage | remainder;
    }

    // Метод для деления многочленов по модулю 2
    private int modDivision(int dividend, int divisor) {
        // Определяем длину генераторного многочлена
        int divisorLength = Integer.toBinaryString(divisor).length();
        // Пока длина делимого больше или равна длине делителя
        while (Integer.toBinaryString(dividend).length() >= divisorLength) {
            // Выполняем XOR между делимым и сдвинутым делителем
            dividend ^= divisor << (Integer.toBinaryString(dividend).length() - divisorLength);
        }
        // Возвращаем остаток от деления
        return dividend;
    }

    // Метод для проверки и исправления ошибок
    public int decodeAndCorrect(int received) {
        // Вычисляем синдром (остаток от деления полученного слова на генераторный многочлен)
        int syndrome = modDivision(received, GENERATOR);

        // Если синдром равен 0, ошибок нет
        if (syndrome == 0) {
            System.out.println("There are no errors.");
            return received;
        }

        // Если синдром не равен 0, выводим его в двоичном виде
        System.out.println("Error detected. Syndrome: " + Integer.toBinaryString(syndrome));

        // Таблица синдромов для (7, 4)-кода, где каждый элемент соответствует ошибке в определенном бите
        int[] errorPatterns = {
                0b1,       // Ошибка в первом бите
                0b10,      // Ошибка во втором бите
                0b100,     // Ошибка в третьем бите
                0b1000,    // Ошибка в четвертом бите
                0b10000,   // Ошибка в пятом бите
                0b100000,  // Ошибка в шестом бите
                0b1000000  // Ошибка в старшем бите
        };

        // Исправление ошибки
        for (int i = 0; i < errorPatterns.length; i++) {
            // Если синдром совпадает с синдромом ошибки в текущем бите
            if (modDivision(errorPatterns[i], GENERATOR) == syndrome) {
                // Выводим номер бита, в котором обнаружена ошибка
                System.out.println("Bit error: " + (i + 1));
                // Исправляем ошибку с помощью XOR
                return received ^ errorPatterns[i];
            }
        }
        // Если ошибка не может быть исправлена
        System.out.println("Unable to fix the error.");
        return received;
    }

    // Метод для извлечения исходного сообщения из исправленного кодового слова
    public int decodeMessage(int correctedCodeword) {
        // Сдвигаем исправленное кодовое слово вправо, чтобы получить исходное сообщение
        return correctedCodeword >> (CODE_LENGTH - MESSAGE_LENGTH);
    }
}