import sqlite3
from doctor import Doctor


def find_doctors(search):
    connection = sqlite3.connect("health_first_florida.db")
    cursor = connection.cursor()

    sql_command = "SELECT * FROM healthfirst_doctors WHERE FullName LIKE ? OR Specialty LIKE ?"
    found_docs = cursor.execute(sql_command, (''.join(("%",search.lower(),"%")),''.join(("%",search.lower(),"%"))))
    

    formatted_docs = []
    for doc in found_docs:
        formatted_docs.append(Doctor(list(doc)))

    connection.close()
    return formatted_docs

def get_all_doctors():
    connection = sqlite3.connect("health_first_florida.db")
    cursor = connection.cursor()

    sql_command = "SELECT * FROM healthfirst_doctors"
    found_docs = cursor.execute(sql_command)
    

    formatted_docs = []
    for doc in found_docs:
        formatted_docs.append(Doctor(list(doc)))

    connection.close()
    return formatted_docs