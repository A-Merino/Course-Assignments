import numpy as np
import matplotlib.pyplot as plt
import time
from scipy.sparse.linalg import spsolve as solve
from scipy.sparse import csr_matrix

# Left boundary function
def left(y):
    return (y ** 2) + 9

# Right boundary function
def right(y):
    return (y ** 2) + 25

# Top boundary function
def top(x):
    return (x ** 2) + 4

# Bottom boundary function
def bottom(x):
    return (x ** 2) + 1

# Exact function
def exact(x, y):
    return x**2 + y**2

# f(x, y)
def f():
    return 5

def finite_diff(h, xs, ys):
    '''
        Function that calculates finite difference method for
        elliptic equations.

        Parameters
        ----------

            h: int, float
                The change/step in x and y

            ys: array_like
                The y values we want to calculate at
            
            xs: array_like
                The x values we want to calculate at

        Returns
        -------
            None, can return mesh if necessary    

    '''
    # create matrix size
    m = len(xs)
    n = len(ys)
    mn = m * n

    # Create A matrix and b vector
    A = np.zeros((mn, mn))
    b = np.zeros(mn)

    tic  = time.time()  # Start timer

    # For all interior points of the matrix
    for i in range(1, m - 1):
        for j in range(1, n - 1):

            # Calculate values of interior points
            A[i+(j-1)*m, i-1+(j-1)*m] = 1 / (h**2)
            A[i+(j-1)*m, i+(j-1)*m] = (-2 / (h**2))-(2 / (h**2))
            A[i+(j-1)*m, i+1+(j-1)*m] = 1 / (h**2)
            A[i+(j-1)*m, i+(j-2)*m] = 1 / (h**2)
            A[i+(j-1)*m, i+(j*m)] = 1 / (h**2)

            # Calculations for b vector
            b[i+(j-1)*m] = f()

    # Calculate all exterior points
    for i in range(m):
        j = 0
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = bottom(xs[i])
        j = n-1
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = top(xs[i])

    for j in range(1, n - 1):
        i = 0
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = left(ys[j])
        i = m - 1
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = right(ys[j])

    # Turn into a sparse matrix
    sa = csr_matrix(A)

    # Sparse solver
    v = solve(sa, b)

    # End timer and print time
    toc  = time.time()
    print(f'Time to solve with h_x = h_y = {h} is {toc-tic:0.4f}secs')
    
    # Get ready to plot
    u = v.reshape(n, m)
    x, y = np.meshgrid(xs, ys)  # Create a mesh

    A_ex = exact(x, y)

    # Plotting functions
    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    ax.plot_surface(x[:-1, :], y[:-1, :], abs(u[:-1, :]-A_ex[:-1, :]))
    # Debugging / show real and est. values
    # ax.plot_surface(x, y, A_ex)
    # ax.plot_surface(x, y, u)
    ax.set_xlabel("x values")
    ax.set_ylabel("y values")
    ax.set_zlabel("error")
    plt.show()


def bary(x, h, y, k, num):
    '''
        Function which calculates the coordinates of
        barycenters

        Parameters
        ----------
            x: int, float
                The x coordinate we want to calculate
                the barycetner from

            h: int, float
                The change/step in x

            y: int, float
                The y coordinate we want to calculate
                the barycetner from

            k: int, float
                The change/step in y

            num: int
                The type of barycenter (1-6)
        Returns
        -------
        x_b, y_b: tuple of floats
            The x and y coordinates of the bary center
    '''
    if num == 1:
        return ((x - (2/3) * h), (y - (1/3) * k))
    elif num == 2:
        return ((x - (1/3) * h), (y - (2/3) * k))
    elif num == 3:
        return ((x + (1/3) * h), (y - (1/3) * k))
    elif num == 4:
        return ((x + (2/3) * h), (y + (1/3) * k))
    elif num == 5:
        return ((x + (1/3) * h), (y + (2/3) * k))
    elif num == 6:
        return ((x - (1/3) * h), (y + (1/3) * k))
    else:
        raise "Not a type of Barycenter"

# r(x, y) for elliptic equation
def r(d):
    x, y = d
    return 1 / exact(x, y)


def finite_element(h, xs, ys):
    '''
        Function that calculates finite element method for
        elliptic equations.

        Parameters
        ----------
            
            h: int, float
                The change/step in x and y

            ys: array_like
                The y values we want to calculate at
            
            xs: array_like
                The x values we want to calculate at

        Returns
        -------
            None, can return mesh if necessary    

    '''
    # create matrix size
    m = len(xs)
    n = len(ys)
    mn = m * n

    h2 = h**2

    # Create A matrix and b vector
    A = np.zeros((mn, mn))
    b = np.zeros(mn)

    tic  = time.time()  # Start timer

    # For all interior points of the matrix
    for i in range(1, m - 1):
        for j in range(1, n - 1):

            # Calculate the function r(x, y) at barycenters
            rb1 = r(bary(xs[i], h, ys[j], h, 1))
            rb2 = r(bary(xs[i], h, ys[j], h, 2))
            rb3 = r(bary(xs[i], h, ys[j], h, 3))
            rb4 = r(bary(xs[i], h, ys[j], h, 4))
            rb5 = r(bary(xs[i], h, ys[j], h, 5))
            rb6 = r(bary(xs[i], h, ys[j], h, 6))

            # Calculate values of interior points
            A[i+(j-1)*m, i+(j-1)*m] = (2 * (h2 + h2) / (h2)) - (h2 * (rb1 + rb2 + rb3 + rb4 + rb5 + rb6)) / 18
            A[i+(j-1)*m, i-1+(j-1)*m] = - (h / h) - (h2 * (rb6 + rb1))/18
            A[i+(j-1)*m, i-1+(j-2)*m] = - (h2/18) * (rb1 + rb2)
            A[i+(j-1)*m, i+(j-2)*m] = - (h / h) - (h2 * (rb2 + rb3))/18
            A[i+(j-1)*m, i+1+(j-1)*m] = - (h / h) - (h2 * (rb3 + rb4))/18
            A[i+(j-1)*m, i+1+(j*m)] = - (h2/18) * (rb4 + rb5)
            A[i+(j-1)*m, i+(j*m)] = - (h / h) - (h2 * (rb6 + rb5))/18
            
            # Calculations for b vector
            tots = 0
            for q in range(1, 7):
                tots += f()    

            b[i+(j-1)*m] = - (h2 * (tots)) / 6

    # Calculate all exterior points
    for i in range(m):
        j = 0
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = bottom(xs[i])
        j = n-1
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = top(xs[i])

    for j in range(1, n - 1):
        i = 0
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = left(ys[j])
        i = m - 1
        A[i+(j-1)*m, i+(j-1)*m] = 1
        b[i+(j-1)*m] = right(ys[j])

    # Turn into a sparse matrix
    sa = csr_matrix(A)
    
    # Sparse solver
    v = solve(sa, b)

    # End timer and print
    toc  = time.time()
    print(f'Time to solve with h_x = h_y = {h} is {toc-tic:0.4f}secs')
    
    u = v.reshape(n, m)  # Reshape to m*n
    x, y = np.meshgrid(xs, ys)  # Create a mesh
    A_ex = exact(x, y)  # Calculate exact

    # Plotting functions
    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    ax.plot_surface(x[:-1, :], y[:-1, :], abs(u[:-1, :]-A_ex[:-1, :]))
    # Debugging / show real and est. values
    # ax.plot_surface(x, y, A_ex)
    # ax.plot_surface(x, y, u)
    ax.set_xlabel("x values")
    ax.set_ylabel("y values")
    ax.set_zlabel("error")
    plt.show()
