import re

import pandas as pd
import unidecode as unidecode
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB

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

vectorizer = TfidfVectorizer(sublinear_tf=True)
train_ingredients_bow = vectorizer.fit_transform(train_ingredients)

clf = MultinomialNB()
clf.fit(train_ingredients_bow, train_cuisine)

test_data = pd.read_json('../input/whats-cooking-kernels-only/test.json')

test_ingredients = test_data['ingredients'].apply(lambda x: ' '.join(x))

test_ingredients_bow = vectorizer.transform(test_ingredients)

test_cuisine = clf.predict(test_ingredients_bow)

output = pd.DataFrame({'id': test_data['id'], 'cuisine': test_cuisine})
output.to_csv('submission.csv', index=False)