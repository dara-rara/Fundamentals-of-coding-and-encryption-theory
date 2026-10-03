package org.example;

public class ChaCha20 {

    private static final int BLOCK_SIZE = 64;
    // Состояние шифра (16 слов по 32 бита)
    private final int[] state = new int[16];
    private final byte[] key;
    private final byte[] nonce;
    private int counter;

    public ChaCha20(byte[] key, byte[] nonce, int counter) {
        // Проверяем, что ключ имеет длину 32 байта
        if (key.length != 32) {
            throw new IllegalArgumentException("Key must be 32 bytes");
        }
        // Проверяем, что нонс имеет длину 12 байт
        if (nonce.length != 12) {
            throw new IllegalArgumentException("Nonce must be 12 bytes");
        }

        this.key = key;
        this.nonce = nonce;
        this.counter = counter;

        initState();
    }

    // Метод для инициализации состояния ChaCha20
    private void initState() {
        // Устанавливаем константы, используемые в качестве начальных значений состояния(это идентификатор)
        state[0] = 0x61707865; // 'expa'
        state[1] = 0x3320646e; // 'nd 3'
        state[2] = 0x79622d32; // '2-by'
        state[3] = 0x6b206574; // 'text'

        // Инициализация состояния ключом
        for (int i = 0; i < 8; i++) {
            state[4 + i] = bytesToInt(key, i * 4);
        }

        // Инициализируем состояние нонсом и счетчиком
        state[12] = counter; // Устанавливаем значение счетчика
        for (int i = 0; i < 3; i++) {
            state[13 + i] = bytesToInt(nonce, i * 4); // Устанавливаем нонс
        }
    }

    // Метод для преобразования массива байтов в целое число
    private static int bytesToInt(byte[] b, int offset) {
        return ((b[offset] & 0xff)) |
                ((b[offset + 1] & 0xff) << 8) |
                ((b[offset + 2] & 0xff) << 16) |
                ((b[offset + 3] & 0xff) << 24);
    }

    // Основной метод для шифрования текста
    public byte[] encrypt(byte[] plaintext) {
        // Определяем количество блоков
        int blocks = (plaintext.length + BLOCK_SIZE - 1) / BLOCK_SIZE;
        // Массив для хранения шифротекста
        byte[] ciphertext = new byte[blocks * BLOCK_SIZE];

        // Проходим по каждому блоку
        for (int block = 0; block < blocks; block++) {
            // Клонируем текущее состояние для обработки блока
            int[] workingState = state.clone();
            // Увеличиваем счетчик для следующего блока
            workingState[12] += 1;

            // Выполняем 20 раундов для шифрования
            for (int i = 0; i < 10; i++) {
                quarterRound(workingState, 0, 4, 8, 12);
                quarterRound(workingState, 1, 5, 9, 13);
                quarterRound(workingState, 2, 6, 10, 14);
                quarterRound(workingState, 3, 7, 11, 15);
                quarterRound(workingState, 0, 5, 10, 15);
                quarterRound(workingState, 1, 6, 11, 12);
                quarterRound(workingState, 2, 7, 8, 13);
                quarterRound(workingState, 3, 4, 9, 14);
            }

            // Складываем текущее состояние с рабочим состоянием
            for (int i = 0; i < 16; i++) {
                workingState[i] += state[i];
            }

            // Получаем байты из результата шифрования
            int offset = block * BLOCK_SIZE;
            for (int i = 0; i < BLOCK_SIZE; i++) {
                ciphertext[offset + i] = (byte) ((workingState[i / 4] >> ((i % 4) * 8)) & 0xff);
            }

            // Выполняем операцию XOR с исходным текстом
            for (int i = 0; i < BLOCK_SIZE && (offset + i < plaintext.length); i++) {
                ciphertext[offset + i] ^= plaintext[offset + i];
            }
        }

        return ciphertext; // Возвращаем шифрованные данные
    }

    // Функция quarterRound, реализующая 4х4 шифрование
    private void quarterRound(int[] state, int a, int b, int c, int d) {
        state[a] += state[b]; // Сложение
        state[d] = Integer.rotateLeft(state[d] ^ state[a], 16); // XOR и поворот
        state[c] += state[d]; // Сложение
        state[b] = Integer.rotateLeft(state[b] ^ state[c], 12); // XOR и поворот
        state[a] += state[b]; // Сложение
        state[d] = Integer.rotateLeft(state[d] ^ state[a], 8); // XOR и поворот
        state[c] += state[d]; // Сложение
        state[b] = Integer.rotateLeft(state[b] ^ state[c], 7); // XOR и поворот
    }

    // Метод для дешифрования, использующий тот же алгоритм, как и шифрование
    public byte[] decrypt(byte[] ciphertext) {
        return encrypt(ciphertext); // Шифрование обратимо(симметричное кодирование)
    }
}

