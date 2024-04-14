import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Assuming your CSV file is named file.csv
file_path = 'submission.csv'

# Read data from the file
df = pd.read_csv(file_path)

# Set a color palette for the bars
palette = sns.color_palette("Set3")

# Set figure size and rotate x-axis labels
plt.figure(figsize=(16, 7))  # Adjust the height to make more room for x-axis labels
plt.xticks(rotation=60, ha="right")  # Rotate labels to make them more readable

# Plot the colorful bar graph
ax = sns.countplot(x='cuisine', data=df, order=df['cuisine'].value_counts().index, palette=palette)

# Set labels and save the plot to a PNG file
ax.set_title('Cuisine Distribution - Naive Bayes')
ax.set_ylabel('Number of Dishes')
ax.set_xlabel('')

# Add annotations above each bar
for p in ax.patches:
    ax.annotate(f'{p.get_height()}', (p.get_x() + p.get_width() / 2., p.get_height()),
                ha='center', va='center', xytext=(0, 10), textcoords='offset points')


# Adjust layout for better visibility of x-axis labels
plt.tight_layout()

# Save the plot to a PNG file
plt.savefig('cuisine_distribution.png')

# Show the plot
plt.show()
