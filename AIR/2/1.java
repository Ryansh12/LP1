
public class alphaBeta {

	display d;
    
    alphaBeta(int[] scores, int height){
    	d=new display();
    	d.add_elements(scores,height);
    }

    public alphaBeta() {
		// TODO Auto-generated constructor stub
	}

	public int minMax(int depth,int nodeNo,boolean isMax,int scores[],int h,int alpha,int beta)
    {
        if(depth == h)	{
            System.out.println("currently evaluating node = "+scores[nodeNo]);
            d.setColor(nodeNo);
            return scores[nodeNo];
        
        }	else if(isMax)	{
            int eval = Integer.MIN_VALUE;
            for(int i=0;i<2;i++)	{
                int val = minMax(depth+1,nodeNo*2+i,false,scores,h,alpha,beta);
                eval = Math.max(val,eval);
                alpha = Math.max(alpha,eval);

                if(beta<=alpha)
                    break;
            }
            d.setText(eval,depth,nodeNo*2,nodeNo*2+1);
            return eval;
        
        }	else	{
            int eval = Integer.MAX_VALUE;
            for(int i=0;i<2;i++)	{
                int val = minMax(depth+1,nodeNo*2+i,true,scores,h,alpha,beta);
                eval = Math.min(eval,val);
                beta = Math.min(beta,eval);

                if(beta<=alpha)
                    break;
            }
            d.setText(eval,depth,nodeNo*2,nodeNo*2+1);
            return eval;
        }
    }

    public static void main(String[] args) {

        System.out.println("For alpha beta");
        int scores[]={3, 4,2,1,7,8,9,10,2,11,1,12,14,9,13,16};
        
        int height = (int)Math.ceil(Math.log(scores.length + 1) / Math.log(2)) - 1 ;
        
        alphaBeta ab = new alphaBeta(scores,height);
        int result = ab.minMax(0,0,true,scores,height,Integer.MIN_VALUE,Integer.MAX_VALUE);
        System.out.println("optimal value is = "+result);
        }
    }
