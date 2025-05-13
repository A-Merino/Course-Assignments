import re
from csv import writer, reader
import requests
from lxml import html

def alice_html():
    try:
        response = requests.get("https://www.gutenberg.org/files/11/old/alice30.txt")
        return response.content.decode()
    except Exception as e:
        print(e)


def read_words(filename, skip_until):
    stop_words = [
    "a", "about", "above", "across", "after", "afterwards", "again", "against",
    "all", "almost", "alone", "along", "already", "also", "although", "always",
    "am", "among", "amongst", "amoungst", "amount", "an", "and", "another",
    "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are",
    "around", "as", "at", "back", "be", "became", "because", "become",
    "becomes", "becoming", "been", "before", "beforehand", "behind", "being",
    "below", "beside", "besides", "between", "beyond", "bill", "both",
    "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con",
    "could", "couldnt", "cry", "de", "describe", "detail", "do", "done",
    "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else",
    "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone",
    "everything", "everywhere", "except", "few", "fifteen", "fifty", "fill",
    "find", "fire", "first", "five", "for", "former", "formerly", "forty",
    "found", "four", "from", "front", "full", "further", "get", "give", "go",
    "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter",
    "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his",
    "how", "however", "hundred", "i", "ie", "if", "in", "inc", "indeed",
    "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter",
    "latterly", "least", "less", "ltd", "made", "many", "may", "me",
    "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly",
    "move", "much", "must", "my", "myself", "name", "namely", "neither",
    "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone",
    "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on",
    "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our",
    "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps",
    "please", "put", "rather", "re", "same", "see", "seem", "seemed",
    "seeming", "seems", "serious", "several", "she", "should", "show", "side",
    "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone",
    "something", "sometime", "sometimes", "somewhere", "still", "such",
    "system", "take", "ten", "than", "that", "the", "their", "them",
    "themselves", "then", "thence", "there", "thereafter", "thereby",
    "therefore", "therein", "thereupon", "these", "they", "thick", "thin",
    "third", "this", "those", "though", "three", "through", "throughout",
    "thru", "thus", "to", "together", "too", "top", "toward", "towards",
    "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us",
    "very", "via", "was", "we", "well", "were", "what", "whatever", "when",
    "whence", "whenever", "where", "whereafter", "whereas", "whereby",
    "wherein", "whereupon", "wherever", "whether", "which", "while", "whither",
    "who", "whoever", "whole", "whom", "whose", "why", "will", "with",
    "within", "without", "would", "yet", "you", "your", "yours", "yourself",
    "yourselves"
]
    word_set = set()
    input_file = open(filename, "r")
    skip = True
    book_dict = dict()
    chapter_num = ""
    chapter_name = ""
    find_chapter_name = False

    for line in input_file :
        line = line.strip()
        if not skip :          
            if find_chapter_name and len(line) > 1:
                find_chapter_name = False
                chapter_name = line
            if line.find("CHAPTER") >= 0 or line.find("THE END") >= 0:     
                if len(word_set) > 11:
                    book_dict[": ".join((chapter_num, chapter_name))] = list(word_set)
                    word_set.clear()
                chapter_num = line
                find_chapter_name = True
            # Use any character other than a-z or A-Z as word delimiters.
            parts = re.split("[^a-zA-Z]+", line) # notice this unique split function from the re module
            for word in parts :
                if len(word) > 0 and word not in stop_words:
                    word_set.add(word.lower())
        elif line.find(skip_until) >= 0 :
            skip = False

    input_file.close()
    return book_dict

def alice():
    source = alice_html()

    with open("alice30.txt", "w") as alice_file:
        alice_file.write(source)

    story_words = read_words("alice30.txt", "*END*")
    print(f"{'CHAPTER':20}{'CHAPTER TITLE':40}{'UNIQUE WORD COUNT'}")
    for chapter in story_words.items():
        print(f"{chapter[0].split(': ')[0]:20}{chapter[0].split(': ')[1]:40}{len(chapter[1]):^15}")


# alice()






def q2a():

    very_dict = dict()

    with open("very_words.txt", "r") as very_file:
        line = very_file.readline()
        while line != "":
            very_dict[line.split()[1]] = line.split()[3]
            line = very_file.readline()

    very_file.close()
    sentence = input("Type a sentence to improve your vocabulary: ")
    print(fix_sent(sentence.split(), very_dict))


def fix_sent(sent_words, very_dict):
    new_sentence = []
    after_very = False
    last_word = ""
    for i, word in enumerate(sent_words):
        if word.lower().find('very') != -1:
            after_very = True
            last_word = word
            continue
        if after_very and very_dict.get(re.sub(r'[^\w]',"",word).lower()) is not None:
            word = "".join(("[",very_dict.get(re.sub(r'[^\w]',"",word).lower()).lower(),"]"))
            after_very = False
            new_sentence.append(word)
        elif after_very:
            after_very = False
            new_sentence.append(last_word)            
            new_sentence.append(word)
        else:
            new_sentence.append(word)

        last_word = word
    return" ".join(new_sentence)


def q2b():

    very_dict = dict()
    with open("very_words.txt", "r") as very_file:
        line = very_file.readline()
        while line != "":
            very_dict[line.split()[1]] = line.split()[3]
            line = very_file.readline()

    very_file.close()

    with open("very_sentences.txt", "r") as sent_file:
        count = 0
        corr_count = 0
        line = sent_file.readline()
        corrected = False
        while line != "":
            count = count + 1
            if line.find(fix_sent(line.split(), very_dict)) == -1:
                corr_count = corr_count + 1
                print("Input:", line.strip())
                print("Output:", fix_sent(line.split(), very_dict))
            line = sent_file.readline()

        print(corr_count, "/", count, "sentences were corected.")
    sent_file.close()

    

# q2a()
# q2b()




def q3():
    countries = dict()
    with open("IMDb_movies.csv", newline='', encoding="utf8") as movies_file:
        file_reader = reader(movies_file)
        for row in file_reader:
            creators = row[7].split(', ')
            for c in creators:
                if countries.get(c) is None:
                    countries[c] = 1
                else:
                    countries[c] = countries[c] + 1

        sort = dict(sorted(countries.items(), key=lambda i:i[1], reverse=True))
        for i, country in enumerate(sort):
            print(f"{country:15}{'█'*int(sort[country]/1000)}{'  ('}{sort[country]/1000:.2f}{'k)'}")
            if i == 9:
                break
 

q3()

