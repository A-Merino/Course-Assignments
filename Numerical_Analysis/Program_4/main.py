import heat
import wave
import elliptic as el
import numpy as np
import matplotlib.pyplot as plt

def main():
    k = [0.25, 0.125, 0.0625, 0.03125]
    h = [0.25, 0.125, 0.0625, 0.03125]


    part1a(k, h)
    # part1b(k, h)
    # part1c(k, h)
    # part2()
    # part3b()
    # part3c()



def part1a(k, h):
    D = 2
    for t, x in zip(k, h):
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)
        heat.forward_diff(D, t, x, ts, xs)


def part1b(h, k):
    D = 2
    for t, x in zip(k, h):
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)
        print(xs, ts)

def part1c(h, k):
    D = 2
    for t, x in zip(k, h):
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)
        print(xs, ts)

def part2():
    pass

def part3b():
    pass

def part3c():
    pass


main()