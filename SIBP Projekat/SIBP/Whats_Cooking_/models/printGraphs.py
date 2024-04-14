import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Assuming your CSV files are named file1.csv, file2.csv, and file3.csv
file_paths = ['submission.csv', 'svm_submission.csv', 'nn_submission.csv']
model_names = ['Naive Bayes', 'Support Vector Machine', 'Neural Network']

# Read data from each file
dfs = [pd.read_csv(file_path) for file_path in file_paths]

# Set up a color palette for the bars
palette = sns.color_palette("Set3", n_colors=len(dfs))

# Initialize a subplot with 3 subplots (3 rows, 1 column)
fig, axs = plt.subplots(3, 1, figsize=(8, 15), sharex=True)

# Plot bar graphs for each file
for i, df in enumerate(dfs):
    # Count the number of dishes for each cuisine
    cuisine_counts = df['cuisine'].value_counts()

    # Plot the bar graph
    sns.barplot(x=cuisine_counts.index, y=cuisine_counts.values, ax=axs[i], palette=palette)

    for bar, cuisine in zip(axs[i].patches, cuisine_counts.index):
        height = bar.get_height()
        axs[i].text(
            bar.get_x() + bar.get_width() / 2,
            height,
            cuisine,
            ha='center',
            va='bottom',
            rotation=45,  # Rotate the text for better visibility
            color='black'  # Set text color to black
        )
    # Set titles and labels
    axs[i].set_title(f'{0}'.format(model_names[i]))
    axs[i].set_ylabel('Number of Dishes')

# Set common x-axis label
axs[-1].set_xlabel('Cuisine')

# Adjust layout and save the figure as a PNG file
plt.tight_layout()
plt.savefig('bar_graphs.png')
plt.show()
