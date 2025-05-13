import math

def q1():
    radius_1 = float(input("Radius of Cylinder 1: "))
    radius_2 = float(input("Radius of Cylinder 2: "))
    height_1 = float(input("Height of Cylinder 1: "))
    height_2 = float(input("Height of Cylinder 2: "))
    height_3 = float(input("Height of Cone: "))
    volume = 0.0

    volume_cyl1 = (radius_1**2)  * height_1 * math.pi
    volume_cyl2 = (radius_2**2) * height_2 * math.pi
    volume_cone = ((radius_1**2) + (radius_2**2) + (radius_2*radius_1)) * height_3 * math.pi / 3

    print(f"The volume is {volume_cyl1 + volume_cyl2 + volume_cone:.2f}")



# q1()




def q2():
    
    names = ["Willena Shupe", "Jolanda Agin", "Leta Stacker",
    "Leonora Oliverio", "Birgit Stoudt", "Aron Valtierra", "Vi Buschman",
    "Janee Barnwell", "Agnus Flower", "Byron Mccartney",
    "Victoria Crabill", "Amy Swinton", "Arla Mohamed", "Bryon Vester",
    "Lue Benway", "Mozelle Macauley", "Suzann Galindo",
    "Delicia Barriere", "Marcella Uyehara", "Jane Curley"]
    years = [2020, 2019, 2016, 2019, 2013, 2014, 2014, 2018, 2016, 2012, 2014, 2015, 2018, 2013, 2019, 2017, 2019, 2020, 2015, 2013]

    suffix = "@my.fit.edu"

    print(f"{'Student Name':25}{'Email Adress':25}")

    for x in range(len(names)):
        print(f'{names[x]:25}{"".join((names[x][0].lower(), names[x].split()[1].lower(), str(years[x]), suffix)):25}')

# q2()



def q3():
    temperatures2 = [72, 32, 24, 61, 30, 42, 51, 21, 56, 32, 39, 70, 75, 54, 62, 49, 28, 54, 39, 24, 31, 64, 72, 27]


    mod = len(temperatures2) / 4
    counter = 1
    quarter = []
    for i in range(len(temperatures2)):
        quarter.append(temperatures2[i])
        if i % mod == mod - 1:
            print(f"Quarter {counter} {quarter}")
            counter = counter + 1
            quarter = []

           


# q3()