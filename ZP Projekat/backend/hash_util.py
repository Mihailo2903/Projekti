import hashlib


def generate_hash(message):
    # Create a SHA-256 hash object
    sha256_hasher = hashlib.sha256()

    # Convert the message to bytes if it's a string
    if isinstance(message, str):
        message = message.encode()

    # Update the hash object with the message
    sha256_hasher.update(message)

    # Compute the hash digest
    hash_digest = sha256_hasher.hexdigest()

    return hash_digest.encode()


def makeKeyForAesFromHash(hash):
    return hash[:32]


# Example usage
if __name__ == "__main__":
    message = "Hello, World!"
    hash_result = generate_hash(message)
    print(type(hash_result))
    print("Message:", message)
    print(type(hash_result[:32]))
    print(hash_result[:32])

    # print(hash_result.encode())
    # print("SHA-256 Hash:", hash_result)
