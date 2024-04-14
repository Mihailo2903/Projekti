import datetime
import os
import pathlib
import string
from typing import Union, List
import pickle

from backend.aes_util import *
from backend.cast5_util import *
from backend.dsa_util import *
from backend.elgamal_util import *
from backend.hash_util import *
from backend.pem_util import *
from backend.rsa_util import *
from backend.zip_util import *

projectName: string = 'ZP_projekat'


class Signature:
    def __init__(self, message, digest, keyID):
        self.message: bytes = message
        self.digest: bytes = digest
        self.keyID: bytes = keyID


class Crypted:
    def __init__(self, compressed, sessionKey, keyID, symAlgoId):
        self.compressed: bytes = compressed     # pickle: Signature -> byte
        self.sessionKey: bytes = sessionKey
        self.keyID: bytes = keyID
        self.SymAlgorithmID: int = symAlgoId    # 0 - cast5, 1 - aes, -1 - error


class PublicRingToken:
    __curr_index = 0

    @staticmethod
    def setCurrIndex(maxIndex: int):
        PublicRingToken.__curr_index = maxIndex

    @staticmethod
    def getCurrIndex():
        return PublicRingToken.__curr_index

    def __init__(self, username: string, mail: string, idAsymAlgo: int, idPublicKey: int,  publicKey: bytes, passwordHash):
        self.username = username
        self.mail = mail
        self.idAsymAlgo = idAsymAlgo
        self.idPublicKey = idPublicKey
        self.publicKey = publicKey
        self.__passwordHash = passwordHash
        self.index = PublicRingToken.__curr_index
        self.timestamp = datetime.datetime.now()
        PublicRingToken.__curr_index += 1

    def passwordHashMatches(self, checkPasswordHash):
        return checkPasswordHash == self.__passwordHash


class PrivateRingToken:
    __curr_index = 0

    @staticmethod
    def setCurrIndex(maxIndex: int):
        PrivateRingToken.__curr_index = maxIndex

    @staticmethod
    def getCurrIndex():
        return PrivateRingToken.__curr_index

    def __init__(self, username: string, mail: string, idAsymAlgo: int, idPublicKey: int,  publicKey: bytes, encryptedPrivateKey: bytes, passwordHash):
        self.username = username
        self.mail = mail
        self.idAsymAlgo = idAsymAlgo
        self.idPublicKey = idPublicKey
        self.publicKey = publicKey
        self.encryptedPrivateKey = encryptedPrivateKey
        self.__passwordHash = passwordHash
        self.index = PrivateRingToken.__curr_index
        self.timestamp = datetime.datetime.now()
        PrivateRingToken.__curr_index += 1

    def passwordHashMatches(self, checkPasswordHash):
        return checkPasswordHash == self.__passwordHash


"""
asymAlgoId: 0 - rsa, 1 - dsa, 2 - elgamal
"""


def createSignature(message, asymKeyId: int, asymAlgoId: int, asymKey):
    encrypted_hash = generate_hash(message)
    if asymAlgoId == 0:
        encrypted_hash = RSA_make_signature(encrypted_hash, asymKey)
    elif asymAlgoId == 1:
        encrypted_hash = DSA_make_signature(encrypted_hash,asymKey)
    else:
        raise Exception("invalid asymAlgoId: " + str(asymAlgoId))

    signature = Signature(message, encrypted_hash, asymKeyId)
    return signature


"""
symAlgoId: 0 - aes, 1 - cast5
asymAlgoId: 0 - rsa, 1 - dsa, 2 - elgamal
"""


def encryptMessage(compressedMessage: bytes, asymKeyId: int, asymAlgoId: int, asymKey, symAlgoId: int):
    if symAlgoId == 0:
        sessionKey = generate_AES_128_key()
        message = AES_128_encrypt(compressedMessage, sessionKey)
    elif symAlgoId == 1:
        sessionKey = generate_CAST5_key()
        message = CAST5_encrypt(compressedMessage, sessionKey)
    else:
        raise Exception("inavlid symAlgoId: " + str(symAlgoId))

    if asymAlgoId == 0:
        sessionKey = RSA_encrypt(sessionKey, asymKey)
    elif asymAlgoId == 2 or asymAlgoId == 3:
        sessionKey = El_Gamal_encrypt(sessionKey, asymKey)
    else:
        raise Exception("inavlid asymAlgoId: " + str(asymAlgoId))

    ciphertext = Crypted(message, sessionKey, asymKeyId, symAlgoId)
    return ciphertext


def sendMessageUtil(message: Union[str, bytes], privateRing: PrivateRingToken, publicRing: PublicRingToken, needSignature: bool, needEncryption: bool, passwordHash, symAlgoId: int, absPath: str = None):
    if needSignature:
        try:
            private_key_pem = AES_128_decrypt(privateRing.encryptedPrivateKey, makeKeyForAesFromHash(passwordHash))
            sig = "\n\nThis message is signed by " + privateRing.username + ", " + privateRing.mail
            if isinstance(message, bytes):
                sig = sig.encode()
            message = message + sig
        except Exception:
            print("invalid password")
            return None
        if privateRing.idAsymAlgo == 0:
            algoName = "RSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, False)
            private_key = import_key_from_data(private_key_pem, typeOfAlgoKey, False)
            message = createSignature(message, privateRing.idPublicKey, privateRing.idAsymAlgo, private_key)
        elif privateRing.idAsymAlgo == 1:
            algoName = "DSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, False)
            private_key = import_key_from_data(private_key_pem, typeOfAlgoKey, False)
            message = createSignature(message, privateRing.idPublicKey, privateRing.idAsymAlgo, private_key)
        else:
            raise Exception("inavlid asymAlgoId: " + str(privateRing.idAsymAlgo))

    message = pickle.dumps(message)
    message = zip_bytes(message)

    if needEncryption:
        public_key_pem = publicRing.publicKey
        if publicRing.idAsymAlgo == 0:
            algoName = "RSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, True)
            public_key = import_key_from_data(public_key_pem, typeOfAlgoKey, True)
            message = encryptMessage(message, publicRing.idPublicKey, publicRing.idAsymAlgo, public_key, symAlgoId)
        elif publicRing.idAsymAlgo == 2 or publicRing.idAsymAlgo == 3:
            algoName = "ElGamal"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, True)
            public_key = import_key_from_data(public_key_pem, typeOfAlgoKey, True)
            message = encryptMessage(message, publicRing.idPublicKey, publicRing.idAsymAlgo, public_key, symAlgoId)

    message = pickle.dumps(message)
    message = encodeBytes(message)

    if absPath is not None:
        with open(absPath, "wb") as f:
            f.write(message)

    return message


# ------------------------------------------------------------------------------------------------


"""
symAlgoId: 0 - aes, 1 - cast5
asymAlgoId: 0 - rsa, 1 - dsa, 2 - elgamal
"""


def decryptMessage(cipherMessage: Crypted, privateRingTokenList: List[PrivateRingToken], passwordHash: bytes):
    try:
        privateRing = [temp for i, temp in enumerate(privateRingTokenList) if temp.idPublicKey == cipherMessage.keyID][0]
        try:
            private_key_pem = AES_128_decrypt(privateRing.encryptedPrivateKey, makeKeyForAesFromHash(passwordHash))
        except Exception:
            print("invalid password")
            return None
        if privateRing.idAsymAlgo == 0:
            algoName = "RSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, False)
            private_key = import_key_from_data(private_key_pem, typeOfAlgoKey, False)
            session_Key = RSA_decrypt(cipherMessage.sessionKey, private_key)
        elif privateRing.idAsymAlgo == 2 or privateRing.idAsymAlgo == 3:
            algoName = "ElGamal"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, False)
            private_key = import_key_from_data(private_key_pem, typeOfAlgoKey, False)
            session_Key = El_Gamal_decrypt(cipherMessage.sessionKey, private_key)
        else:
            raise Exception("inavlid asymAlgoId: " + str(privateRing.idAsymAlgo))

        if cipherMessage.SymAlgorithmID == 0:
            message = AES_128_decrypt(cipherMessage.compressed, session_Key)
        elif cipherMessage.SymAlgorithmID == 1:
            message = CAST5_decrypt(cipherMessage.compressed, session_Key)
        else:
            raise Exception("inavlid symAlgoId: " + str(privateRing.idAsymAlgo))
        return message
    except Exception as e:
        print("error in method: decryptMessage")
        return None


def verifySignature(signature: Signature, publicRingTokenList: List[PublicRingToken]):
    try:
        publicRing = [temp for i, temp in enumerate(publicRingTokenList) if temp.idPublicKey == signature.keyID][0]
        cipheredDigest = signature.digest

        if publicRing.idAsymAlgo == 0:
            algoName = "RSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, True)
            public_key = import_key_from_data(publicRing.publicKey, typeOfAlgoKey, True)
            valid = RSA_verify_signature(generate_hash(signature.message), cipheredDigest, public_key)
        elif publicRing.idAsymAlgo == 1:
            algoName = "DSA"
            typeOfAlgoKey = KeyTypes.getClassForAlgorithmAndKeyType(algoName, True)
            public_key = import_key_from_data(publicRing.publicKey, typeOfAlgoKey, True)
            valid = DSA_verify_signature(generate_hash(signature.message), cipheredDigest, public_key)
        else:
            raise Exception("inavlid asymAlgoId: " + str(publicRing.idAsymAlgo))
        if valid is True:
            return valid, "Successfully verified signature made by " + publicRing.username + ", " + publicRing.mail
    except Exception as e:
        print("error in method: verifySignature:", str(e))
    return False, "Error in verifying signature"


def receiveMessageUtil(cipherMessage: bytes, privateRingTokenList: List[PrivateRingToken], publicRingTokenList: List[PublicRingToken], passwordHash: bytes):
    cipherMessage = pickle.loads(decodeBytes(cipherMessage))
    if isinstance(cipherMessage, Crypted):
        try:
            cipherMessage = decryptMessage(cipherMessage, privateRingTokenList, passwordHash)
        except Exception:
            print("error in decrypting message")
            return None

    if cipherMessage is None:
        raise Exception("Invalid password")

    cipherMessage = pickle.loads(unzip_bytes(cipherMessage))

    status = None
    if isinstance(cipherMessage, Signature):
        valid, status = verifySignature(cipherMessage, publicRingTokenList)
        cipherMessage = cipherMessage.message
        if valid is False:
            cipherMessage += '\n-signature not verified!\n'

    return cipherMessage, status


def getIdFromPublicKey(publicKey):
    if isinstance(publicKey, rsa_backend._RSAPublicKey):
        return publicKey.public_numbers().n & ((1 << 64) - 1)
    elif isinstance(publicKey, dh_backend._DHPublicKey):
        return publicKey.public_numbers().y & ((1 << 64) - 1)
    elif isinstance(publicKey, dsa_backend._DSAPublicKey):
        return publicKey.public_numbers().y & ((1 << 64) - 1)
    else:
        raise Exception("wrong public key")


def addPairOfKeys(username, mail, passwordHash, asymAlgoId, privateRingTokenList: List[PrivateRingToken], publicRingTokenList: List[PublicRingToken]):
    if asymAlgoId == 0:
        private_key, public_key = generate_RSA_key()
        publicKeyId = getIdFromPublicKey(public_key)
        algoName = "RSA"
    elif asymAlgoId == 1:
        private_key, public_key = generate_DSA_key()
        publicKeyId = getIdFromPublicKey(public_key)
        algoName = "DSA"
    elif asymAlgoId == 2:
        private_key, public_key = generate_El_Gamal_Key(1024)
        publicKeyId = getIdFromPublicKey(public_key)
        algoName = "ElGamal"
    elif asymAlgoId == 3:
        private_key, public_key = generate_El_Gamal_Key(2048)
        publicKeyId = getIdFromPublicKey(public_key)
        algoName = "ElGamal"
    else:
        raise Exception("inavlid asymAlgoId: " + str(asymAlgoId))

    public_key = export_key(public_key, KeyTypes.getClassForAlgorithmAndKeyType(algoName, True), True)
    private_key = export_key(private_key, KeyTypes.getClassForAlgorithmAndKeyType(algoName, False), False)

    private_key = AES_128_encrypt(private_key, makeKeyForAesFromHash(passwordHash))

    # IdAsymAlgo = asymAlgoId  # el gamal 1024 = 2, el gamal 2048 = 3, 2 for both in ring
    # if IdAsymAlgo == 3:
    #     IdAsymAlgo = 2

    privateRingToken = PrivateRingToken(username, mail, asymAlgoId, publicKeyId, public_key, private_key, passwordHash)
    publicRingToken = PublicRingToken(username, mail, asymAlgoId, publicKeyId, public_key, passwordHash)

    privateRingTokenList.append(privateRingToken)
    publicRingTokenList.append(publicRingToken)


# ----------------------------------------------------------------------------------------------------------------------

# Only Interface should be imported to the frontend

class Interface:
    __publicRingList: List[PublicRingToken] = list()
    __privateRingList: List[PrivateRingToken] = list()

    @staticmethod
    def __initConfig():
        basePath = str(pathlib.Path().resolve().absolute())
        indexOfProjectPathEnd = basePath.find(projectName)
        currPath = basePath[:indexOfProjectPathEnd + len(projectName)] + r"\backend\files\config.pkl"
        isExisting = os.path.exists(currPath)
        if not isExisting:
            Interface.insertKeyIntoRings("pera", "pera@gmail", Interface.generateHash("pera123"), 0)
            Interface.insertKeyIntoRings("zika", "zika@gmail", Interface.generateHash("zika123"), 0)
            Interface.insertKeyIntoRings("maka", "maka@gmail", Interface.generateHash("maka123"), 1)
            Interface.insertKeyIntoRings("zaka", "zaka@gmail", Interface.generateHash("zaka123"), 1)
            Interface.insertKeyIntoRings("paka", "paka@gmail", Interface.generateHash("paka123"), 2)
            #Interface.insertKeyIntoRings("raka", "raka@gmail", Interface.generateHash("raka123"), 3)
            export_object_to_pem([Interface.__privateRingList, Interface.__publicRingList], currPath)
            Interface.__resetRings()

    @staticmethod
    def __resetRings():
        Interface.__privateRingList = []
        Interface.__publicRingList = []

    @staticmethod
    def loadConfig():
        Interface.__initConfig()
        basePath = str(pathlib.Path().resolve().absolute())
        indexOfProjectPathEnd = basePath.find(projectName)
        currPath = basePath[:indexOfProjectPathEnd + len(projectName)] + r"\backend\files\config.pkl"
        listOfRings = import_object_from_pem(currPath)
        Interface.__privateRingList = listOfRings[0]
        Interface.__publicRingList = listOfRings[1]

        maxIndex = max(max([ring.index for ring in Interface.__privateRingList]), max([ring.index for ring in Interface.__publicRingList])) + 1
        PublicRingToken.setCurrIndex(maxIndex)
        PrivateRingToken.setCurrIndex(maxIndex)


    @staticmethod
    def storeConfig():
        basePath = str(pathlib.Path().resolve().absolute())
        indexOfProjectPathEnd = basePath.find(projectName)
        currPath = basePath[:indexOfProjectPathEnd + len(projectName)] + r"\backend\files\config.pkl"
        export_object_to_pem([Interface.__privateRingList, Interface.__publicRingList], currPath)

    @staticmethod
    def getPublicRing():
        return Interface.__publicRingList.copy()

    @staticmethod
    def getPrivateRing():
        return Interface.__privateRingList.copy()

    @staticmethod
    def sendMessage(
        plaintext: Union[str, bytes],
        privateRingPublicKeyId: int,
        publicRingPublicKeyId: int,
        needsSignature: bool,
        needsEncryption: bool,
        passwordHash,
        symmetricAlgoId: int,
        absolutePath: str = None
    ):
        if needsSignature:
            privateRingToken = [ring for ring in Interface.__privateRingList if ring.idPublicKey == privateRingPublicKeyId][0]
        else:
            privateRingToken = None
        if needsEncryption:
            publicRingToken = [ring for ring in Interface.__publicRingList if ring.idPublicKey == publicRingPublicKeyId][0]
        else:
            publicRingToken = None
        return sendMessageUtil(
            plaintext,
            privateRingToken,
            publicRingToken,
            needsSignature,
            needsEncryption,
            passwordHash,
            symmetricAlgoId,
            absolutePath
        )

    @staticmethod
    def receiveMessageFromCiphertext(ciphertext: bytes, passwordHash):
        try:
            plaintext, status = receiveMessageUtil(
                ciphertext,
                Interface.__privateRingList,
                Interface.__publicRingList,
                passwordHash
            )
            return plaintext
        except Exception as e:
            print("Exception in method receiveMessageFromCiphertext: " + str(e))
            return str(e)

    @staticmethod
    def receiveMessageFromFile(pathSrc: str, passwordHash, pathDest: str = None):
        try:
            with open(pathSrc, "rb") as f:
                ciphertext_inner = f.read()
            plaintext, status = receiveMessageUtil(
                ciphertext_inner,
                Interface.__privateRingList,
                Interface.__publicRingList,
                passwordHash
            )
            if pathDest is not None:
                with open(pathDest, "w") as f:
                    if type(plaintext) == bytes:
                        plaintext = plaintext.decode('utf-8')
                    f.write(plaintext)
                    return status
            else:
                return status
        except Exception as e:
            print("Exception in method receiveMessageFromFile: " + str(e))
            return str(e)

    @staticmethod
    def insertKeyIntoRings(username, mail, passwordHash, asymAlgoId):
        try:
            addPairOfKeys(username, mail, passwordHash, asymAlgoId, Interface.__privateRingList, Interface.__publicRingList)
            return True
        except Exception as e:
            print(e)
            return False

    @staticmethod
    def deleteKeyFromRings(idPublicKeyForDel: int, passwordHash):
        try:
            pubRingToken = [ring for ring in Interface.__publicRingList if ring.idPublicKey == idPublicKeyForDel][0]
            privRingToken = [ring for ring in Interface.__privateRingList if ring.idPublicKey == idPublicKeyForDel][0]

            if pubRingToken.passwordHashMatches(passwordHash) and privRingToken.passwordHashMatches(passwordHash):
                Interface.__publicRingList.remove(pubRingToken)
                Interface.__privateRingList.remove(privRingToken)
                return True
            else:
                print("passwordHash did not match")
        except Exception as err:
            print("Error while deleting key from rings, error:", err)
        return False

    @staticmethod
    def exportKeyPairFromRings(idPublicKey: int, pathToFile: str):
        try:
            pubRingToken = [ring for ring in Interface.__publicRingList if ring.idPublicKey == idPublicKey][0]
            privRingToken = [ring for ring in Interface.__privateRingList if ring.idPublicKey == idPublicKey][0]
            objToExport = [pubRingToken, privRingToken]
            return export_object_to_pem(objToExport, pathToFile)
        except Exception as err:
            print("Error while exporting private key from rings, error:", err)
        return None

    @staticmethod
    def importKeyPairFromRings(pathToFile: str):
        try:
            retMsg: str = ""
            importedListRingTokens = import_object_from_pem(pathToFile)
            importedPublicRingToken: PublicRingToken = importedListRingTokens[0]
            importedPrivateRingToken: PrivateRingToken = importedListRingTokens[1]
            if len([ring for ring in Interface.__publicRingList if
                    ring.idPublicKey == importedPublicRingToken.idPublicKey]) == 0:
                Interface.__publicRingList.append(importedPublicRingToken)
            else:
                print("public key already in ring")
                retMsg += "public key already in ring"
            if len([ring for ring in Interface.__privateRingList if
                    ring.idPublicKey == importedPrivateRingToken.idPublicKey]) == 0:
                Interface.__privateRingList.append(importedPrivateRingToken)
            else:
                print("private key already in ring")
                retMsg += ", " if retMsg != "" else ""
                retMsg += "private key already in ring"
            return True if retMsg == "" else retMsg
        except Exception as e:
            return "Failed import, error: " + str(e)

    @staticmethod
    def exportPublicKey(idPublicKey: int, pathToFile: str):
        try:
            pubRingToken = [ring for ring in Interface.__publicRingList if ring.idPublicKey == idPublicKey][0]
            return export_object_to_pem(pubRingToken, pathToFile)
        except Exception as err:
            print("Error while exporting public key from rings, error:", err)
        return None

    @staticmethod
    def importPublicKey(pathToFile: str):
        try:
            importedPublicRingToken: PublicRingToken = import_object_from_pem(pathToFile)
            if len([ring for ring in Interface.__publicRingList if
                    ring.idPublicKey == importedPublicRingToken.idPublicKey]) == 0:
                Interface.__publicRingList.append(importedPublicRingToken)
                return True
            else:
                print("public key already in ring")
            return "public key already in ring"
        except Exception as e:
            return "Failed import, error: " + str(e)

    @staticmethod
    def generateHash(password):
        return generate_hash(password)

    @staticmethod
    def decodeBytes(data: bytes):
        try:
            return data.decode("utf-8")
        except Exception as e:
            return str(e)

    @staticmethod
    def formSampleStringFromRings(ring: Union[PublicRingToken, PrivateRingToken], asymmetricAlgorithms: List[str]):
        return ring.username + ", " + ring.mail + ", " + asymmetricAlgorithms[ring.idAsymAlgo] + ", " + str(ring.idPublicKey)

# ----------------------------------------------------------------------------------------------------------------------


if __name__ == "__main__":
    try:
        message1 = b"Teletoviccccccccccccccccccccccccc"
        message2 = b"Sa parkingaaaaaaaaaaaaaaaaaaaaaaaaa"

        Interface.loadConfig()

        # crypted = sendMessage(message1,privateRingList[0],publicRingList[1],True,True,generate_hash("pera123"),0,None)
        # print(crypted)
        # decrypted = receiveMessage(crypted, privateRingList, publicRingList, generate_hash("zika123"))
        # print(decrypted)

        basePath = str(pathlib.Path().resolve().absolute())
        indexOfProjectPathEnd = basePath.find(projectName)
        basePath = basePath[:indexOfProjectPathEnd + len(projectName)] + r"\backend\files\config.pkl"

        srcPath = basePath + r"\backend\files\aaa.pem"
        destPath = basePath + r"\backend\files\aaa.txt"

        srcPath1 = basePath + r"backend\files\aaa_incorrect.pem"
        destPath1 = basePath + r"\backend\files\aaa_incorrect.txt"

        crypted = Interface.sendMessage(message2, 2, 3, True, True, Interface.generateHash("maka123"), 1, srcPath)
        # crypted2 = Interface.sendMessage(message2, 2, 3, False, False, Interface.generateHash("maka123"), 1)

        # print("crypted == crypted2 ? -", crypted == crypted2)

        # if crypted:
        #     print(crypted)
        # else:
        #     print("error in ecrypting")
        #     raise Exception()

        # decrypted = Interface.receiveMessageFromCiphertext(crypted, generate_hash("maka123"))

        # decrypted = Interface.receiveMessageFromFile(srcPath, Interface.generateHash("maka123"), destPath)
        # decrypted = Interface.receiveMessageFromFile(srcPath, Interface.generateHash("maka123"), destPath)
        #
        # if decrypted:
        #     print(decrypted)
        # else:
        #     print("error in decrypting")
        #     raise Exception()
    except Exception as e:
        print("ERROR", e)
