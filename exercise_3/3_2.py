import os
from collections import Counter, defaultdict
import math
from typing import List, Dict, Tuple, Set, Generator, Iterable

# Paths to the data files
TRAIN_FILE = "de_text/de_text.train"
TEST_FILE = "de_text/de_text.test"

SPECIAL_BEGIN = "%^%"
SPECIAL_END = "%$%"

# Lambda values for interplocation
LAMBDA_0 = 1.0e-10
LAMBDA_1 = 0.01
LAMBDA_2 = 0.2
LAMBDA_3 = 1 - LAMBDA_0 - LAMBDA_1 - LAMBDA_2

def read_sentences(file_path: str) -> List[str]:
    """
    Read sentences from file and return a list of sentences
    """
    sentences = []
    with open(file_path, 'r', encoding='utf-8') as file:
        for line in file:
            line = line.split()
            line = line[2: -2]
            line = ' '.join(line)
            sentences.append(line)
    return sentences

def calculate_frequency(sentences: List[str]) -> Dict[str, int]:
    """
    Calculate the frequency of each word in the sentences
    """
    frequency = defaultdict(int)
    for sentence in sentences:
        words = sentence.split()
        for word in words:
            frequency[word] += 1
    return frequency


def calculate_bigram_frequency(sentences: List[str]) -> Dict[Tuple[str, str], int]:
    """
    Calculate the frequency of each bigram in the sentences
    """
    bigram_frequency = defaultdict(int)
    for sentence in sentences:
        words = sentence.split()
        if len(words) < 2:
            continue
        for i in range(len(words) - 1):
            bigram = (words[i], words[i+1])
            bigram_frequency[bigram] += 1
    return bigram_frequency

def calculate_trigram_frequency(sentences: List[str]) -> Dict[Tuple[str, str, str], int]:
    """
    Calculate the frequency of each trigram in the sentences
    """
    trigram_frequency = defaultdict(int)
    for sentence in sentences:
        words = sentence.split()
        if len(words) < 3:
            continue
        for i in range(len(words) - 2):
            trigram = (words[i], words[i+1], words[i+2])
            trigram_frequency[trigram] += 1
    return trigram_frequency


train_sentences = read_sentences(TRAIN_FILE)
test_sentences = read_sentences(TEST_FILE)

train_frequency = calculate_frequency(train_sentences)
train_vocab = set(train_frequency.keys())


test_frequency = calculate_frequency(test_sentences)
test_vocab = set(test_frequency.keys())
most_common_words = sorted(train_frequency.items(), key=lambda x: x[1], reverse=True)[:30]

print("""
Top 30 most common words from the training set:
--------------------------------
""")
for word, count in most_common_words:
    print(f"{word}", end=" ")
print("--------------------------------")


# UNseen tokens in the training set which are present in the test set
unseen_words = test_vocab - train_vocab

print("Percentage of tokens in the test data that have not been seen in the training data:")
print(f"{len(unseen_words) / len(test_vocab) * 100} %")


# Calculate bigram frequency
train_bigram_frequency = calculate_bigram_frequency(train_sentences)
train_bigram_vocab = set(train_bigram_frequency.keys())

print("Top 30 most common bigrams from the training set:")
print("--------------------------------")
for bigram, count in sorted(train_bigram_frequency.items(), key=lambda x: x[1], reverse=True)[:30]:
    print(f"{bigram[0]} {bigram[1]}", end=" ")
print("--------------------------------")


# Calculate bigram frequency for test set
test_bigram_frequency = calculate_bigram_frequency(test_sentences)

test_bigram_vocab = set(test_bigram_frequency.keys())

# Unseen bigrams in the test set
unseen_bigrams = test_bigram_vocab - train_bigram_vocab

print("Percentage of bigrams in the test data that have not been seen in the training data:")
print(f"{len(unseen_bigrams) / len(test_bigram_vocab) * 100} %")


# Calculate trigram frequency
train_trigram_frequency = calculate_trigram_frequency(train_sentences)
train_trigram_vocab = set(train_trigram_frequency.keys())

test_trigram_frequency = calculate_trigram_frequency(test_sentences)
test_trigram_vocab = set(test_trigram_frequency.keys())

# Unseen trigrams in the test set
unseen_trigrams = test_trigram_vocab - train_trigram_vocab

print("Percentage of trigrams in the test data that have not been seen in the training data:")
print(f"{len(unseen_trigrams) / len(test_trigram_vocab) * 100} %")


# Calculate how many sentences in the test data have zero probability under MLE bigram model
zero_probability_sentences = 0
total_test_sentences = len(test_sentences)

for sentence in test_sentences:
    words = sentence.split()
    if len(words) < 2:
        continue

    has_unseen_bigram = False
    for i in range(len(words) - 1):
        bigram = (words[i], words[i+1])
        if bigram not in train_bigram_vocab:
            has_unseen_bigram = True
            break

    if has_unseen_bigram:
        zero_probability_sentences += 1

print("\nMLE Bigram Model Analysis:")
print(f"Number of test sentences with zero probability: {zero_probability_sentences} out of {total_test_sentences}")
print(f"Percentage: {zero_probability_sentences / total_test_sentences * 100:.2f}%")


def interpolated_prob(word, prev_word=None, prev_prev_word=None, 
                      unigram_freq=None, total_words=0, 
                      bigram_freq=None, unigram_context_counts=None,
                      trigram_freq=None, bigram_context_counts=None):
    """Calculate interpolated probability using all models"""
    # 0-gram (uniform distribution)
    p_0gram = LAMBDA_0 * (1.0 / len(unigram_freq)) if unigram_freq else LAMBDA_0
    
    # Unigram probability P(w)
    p_unigram = LAMBDA_1 * (unigram_freq.get(word, 0) / total_words) if total_words > 0 else 0
    
    # Bigram probability P(w|prev_word)
    p_bigram = 0
    if prev_word is not None:
        prev_word_count = unigram_context_counts.get(prev_word, 0)
        if prev_word_count > 0:
            p_bigram = LAMBDA_2 * (bigram_freq.get((prev_word, word), 0) / prev_word_count)
    
    # Trigram probability P(w|prev_prev_word,prev_word)
    p_trigram = 0
    if prev_word is not None and prev_prev_word is not None:
        bigram_count = bigram_context_counts.get((prev_prev_word, prev_word), 0)
        if bigram_count > 0:
            p_trigram = LAMBDA_3 * (trigram_freq.get((prev_prev_word, prev_word, word), 0) / bigram_count)
    
    return p_0gram + p_unigram + p_bigram + p_trigram

def sentence_prob(sentence, unigram_freq, total_words, bigram_freq, unigram_context_counts, 
                 trigram_freq, bigram_context_counts):
    """Calculate sentence probability using interpolation model"""
    words = sentence.split()
    if len(words) < 1:
        return 0
    
    log_prob = 0
    
    # First word (only unigram and 0-gram)
    prob = interpolated_prob(words[0], None, None, unigram_freq, total_words, 
                           bigram_freq, unigram_context_counts, 
                           trigram_freq, bigram_context_counts)
    if prob > 0:
        log_prob += math.log(prob)
    else:
        return float('-inf')
        
    # Second word (only bigram, unigram and 0-gram)
    if len(words) > 1:
        prob = interpolated_prob(words[1], words[0], None, unigram_freq, total_words, 
                               bigram_freq, unigram_context_counts, 
                               trigram_freq, bigram_context_counts)
        if prob > 0:
            log_prob += math.log(prob)
        else:
            return float('-inf')
    
    # Rest of the words (full model)
    for i in range(2, len(words)):
        prob = interpolated_prob(words[i], words[i-1], words[i-2], 
                              unigram_freq, total_words, 
                              bigram_freq, unigram_context_counts, 
                              trigram_freq, bigram_context_counts)
        if prob > 0:
            log_prob += math.log(prob)
        else:
            return float('-inf')
    
    return log_prob

# Read training data
train_sentences = read_sentences(TRAIN_FILE)

# Read only the first 3 test sentences
test_sentences = read_sentences(TEST_FILE)

# Calculate all necessary frequencies
unigram_freq = calculate_frequency(train_sentences)
bigram_freq = calculate_bigram_frequency(train_sentences)
trigram_freq = calculate_trigram_frequency(train_sentences)

def calculate_unigram_context_counts(sentences: List[str]) -> Dict[str, int]:
    """Calculate unigram context counts"""
    counts = defaultdict(int)
    for sentence in sentences:
        words = sentence.split()
        for i in range(len(words)):
            counts[words[i]] += 1
    return counts

def calculate_bigram_context_counts(sentences: List[str]) -> Dict[Tuple[str, str], int]:
    """Calculate bigram context counts"""
    counts = defaultdict(int)
    for sentence in sentences:
        words = sentence.split()
        if len(words) < 2:
            continue
        for i in range(len(words) - 1):
            counts[(words[i], words[i+1])] += 1
    return counts
total_words = sum(unigram_freq.values())
unigram_context_counts = calculate_unigram_context_counts(train_sentences)
bigram_context_counts = calculate_bigram_context_counts(train_sentences)


# Calculate probabilities for the first 3 test sentences
print("\nLinear Interpolation Model Probabilities:")
print(f"λ0 = {LAMBDA_0}, λ1 = {LAMBDA_1}, λ2 = {LAMBDA_2}, λ3 = {LAMBDA_3}")
print("-" * 60)

for i, sentence in enumerate(test_sentences):
    log_prob = sentence_prob(sentence, unigram_freq, total_words, 
                            bigram_freq, unigram_context_counts, 
                            trigram_freq, bigram_context_counts)
    prob = math.exp(log_prob) if log_prob != float('-inf') else 0
    
    print(f"Sentence {i+1}: {sentence}")
    print(f"Log probability: {log_prob:.6f}")
    print(f"Probability: {prob:.10e}")
    print("-" * 60)















    