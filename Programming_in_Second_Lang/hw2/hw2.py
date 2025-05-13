###
# This code is written by Dr. Fitz and provided to students taking CSE5150/CSE2050
# at Florida Institute of Technology.
# Do not copy or reproduce without permission
###

from lxml import html
import requests
import dateparser as dparse



def get_date(page):
    date_span = page.cssselect('[class="date-display-single"]')
    span_content =  date_span[0].get('content')
    date, hours = span_content.split("T")
    full_time = hours.split("+")
    time = full_time[0]
    if date.find("179") >= 0:
        date = date.replace("17", "19")
    parsed_date = dparse.parse(date)
    parsed_time = dparse.parse(time)
    total = dparse.parse(span_content)
    # print(formatted_date)
    # print(formatted_time)
    date_format = "%m/%d/%y"
    time_format = "%I:%M %p"

    return " ".join((parsed_date.strftime(date_format), parsed_time.strftime(time_format)))




def get_speech(url):
    speech_code = get_html(url)
    html_el = html.document_fromstring(speech_code)

    div = html_el.cssselect('[class="field-docs-content"]')
    paragraphs = div[0].cssselect("p")
    speech = []

    for paragraph in paragraphs:
        speech.append(paragraph.text_content().strip())


    return (" ".join(speech), get_date(html_el))





def get_html(url):
    """
    This function extracts the html code from a url
    :param url: 
    :return: html code from the web page referenced by url
    """
    response = requests.get(url)  # get page data from server, block redirects
    source_code = response.content  # get string of source code from response
    return source_code


def get_data_table(source_code):
    """
    This function creates a 2-D list of the following fields:
    [president_name, tenure, speech_date, speech]
    The speech_date and speech are currently left blank.
    :param source_code: the html source code extracted from a url
    :return: a 2-D Python list or table
    """
    data_table = []
    speech_table = None
    trs = None
    html_elem = html.document_fromstring(source_code)  # make HTML element object
    tables = html_elem.cssselect("table") # select the table element on the page

    # if you find a table, initialize the speech table to the first table
    if len(tables) > 0:
        speech_table = tables[0]

    # if you find the speech table, select its rows
    if speech_table is not None:
        trs = speech_table.cssselect("tr")

    # If you find rows in the table, go through each row
    # and process the data. Skip the header row (start at row 1)
    if trs is not None:
        president_name = ""
        
        for i in range(1, len(trs)):
            tr = trs[i]
            tds = tr.cssselect("td")

            # simple check to make sure the row has president name and speech url
            if len(tds) == 12:
                first_cell_data = tds[0].text_content().strip()
                tenure = tds[1].text_content().strip()
                
                # get the link element for the link to the speech
                if tds[2] is not None:
                    speech_link_elmnt = tds[2].cssselect("a")
                    speech_date = ""
                    speech = ""

                    if len(first_cell_data) > 0:
                        president_name = first_cell_data

                        if len(speech_link_elmnt) > 0:
                            speech_link = speech_link_elmnt[0].get("href")

                            if len(president_name) > 0 and len(speech_link) > 0 and "#" not in speech_link: # valid links should not link to an object on the page::
                                # Write the function get_speech(speech_link) to extract the speech and 
                                # speech_date from the speech_link
                                speech, speech_date = get_speech(speech_link)
                                data_table.append([president_name, tenure, speech_date, speech])
    return data_table


def scrape_data(url):
    """
    :param url: the url to 
    :return: 
    """
    return get_data_table(get_html(url))


def first_print(data):
    header_1 = "President's Name"
    header_2 = "Term"
    header_3 = "Date of Speech"
    header_4 = "Excerpt"
    print(f"{header_1:25}{header_2:15}{header_3:22}{header_4:20}")
    for prez in data:
        print(f"{prez[0]:25}{prez[1]:15}{prez[2]:22}{prez[3]:20.50}")




def count_sentences(speech):
    punct = ".?!;:"
    count = 0
    for x in range(len(speech)):
        if punct.find(speech[x]) > -1:
            count = count + 1
    # print(count)
    return count


def count_words(speech):
    # print("word",len(speech.split(" ")))
    return len(speech.split(" "))


def count_syllables(speech):
    vowels = "aeiou"
    punct = ".?!;: "
    last_char = ""
    next_char = speech[1]
    count = 0
    for x in range(len(speech) - 2):
        current_char = speech[x].lower()
        if current_char == "e" and next_char == " ":
            count = count - 1
        
        if vowels.find(current_char) > -1:
            count = count + 1

        elif (current_char == 's' or current_char == 'd') and punct.find(next_char) > -1 and last_char == 'e':
            count = count - 1
        else:
            pass
        last_char = current_char
        next_char = speech[x+2]
    # print(count)
    return count 

def compute_flesch_index(syllables, words, sentences):
    # print("flesch", 206.835 - 1.015 * (words/sentences) - 84.6 * (syllables/words))
    return 206.835 - (1.015 * (words/sentences)) - (84.6 * (syllables/words)) 


def compute_grade_level(syllables, words, sentences):
    # print("grade", 0.39 * (words/sentences) + 11.8 * (syllables/words) - 15.59)
    return (0.39 * (words/sentences)) + (11.8 * (syllables/words)) - 15.59



def classify_article_readability(flesch):
    if flesch < 30.0:
        return "College graduate"

    elif flesch < 50.0:
        return "College"

    elif flesch < 60.0:
        return "10th to 12th Grade" 

    elif flesch < 70.0:
        return "8th & 9th Grade" 

    elif flesch < 80.0:
        return "7th Grade" 

    elif flesch < 90.0:
        return "6th Grade" 

    else:
        return "5th Grade" 



def main():
    """
    The main driver of the program.
    It uses the base link to The American Presidency Project
    at UC Santa Barbara to extract SOU addresses
    """
    url = "https://www.presidency.ucsb.edu/" \
          "documents/presidential-documents-archive-guidebook/" \
          "annual-messages-congress-the-state-the-union"
    data_table = scrape_data(url)
    # first_print(data_table)
    head_1 = "President's Name"    
    head_2 = "Term"    
    head_3 = "Date of Speech"    
    head_4 = "Excerpt"    
    head_5 = "Flesch"    
    head_6 = "Grade Level #"    
    head_7 = "Grade Level"    
    print(f"{head_1:25}{head_2:15}{head_3:20}{head_4:53}{head_5:10}{head_6:18}{head_7}")


    for prez in data_table:
        syllables = count_syllables(prez[3])
        words = count_words(prez[3])
        sentences = count_sentences(prez[3])
        flesch = compute_flesch_index(syllables, words, sentences)
        grade_level = compute_grade_level(syllables, words, sentences)
        readability = classify_article_readability(flesch)
        print(f"{prez[0]:25}{prez[1]:15}{prez[2]:20}{prez[3]:0.50}{flesch:9.2f}{int(grade_level):^22}{readability:5}")


# call the main function to run the program
main()
