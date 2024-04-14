from cryptography.hazmat.primitives import serialization
import cryptography.hazmat.backends.openssl.rsa as rsa_backend
import cryptography.hazmat.backends.openssl.dsa as dsa_backend
import cryptography.hazmat.backends.openssl.dh as dh_backend
import pickle
from backend.rsa_util import *


def serialize_rsa_key_to_pem(key):
    pem = key.private_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PrivateFormat.PKCS8,
        encryption_algorithm=serialization.NoEncryption()
    )
    return pem


def serialize_rsa_key_to_pem_public(key):
    return key.public_bytes(
        encoding=serialization.Encoding.PEM,
        format=serialization.PublicFormat.SubjectPublicKeyInfo
    )


def deserialize_rsa_key_from_pem(pem):
    key = serialization.load_pem_private_key(pem, password=None)
    return key


def deserialize_rsa_key_from_pem_public(pem):
    key = serialization.load_pem_public_key(pem)
    return key


def export_key(key, typeOfDesiredKey: type, isPublicNotPrivateKey: bool, pathToFile: str = None):
    try:
        if type(key) != typeOfDesiredKey:
            raise Exception("key was not of type: " + str(typeOfDesiredKey) + ", got: " + str(type(key)))
        if isPublicNotPrivateKey:
            key_data = key.public_bytes(
                encoding=serialization.Encoding.PEM,
                format=serialization.PublicFormat.SubjectPublicKeyInfo
            )
        else:
            key_data = key.private_bytes(
                encoding=serialization.Encoding.PEM,
                format=serialization.PrivateFormat.PKCS8,
                encryption_algorithm=serialization.NoEncryption()
            )
        if pathToFile is not None:
            with open(pathToFile, "wb") as f:
                f.write(key_data)
        return key_data
    except Exception as e:
        print("Error in export_key:", e)
        return None


def import_key_from_data(key_data: bytes, typeOfDesiredKey: type, isPublicNotPrivateKey: bool):
    try:
        if isPublicNotPrivateKey:
            key_inner = serialization.load_pem_public_key(key_data)
        else:
            key_inner = serialization.load_pem_private_key(key_data, password=None)
        if type(key_inner) != typeOfDesiredKey:
            raise Exception("pem was in incompatible format, got: " + str(type(key_inner)))
        return key_inner
    except Exception as e:
        print("Error in import_key_from_data:", e)
        return None


def import_key_from_file(pathToFile: str, typeOfDesiredKey: type, isPublicNotPrivateKey: bool):
    try:
        with open(pathToFile, "rb") as f:
            key_data = f.read()
        if isPublicNotPrivateKey:
            key_inner = serialization.load_pem_public_key(key_data)
        else:
            key_inner = serialization.load_pem_private_key(key_data, password=None)
        if type(key_inner) != typeOfDesiredKey:
            raise Exception("pem was in incompatible format, got: " + str(type(key_inner)))
        return key_inner
    except Exception as e:
        print("Error in import_key_from_file:", e)
        return None


def export_object_to_pem(objectToExport, pathToFile: str):
    try:
        with open(pathToFile, "wb") as f:
            pickle.dump(objectToExport, f)
            return True
    except Exception as e:
        print("Error in export_object_to_pem:", e)
    return False


def import_object_from_pem(pathToFile: str):
    try:
        with open(pathToFile, "rb") as f:
            retObj = pickle.load(f)
            return retObj
    except Exception as e:
        print("Error in import_object_from_pem:", e)
    return None


class KeyTypes:
    __keyTypeMap = {
        "rs": {
            True: rsa_backend._RSAPublicKey,
            False: rsa_backend._RSAPrivateKey
        },
        "ds": {
            True: dsa_backend._DSAPublicKey,
            False: dsa_backend._DSAPrivateKey
        },
        "el": {
            True: dh_backend._DHPublicKey,
            False: dh_backend._DHPrivateKey
        }
    }

    @staticmethod
    def getClassForAlgorithmAndKeyType(algorithmName: str, isPublicNotPrivate: bool):
        firstEntry = algorithmName.lower()[:2]
        try:
            return KeyTypes.__keyTypeMap.get(firstEntry).get(isPublicNotPrivate)
        except Exception as e:
            return None


if __name__ == "__main__":
    class MyClass:
        def __init__(self, private_key, public_key, string1, string2):
            self.private_key = private_key
            self.public_key = public_key
            self.string1 = string1
            self.string2 = string2

    # Create an instance of MyClass
    private_key, public_key = generate_RSA_key()
    pk1, pk2 = generate_RSA_key()
    public_key = serialize_rsa_key_to_pem_public(public_key)
    private_key = serialize_rsa_key_to_pem(private_key)
    pk1 = serialize_rsa_key_to_pem(pk1)
    pk2 = serialize_rsa_key_to_pem_public(pk2)
    string1 = "sss"
    string2 = "..."
    my_object1 = MyClass(private_key, public_key, string1, string2)
    my_object2 = MyClass(pk1, pk2, string1, string2)

    # pathToPkl = r'files\my_object_dump.pkl'
    pathToPkl = r'files\my_object_dump.pem'

    # Dump the object using pickle
    with open(pathToPkl, 'wb') as file:
        pickle.dump([my_object1, my_object2], file)

    print("Object dumped successfully.")

    # Load the object from the pickle file
    with open(pathToPkl, 'rb') as file:
        list = pickle.load(file)

    # Deserialize the RSA keys from the PEM strings
    private_key1 = deserialize_rsa_key_from_pem(list[0].private_key)
    public_key1 = deserialize_rsa_key_from_pem_public(list[0].public_key)

    private_key2 = deserialize_rsa_key_from_pem(list[1].private_key)
    public_key2 = deserialize_rsa_key_from_pem_public(list[1].public_key)

    print(private_key1.private_numbers().p)
    print(private_key2.private_numbers().p)
    print(public_key1.public_numbers().n)
    print(public_key2.public_numbers().n)

    # # Update the object with the RSA key objects
    # my_object.private_key = private_key
    # my_object.public_key = public_key
    #
    # # Now you can work with the deserialized object
    # print(my_object.public_key)
    # print(my_object.private_key)

    msg = b"Sa suprotne polovineeeeeeeeeeeeeeeeeeeeeeeee"
    msgb = pickle.dumps(msg)
    msgr = pickle.loads(msgb)

    aa = MyClass(1, 2, 3, 4)
    bb = pickle.dumps(aa)
    cc = pickle.loads(bb)
    if isinstance(msgr, MyClass):
        print("OPAAAAAAAAAA")
    else:
        print("Nijeeee")
        print(type(msgr))
        print(type(msg))
        print(msgr)
        print(msg)
