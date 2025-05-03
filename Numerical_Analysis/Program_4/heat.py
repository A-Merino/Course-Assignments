import numpy as np
import matplotlib.pyplot as plt


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

    return np.exp((2 * t) + x) + np.exp((2 * t) - x)


def t0(x):
    return 2 * np.cosh(x)

def l_bound(t):
    return 2 * np.exp(2 * t)


def r_bound(t):
    return (np.exp(2)  + 1) * np.exp((2 * t) - 1)



def forward_diff(D, k, h, ts, xs):

    sig = sigma(D, k, h)
    matrix = triMatrix([sig, 1 - (2  * sig), sig], len(ts) - 2)

    fac = [sig, 1 - (2  * sig), sig]


    u = np.zeros((len(xs), len(ts)))

    u[:,0] = t0(xs)


    for j, t in enumerate(ts[1:-1]):
        u[0, j + 1] = fac[0]* l_bound(t) + fac[1] * u[1,j] + fac[2] * u[2, j]

        u[1:-2, j + 1] = fac[0]* u[0:-3, j] + fac[1] * u[1:-2,j] + fac[2] * u[2:-1, j]

        u[-1, j + 1] = fac[2]* r_bound(t) + fac[1] * u[-1,j] + fac[0] * u[-2, j]

        correct = exact(t, xs)


        plt.plot(xs, abs(correct - u[:, j+1]))

    plt.show()
    print(u)
    ex = exact(1,xs)
    est = u[:, -2]

    print(ex)
    print(est)

    correct = exact(1, xs)






def backward_diff(D, k, h, ts, xs):
    sig = sigma(D, k, h)
    matrix = triMatrix([-sig, 1 + (2 * sig), -sig], len(ts) - 2)

def crank_nicolson():
    pass

