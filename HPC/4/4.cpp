#include <iostream> 
#include <omp.h>
using namespace std; 

struct Node 
{ 
	int data; 
	struct Node* left, *right; 
	Node(int data) 
	{ 
		this->data = data; 
		left = right = NULL; 
	} 
}; 

void bfs(struct Node* node,int key) 
{ 
	if (node == NULL) 
		return; 
	if(node->data==key)
	cout<<"FOUND";
	
	#pragma omp parallel sections
	{
	#pragma omp section
	{bfs(node->left,key); 
	}
	#pragma omp section
	{bfs(node->right,key); 
	}
	}
} 
int main() 
{ 
	struct Node *root = new Node(1); 
	root->left = new Node(2); 
	root->right = new Node(3); 
	root->left->left= new Node(4); 
	root->left->right = new Node(5); 

	bfs(root,4); 

	return 0; 
} 

