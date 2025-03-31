import numpy as np
import scipy as sc
import time


def createDF(satellites, guess):
    '''
        Function which creates the Jacobian
        matrix given a list of satellites

        Parameters:
            - satellites: A list of satellite positions and times
                - a_i: The x position of starting input
                - b_i: The y position of starting input
                - c_i: The z position of starting input
                - t_i: The time at which the reciever got information
            - guess: A 4-dimensional vector of the current guess (x, y, z, d)
    '''

    sl = 299792.458  # Speed of light
    jacobian = []  # create jacobian holder for rows

    # separate the guess into individual variables
    x, y, z, d = guess

    # for each satellite in the list
    for a, b, c, t in satellites:
        # Calculate the row of the jacobian matrix given
        # the satellite position and current guess
        jacobian.append([2*(x-a), 2*(y-b), 2*(z-c), 2*((sl**2)*(t-d))])

    return jacobian


def createF(satellites, guess):
    '''
        Function that creates the F(X_k) vector,
        which is needed in the Mutivariable Newton's Method

        Parameters
    '''

    sl = 299792.458  # Speed of light
    x, y, z, d = guess
    Fx = []
    for a, b, c, t in satellites:
        # Derived formula of each element f_i in F(X_k)
        f_i = ((x-a)**2) + ((y-b)**2) + ((z-c)**2) - ((sl*(t-d))**2)
        Fx.append(f_i)

    return Fx


def multiNewtMethod(satellites, xi):
    '''
        Function that computes Multivariable
        Newton's method

        Parameters:
            - satellites: The coordinates of the satellites given
                        in the problem asked
            - xi: The initial guess of the position of the reciever
    '''

    earth = [0, 0, 0]  # Location of Earth

    bigChange = True  # Boolean to break while loop

    # Counter to end after 50 iterations if not converging
    counter = 0

    while bigChange and counter < 50:

        df = createDF(satellites, xi)  # Create the DF(x_k) matrix
        Fx = createF(satellites, xi)  # Create the Fx_k vector 

        # Solve the Ax = b problem from inverse matrix
        temp = sc.linalg.solve(df, Fx)

        # Check if the L2 norm is below the arbitrary epsilon
        if sc.linalg.norm(temp) < 0.5e-10:
            # If so then stop loop after iteration
            bigChange = False

        # subtract the solved vector from DF * x = F
        # from guess and make it the new next guess
        xi = xi - temp

        counter += 1

    return xi


def part2():
    '''
        Function that creates and runs the problem
        asked in part 2
    '''

    # The given satellites in part2
    sats = [[15600, 7540, 20140, 0.07074],
            [18760, 2750, 18610, 0.07220],
            [17610, 14630, 13480, 0.07690],
            [19170, 610, 18390, 0.07242]]

    # the initial guess given in part 2
    init = [0, 0, 6670, 0]

    # run the inputs into the Multivariable
    # Newton's method and save the output
    position = multiNewtMethod(sats, init)

    # Formatted output with the given output
    print('')
    print("Part 1:\nThe receiver's position based on the four satellites are:")
    print(f"x: {position[0]:0.4f}\ny: {position[1]:0.4f}\nz: {position[2]:0.4f}\nTime: {sats[0][3] + position[3]:0.4f} seconds")


def part3():
    # The given satellites in part2
    sats = [[15600, 7540, 20140, 0.07074],
            [18760, 2750, 18610, 0.07220],
            [17610, 14630, 13480, 0.07690],
            [19170, 610, 18390, 0.07242]]

    # the initial guess given in part 2
    init = [0, 0, 6670, 0]

    true_pos = multiNewtMethod(sats, init)

    t_eps = 10e-8
    big_error = 0

    '''
        I hate that I have to do this...
        but quintuple nest for-loop RAHHHH
        (Dr. Stansifer and Dr. Chan please don't hurt me)

        But its really only 2^4 = 16 different iterations
        So 16 runs of Multivariable Newton's method, which
        is just AWFUL.
    '''

    for i in range(-1, 2, 2):
        for j in range(-1, 2, 2):
            for k in range(-1, 2, 2):
                for m in range(-1, 2, 2):
                    # Collect signs
                    sign = [i, j, k, m]
                    n_sats = sats
                    for n in range(len(n_sats)):
                        # Go through each of the four satellites
                        # and change by epsilon
                        n_sats[n][-1] += sign[n] * t_eps

                    pos = multiNewtMethod(n_sats, init)

                    # Calculate difference
                    delta_x = true_pos - pos

                    if sc.linalg.norm(delta_x) > big_error:
                        big_error = sc.linalg.norm(delta_x)

    print(f"\nPart 2:\nThe largest error found was: {big_error:0.4f}")


# Main funciton
def main():
    part2()
    part3()


main()
