import numpy as np
import raster_geometry as rg

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

def generateShape(shape, size):
    if(shape == "cube"):
        return rg.cube(16, size).flatten().astype('byte')
    elif(shape == "sphere"):
        return rg.sphere(16, size/2).flatten().astype('byte')
    elif(shape == "cylinder"):
        return rg.cylinder(16, size, size/2).flatten().astype('byte')
    elif (shape == "rhomboid"):
        return rg.rhomboid(16, size).flatten().astype('byte')