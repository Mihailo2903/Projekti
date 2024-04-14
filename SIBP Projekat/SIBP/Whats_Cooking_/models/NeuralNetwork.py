import re
import pandas as pd
import unidecode as unidecode
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import accuracy_score, classification_report
from keras.models import Sequential
from keras.layers import Embedding, Flatten, Dense
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

def preprocess(ingredients):
    ingredients = ingredients.lower()
    ingredients = re.sub('[,\.!?:()"]', '', ingredients)
    ingredients = re.sub('[^a-zA-Z"]', ' ', ingredients)
    words = []
    for word in ingredients.split():
        word = re.sub((r'\b(oz|ounc|ounce|pound|lb|inch|inches|kg|to)\b'), ' ', word)
        if len(word) <= 2: continue
        word = unidecode.unidecode(word)
        if len(word) > 0: words.append(word)
    return ' '.join(words)

train_data = pd.read_json('../input/whats-cooking-kernels-only/train.json')

train_ingredients = train_data['ingredients'].apply(lambda x: ' '.join(x))
train_ingredients = train_ingredients.apply(preprocess)
train_cuisine = train_data['cuisine']

label_encoder = LabelEncoder()
train_cuisine_encoded = label_encoder.fit_transform(train_cuisine)

tokenizer = Tokenizer()
tokenizer.fit_on_texts(train_ingredients)
total_words = len(tokenizer.word_index) + 1

train_sequences = tokenizer.texts_to_sequences(train_ingredients)

max_sequence_length = max(len(seq) for seq in train_sequences)
train_padded_sequences = pad_sequences(train_sequences, maxlen=max_sequence_length, padding='post')

X_train, X_val, y_train, y_val = train_test_split(train_padded_sequences, train_cuisine_encoded, test_size=0.2, random_state=42)

model = Sequential()
model.add(Embedding(total_words, 85, input_length=max_sequence_length))
model.add(Flatten())
model.add(Dense(128, activation='sigmoid'))
model.add(Dense(len(label_encoder.classes_), activation='softmax'))

model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

model.fit(X_train, y_train, epochs=4, batch_size=32, validation_data=(X_val, y_val))

val_probabilities = model.predict(X_val)
val_predictions = val_probabilities.argmax(axis=-1)
accuracy = accuracy_score(y_val, val_predictions)
print(f'Accuracy: {accuracy}')
print(classification_report(y_val, val_predictions))


test_data = pd.read_json('../input/whats-cooking-kernels-only/test.json')
test_ingredients = test_data['ingredients'].apply(lambda x: ' '.join(x))
test_sequences = tokenizer.texts_to_sequences(test_ingredients)
test_padded_sequences = pad_sequences(test_sequences, maxlen=max_sequence_length, padding='post')

test_probabilities = model.predict(test_padded_sequences)
test_cuisine_encoded = test_probabilities.argmax(axis=-1)

test_cuisine = label_encoder.inverse_transform(test_cuisine_encoded)

output = pd.DataFrame({'id': test_data['id'], 'cuisine': test_cuisine})
output.to_csv('nn_submission.csv', index=False)
