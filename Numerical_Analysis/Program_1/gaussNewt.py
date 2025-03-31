import scipy as sc
import numpy as np


def createDr(satellites, guess):
    '''
        Function which creates the jacobian matrix
        for Gauss-Newton method.

        To create the jacobian matrix through Gauss-Newton method
        the jacobian DJ is equivalent to Dr^TDr where Dr is the \
        m x n partial derivative matrix

        Parameters:
            - satellites: A list of satellite positions and times
                - a_i: The x position of starting input
                - b_i: The y position of starting input
                - c_i: The z position of starting input
                - t_i: The time at which the reciever got information
            - guess: A 4-dimensional vector of the current guess (x, y, z, d)
    '''

    sl = 299792.458  # Speed of light
    x, y, z, d = guess  # Converting guess into vars

    # initialize the column vectors of the Dr matrix
    A = []
    B = []
    C = []
    D = []

    # Calculate the partial derivative for each satellite
    for a, b, c, t in satellites:
        A.append((x - a))
        B.append((y - b))
        C.append((z - c))
        # The only reason I multiply the constant here
        # is because I don't feel like adding if statements
        D.append((sl ** 2) * (t - d))

    jacobian = [[0, 0, 0, 0],
                [0, 0, 0, 0],
                [0, 0, 0, 0],
                [0, 0, 0, 0]]

    # Create the matrix Dr^T
    derivatives = [A, B, C, D]

    # The Jacobian is symmetrical so we calculate
    # the upper triangular portion and copy to lower half

    for i in range(len(derivatives)):
        for j in range(i, len(derivatives)):

            # Calculate Diagonal
            if i == j:
                jacobian[i][j] = calcDrDiag(derivatives[i])
            # Calculate Upper half of triangle
            else:
                jacobian[i][j] = jacobian[j][i] = calcDrRest(derivatives[i], derivatives[j])
                # jacobian[j][i] = jacobian[i][j]

    return jacobian


def calcDrDiag(diffs):
    '''
        Function which calculates the diagonal of the jacobian matrix

        Parameters:
            - diffs: A column of calculated partial derivatives
    '''

    total = 0

    # Go through each difference, square it, and add to sum
    for num in diffs:
        total += num ** 2

    # Multiply by constant of 4 which is part of derivaite
    return 4 * total


def calcDrRest(diff1, diff2):
    '''
        Function which calculates a cell of the jacobian matrix
        as long as the cell is not on the diagonal

        Parameters:
            - diff1: The column vector of the matrix Dr^T
            - diff2: The row vector of the matrix Dr
    '''

    total = 0

    # Go through each pair of differences, multiply, and add to sum
    for num1, num2 in zip(diff1, diff2):
        total += num1 * num2

    # Multiply by constant which is calculated in derivative
    return 4 * total


def createF(satellites, guess):
    '''
        Function which creates the vector F(X_k) at
        iteration step k in Gauss-Newton method

        Paramters:
            - satellites: A list of satellite positions and times
                - a_i: The x position of starting input
                - b_i: The y position of starting input
                - c_i: The z position of starting input
                - t_i: The time at which the reciever got information
            - guess: A 4-dimensional vector of the current guess (x, y, z, d)
    '''

    sl = 299792.458  # Speed of light
    x, y, z, d = guess
    Fx = []
    for a, b, c, t in satellites:
        # Derived formula of each element f_i in F(X_k)
        f_i = ((x-a)**2) + ((y-b)**2) + ((z-c)**2) - ((sl*(t-d))**2)
        Fx.append(f_i)

    return Fx


def createDjT(satellites, guess):
    '''
        Function which creates the jacobian matrix
        for Gauss-Newton method.

        To create the jacobian matrix through Gauss-Newton method
        the jacobian DJ is equivalent to Dr^TDr where Dr is the
        m x n partial derivative matrix

        Parameters:
            - satellites: A list of satellite positions and times
                - a_i: The x position of starting input
                - b_i: The y position of starting input
                - c_i: The z position of starting input
                - t_i: The time at which the reciever got information
            - guess: A 4-dimensional vector of the current guess (x, y, z, d)
    '''

    sl = 299792.458  # Speed of light
    x, y, z, d = guess  # Converting guess into vars

    # initialize the column vectors of the Dr matrix
    A = []
    B = []
    C = []
    D = []

    # Calculate the partial derivative for each satellite
    for a, b, c, t in satellites:
        A.append(2 * (x - a))
        B.append(2 * (y - b))
        C.append(2 * (z - c))
        # The only reason I multiply the constant here
        # is because I don't feel like adding if statements
        D.append(2 * (sl ** 2) * (t - d))

    # Create the matrix Dr^T
    return [A, B, C, D]


def gaussNewt(satellites, xi):
    '''
        Function that computes the Gauss-Newton iterative method
        which approximates the location of a reciever on earth.

        Paramters:
            - satellites: A list of satellite positions and times
                - a_i: The x position of starting input
                - b_i: The y position of starting input
                - c_i: The z position of starting input
                - t_i: The time at which the reciever got information
            - xi: The initial 4-dimensional vector of the current guess (x, y, z, d)
    '''

    # location of Earth
    earth = [0, 0, 0]

    # initialize loop variables
    counter = 0
    bigChange = True

    while bigChange and counter < 100:

        # calculate the jacobian and current vector error
        dF = createDr(satellites, xi)
        drT = createDjT(satellites, xi)
        Fx = createF(satellites, xi)

        short = np.array(drT) @ np.array(Fx)
        # Solve the inverse problem
        temp = sc.linalg.solve(dF, short)

        # Check if the change is smaller than really small
        if sc.linalg.norm(temp) < 0.5e-10:
            bigChange = False
        # update to new vector using the difference
        xi = xi - temp

        # increment
        counter += 1

    return xi


def part5a():
    '''
        Function to run part 5a of the assignment

    '''

    receiver = [100, -200, 6000, 0]

    sats = [[-2019, 2279, 26395, 0.0665747329503],
            [-17706, -15990, 11695, 0.0813262620503],
            [-6503, 13883, 21701, 0.0716159594731],
            [17200, 2763, 20062, 0.0732945778521],
            [7161, 8292, 24205, 0.0689690379518],
            [10690, -21329, 11695, 0.081327616831],
            [-25706, 0, 6722, 0.0857461617486],
            [2559, -1659, 26394, 0.0665739901458]]

    # Run Gauss Newton
    position = gaussNewt(sats, receiver)


    print("Part 5a:\nThe receiver's position based on the eight satellites are:")
    print(f"x: {position[0]:0.4f}\ny: {position[1]:0.4f}\nz: {position[2]:0.4f}\nTime: {position[3]:0.4f} seconds")


def part5b():
    '''
        Function to run part 5b of the assignment

    '''
    t_eps = 10e-8

    true_pos = [0, 0, 6670, 0]
    big_error = 0
    receiver = [100, -200, 6000, 0]

    # "random" satellites
    sats = [[-2019, 2279, 26395, 0.0665747329503],
            [-17706, -15990, 11695, 0.0813262620503],
            [-6503, 13883, 21701, 0.0716159594731],
            [17200, 2763, 20062, 0.0732945778521],
            [7161, 8292, 24205, 0.0689690379518],
            [10690, -21329, 11695, 0.081327616831],
            [-25706, 0, 6722, 0.0857461617486],
            [2559, -1659, 26394, 0.0665739901458]]

    # Introducing the never seen before.... NONTUPLE FOR-LOOP
    # Forgive me Chan for I have sinned
    for i in range(-1, 2, 2):
        for j in range(-1, 2, 2):
            for k in range(-1, 2, 2):
                for m in range(-1, 2, 2):
                    for n in range(-1, 2, 2):
                        for p in range(-1, 2, 2):
                            for q in range(-1, 2, 2):
                                for r in range(-1, 2, 2):

                                    # Go through each of the EIGHT satellites
                                    # and change by epsilon
                                    signs = [i, j, k, m, n, p, q, r]
                                    n_sats = sats
                                    for s in range(len(n_sats)):
                                        n_sats[s][-1] += signs[s] * t_eps

                                    pos = gaussNewt(n_sats, receiver)

                                    delta_x = true_pos - pos

                                    if sc.linalg.norm(delta_x) > big_error:
                                        big_error = sc.linalg.norm(delta_x)

    print(f"\nPart 5b:\nThe largest error found was: {big_error:0.4f}")


# Main Function
def main():
    part5a()
    part5b()


main()
