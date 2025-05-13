import numpy as np
import matplotlib.pyplot as plt


evaluation = []
xs = []
x = 0.1
for i in range(20):
    evaluation.append((np.sqrt(1 + x) - 1) / x)
    xs.append(x)
    x = x / 10


def plotter(xs, evaluation):
    ax = plt.subplot()

    plt.plot(xs, evaluation, "r-o")
    ax.set_xscale("logit")
    plt.show()

# plotter(xs, evaluation)

print(f"The list of numbers calculated is:\n{evaluation}")