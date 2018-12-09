#!/usr/bin/env python
# coding: utf-8

# Cummulative Density Function (cdf) of exponential distributions
# 
# Note that the  Cummulative Density Function of exponential distribution with parameter $\lambda$, $\lambda > 0$ looks like this
# 
# $\begin{equation}
#     F(x;\lambda)=
#     \begin{cases}
#        1 -  e^{-\lambda x}, & \text{if}\ x\geq 0 \\
#       0, & \text{otherwise}
#     \end{cases}
#   \end{equation}$
#   
# 
# 
# 
# Given $\lambda$, and an array $arr$; your function can return an array of values $F(x;\lambda)$ for each $x\in arr$. 
# 
# eg: F([-1,1],0.5) should return [0   0.69673467014368329] of type numpy.ndarray
# 
# For fixed arr = np.arange(50)/50:
# 
# For $\lambda = 0.5~,~1,~1.5$ , save the values $F(arr;\lambda)$ to a new variable $yi$, and print out their mean.

# In[1]:


def exp_cdf(arr,lam):
    
    return np.array([])


# In[1]:


arr = np.arange(50)/50

lambda1 = 0.5
y1 = exp_cdf(arr,lambda1)

lambda2 = 1
y2 = exp_cdf(arr,lambda2)

lambda3 = 1.5
y3 = exp_cdf(arr,lambda3)

print()


# In[ ]:




