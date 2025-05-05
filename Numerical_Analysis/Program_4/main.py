import heat
import wave
import elliptic as el
import numpy as np
import matplotlib.pyplot as plt

def main():

    # k and h values chosen for Question 1
    k = [0.1, 0.0625, 0.03125, 0.01]
    h = [0.1, 0.0625, 0.03125, 0.01]

    part1a(k, h)
    part1b(k, h)
    part1c(k, h)
    part2()
    part3b()
    part3c()

def part1a(k, h):
    # from the equation, D is 2
    D = 2
    # for each pair of h and k values
    for t, x in zip(k, h):
        # Create x and t arrays from 0 to 1
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)

        # Calculate the forward difference
        heat.forward_diff(D, t, x, ts, xs)

def part1b(k, h):
    D = 2
    for t, x in zip(k, h):
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)

        # Calculate backwards difference
        heat.backward_diff(D, t, x, ts, xs)

def part1c(k, h):
    D = 2
    for t, x in zip(k, h):
        xs = np.linspace(0, 1, int(1/x) + 1)
        ts = np.linspace(0, 1, int(1/t) + 1)

        # Calculate crank nicolson
        heat.crank_nicolson(D, t, x, ts, xs)

def part2():

    # From the problem equation, c is 4 
    c = 4

    # our p values asked to calculate at
    ps = [4, 5, 6, 7, 8, 9, 10]

    # funny formatting attempt, which is basically hardcoded now
    head = f'h\t\tk\t\tnum. sol.\t\terror' 
    print(f'{head}\n{"_"*((2*len(head))-5)}')

    for p in ps:

        # Calculate h and k
        h = 2 ** (-p)
        k = h / c

        # Get x and t values
        xs = np.linspace(0, 1, int(1/h) + 1)
        ts = np.linspace(0, 1, int(1/k) + 1)
        
        # calculate forward difference for wave equations
        wave.forward_diff(k, h, ts, xs)

def part3b():
    '''
        Function that runs calculations of the
        finite difference method at certain p values.
        The p values determine the step size in x and y
    '''

    # p values requested
    ps = [2, 3, 4, 5, 6, 7]

    for p in ps:
        # Calculate step
        h = 2 ** (-p)
        # Create array of x and y values from step
        xs = np.linspace(3, 5, int(2/h) + 1)
        ys = np.linspace(1, 2, int(1/h) + 1)
        el.finite_diff(h, xs, ys)  # Run method
    
def part3c():
    '''
        Function that runs calculations of the
        finite element method at certain p values.
        The p values determine the step size in x and y
    '''

    # p values requested
    ps = [2, 3, 4, 5, 6, 7]

    for p in ps:
        # Calculate step
        h = 2 ** (-p)
        # Create array of x and y values from step
        xs = np.linspace(3, 5, int(2/h) + 1)
        ys = np.linspace(1, 2, int(1/h) + 1)
        el.finite_element(h, xs, ys)  # Run method

main()
