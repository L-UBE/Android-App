from scipy import ndimage
import numpy as np

def rotate(model, axis, angle):
    if(axis == "x"):
        axes = (1,2)
    elif(axis == "y"):
        axes = (0,2)
    elif(axis == "z"):
        axes = (0,1)
    else:
        axes = (0,1)
    return ndimage.rotate(np.array(model).astype(bool).reshape(16,16,16), angle, mode='constant', axes=axes, order = 0, reshape=False).tobytes()
