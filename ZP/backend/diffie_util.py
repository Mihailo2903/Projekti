from cryptography.hazmat.primitives.asymmetric import dh
from cryptography.hazmat.primitives import serialization

if __name__ == "__main__":
    # Generate parameters for Diffie-Hellman key exchange
    parameters = dh.generate_parameters(generator=2, key_size=2048)

    # Generate a private key
    private_key = parameters.generate_private_key()

    # Get the corresponding public key
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

    # Print the private and public keys in PEM format
    print(public_key.public_numbers().y)
    print(private_key.private_numbers().x)
    print(parameters.parameter_numbers().p)
    print(parameters.parameter_numbers().g)

    print("Private Key:")
    print(private_key_pem.decode())
    print("Public Key:")
    print(public_key_pem.decode())
