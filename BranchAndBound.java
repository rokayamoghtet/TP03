package PROJECT;

import java.util.LinkedList;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Node;
import com.sun.tools.javac.jvm.Items;

public class BranchAndBound {
	
		static int branchAndBound ( LinkedList<Items>items, int W) {
			int n = items.size();
			int [] p= new int[n];
			int [] w= new int[n];
			for (int i=0; i<n;i++){
			items.get(i);
			p [i]= (int)Items.value;
			items.get(i);
			w [i]= (int)Items.weight;
		}
			Node u = new Node();
			Node v = new Node(); //tree root
			int maxProfit=0;
			LinkedList <Node> Q = new LinkedList<Node>();
			v.level=-1;
			v.profit=0;
			v.weight=0; //v initialized to -1, dummy root
			Q.offer(v); //place the dummy at the root
			while(!Q.isEmpty()){
			   v = Q.poll();
			   if (v.level==-1){
			       u.level=0;
			   }
			   else if(v.level != (n - 1)){
			       u.level = v.level+1; //set u to be a child of v
			   }
			   u = new Node();
			   u.weight = v.weight + w[u.level];//set u to the child
			   u.profit = v.profit + p[u.level]; //that includes the
			   //next item
			   double bound = bound(u, W, n, w, p);
			   u.bound=bound;
			   if(u.weight<=W && u.profit>maxProfit){
			        maxProfit = u.profit;
			   }
			   if(bound>maxProfit){
			        Q.add(u);
			   }
		       u = new Node();
			   u.weight = v.weight; //set u to the child that
			   u.profit = v.profit;//does NOT include the next item
			   bound = bound(u, W, n, w, p);
			   u.bound = bound;
			   if (bound>maxProfit){
			        Q.add(u);
			   }
			}
			return maxProfit;
		}
		public static float bound(Node u, int W, int n, int [] w, int [] p){
			int j=0;
			int k=0;
			int totWeight=0;
			float result=0;
			if(u.weight>=W)
			   return 0;
			else {
			    result = u.profit;
			    j= u.level +1;
			    totWeight = u.weight;
		    	while ((j < n) && (totWeight + w[j]<=W)){
			       totWeight = totWeight + w[j]; //grab as many items as possible
			       result = result + p[j];
			       j++;
			    }
			    k=j; //use k for consistency with formula in text
			    if (k<n)
			       result = result + (W-totWeight) * p[k]/w[k];//grab fraction of kth item
			       return result;
			 }
	    }
		static void main(String[] args) {
			// TODO Auto-generated method stub
			
			
		}
	}

