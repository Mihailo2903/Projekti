from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import rsa


def generate_rsa_key_pair():
    # Generate an RSA private key
    private_key = rsa.generate_private_key(
        public_exponent=65537,
        key_size=2048,
    )

    # Get the public key from the private key
    public_key = private_key.public_key()

    # Serialize the private key to PEM format
    private_key_pem = private_key.private_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PrivateFormat.PKCS8,
        encryption_algorithm=serialization.NoEncryption()
    )

    # Serialize the public key to PEM format
    public_key_pem = public_key.public_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PublicFormat.SubjectPublicKeyInfo
    )

    return private_key_pem, public_key_pem


if __name__ == "__main__":
    # Generate the RSA key pair
    private_key_pem, public_key_pem = generate_rsa_key_pair()

    # Print the private and public keys
    print("Private Key:")
    print(private_key_pem.decode())

    print("\nPublic Key:")
    print(public_key_pem.hex())

    public_key = serialization.load_pem_public_key(
        public_key_pem
    )

    modulus = public_key.public_numbers().n
    print("e:")
    print(public_key.public_numbers().e)
    print(public_key.public_numbers().n)
    lower_64_bits = modulus & ((1 << 64) - 1)

    print(type(lower_64_bits))
    print(lower_64_bits)

    bit_string = format(lower_64_bits, '064b')

    print(bit_string)

