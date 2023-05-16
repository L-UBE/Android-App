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
    filled_points = np.array(bytearray(4096))
    for i in range(256):
        start = None
        end = None
        for j in range(16):
            if (nonfilled_points[16*i + j] == 0x01):
                if (start == None):
                    start = 16*i + j
                    filled_points[start] = 0x01
                elif (end == None):
                    end = 16*i + j
                    filled_points[end] = 0x01
                    filled_points[start + 1:end] = 0x01
                    start = None
                    end = None
    return filled_points

def compute(equation, scale, shouldFillIn):
    computed_points = np.array(bytearray(4096))
    x_var, y_var, z_var = symbols('x y z')
    scaled_points = [*np.arange(-scale*7.5, scale*7.5 + scale, scale)]
    equation = parse_expr(equation, transformations=transformations, evaluate=False)
    for x, x_scaled in enumerate(scaled_points):
        for y, y_scaled in enumerate(scaled_points):
            z = solveset(equation.subs({x_var: x_scaled, y_var: y_scaled}), z_var, domain=S.Reals).args
            for root in z:
                index = round(root, scale, scaled_points)
                if(index):
                    if (isinstance(index, int)):
                        computed_points[256*x + 16*y + index] = 0x01
                    elif (isinstance(index, tuple)):
                        computed_points[256*x + 16*y + index[0]] = 0x01
                        computed_points[256*x + 16*y + index[1]] = 0x01

    if(shouldFillIn):
        return fill_in(computed_points)
    else:
        return computed_points