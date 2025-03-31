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


def partA(ts, Func, ns):
    '''
        This function runs the associated code with
        Part A where we plot the error of the true
        function against the polynomial interpolation
        of it.

        Parameters
        ----------
            ts : array_like
                The t values at which we evaluate the
                function
            Func : function
                The function at which we will evaluate
                the t values
            ns : array_like
                The list of degrees at which we want to
                evaluate the polynomial interpolation

    '''

    # true values of given function
    truePoints = Func(ts)

    for N in ns:
        # Create Matrix A and vector B to solve least squares problem
        mat = []
        es = []

        # Go through each t value given
        for t in ts:

            # Calculate b
            es.append(Func(t))

            # Create row of matrix, work shown in report
            row = [1]

            for i in range(1, N + 1):

                # Add the power of the current value of t
                row.append(t ** i)

            # Append row to matrix
            mat.append(row)

        # Now that we have A and b we can solve Ax = b
        # This gives us our a0, a1, ..., aN
        lstq = np.linalg.lstsq(mat, es, rcond=None)[0][::-1]

        # We can now evaluate the function at the points t_i
        approx = np.polyval(lstq, ts)

        # Plot the absolute value of the error
        plt.plot(ts, abs(truePoints - approx))
        plt.show()


def main():
    '''
        Function to organize and run the code for part 1
        of program 2 assignment
    '''

    # Create M evenly spaced points from -1 to 1
    M = 51
    t = np.linspace(-1, 1, M)

    # hard code n values
    ns = [10, 20, 30, 40, 50]

    partA(t, function_a, ns)
    partA(t, function_b, ns)

    # hard code n values
    ns = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]

    # Create M evenly spaced points from -1 to 1
    M = 101
    t = np.linspace(-1, 1, M)

    partA(t, function_a, ns)
    partA(t, function_b, ns)


main()
