import qrcode
import barcode
from barcode.writer import ImageWriter
import matplotlib.pyplot as plt


# Функция для генерации QR-кода
def generate_qr_code(data, filename):
    qr = qrcode.QRCode(
        version=10,
        error_correction=qrcode.constants.ERROR_CORRECT_Q,
        box_size=10,
        border=4,
    )
    qr.add_data(data)
    qr.make(fit=True)

    img = qr.make_image(fill_color="black", back_color="white")
    img.save(filename)


# Функция для генерации штрих-кода
def generate_barcode(data, filename):
    ean = barcode.get('ean13', data, writer=ImageWriter())
    ean.save(filename)


# Основная часть программы
if __name__ == "__main__":

    # Генерация QR-кода
    qr_data = "https://elearn.urfu.ru/my/"
    qr_filename = "qrcode.png"
    generate_qr_code(qr_data, qr_filename)

    # Генерация штрих-кода
    barcode_data = "423156897011"  # EAN-13 должен содержать 12 цифр
    barcode_filename = "barcode"
    generate_barcode(barcode_data, barcode_filename)

    # Отображение QR-кода
    qr_img = plt.imread(qr_filename)
    plt.imshow(qr_img)
    plt.axis('off')
    plt.title('QR Code')
    plt.show()

    # Отображение штрих-кода
    barcode_img = plt.imread(barcode_filename + '.png')
    plt.imshow(barcode_img)
    plt.axis('off')
    plt.title('Barcode')
    plt.show()

