#include<iostream>
#include<cstdio>
#include<math.h>

#define N (2048 * 2048)
#define THREADS_PER_BLOCK 256
using namespace std;

__global__ void add(int *a, int*b, int *c,int n){
    int index = threadIdx.x + blockIdx.x*blockDim.x;
    if(index<n){
        c[index] = a[index] + b[index];
    }
}


int main() {
    int *a, *b, *c;
    int *d_a, *d_b, *d_c;
    int size = N * sizeof(int);

    a=(int *)malloc(size);
    b=(int *)malloc(size);
    c=(int *)malloc(size);

    
	int i;
	for(i=0;i<N;i++) {
		a[i] = rand()%70;
		b[i] = rand()%70;
	}

    cudaMalloc(&d_a,size);
    cudaMalloc(&d_b,size);
    cudaMalloc(&d_c,size);

    cudaMemcpy(d_a,a,size,cudaMemcpyHostToDevice);
    cudaMemcpy(d_b,b,size,cudaMemcpyHostToDevice);

    add<<<(N + THREADS_PER_BLOCK-1)/THREADS_PER_BLOCK,THREADS_PER_BLOCK>>>(d_a,d_b,d_c,N);

    cudaMemcpy(c,d_c,size,cudaMemcpyDeviceToHost);

    double error = 0;
	for(i = 0;i<N;i++) {
		double diff = double((a[i]+b[i])-c[i]);
		error+=diff;
    if(diff>0){
      printf("A+B=%d",a[i]+b[i]);
		  printf("C = %d",c[i]);
    }
       
	}

	error = sqrt(error);
    cout<<"error  = "<<error<<endl;
    
    free(a); free(b); free(c);
    cudaFree(d_a);  cudaFree(d_b); cudaFree(d_c);

    return 0;
}