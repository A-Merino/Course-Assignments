import numpy as np
import scipy as sc

def finite_diff():
    '''
        This function is an implemented version of
        the finite difference method to solve second
        order differential equations

        Parameters
        ----------

        Returns
        -------
    '''

    A = createA()

    B = createB()

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
    return np.diags(diags, size=n)

def createB(func, h, L, z, b0, bn):
    '''
        Function that creates the vector B which is used in the
        numerical methods of finite difference and finite element
        specifically with the poisson_boltz equation

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
    ts = np.linspace(0,L,h)
    
    # Init vector B
    B = np.zeros(len(ts))

    # If the function then calculate it
    if func != 0:
        B = np.array(func(ts ,z, h))

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

            v: int, float
                The voltage of the capacitor

        Returns
        -------
            c: float
                The capacitance of the capacitor
    '''

    # Equation of Capacitence
    return sigma / v
