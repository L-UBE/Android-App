import numpy as np

res = 16

def translateShape(matrix, xoff, yoff, zoff):
    matrix = np.array(matrix).reshape(res,res,res)
    shifted_matrix = np.zeros_like(matrix)
    shifted_matrix[max(0, xoff):min(res, res + xoff),
    max(0, yoff):min(res, res + yoff),
    max(0, zoff):min(res, res + zoff)] = matrix[max(0, -xoff):min(res, res - xoff),
                                         max(0, -yoff):min(res, res - yoff),
                                         max(0, -zoff):min(res, res - zoff)]
    return shifted_matrix.flatten().tobytes()