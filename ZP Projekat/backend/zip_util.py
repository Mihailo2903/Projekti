import pickle
import zipfile
import base64
import io


def zip_bytes(data):
    # Create a BytesIO object
    if isinstance(data, str):
        data = data.encode()
    buffer = io.BytesIO()

    # Create a ZipFile object with the buffer
    with zipfile.ZipFile(buffer, 'w', zipfile.ZIP_DEFLATED) as zip_file:
        # Write the data to the zip file
        zip_file.writestr('data', data)

    # Retrieve the compressed data from the buffer
    compressed_data = buffer.getvalue()

    # Encode the compressed data using radix64 (base64)
    # encoded_data = base64.b64encode(compressed_data)

    return compressed_data


def unzip_bytes(compressed_data):
    # Decode the radix64 (base64) encoded data
    # compressed_data = base64.b64decode(encoded_data)

    # Create a BytesIO object with the compressed data
    buffer = io.BytesIO(compressed_data)

    # Create a ZipFile object with the buffer
    with zipfile.ZipFile(buffer, 'r', zipfile.ZIP_DEFLATED) as zip_file:
        # Read the data from the zip file
        data = zip_file.read('data')

    return data


def encodeBytes(data):
    return base64.b64encode(data)


def decodeBytes(data):
    return base64.b64decode(data)


if __name__ == "__main__":
    # Example usage
    original_data = b'This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.This is some example data to compress and encode.'

    # Zip and encode the bytes
    encoded_data = zip_bytes(original_data)
    encoded_data = encodeBytes(encoded_data)

    # Decode and unzip the bytes
    encoded_data = decodeBytes(encoded_data)
    decoded_data = unzip_bytes(encoded_data)

    # Verify the decoded data
    print(decoded_data == original_data)

    data = "gASViwAAAAAAAABDh1BLAwQUAAAACADvetJW0JUS1R0AAAAgAAAABAAAAGRhdGFrYJkqygABPYIB+UWl2YkKSalVCpnJqemJXFP0AFBLAQIUABQAAAAIAO960lbQlRLVHQAAACAAAAAEAAAAAAAAAAAAAACAAQAAAABkYXRhUEsFBgAAAAABAAEAMgAAAD8AAAAAAJQu"
    data = decodeBytes(data)
    data = pickle.loads(data)
    data = unzip_bytes(data)
    data = pickle.loads(data)
    print(data)
