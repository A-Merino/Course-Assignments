import numpy as np
import matplotlib.pyplot as plt
import scipy.interpolate as intt


def main():
    # create 21 valeus [1, 3]
    x = np.linspace(1, 3, 21)
    # ln(x)
    y = np.log(x)
    # Create a natural spline then a clamped spline
    spline = intt.CubicSpline(x, y, bc_type="natural")
    spline2 = intt.CubicSpline(x, y, bc_type=((1, 1.0), (1, 1 / 3)))

    # list to evaluate [1, 3]
    subs = np.linspace(1, 3, int(10e6))
    truth = np.log(subs)  # evaluate true values
    pred = spline(subs)  # evaluate natural spline
    plt.plot(subs, truth - pred)  # plot error
    plt.show()

    pred2 = spline2(subs)  # evaluate clamped spline
    plt.plot(subs, truth - pred2)  # plot error
    plt.show()


main()
