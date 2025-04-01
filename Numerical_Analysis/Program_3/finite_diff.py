import numpy as np
import scipy as sc


def f(h, z, phi, b0, bn):
    '''
        The function calculates all f_i of
        the Poisson-Boltzmann equation

        Parameters
        ----------

            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            phi: array_like
                The y value we would like to calculate the diffEq at

            b0: int, float
                The value of the function at time 0

            bn: int, float
                The value of the function at time L

        Returns
        -------
            F_x: array_like
                The y values at every timestep t of the
                given equation
    '''

    # All phi values needed for phi_i-1
    p1 = np.insert(phi, 0, b0)[:-1]

    # All phi values needed for phi_i+1
    p3 = np.append(phi, bn)[1:]

    # All phi values needed for phi_i were given in parameter 
    return p1 + phi_i(z, h, phi) + p3


def phi_i(h, z, phi):
    '''
        The function calculates the value of the current phi
        for the vector F

        Parameters
        ----------

            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            phi: array_like
                The y value we would like to calculate the PDE at

        Returns
        -------
            y: array_like
                The values for the vector F in Newton's Method
    '''
    return (-2 * phi) - (z * (h**2)* np.sinh(z * phi))


def f_prime(h, z, phi):
    '''
        The function calculates the value of f_prime
        at the y-value, phi
        Parameters
        ----------

            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            phi: array_like
                The y value we would like to calculate the PDE at

        Returns
        -------
            f'(phi): array_like
                The values for the diagonal of the jacobian matrix DF
    '''
    return (-2) - ((z**2) * h * np.cosh(z * phi))


def createDF(h, z, phi, n):
    '''
        The function calls the createA function with
        the correct diagonal values for the jacobian matrix
        of the Poisson-Boltzmann equation

        Parameters
        ----------

            L: int, float
                The time at the boundary of pde
            
            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            b0: int, float
                The value of the function at time 0

            bn: int, float
                The value of the function at time L

        Returns
        -------
            y: array_like
                The y values at every timestep t of the
                given equation
    '''
    return createA([1, f_prime(h,z,phi), 1], n)


def finite_diff(L, h, z, b0, bn):
    '''
        This function is an implemented version of
        the finite difference method to solve second
        order differential equations. This includes the 
        code for Newton's Method since it is iterative

        Parameters
        ----------

            L: int, float
                The time at the boundary of pde
            
            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            b0: int, float
                The value of the function at time 0

            bn: int, float
                The value of the function at time L

        Returns
        -------
            y: array_like
                The y values at every timestep t of the
                given equation
    '''

    # Create initial guess and amount of t values
    N = int(L/h)
    y = np.zeros(N)

    bigChange = True  # Boolean to break while loop
    # Counter to end after 50 iterations if not converging
    counter = 0

    # start newton's method
    while bigChange and counter < 50:

        # Create the DF(x_k) matrix
        DF = createDF(h, z, y, N)

        # Create the Fx_k vector
        F = f(h, z, y, b0, bn)

        # Solve the Ax = b problem from inverse matrix
        temp = sc.linalg.solve(DF, F)

        # Check if the L2 norm is below the arbitrary epsilon
        if sc.linalg.norm(temp) < h ** 2:
            # If so then stop loop after iteration
            bigChange = False

        # subtract the solved vector from DF * x = F
        # from guess and make it the new next guess
        y = y - temp

        counter += 1  # add 1 iteration

    # return closest correct y-values
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

    return np.divide(sigma, v)
