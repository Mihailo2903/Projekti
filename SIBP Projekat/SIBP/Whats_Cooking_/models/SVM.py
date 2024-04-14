import re
import pandas as pd
import unidecode as unidecode
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC

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

vectorizer = TfidfVectorizer()
train_ingredients_bow = vectorizer.fit_transform(train_ingredients)

X_train, X_val, y_train, y_val = train_test_split(train_ingredients_bow, train_cuisine, test_size=0.2, random_state=42)

clf = SVC(kernel='rbf', C=1000, degree=3, gamma=1, coef0=1, shrinking=True, tol=0.001,
          probability=False, cache_size=200, class_weight=None, verbose=True, max_iter=-1,  random_state=None)
clf.fit(X_train, y_train)

val_predictions = clf.predict(X_val)


test_data = pd.read_json('../input/whats-cooking-kernels-only/test.json')
test_ingredients = test_data['ingredients'].apply(lambda x: ' '.join(x))
test_ingredients_bow = vectorizer.transform(test_ingredients)

test_cuisine = clf.predict(test_ingredients_bow)

output = pd.DataFrame({'id': test_data['id'], 'cuisine': test_cuisine})
output.to_csv('svm_submission.csv', index=False)
