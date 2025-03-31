import numpy as np
import scipy as sc
import finite_diff as fd


def alpha(h, z):
    '''
        The equation of the alpha values in the
        finite element method of the Poisson-Boltzman
        equation given a step size and valence value

        Parameters
        ----------
            z: int, float
                The valence of the ion types

            h: int, float
                The value of the step size that the function
                is being calculated at
        
        Returns
        -------
            alpha: float
                The value of alpha
    '''
    return (2 / h) + (2 * (z ** 2) * h / 3)


def beta(h, z):
    '''
        The equation of the beta values in the
        finite element method of the Poisson-Boltzman
        equation given a step size and valence value

        Parameters
        ----------
            z: int, float
                The valence of the ion types

            h: int, float
                The value of the step size that the function
                is being calculated at
        
        Returns
        -------
            beta: float
                The value of beta
    '''
    return (1 / h) - (2 * (z ** 2) * h / 6)


def createB(h, L, z, b0, bn, beta):
    '''
        Function that creates the vector B which is used in the
        numerical methods of finite difference and finite element
        specifically with the poisson_boltz equation

        Parameters
        ----------

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

            beta: int, float
                The beta value of the A matrix

        Returns
        -------

            B: array_like
                The vector B of size L / h, which is used
                in the numerical methods for partial differential
                equations.  
    '''

    # create t values we want to calculate at
    ts = np.round(np.linspace(0, L,int(L / h)), 2)

    # Init vector B
    B = np.zeros(len(ts))

    # Boundary Conditions
    B[0] = (beta * (-1)) * b0
    B[-1] = (beta * (-1)) * bn
    
    return B


def finite_element(L, h, z, b0, bn):
    '''
        Function that replicates the finite element method
        for the Poisson-Boltzmann equation. 
    '''

    N = int(L / h)

    alph = alpha(h, z)
    bet = beta(h, z)
    
    print(alph, bet)

    diag = [bet, -1 * alph, bet]

    A = fd.createA(diag, N) 
    B = createB(h, L, z, b0, bn, bet)

    y = sc.linalg.solve(A, B)

    return y

