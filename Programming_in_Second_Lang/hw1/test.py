# intial values
radius_1 = 5
radius_2 = 10
height_2 = 5
height_1 = 30
height_3 = (10 + 47 + 13) / 3

pi = 3.14159



# print(height_3)

# Volume of Cyl 1
cylinder1 = pi * (radius_2 ** 2) * height_2

# Volume of cone
cone = (pi * ((radius_1 ** 2) + (radius_1 * radius_2) + (radius_2 ** 2)) * height_3) / 3


# volume of cyl 2
cylinder2 = pi * (radius_1 ** 2) * height_1

# Total volume of the bottle
total = cylinder1 + cone + cylinder2

# print(total)







names = ["Willena Shupe", "Jolanda Agin", "Leta Stacker",
"Leonora Oliverio", "Birgit Stoudt", "Aron Valtierra", "Vi Buschman",
"Janee Barnwell", "Agnus Flower", "Byron Mccartney",
"Victoria Crabill", "Amy Swinton", "Arla Mohamed", "Bryon Vester",
"Lue Benway", "Mozelle Macauley", "Suzann Galindo",
"Delicia Barriere", "Marcella Uyehara", "Jane Curley"
]
years = [2020, 2019, 2016, 2019, 2013, 2014, 2014, 2018, 2016, 2012,
2014, 2015, 2018, 2013, 2019, 2017, 2019, 2020, 2015, 2013]

end = "@my.fit.edu"

for person, year in zip(names, years):


    name = person.split(" ")
    first = name[0]
    last = name[1]

    intial = first[0]
    str_year = str(year)

    email = intial + last + str_year + end
    print(email)


    # 