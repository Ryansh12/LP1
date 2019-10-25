

import javax.swing.*;
import java.awt.*;

public class display {
    JFrame jf;
    int height;
    JTextField[] array;

    display()
    {
        jf=new JFrame("minmax");
        jf.setVisible(true);                //It sets the currently made attributes to be visible on the screen in GUI
        jf.setBounds(0,0,800,1000);         //setBounds is used to define the bounding rectangle of a component.
        //jf.setBackground(new Color(127,127,127));
        //jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //jf.setLayout(null);
    }

    void add_elements(int[] scores,int height)
    {
        this.height=height;
        int total_nodes = 2*scores.length-1;
        array = new JTextField[total_nodes];

        int rows_of_Frame = 800/height;

        int[] complete_tree = new int[total_nodes];

        int start = scores.length-1;
        int j=0;

        while (start<total_nodes) {
            complete_tree[start] = scores[j];
            start++;
            j++;
        }

        start = 0;
        while (start<scores.length-1)
        {
            complete_tree[start]=-9;
            start++;
        }


        /*System.out.println("complete tree array = ");
        for(int y=0;y<total_nodes;y++)
            System.out.print(complete_tree[y]+"\t");*/


        int temp = height;

        int box_width = 25;
        int box_height = 25;

      //  Font f = new Font("Serif",Font.PLAIN, 24);

        while (temp>=0)
        {
            int columnms_of_Frame =  (800/(int)Math.pow(2,temp));
            int no_of_elemnts = (int)Math.pow(2,temp);

            //System.out.println("no of elements = "+no_of_elemnts);

            int offset = columnms_of_Frame-box_width;
            offset=offset/2;

            int k=1;

            int y = rows_of_Frame*(temp)+box_height;
            int x = offset;

            //System.out.println("at start x = "+x+" and y = "+y);

            int s = no_of_elemnts-1;

            for( j=s;k<=no_of_elemnts;k++,j++)
            {
               // System.out.println("element @ j = "+complete_tree[j]);
                array[j]=new JTextField();
                array[j].setText("  "+String.valueOf(complete_tree[j]));
                array[j].setBounds(x,y,box_width,box_height);
                array[j].setBackground(new Color(150,0,150));
                //array[j].setFont(f);
                jf.add(array[j]);
                jf.repaint();

                x=x+box_width+(2*offset);

            }
            temp-=1;
        }
    }


    void setColor(int nodeNo)
    {
        int offset = (int)Math.pow(2,height)-1;
        array[offset+nodeNo].setBackground(new Color(120,255,120));
    }


    void setText(int ans,int depth,int node1,int node2)
    {

        int nodes = (int) Math.pow(2, depth);
        int index = nodes-1+(node1/2);
        array[index].setText("  " + ans);
        array[index].setBackground(new Color(150, 150, 150));
    }

    void setRoot(int ans)
    {
        array[0].setText("  "+ans);
        array[0].setBackground(new Color(150, 150, 150));

    }

}

