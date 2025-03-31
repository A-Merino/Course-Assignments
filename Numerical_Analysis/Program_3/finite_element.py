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

def finite_element():
    '''
        Function that replicates the finite element method
        for the Poisson-Boltzmann equation. 
    '''

    alph = alpha(h, z)
    bet = beta(h, z)
    
    diag = [bet, -1 * alph, bet]

    A = fd.createA(diag, ) # Needs n

