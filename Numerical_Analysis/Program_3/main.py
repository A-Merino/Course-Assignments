import finite_diff as fd
import finite_element as fe
import numpy as np
import scipy as sc
import matplotlib.pyplot as plt


def startMethod(L, h, z, phi_0, Vs, method = "f_diff", same_plot = False):
    '''
        Function that runs the chosen finite method for evaluating unique
        boundary problems of differential equations

        Parameters
        ----------

            L: int, float
                The time at the boundary of diffEq

            h: int, float
                The step size that is being used in the
                numerical method.

            z: int
                The valence of the ion types

            phi_0: int, float
                The value of the function at time 0

            Vs: int, float
                The value of the function at time L
                for different voltages, V

            method: string
                The numerical method desired.
                    - f_diff: Finite Difference Method
                    - f_elem: Finite Difference Method
            
            same_plot: bool
                A boolean that determines whether all Voltages
                are displayed on the same graph

        Returns
        -------
            None
    '''

    # Finite Difference Method
    if method == 'f_diff':

        # Compute
        y_v = [fd.finite_diff(L, h, z, phi_0, V) for V in Vs]
        
        # Calculate capacity
        capacity = fd.capacitence(np.array([y[1] / h for y in y_v]), Vs)
        
        # Plot curves
        plotCurves(y_v, capacity, Vs, L, h, many=same_plot)

    # Finite Element Method
    elif method == 'f_elem':
        
        # Compute
        y_v = [fe.finite_element(L, h, z, phi_0, V) for V in Vs]

        # Calculate capacity
        capacity = fd.capacitence(np.array([y[1] / h for y in y_v]), Vs)

        # Plot curves
        plotCurves(y_v, capacity, Vs, L, h, many=same_plot)

    else:
        raise Exception("Method given not correct")


def plotCurves(phi_x, c_v, Vs, L, h, many=False):
    '''
        Function that plots given curves

        Parameters
        ----------

            phi_x: array_like, 2D
                An array of arrays of y values
            
            c_v: array_like
                The capacities of the capacitator
                given a Voltage

            Vs: int, float
                The value of the function at time L
                for different voltages, V
            
            L: int, float
                The time at the boundary of diffEq

            h: int, float
                The step size that is being used in the
                numerical method.
            
            many: default = False, bool
                A boolean value to track whether to put many
                curves on one graph or to separately graph

        Returns
        -------

            None
    '''


    if many:

        # Plot all
        for y in phi_x:
            plt.plot(np.round(np.linspace(0, L,int(L / h)), 2), y)
        # Show plot
        plt.show()
    
    else:

        # Plot 1 and show 
        for y, c, v in zip(phi_x, c_v, Vs):
            print(c)
            plt.title(f"Phi(x) with Voltage {v}")
            plt.plot(np.round(np.linspace(0, L,int(L / h)), 2), y)
            plt.plot([0,L], [c,c], label=f'Capacity: {c}')
            plt.legend()
            plt.show()
    

def main():
    '''
        Main driver for Program 3
    '''

    # Get our array of Voltages
    Vs = np.delete(np.arange(-5, 6), np.where(np.arange(-5, 6) == 0))

    # Part B (z=1, L = 6, h=0.01)
    startMethod(6, 0.01, 1, 0, Vs)

    # Part C (z=1, L = 3, h=0.01)
    startMethod(3, 0.01, 1, 0, Vs)

    # Part D (z=2, L = 6, h=0.01)
    startMethod(6, 0.01, 2, 0, Vs)

    # Part F1 (z=1, L = 6, h=0.01)
    startMethod(6, 0.01, 1, 0, Vs, method="f_elem")

    # Part F2 (z=1, L = 3, h=0.01)
    startMethod(3, 0.01, 1, 0, Vs, method="f_elem")

    # Part F3 (z=2, L = 6, h=0.01)
    startMethod(6, 0.01, 2, 0, Vs, method="f_elem")


if __name__ == "__main__":
    main()
