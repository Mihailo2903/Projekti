from cryptography.hazmat.primitives.asymmetric import dsa
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.backends import default_backend
from backend.hash_util import *
from backend.pem_util import *


def generate_DSA_key():
    private_key = dsa.generate_private_key(
        key_size=2048,
        backend=default_backend()
    )
    public_key = private_key.public_key()
    return private_key, public_key


def DSA_make_signature(message, private_key):
    if isinstance(message, str):
        message = message.encode()
    signature = private_key.sign(
        message,
        hashes.SHA256()
    )
    return signature


def DSA_verify_signature(message, signature, public_key):
    if isinstance(message, str):
        message = message.encode()
    try:
        public_key.verify(
            signature,
            message,
            hashes.SHA256()
        )
        return True
    except Exception:
        return False


if __name__ == "__main__":
    # Generate DSA key pair
    private_key, public_key = generate_DSA_key()

    # Original message
    message = "Hello, World!"
    hash = generate_hash(message)
    # Sign the message with the private key
    signature = DSA_make_signature(hash, private_key)

    # Verify the signature using the public key
    is_valid = DSA_verify_signature(hash, signature, public_key)

    print("Original Message:", hash)
    print("Signature:", signature)
    print("Is Valid:", is_valid)

    pathToPublicPem = r"files\dsa_public.pem"
    pathToPrivatePem = r"files\dsa_private.pem"

    typeOfPublicKey = KeyTypes.getClassForAlgorithmAndKeyType("DSA", True)
    typeOfPrivateKey = KeyTypes.getClassForAlgorithmAndKeyType("DSA", False)

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
