from Cryptodome.Cipher import CAST
from Cryptodome.Util.Padding import pad, unpad
import secrets


def generate_CAST5_key():
    # Generate a 128-bit (16-byte) key
    return secrets.token_bytes(16)


def CAST5_encrypt(plaintext, key):
    # Create the CAST5 cipher object with the key
    cipher = CAST.new(key, CAST.MODE_ECB)
    # Encrypt a message
    if isinstance(plaintext, str):
        plaintext = plaintext.encode()
    # Encode the string to bytes
    padded_message = pad(plaintext, 8)  # Pad the message to be a multiple of the block size
    ciphertext = cipher.encrypt(padded_message)
    return ciphertext


def CAST5_decrypt(ciphertext, key):
    cipher = CAST.new(key, CAST.MODE_ECB)
    # Decrypt the ciphertext
    decrypted_message = unpad(cipher.decrypt(ciphertext), 8)  # Unpad the decrypted message
    # decrypted_message_str = decrypted_message.decode()  # Decode the bytes to string
    return decrypted_message


if __name__ == "__main__":
    message = "Teletovic sa parkingaaaaaaaaaaa"
    key = generate_CAST5_key()
    encrypted = CAST5_encrypt(message, key)
    decrypted = CAST5_decrypt(encrypted, key)

    print("Original Message:", message)
    print("Ciphertext:", encrypted)
    print("Decrypted Message:",decrypted)
