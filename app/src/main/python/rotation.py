from scipy import ndimage
import numpy as np

def rotate(model, angle):
    return ndimage.rotate(np.array(model).reshape((8,8,8)), angle, mode='constant', axes=(0,1), order = 0, reshape=False).tobytes()