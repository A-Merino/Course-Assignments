import numpy as np
import scipy as sc
import matplotlib.pyplot as plt

def function_a(t):
    '''
        Evaluates the function:

            1 / (1 + (12 * t^2))
        
        at the input t

        Parameters
        ----------
            t : array_like
                The t-values where we will evaluate
                the function

        Returns
        -------
            f_t : array_like
                The evaluated points of the function at
                the input points t
    '''

    return 1 / (1 + (12 * (t ** 2)))


def function_b(t):
    '''
        Evaulates the function:

                e^t

        at the input t

        Parameters
        ----------
            t : array_like
                The t-values where we will evaluate
                the function

        Returns
        -------
            f_t : array_like
                The evaluated points of the function at
                the input points t
    '''

    return np.exp(t)


def trigpolation(f_t, t, Func, x_points=2048, error=True, m=0):
    '''
        Function that calculates the trigonometric
        interpolation of a function f(t)

        Parameters
        ----------
            f_t : array_like
                An array of values of the function f, evaluated
                at points from parameter t
            t : array_like
                An array of values that we want to
                interpolate over
            Func : function
                The function which calculates the mathematical
                function f(t)
            x_points : int, default=2048
                The number of evenly spaced points we want to
                evaluate the interpolation at
            error : boolean, default=True
                Boolean value to denote whether or not to plot
                the error between the functions or both the
                function given and the trig interpolation of it
            m : int
                The number of terms wanted in the least squares
                approximation of function f(t)

        Returns
        -------
            A Plot!!!

            P : array_like
                The array of values P(t) at
                x_points
    '''

    # Polynomial interpolation constants
    n = len(t)  # Number of points we evaluate fft
    c = t[0]    # Beginning of interval
    d = t[-1]   # Ending of interval

    x = np.linspace(c, d, x_points)  # Evaluate at 2048 points at

    # Calculate the fft of the evaulated points
    # We do not divide by sqrt(n) because it is unnecessary operation
    ff = sc.fft.fft(f_t)

    # init array of p values
    ps = []

    '''
        So idk if all of the math can be done in np arrays.
        I tried to do it but it didn't work since we have two
        different array sizes in 'numbs' and 'x'.

        I may be wrong though who knows
    '''

    # Go through each x point
    for val in x:
        p = 0
        numbs = None

        if m == 0:

            # Calculate first and middle values
            # Divide by full n instead of sqrt(n) because
            # we didnt Divide fft earlier
            p = (ff[0].real / n) + ((ff[int(n / 2)].real / n)
                * np.cos(np.pi * n * ((val - c) / (d - c))))

            # Need to calculate inside the sum
            # get Coefficient a_k and b_k for 1 -> n/2 - 1
            numbs = ff[1:int(n / 2) - 1]
        
        else:
            p = (ff[0].real / n) + ((ff[int(m / 2)].real / n)
                * np.cos(np.pi * m * ((val - c) / (d - c))))
            numbs = ff[1:int(m / 2) - 1]

        rest = 0

        # Add all cosine terms
        rest += sum(numbs.real * np.cos(2 * np.pi * np.arange(1, len(numbs)+1) * ((val - c) / (d - c))))
        # Subtract all sine terms
        rest -= sum(numbs.imag * np.sin(2 * np.pi * np.arange(1, len(numbs)+1) * ((val - c) / (d - c))))

        # Multiply sum by 2 / n and add to rest
        ps.append(p + ((2 / n) * rest))

    # Plots the absolute error of the interpolated function
    if error:
        plt.plot(x, abs(ps - Func(x)))
        if m == 0:
            plt.title(f"Absolute Error of P(t) - f(t) with M={n} data points and m={m}")
        else:
            plt.title(f"Absolute Error of P(t) - f(t) with M={n} data points")

    # Plots the true function and interpolated
    # function next to each other for visual comparison
    else:
        if m != 0:
            plt.title(f"Plot of P(t) and f(t) with m={m} least square approx. ")

        else:
            plt.title(f"Plot of P(t) and f(t)")
        plt.plot(x, ps)
        plt.plot(x, Func(x))

    plt.show()  # Show Plot

    return ps  # Return points evaluated at x
    

def main():
    '''
        Function to organize and run the code for part 2
        of program 2 assignment
    '''

    ms = [8, 16, 32, 64, 128, 256]

    # Function A loop for all M
    for M in ms:
        points = np.linspace(-1, 1, M)
        trigpolation(function_a(points), points, function_a)

    # Function B loop for all M
    for M in ms:
        points = np.linspace(-1, 1, M)
        trigpolation(function_b(points), points, function_b)

    # Function A loop for least squares approx
    for m in ms[:-1]:
        points = np.linspace(-1, 1, 256)
        trigpolation(function_a(points), points, function_a, error=False, m=m)

    # Function B loop for least squares approx
    for m in ms[:-1]:
        points = np.linspace(-1, 1, 256)
        trigpolation(function_b(points), points, function_b, error=False, m=m)

main()