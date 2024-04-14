from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import dh
import random

def generate_El_Gamal_Key(keysize):
    # Generate parameters for Diffie-Hellman key exchange, can also be used for ElGamal
    parameters = dh.generate_parameters(generator=2, key_size=keysize)
    # Generate a private key
    private_key = parameters.generate_private_key()
    # Get the corresponding public key
    public_key = private_key.public_key()
    return private_key,public_key

def El_Gamal_encrypt(plaintext, public_key):
    p = int(public_key.parameters().parameter_numbers().p)
    g = int(public_key.parameters().parameter_numbers().g)
    y = int(public_key.public_numbers().y)

    # Convert plaintext to bytes
    if isinstance(plaintext, str):
        plaintext = plaintext.encode()

    # Generate random ephemeral key pair
    k = random.randint(1, p - 2)

    # Compute shared secret
    K = pow(y, k, p)

    # Encrypt each byte of the plaintext
    encrypted_bytes = bytearray()
    C1 = pow(g, k, p)
    encrypted_bytes += C1.to_bytes((p.bit_length() + 7) // 8, 'big')
    for byte in plaintext:
        C2 = (byte * pow(K, 1, p)) % p
        encrypted_bytes += C2.to_bytes((p.bit_length() + 7) // 8, 'big')

    # Convert encrypted bytes to hex string
    encrypted_hex = encrypted_bytes.hex()
    return encrypted_hex

def El_Gamal_decrypt(encrypted_hex, private_key):
    p = int(private_key.parameters().parameter_numbers().p)
    x = int(private_key.private_numbers().x)

    # Convert hex string to bytes
    encrypted_bytes = bytes.fromhex(encrypted_hex)

    # Decrypt each pair of bytes
    decrypted_bytes = bytearray()
    byte_length = (p.bit_length() + 7) // 8
    C1 = int.from_bytes(encrypted_bytes[0:byte_length], 'big')
    for i in range(byte_length, len(encrypted_bytes), byte_length):
        C2 = int.from_bytes(encrypted_bytes[i:i+byte_length], 'big')
        K = pow(C1, x, p)
        decrypted_byte = (C2 * pow(K, p - 2, p)) % p
        decrypted_bytes.append(decrypted_byte)

    # Convert decrypted bytes to plaintext using the 'latin-1' encoding
    #plaintext = decrypted_bytes.decode('latin-1')
    #return plaintext
    return bytes(decrypted_bytes) #decrypted_bytes was bytearray


if __name__ == "__main__":
    # Example usage
    message = "Teleeeeeetovicccc sa parkingaaaaaa, ratatatatatatatatatta"
    # Generate ElGamal key pair
    private_key, public_key = generate_El_Gamal_Key(512)
    print(public_key.public_numbers().y.bit_length())
    print((public_key.public_numbers().y & ((1 << 64) - 1)).bit_length())

    # Encrypt the message using the public key
    encrypted_message = El_Gamal_encrypt(message, public_key)
    # Decrypt the encrypted message using the private key
    decrypted_message = El_Gamal_decrypt(encrypted_message,private_key)

    print("Original Message:", message)
    print("Encrypted Message:", encrypted_message)
    print("Decrypted Message:", decrypted_message)


"""
class ElGamal_PU:
    def __init__(self, p, g, y):
        self.p = p
        self.g = g
        self.y = y
class ElGamal_PR:
    def __init__(self, p, x):
        self.p = p
        self.x = x
"""

"""
randfunc = Random.new().read
# Generate an ElGamal key pair
key = ElGamal.generate(1024,randfunc)

# Get the private and public keys
private_key = key
public_key = key.publickey()


# Extract the key components
p = key.p
g = key.g
y = key.y
x = key.x
print(p)
print(g)
print(x)
print(y)
# Format the components as strings
p_str = number.long_to_bytes(p).hex()
g_str = number.long_to_bytes(g).hex()
y_str = number.long_to_bytes(y).hex()
x_str = number.long_to_bytes(x).hex()

# Create the PEM-encoded private key string
private_key_pem = f"-----BEGIN PRIVATE KEY-----\n{p_str}\n{g_str}\n{y_str}\n{x_str}\n-----END PRIVATE KEY-----"

# Create the PEM-encoded public key string
public_key_pem = f"-----BEGIN PUBLIC KEY-----\n{p_str}\n{g_str}\n{y_str}\n-----END PUBLIC KEY-----"

# Print the PEM-encoded keys
print("Private Key (PEM):")
print(private_key_pem)
print("\nPublic Key (PEM):")
print(public_key_pem)
"""