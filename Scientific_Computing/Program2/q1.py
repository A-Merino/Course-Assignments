import numpy as np
import matplotlib.pyplot as plt
import time


# Function for creating a list of y values
# given a list of x vales
def f_array(xs):
    ys = []
    for x in xs:
        ys.append((54 * (x**6)) + (45 * (x**5)) - (102 * (x**4)) - (69 * (x**3)) + (35 * (x**2)) + (16 * x) - 4)

    return ys


# Returns the y value for a given x value
def f(x):
    return (54 * (x**6)) + (45 * (x**5)) - (102 * (x**4)) - (69 * (x**3)) + (35 * (x**2)) + (16 * x) - 4


# Returns the y value of the derivative of the function
def f_prime(x):
    return (324 * (x**5)) + (225 * (x**4)) - (408 * (x**3)) - (207 * (x**2)) + (70 * x) + 16


# Plots error of i with the error of i+1. 
# Takes in a list of calculated errors
def plotError(es):
    # Uses the one list of errors and
    # copies to a new list missing the first index
    # The old list then loses it's last index
    es1 = es[1:]
    es = es[:-1]

    # Create a plot, plot the points
    ax = plt.subplot()
    ax.plot(es, es1)
    # Set the scale of axies to log and show
    ax.set_xscale("logit")
    ax.set_yscale("logit")
    plt.show()


# Runs Newton's method given a starting point x0
def newtonsMethod(x0, plotting=False):
    # Put the starting point into an array
    # and create an array for errors
    x = [x0]
    errors = []

    # There is a max of 100 iterations
    for i in range(100):
        # Calcuate x_(i+1)
        xNew = x[i] - (f(x[i]) / f_prime(x[i]))
        # Calculate relative error and
        # append to errors array
        relErr = abs((xNew - x[i]) / xNew)
        errors.append(abs((xNew - x[i])))
        # With a tolerance of 8 decimal places
        # stop iterating once error is below
        if relErr < 0.000000001:
            break
        x.append(xNew)  # add to list of x
    # Print output and return errors
    if plotting:
        plotError(errors)
    else:
        print(f'Starting at x-value {x0}, we get the root: {x[-1]:.9f}')

    return errors


def secantMethod(x0, x1):
    # Put the starting point into an array
    # and create an array for errors
    x = [x0, x1]
    errors = []

    # There is a max of 100 iterations
    for i in range(1,100):
        # Calcuate x_(i+1)
        xNew = x[i] - (f(x[i]) / (f(x[i]) - f(x[i - 1]))) * (x[i] - x[i - 1])
        # Calculate relative error and
        # append to errors array
        relErr = abs((xNew - x[i]) / xNew)
        errors.append(abs((xNew - x[i])))
        # With a tolerance of 8 decimal places
        # stop iterating once error is below
        if relErr < 0.00000001:
            break
        x.append(xNew)  # add to list of x
    # Print output and return errors

    print(f'Starting at x-value {x0}, we get the root: {x[-1]:.9f}')
    plotError(errors)

    return errors



# Main function
def main():

    partA()

    partB()

    partC()

    partD()


# Plots the graph between -2 and 2
def partA():
    xs = np.linspace(-2, 2.1, 100)
    ys = f_array(xs)
    plt.plot(xs, ys)
    plt.show()


# Runs the Newton's Method of the function
# at 5 different starting points to get
# the 5 different roots of the function.
def partB():
    newtonsMethod(-2)
    newtonsMethod(-0.5)
    newtonsMethod(0.1)
    newtonsMethod(-0.1)
    newtonsMethod(1)



# The newton's method is calculated
# and the error is plotted on a graph
def partC():
    newtonsMethod(-2, True)
    newtonsMethod(-0.5, True)
    newtonsMethod(0.1, True)
    newtonsMethod(-0.1, True)
    newtonsMethod(1, True)


def partD():
    secantMethod(-0.5, -0.6)





main()
