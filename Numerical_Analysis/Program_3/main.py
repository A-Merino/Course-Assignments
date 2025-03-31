import finite_diff as fd
import finite_element as fe
import numpy as np
import scipy as sc
import matplotlib.pyplot as plt


def startMethod(L, h, z, phi_0, Vs, sigma = 1, method = "f_diff", same_plot=False):
    capacity = fd.capacitence(sigma, Vs)

    if method == 'f_diff':
        y_v = [fd.finite_diff(L, h, z, phi_0, V) for V in Vs]
        plotCurves(y_v, capacity, L, h, many=same_plot)
        
    elif method == 'f_elem':
        y_v = [fe.finite_element(L, h, z, phi_0, V) for V in Vs]
        plotCurves(y_v, capacity, L, h, many=same_plot)
    else:
        raise Exception("Method given not correct")


def plotCurves(phi_x, c_v, L, h, many=False):

    if many:
        for y in phi_x:
            plt.plot(np.round(np.linspace(0, L,int(L / h)), 2), y)
        plt.show()
    else:
        plt.plot(np.round(np.linspace(0, L,int(L / h)), 2), phi_x)
        # plt.plot(c_v)
        plt.show()
    

def main():

    Vs = np.delete(np.arange(-5, 6), np.where(np.arange(-5, 6) == 0))

    # Part B
    # startMethod(6, 0.01, 1, 0, Vs)
    startMethod(6, 0.01, 1, 0, Vs, same_plot=True)
    # Part C
    # startMethod(3, 0.01, 1, 0, Vs)
    startMethod(3, 0.01, 1, 0, Vs, same_plot=True)
    # Part D
    # startMethod(6, 0.01, 2, 0, Vs)
    startMethod(6, 0.01, 2, 0, Vs, same_plot=True)
    # Part E1
    # startMethod(6, 0.01, 1, 0, Vs, method="f_elem")
    startMethod(6, 0.01, 1, 0, Vs, method="f_elem", same_plot=True)
    # Part E2
    # startMethod(3, 0.01, 1, 0, Vs, method="f_elem")
    startMethod(3, 0.01, 1, 0, Vs, method="f_elem", same_plot=True)
    # Part E3
    # startMethod(6, 0.01, 2, 0, Vs, method="f_elem")
    startMethod(6, 0.01, 1, 0, Vs, method="f_elem", same_plot=True)


    # print("Hello world")

if __name__ == "__main__":
    main()