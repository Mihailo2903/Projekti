import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB

train_data = pd.read_json('../input/whats-cooking-kernels-only/train.json')

# Extract the ingredients and cuisine columns
train_ingredients = train_data['ingredients'].apply(lambda x: ' '.join(x))
train_cuisine = train_data['cuisine']

# Convert the ingredients into a bag-of-words representation
vectorizer = CountVectorizer()
train_ingredients_bow = vectorizer.fit_transform(train_ingredients)

# Train the Naive Bayes classifier
clf = MultinomialNB()
clf.fit(train_ingredients_bow, train_cuisine)

# Load the test data
test_data = pd.read_json('../input/whats-cooking-kernels-only/test.json')

# Extract the ingredients column
test_ingredients = test_data['ingredients'].apply(lambda x: ' '.join(x))

# Convert the ingredients into a bag-of-words representation
test_ingredients_bow = vectorizer.transform(test_ingredients)

# Predict the cuisine for each test dish
test_cuisine = clf.predict(test_ingredients_bow)

# Save the predictions to a file
output = pd.DataFrame({'id': test_data['id'], 'cuisine': test_cuisine})
output.to_csv('NaiveBayesNoPreprocess.csv', index=False)


