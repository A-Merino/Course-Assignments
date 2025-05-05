import numpy as np
import matplotlib.pyplot as plt
from scipy.linalg import solve


def sigma(D, k, h):
    '''
        This function calculates the value of sigma in the
        forward difference method for parabolic/heat equations

        Parameters
        ----------
            D: int, float
                The constant infront of u_xx in the PDE

            k: int, float
                The set change in t we want to estimate

            h: int, float
                The set change in x we want to estimate

        Returns
        -------
            sigma: float
                The calculated value of sigma
    '''

    return (D * k) / h**2


def triMatrix(diags, n):
    '''
        Function that creates the tri-diagonal matrix, which is used in the 
        numerical methods of finite difference and finite element. This piece
        of code was taken from Program 3's createA fuction

        Parameters
        ----------
            
            diags: array_like
                The three diagonal values which are needed for
                the creation of the matrix

            n: int
                The size of matrix we would like to output

        Returns
        -------

            A: 2D array_like
                The matrix of size n which contains the diagonal values
                given in the diags parameter
    '''

    # diags function from numpy
    off_d = np.diag(diags[0] * np.ones(n - 1), k=1)
    off_d = off_d + off_d.T

    diag = np.diag(diags[1] * np.ones(n))

    return off_d + diag


def exact(t, x):
    # Return exact value with given x and t
    return np.exp((2 * t) + x) + np.exp((2 * t) - x)

def t0(x):
    # value of x at t = 0
    return 2 * np.cosh(x)

def l_bound(t):
    # Value of t at x = 0 
    return 2 * np.exp(2 * t)


def r_bound(t):
    # Value of t at x = 1 
    return (np.exp(2)  + 1) * np.exp((2 * t) - 1)



def forward_diff(D, k, h, ts, xs):
    '''
        Calculates the forward difference method for heat
        equations

        Parameters
        ----------

            D: int, float
                The coefficient infront of u_xx in the heat equation
            
            k: int, float
                The change/step in t

            h: int, float
                The change/step in x

            ts: array_like
                The t values we want to calculate at
            
            xs: array_like
                The x values we want to calculate at

        Returns
        -------
            None, can return mesh if necessary
    '''

    sig = sigma(D, k, h)  # Calculate sigma

    # coefficients from matrix
    fac = [sig, 1 - (2  * sig), sig]

    # create empty matrix to hold values
    u = np.zeros((len(xs), len(ts)))

    # create same matrix for exact values
    u_ex = np.zeros((len(xs), len(ts)))

    u[:,0] = t0(xs)  # At t = 0, solve x
    u_ex[:,0] = t0(xs)  # Same for exact

    # for each t value
    for j, t in enumerate(ts[:-1]):
        # Calculate x_0
        u[0, j + 1] = fac[0]* l_bound(t) + fac[1] * u[1,j] + fac[2] * u[2, j]

        # Calculate x_1 to x_(n-1)
        u[1:-1, j + 1] = fac[0]* u[0:-2, j] + fac[1] * u[1:-1,j] + fac[2] * u[2:, j]

        # Calculate x_n
        u[-1, j + 1] = fac[2]* r_bound(t) + fac[1] * u[-1,j] + fac[0] * u[-2, j]

        u_ex[:, j+1] = exact(t, xs) #Calculate exact

    x, y = np.meshgrid(xs, ts)  # Create a mesh

    # Plotting functions
    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    ax.plot_surface(x, y, abs(u - u_ex))
    ax.set_xlabel("x values")
    ax.set_ylabel("t values")
    ax.set_zlabel("error")
    plt.show()

    # Calculate and print error
    ex = exact(1,xs)
    err =  abs(ex - u[:, -1])
    print(f'The infinity norm of the error is {np.linalg.norm(err, np.inf)}, h + k^2 is {h + k ** 2}')
    

def backward_diff(D, k, h, ts, xs):
    '''
        Function that calculates the backwards difference method for
        heat equations.

        Parameters
        ----------

            D: int, float
                The coefficient infront of u_xx in the heat equation
            
            k: int, float
                The change/step in t

            h: int, float
                The change/step in x

            ts: array_like
                The t values we want to calculate at
            
            xs: array_like
                The x values we want to calculate at

        Returns
        -------
            None, can return mesh if necessary    

    '''

    # Calculate constants for iteration
    sig = sigma(D, k, h)
    matrix = triMatrix([-sig, 1 + (2 * sig), -sig], len(ts))

    # Create u matricies
    u = np.zeros((len(xs), len(ts)))
    u[:,0] = t0(xs)

    u_ex = np.zeros((len(xs), len(ts)))
    u_ex[:,0] = t0(xs)

    # init the vector we add with boundary conditions 
    s = np.zeros(len(xs))

    # go through each time t
    for j, t in enumerate(ts[:-1]):
        # Calculate the boundary conditions
        s[0] = sig * l_bound(ts[j+1])
        s[-1] = sig * r_bound(ts[j+1])

        # Solve the values of vector for next time interval 
        u[:, j+1] = solve(matrix, u[:, j] + s) 

        # Solve exact equation
        u_ex[:, j+1] = exact(t, xs)

    # Meshgrid
    x, y = np.meshgrid(xs, ts)

    # Plotting functions
    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    ax.plot_surface(x, y, abs(u - u_ex))
    ax.set_xlabel("x values")
    ax.set_ylabel("t values")
    ax.set_zlabel("error")
    plt.show()

    # Calculate the error
    ex = exact(1,xs)
    err =  abs(ex - u[:, -1])
    print(f'The infinity norm of the error is {np.linalg.norm(err, np.inf)}, h + k^2 is {h + k ** 2}')



def crank_nicolson(D, k, h, ts, xs):
    '''
        Function that calculates crank nicolson method for
        heat equations.

        Parameters
        ----------

            D: int, float
                The coefficient infront of u_xx in the heat equation
            
            k: int, float
                The change/step in t

            h: int, float
                The change/step in x

            ts: array_like
                The t values we want to calculate at
            
            xs: array_like
                The x values we want to calculate at

        Returns
        -------
            None, can return mesh if necessary    

    '''
    # Calculate sigma, and create A and B matricies
    sig = sigma(D, k, h)
    A = triMatrix([sig/2, 1 - sig, sig/2], len(ts))
    B = triMatrix([-sig / 2, 1 + sig, -sig / 2], len(ts))

    # Initialize u and exact u matricies
    u = np.zeros((len(xs), len(ts)))
    u[:,0] = t0(xs)

    u_ex = np.zeros((len(xs), len(ts)))
    u_ex[:,0] = t0(xs)

    # Create vector to add boundary conditions
    s = np.zeros(len(xs))

    # For each t
    for j, t in enumerate(ts[:-1]):

        # Calculate boundary conditions
        s[0] = (sig / 2) * (l_bound(ts[j+1]) + l_bound(t))
        s[-1] = (sig / 2) * (r_bound(ts[j+1]) + r_bound(t))

        # Calculate numerical est and exact solution
        u[:, j+1] = solve(B, (A @ u[:, j]) + s) 
        u_ex[:, j+1] = exact(t, xs)

    x, y = np.meshgrid(xs, ts)  # Create mesh

    # Plotting functions
    fig, ax = plt.subplots(subplot_kw={"projection": "3d"})
    ax.plot_surface(x, y, abs(u - u_ex))
    ax.set_xlabel("x values")
    ax.set_ylabel("t values")
    ax.set_zlabel("error")
    plt.show()

    # Error calculations
    ex = exact(1,xs)
    err =  abs(ex - u[:, -1])
    print(f'The infinity norm of the error is {np.linalg.norm(err, np.inf)}, h^2 + k^2 is {(h**2) + k ** 2}')
