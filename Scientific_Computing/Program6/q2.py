import numpy as np
from numpy.random import rand


def f(t, y):
    return 0-y

def dBt(dt):
    return rand(1,1)[0,0] * dt

def dg(t, y):
    return 0.1

def g(t, y):
    return 2

def emmethod():
    print("Euler-Maruyama's Method:")
    # Create array of values for each h size
    ns = [np.linspace(0,1,11),np.linspace(0,1,101),np.linspace(0,1,1001)]  
    hs = [0.1, 0.01, 0.001]

    for ar, h in zip(ns, hs):
        y1s = []
        for d in range(5000):
            w = [1]
            for t in ar:
                # Formula for Euler-Maruyama's method 
                w.append(w[-1] + h * f(t, w[-1]) + g(t, w[-1]) * dBt(h))
            y1s.append(w[-1])
        print(f"The mean for timestep {h} at y(1) is {np.mean(y1s):0.4f} and the standard deviation is {np.std(y1s):0.4f}")




def milstein():
    print("Milstein's Method:")
    # Create array of values for each h size
    ns = [np.linspace(0,1,11),np.linspace(0,1,101),np.linspace(0,1,1001)]
    hs = [0.1, 0.01, 0.001]

    for ar, h in zip(ns, hs):
        y1s = []
        for d in range(5000):
            w = [1]
            for t in ar:
                # Formula for Milsteins's method
                db = dBt(h)
                w.append(w[-1] + (f(t, w[-1]) * h) + (g(t, w[-1]) * db) + (0.5 * g(t, w[-1]) * w[-1]  * (db**2 - h)))
            y1s.append(w[-1])
        print(f"The mean for timestep {h} at y(1) is {np.mean(y1s):0.4f} and the standard deviation is {np.std(y1s):0.4f}")


emmethod()
milstein()
