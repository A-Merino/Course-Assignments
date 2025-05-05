import numpy as np
import heat
import matplotlib.pyplot as plt


# Left boundary condition
def left():
    return 0

# Right boundary condition
def right():
    return 0

# Exact solution
def exact(x, t):
    return np.sin(np.pi * x) * np.cos(4 * np.pi * t)

def forward_diff(k, h, ts, xs):
    '''
        Calculates the forward difference method for wave
        equations

        Parameters
        ----------

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

    # Calculate sigma and matrix
    sigma = k**2 / h**2
    matrix = heat.triMatrix([sigma**2, 2- (2*sigma**2) ,sigma**2], len(xs))

    # Vector to add
    g = np.zeros(len(xs))

    # Create boundary condition vector
    bc = np.zeros(len(xs))
    bc[0] = sigma ** 2 * left() / 2
    bc[-1] = sigma ** 2 * right() / 2

    u = np.zeros((len(xs), len(ts)))  # Create u matrix

    # Solve t_0 and t_1
    u[:,0] = np.sin(np.pi * xs)
    u[:,1] = matrix @ u[:,0] / 2 + bc + k * g

    # Create and solve exact matrix at t_0 and t_1
    u_ex = np.zeros((len(xs), len(ts)))
    u_ex[:, 0] = exact(xs, ts[0])
    u_ex[:, 1] = exact(xs, ts[1])

    # for each time, t
    for j in range(1 ,len(ts) - 1):
        # Create boundary conditions
        bc[0] = sigma ** 2 * left()
        bc[-1] = sigma ** 2 * right()

        # Solve est. t_j+1 and exact for t_j+1
        u[:, j + 1] = matrix @ u [:,j]  + bc - u[:, j - 1]
        u_ex[:,j + 1] = exact(xs, ts[j+1])

    # Calculate and print the error
    x_ind = (len(xs) - 1) // 4
    t_ind = 3 * (len(ts) -1 ) // 4
    err = abs(u_ex[x_ind, t_ind] - u[x_ind, t_ind])
    print(f'{h:0.5f}\t{k:0.5f}\t{u[x_ind, t_ind]:0.7f}\t\t{err:0.5f}')
