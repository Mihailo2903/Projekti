from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import rsa, padding
from backend.hash_util import *
from backend.pem_util import *


def generate_RSA_key():
    private_key = rsa.generate_private_key(
        public_exponent=65537,
        key_size=2048
    )
    public_key = private_key.public_key()
    return private_key, public_key


def RSA_encrypt(message, public_key):
    if isinstance(message, str):
        message = message.encode()
    encrypted_message = public_key.encrypt(
        message,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return encrypted_message


def RSA_decrypt(encrypted_message, private_key):
    decrypted_message = private_key.decrypt(
        encrypted_message,
        padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
    )
    return decrypted_message #.decode()


def RSA_make_signature(message, private_key):
    if isinstance(message, str):
        message = message.encode()
    signature = private_key.sign(
        message,
        padding.PSS(
            mgf=padding.MGF1(hashes.SHA256()),
            salt_length=padding.PSS.MAX_LENGTH
        ),
        hashes.SHA256()
    )
    return signature


def RSA_verify_signature(message, signature, public_key):
    if isinstance(message, str):
        message = message.encode()
    try:
        public_key.verify(
            signature,
            message,
            padding.PSS(
                mgf=padding.MGF1(hashes.SHA256()),
                salt_length=padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )
        return True
    except Exception:
        return False


if __name__ == "__main__":
    # Generate RSA key pair
    private_key, public_key = generate_RSA_key()
    aa = public_key.public_numbers().n & ((1 << 64) - 1)
    bb = aa.to_bytes(8, "big")
    aa1 = int.from_bytes(bb, "big")
    print(aa)
    print(bb)
    print(aa1)
    # Original message
    message = b"Hello, RSA encryption!"
    # Encrypt the message using the public key
    encrypted_message = RSA_encrypt(message, public_key)
    print("Encrypted Message:", encrypted_message)
    # Decrypt the message using the private key
    decrypted_message = RSA_decrypt(encrypted_message, private_key)
    print("Decrypted Message:", decrypted_message)

    hash = generate_hash(message)
    # Sign the message with the private key
    signature = RSA_make_signature(hash, private_key)

    # Verify the signature using the public key
    is_valid = RSA_verify_signature(hash, signature, public_key)

    print("Original Hash:", hash)
    print("Signature:", signature)
    print("Is Valid:", is_valid)

    pathToPublicPem = r"files\rsa_public.pem"
    pathToPrivatePem = r"files\rsa_private.pem"

    typeOfPublicKey = KeyTypes.getClassForAlgorithmAndKeyType("RSA", True)
    typeOfPrivateKey = KeyTypes.getClassForAlgorithmAndKeyType("RSA", False)

    publicPem = export_key(public_key, typeOfPublicKey, True, pathToPublicPem)
    privatePem = export_key(private_key, typeOfPrivateKey, False, pathToPrivatePem)

    pu = import_key_from_data(publicPem, typeOfPublicKey, True)
    pr = import_key_from_data(privatePem, typeOfPrivateKey, False)

    puFile = import_key_from_file(pathToPublicPem, typeOfPublicKey, True)
    prFile = import_key_from_file(pathToPrivatePem, typeOfPrivateKey, False)

    print(pu)
    print(pr)

    print(puFile)
    print(prFile)
