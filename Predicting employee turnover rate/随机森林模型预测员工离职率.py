#!/usr/bin/env python
# coding: utf-8

# In[147]:


import pandas as pd
hr = pd.read_csv('turnover.csv')
col_names = hr.columns.tolist()
print("Column names:")
print(col_names)
print("Sample data:")
hr.head()


# In[148]:


hr=hr.rename(columns = {'sales':'department'})


# In[149]:


hr.dtypes


# In[150]:


hr.isnull().any()


# In[151]:


hr.shape


# In[152]:


hr['department'].unique()
import numpy as np
hr['department']=np.where(hr['department'] =='support', 'technical', hr['department'])
hr['department']=np.where(hr['department'] =='IT', 'technical', hr['department'])


# In[153]:


hr['left'].value_counts()


# In[154]:


hr.groupby('left').mean()


# In[155]:


hr.groupby('department').mean()


# In[156]:


hr.groupby('salary').mean()


# # 数据可视化：

# # 工作与离职率的条形图

# In[157]:


get_ipython().run_line_magic('matplotlib', 'inline')
import matplotlib.pyplot as plt
pd.crosstab(hr.department,hr.left).plot(kind='bar')
plt.title('Turnover Frequency for Department')
plt.xlabel('Department')
plt.ylabel('Frequency of Turnover')
plt.savefig('department_bar_chart')


# # 薪资与离职率的条形图

# In[158]:


table = pd.crosstab(hr.salary,hr.left)
table.div(table.sum(1).astype(float),axis=0).plot(kind = 'bar',stacked = True)
plt.title('Stacked Bar Chart of Salary Level vs Turnover')
plt.xlabel('Salary Level')
plt.ylabel('Proportion of Employees')
plt.savefig('salary_bar_chart')


# # 变量直方图

# In[159]:


num_bins = 10
hr.hist(bins= num_bins,figsize=(20,15))
plt.savefig("hr_histogram_plots")
plt.show()


# In[ ]:




