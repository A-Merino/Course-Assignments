import numpy as np
import matplotlib.pyplot as plt


def main():
    ns = np.arange(10, 101, 10)  # list of different 10 values
    xs = np.linspace(-2, 2, 1000)  # range of points [-2,2]
    maxs = []
    z = []

    for n in ns:
        nodes = np.linspace(-2, 2, n)

        # Create a list of y values
        outs = []
        ys = []
        # for 1000 points between -2 and 2
        for x in xs:
            y = 1
            # Calculate (x-x1)(x-x2)...(x-xn)
            for p in nodes:
                y *= x - p
            # make all values positive
            outs.append(abs(y))
            ys.append(y)
        # Add maximum
        z.append(ys)
        maxs.append(max(outs))

    for i, l in enumerate(z):
        plt.plot(xs, l)
        plt.title(f'n = {(i+1)*10}')
        plt.show()
    plt.plot(maxs)
    plt.yscale("log")
    plt.show()


def cheb():

    # same actiosn as main function

    ns = np.arange(10, 101, 10)  # n values
    xs = np.linspace(-2, 2, 1000)  # x values
    z = []
    for n in ns:

        nodes = np.arange(1, n+1)

        outs = []
        for x in xs:
            y = 1
            for p in nodes:
                # Chebyshev nodes
                y *= x - (2* np.cos(((2 * p - 1) / (2 * n)) * np.pi))
            # Add y values to plot
            outs.append(y)
        z.append(outs)
    # plot
    for i, l in enumerate(z):
        plt.plot(xs, l)
        plt.title(f'n = {(i+1)*10}')
        plt.show()


main()
cheb()
