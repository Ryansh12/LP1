#include<iostream>
#include<time.h>
#include<cstdio>
#include<math.h>

#define N  2048
#define V  2048  //vector size
#define THREADS_PER_BLOCK 256
using namespace std;

__global__ void multiplication(int *a, int*b, int *c,int width){
   int row = blockIdx.y*width+threadIdx.y;
    int col = blockIdx.x*width+threadIdx.x;
    if(row<width && col <width) {
        float product_val = 0
        for(int k=0;k<width;k++) {
            product_val += d_M[row*width+k]*d_N[k*width+col];
        }
        d_p[row*width+col] = product_val;
    }
}


int main() {
    int *a, *b, *c;
    int *d_a, *d_b, *d_c;
    int Size = N * sizeof(int);
    int vectorSize = V *sizeof(int);

    a=(int *)malloc(vectorSize);
    b=(int *)malloc(size*vectorSize);
    c=(int *)malloc(vectorSize);

    srand(time(0));

	//initialize host vector by random elements
	for(int i=0;i<vectorSize;i++) {
		a[i] = rand();
    }
    
    //initialize matrix by random elements
	for(int i=0;i<N;i++) {
		for(int j=0;j<vectorSize;j++) {
            b[i*vectorSize+j] = rand();
            //In row-major layout, element(x,y) can be addressed as: x*width + y. 
            //Suppose the width of the matrix is 4. Then element (1,1) will be found at position −
            //1*4 + 1 = 5 in the 1D array.
		}
	}

    cudaMalloc((void**)&d_a,vectorSize);
    cudaMalloc((void**)&d_b, N*vectorSize);
    cudaMalloc((void**)&d_c,vectorSize);

    cudaMemcpy(d_a,a,vectorSize,cudaMemcpyHostToDevice);
    cudaMemcpy(d_b,b,N*vectorSize,cudaMemcpyHostToDevice);

    multiplication<<<N,1>>>(d_a,d_b,d_c,N,vectorSize);

    cudaDeviceSynchronize();

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