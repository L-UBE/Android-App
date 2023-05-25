from sympy.parsing.sympy_parser import parse_expr, standard_transformations, implicit_multiplication_application, convert_xor
from sympy import *
import numpy as np

transformations = (standard_transformations + (implicit_multiplication_application,) + (convert_xor,))

def round(value, scale, scaled_points):
    margin = scale * 0.5

    if value == 0:
        return (7, 8)

    if (value <= scaled_points[0] - margin or value >= scaled_points[15] + margin):
        return None

    for i, point in enumerate(scaled_points):
        difference = abs(point - value)

        if difference <= margin:
            if difference == margin and scaled_points.index(point) >= 8:
                i += 1
            return i


def fill_in(nonfilled_points):
    filled_points = np.zeros(4096, dtype=np.uint8)
    for i in range(256):
        start = None
        for j in range(16):
            index = 16 * i + j
            if nonfilled_points[index] == 1:
                if start is None:
                    start = index
                    filled_points[start] = 1
                else:
                    filled_points[start + 1:index + 1] = 1
                    start = None
    return filled_points

def compute(equation, scale, xoff, yoff, zoff, shouldFillIn):
    computed_points = np.zeros(4096, dtype=np.uint8)
    scaled_points = [*np.arange(-scale*7.5, scale*7.5 + scale, scale)]

    x_var, y_var = symbols('x y')

    equation = parse_expr(equation, transformations=transformations, evaluate=False)
    equation = lambdify((x_var, y_var), equation)

    for x, x_scaled in enumerate(scaled_points):
        for y, y_scaled in enumerate(scaled_points):

            z = solveset(equation(x_scaled - xoff, y_scaled - yoff),
                         domain=S.Reals).args
            for root in z:
                index = round(root + zoff, scale, scaled_points)
                if(index):
                    if (isinstance(index, int)):
                        computed_points[256*x + 16*y + index] = 1
                    elif (isinstance(index, tuple)):
                        computed_points[256*x + 16*y + index[0]] = 1
                        computed_points[256*x + 16*y + index[1]] = 1

    if(shouldFillIn):
        return fill_in(computed_points)
    else:
        return computed_points