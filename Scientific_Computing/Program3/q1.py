import numpy as np
import matplotlib.pyplot as plt
import scipy as sc
import scipy.sparse.linalg as splg

def partA(A, b):
    # Equivalent to matlab backslash
    x = splg.spsolve(A.tocsr(),b)
    print(x)
    return x


def partB(A, b, xReal):
    # Get Lower Triangle
    L = sc.sparse.tril(A, k =-1)
    # Get Upper Triangle
    U = sc.sparse.triu(A, k = 1)
    # Get Diagonal 
    D = sc.sparse.tril(A) - L

    # Initial Guess
    x = np.zeros(1000)

    # Solve backards error 
    backErr = b - A*x
    backError = np.linalg.norm(backErr, ord=np.inf)

    # Initialize variables
    itr = 0
    fwerrs = []
    bwerrs = []

    while backError > 0.00001 and itr < 100000:
        # Solve for x using Jacobi method
        x = splg.spsolve(D, (b - (L+U)*x))
        # Calculate Backwards error
        backErr = b - A*x
        backError = np.linalg.norm(backErr, ord=np.inf)
        bwerrs.append(backError)

        # Calculate Forward error
        forwardErr = xReal - x
        forwardError = np.linalg.norm(forwardErr, ord=np.inf)
        fwerrs.append(forwardError)

        itr += 1

    # Plotting functions
    xs = np.arange(itr)
    ax = plt.subplot()
    ax.plot(xs, fwerrs, "b", label="Forward Error")
    ax.plot(xs, bwerrs, 'r', label="Backward Error")
    ax.set_yscale("log")
    ax.legend()
    plt.show()


def partC(A, b, xReal):
    # Get Diagonal and Lower matrix
    DnL = sc.sparse.tril(A, k=0)
    # Upper triangle
    U = sc.sparse.triu(A, k=1)

    # Initial guess and error
    x = np.zeros(1000)
    backErr = b - A*x
    backError = np.linalg.norm(backErr, ord=np.inf)

    # Initialize variables
    itr = 0
    fwerrs = []
    bwerrs = []
    while backError > 0.00001 and itr < 100000:
        # Solve using Gauss-Siedel method
        x = splg.spsolve(DnL.tocsr(), (b - (U)*x))

        # Calculate Backwards error
        backErr = b - A*x
        backError = np.linalg.norm(backErr, ord=np.inf)
        bwerrs.append(backError)

        # Calculate Forward error
        forwardErr = xReal - x
        forwardError = np.linalg.norm(forwardErr, ord=np.inf)
        fwerrs.append(forwardError)

        itr += 1

    # Plotting functions
    xs = np.arange(itr)
    ax = plt.subplot()
    ax.plot(xs, fwerrs, "b", label="Forward Error")
    ax.plot(xs, bwerrs, 'r', label="Backward Error")
    ax.set_yscale("log")
    ax.legend()
    plt.show()


def partD(A, b, xReal):

    L = sc.sparse.tril(A, k=-1)  # Lower
    U = sc.sparse.triu(A, k=1)  # Upper
    D = sc.sparse.tril(A) - L  # Diagonal

    # Initiaize omegas and lists
    omegas = np.arange(0.1,2, 0.1)
    fwerrs = []
    bwerrs = []
    speeds = []

    for om in omegas:

        # Initial Guess and errors
        x = np.zeros(1000)
        backErr = b - A * x
        backError = np.linalg.norm(backErr, ord=np.inf)
        itr = 0

        while backError > 0.00001 and itr < 100000:
            # Solve for x using SOR
            x = splg.spsolve((D + (om * L)), ((om*b) - (om*U)*x + (1-om)*D*x))

            # Calculate Backwards error
            backErr = b - A*x
            backError = np.linalg.norm(backErr, ord=np.inf)
            bwerrs.append(backError)

            # Calculate Forward error
            forwardErr = xReal - x
            forwardError = np.linalg.norm(forwardErr, ord=np.inf)
            fwerrs.append(forwardError)

            itr += 1
        speeds.append(itr)
    
    # Plotting functions
    ax = plt.subplot()
    ax.plot(omegas, speeds, "b", label="Convergence Speed")
    ax.set_yscale("log")
    ax.legend()
    plt.show()

# Initialize A and b
nums = [-1.0, 2.001, -1.0]
A = sc.sparse.diags(nums, [-1,0,1], (1000,1000))
b = np.ones(1000)

# Solve for x
xReal = partA(A, b)

# Run through methods
partB (A, b, xReal)
partC(A, b, xReal)
partD(A, b, xReal)