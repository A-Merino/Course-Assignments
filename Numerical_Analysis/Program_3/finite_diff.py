import numpy as np
import scipy as sc

def finite_diff(L, h, z, b0, bn):
    '''
        This function is an implemented version of
        the finite difference method to solve second
        order differential equations

        Parameters
        ----------

        Returns
        -------
    '''

    N = int(L/h)

    A = createA([1, -2], N)
    B = createB(poisson_boltz, h, L, z, b0, bn)

    y = sc.linalg.solve(A, B) # Maybe???

    return y


def createA(diags, n):
    '''
        Function that creates the matrix A, which is used in the 
        numerical methods of finite difference and finite element. 

        Parameters
        ----------
            
            diags: array_like
                The three diagonal values which are needed for
                the creation of A

            n: int
                The size of matrix A we would like to output

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


def createB(func, h, L, z, b0, bn):
    '''
        Function that creates the vector B which is used in the
        numerical method of finite difference specifically with
        the poisson_boltz equation

        Parameters
        ----------

            func: function, int
                A function that describes the values of vector B
                which will be calculated arraywise. If it is int
                then we do not calculate function

            h: int, float
                The step size that is being used in the
                numerical method.

            L: int, float
                The time at the boundary of pde

            z: int
                The valence of the ion types

            b0: int, float
                The value of the function at time 0

            bn: int, float
                The value of the function at time L

        Returns
        -------

            B: array_like
                The vector B of size L / h, which is used
                in the numerical methods for partial differential
                equations.  
    '''

    # create t values we want to calculate at
    ts = np.round(np.linspace(0, L,int(L / h)), 2)

    # Create vector B
    B = np.array(func(ts ,z, h))

    # Boundary Conditions
    B[0] = b0
    B[-1] = bn

    return B


def poisson_boltz(x, z, h):
    '''
        Function which implements the Poisson-Boltzman equation
        for the vector B in the finite difference method

        Parameters
        ----------
            x: int, array_like
                The x value(s) that we want to calculate the
                function at

            z: int, float
                The valence of the ion types

            h: int, float
                The value of the step size that the function
                is being calculated at
    '''
    # The equation of Poisson-Boltzmann equation
    return z * (h ** 2) * np.sinh(z * x)


def capacitence(sigma, v):
    '''
        Function that calculates the capacitence of the
        capacitor given a specific voltage, V. 

        Paramters
        ---------
            sigma: function ???

            v: int, float, array_like
                The voltage of the capacitor

        Returns
        -------
            c: float
                The capacitance of the capacitor
    '''

    # Equation of Capacitence

    return sigma / v
