from Cryptodome.Cipher import AES
from Cryptodome.Util.Padding import pad, unpad
import secrets


def generate_AES_128_key():
    # Generate a 128-bit (16-byte) key
    temp = secrets.token_bytes(16)
    # print("type of temp = " + str(type(temp)))
    return temp


def AES_128_encrypt(plaintext, key):
    # Create the AES cipher object with the key
    cipher = AES.new(key, AES.MODE_ECB)
    # Encrypt a message
    if isinstance(plaintext, str):
        plaintext = plaintext.encode()
    padded_message = pad(plaintext, AES.block_size)  # Pad the message to be a multiple of the block size
    ciphertext = cipher.encrypt(padded_message)
    return ciphertext


def AES_128_decrypt(ciphertext, key):
    cipher = AES.new(key, AES.MODE_ECB)
    # Decrypt the ciphertext
    decrypted_message = unpad(cipher.decrypt(ciphertext), AES.block_size)  # Unpad the decrypted message
    # decrypted_message_str = decrypted_message.decode()  # Decode the bytes to string
    return decrypted_message


if __name__ == "__main__":
    key = generate_AES_128_key()
    message = "Mirzaaaaa, sa suprotne polovineeeeeeeeeee"
    ciphertext = AES_128_encrypt(message, key)
    decrypted_message = AES_128_decrypt(ciphertext, key)

    print("Original Message:", message)
    print("Ciphertext:", ciphertext)
    print("Decrypted Message:", decrypted_message)
