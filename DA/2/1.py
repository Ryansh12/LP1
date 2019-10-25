#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix


# In[3]:


data = pd.read_csv("Pima.csv")
columns = data.columns
columns = [col_name for col_name in columns if not col_name=="Outcome"]


# In[4]:


data.head()


# In[5]:


y = data["Outcome"]
X = data.drop(["Outcome"], axis=1)


# In[6]:


train_X, test_X, train_y, test_y = train_test_split(X, y, test_size=0.3)


# In[7]:


#generate summary
train_mean_pos = train_X[train_y==1].mean()
train_std_pos = train_X[train_y==1].std()
train_mean_neg = train_X[train_y==0].mean()
train_std_neg = train_X[train_y==0].std()


# In[8]:


train_mean_pos


# In[9]:


train_std_pos


# In[10]:


train_mean_neg


# In[11]:


train_std_neg


# In[12]:


summary = { "train_mean_pos": train_mean_pos.tolist() , "train_std_pos": train_std_pos.tolist(), 
           "train_mean_neg": train_mean_neg.tolist(), "train_std_neg": train_std_neg.tolist()}


# In[13]:


summary


# In[14]:


import numpy as np
def cond_prob(x, mn, stddv):  #value , mean, standard dev| this is that fancy formula: 1/root(sigma^2)....
    varnc = stddv*stddv
    p = 1/(np.sqrt(2*np.pi*varnc)) * np.exp((-(x-mn)**2)/(2*varnc))
    return p


# In[15]:


def predict(row, summary):

  #prior probability obtained as probability of class
  #i.e. we find fraction of positive samples present in the whole dataset
  prob_positive=len(summary["train_mean_pos"])/( len(summary["train_mean_pos"])+len(summary["train_mean_neg"]) )
  #then multiply it with conditional probability of each feature
  for i in range(0, len(row)):
    prob_positive = prob_positive * cond_prob(row[i],summary["train_mean_pos"][i], summary["train_std_pos"][i])
  

  #exact same process for negative
  prob_negative=len(summary["train_mean_neg"])/( len(summary["train_mean_pos"])+len(summary["train_mean_neg"]) )
  for i in range(0, len(row)):
    prob_negative = prob_negative * cond_prob(row[i],summary["train_mean_neg"][i], summary["train_std_neg"][i])
  
  return [prob_positive, prob_negative]


# In[16]:


predictions_raw = []
for row in test_X.values.tolist():
  predictions_raw.append(predict(row, summary))


# In[17]:


predictions_raw


# In[18]:


predictions = []
for row in predictions_raw:
  if(row[0]>row[1]):
    predictions.append(1)
  else:
    predictions.append(0)


# In[19]:


confusion_matrix(test_y.tolist(), predictions)


# In[ ]:




