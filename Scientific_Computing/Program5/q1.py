import numpy as np
import matplotlib.pyplot as plt
from scipy.integrate import solve_ivp


def f(t, y):
    # Returns value of f given y
    return y * (1 - y)


def y(t):
    # Returns the y values based on t
    return np.exp(t) / (1 + np.exp(t))


def partA():

    # initial values
    t_span = [0, 2]
    y0 = [0.5]

    # Solve the ivp
    ts = np.linspace(0, 2)
    sol = solve_ivp(f, t_span, y0, t_eval=ts)

    # Plot the graph of the IVP
    plt.plot(ts, sol.y[0], "r-")
    plt.plot(ts, y(ts), "b.")
    plt.show()

    # Return actual end value
    return(sol.y[0][-1])



def partB(realY):
    print("Euler's Method:")
    # Create array of values for each h size
    ns = [np.linspace(0,2,21),np.linspace(0,2,201),np.linspace(0,2,2001), np.linspace(0,2,20001)]  
    hs = [0.1, 0.01, 0.001, 0.0001]

    for ar, h in zip(ns, hs):
        w = [0.5]
        for t in ar:
            # Formula for Euler's method 
            w.append(w[-1] + h * f(t, w[-1]))
        print(f"Global error for {h} is: {abs(realY - w[-2])}")
        plt.plot(ar,w[0:-1])
    plt.show()


def partC(realY):
    print("Implicit Euler's method:")
    # Create array of values for each h size

    ns = [np.linspace(0,2,21),np.linspace(0,2,201),np.linspace(0,2,2001), np.linspace(0,2,20001)]  
    hs = [0.1, 0.01, 0.001, 0.0001]

    for ar, h in zip(ns, hs):
        w = [0.5]
        for t in ar:
            # Formula for Implicit Euler's Method
            w.append(w[-1] + ((h / 2) * (f(t, w[-1]) + f(t+h, w[-1] + (h * f(t, w[-1]))))))
        print(f"Global error for {h} is: {abs(realY - w[-2])}")
        plt.plot(ar,w[0:-1])
    plt.show()


def partD(realY):
    print("Explicit Trapezoid method:")

    # Create array of values for each h size
    ns = [np.linspace(0,2,21),np.linspace(0,2,201),np.linspace(0,2,2001), np.linspace(0,2,20001)]  
    hs = [0.1, 0.01, 0.001, 0.0001]

    for ar, h in zip(ns, hs):
        w = [0.5]
        for t in ar:
            # Formula for Explicit Trapezoid Method
            w.append(w[-1] + ((h / 2) * (f(t, w[-1]) + f(t+h, w[-1] + (h * f(t, w[-1]))))))
        print(f"Global error for {h} is: {abs(realY - w[-2])}")
        plt.plot(ar,w[0:-1])
    plt.show()


def partE(realY):
    print("Adam-Bashforth Two-step method:")

    # Create array of values for each h size
    ns = [np.linspace(0,2,20),np.linspace(0,2,200),np.linspace(0,2,2000), np.linspace(0,2,20000)]  
    hs = [0.1, 0.01, 0.001, 0.0001]

    for ar, h in zip(ns, hs):
        w = [0.5, y(h)]
        for t in ar:
            # Formula for Adam-Bashforth Method
            w.append(w[-1] + (h * ((1.5 * f(t, w[-1])) - (0.5 * f(t-h, w[-2])))))
        print(f"Global error for {h} is: {abs(realY - w[-2])}")
        plt.plot(ar, w[0:-2])
    plt.show()




# 
t2 = partA()
partB(t2)
partC(t2)
partD(t2)
partE(t2)