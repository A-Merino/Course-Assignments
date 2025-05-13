import numpy as np
import time
import matplotlib.pyplot as plt


# A class for creating a Hilbert Matrix object
class HilbertMat:

    """
        Parameters:
            n: Size of Hilbert Matrix

        When creating the object a numpy matrix is
        instantiated with size nxn, then filled with
        values which are algorithmically given.
    """

    def __init__(self, n):
        self.n = n
        self.mat = np.ones((n, n))

        for i in range(1, self.n + 1):
            for j in range(1, self.n + 1):
                self.mat[i - 1][j - 1] = 1 / (i + j - 1)


"""
    Parameters:
        H: A matrix
        b: The vector we are trying to solve for
           in the equation Hx = b

"""
def GaussElim(H, b):
    # Iterate through the columns of the matrix
    for j in range(1, H.n):
        # Iterate through each row in the columns
        for i in range(j + 1, H.n + 1):
            # Do the calculations for the Eimination step
            mult = H.mat[i - 1][j - 1] / H.mat[j - 1][j - 1]
            for k in range(j, H.n + 1):
                H.mat[i - 1][k - 1] = H.mat[i - 1][k - 1] - (H.mat[j - 1][k - 1] * mult)
            b[i - 1] = b[i - 1] - (b[j - 1] * mult)

    x = np.zeros(H.n)
    # Loops for back substitution
    for i in range(H.n - 1, -1, -1):
        for j in range(i, H.n - 1):
            b[i] = b[i] - H.mat[i][j + 1] * x[j + 1]
        x[i] = b[i] / H.mat[i][i]
    # Returns the matrix x that you are solving for
    return x


def main():
    # Denote the amount of trials needed to be run
    trials = [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000]
    times = []

    for t in trials:
        tic = time.perf_counter()  # Timer
        # Create a Hilbert Matrix object of size t
        matrix = HilbertMat(t)
        b = np.ones(t)

        # Solve for x
        x = GaussElim(matrix, b)
        # Solve for and append times to an array
        toc = time.perf_counter()
        times.append(toc - tic)
        print(f'Hilbert matrix of size {t}x{t} took {toc-tic} seconds to solve.')

    # Plot the times over the trials 
    ax = plt.subplot()
    ax.plot(trials, times)
    plt.show()


main()
